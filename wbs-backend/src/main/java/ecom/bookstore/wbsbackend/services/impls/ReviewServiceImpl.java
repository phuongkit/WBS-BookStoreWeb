package ecom.bookstore.wbsbackend.services.impls;

import ecom.bookstore.wbsbackend.dto.request.ReviewCreationDTO;
import ecom.bookstore.wbsbackend.dto.request.ReviewUpdationDTO;
import ecom.bookstore.wbsbackend.dto.response.ReviewResponseDTO;
import ecom.bookstore.wbsbackend.entities.*;
import ecom.bookstore.wbsbackend.exceptions.ResourceAlreadyExistsException;
import ecom.bookstore.wbsbackend.exceptions.ResourceNotFound;
import ecom.bookstore.wbsbackend.exceptions.ResourceNotFoundException;
import ecom.bookstore.wbsbackend.mapper.ReviewMapper;
import ecom.bookstore.wbsbackend.models.clazzs.Rating;
import ecom.bookstore.wbsbackend.models.enums.EImageType;
import ecom.bookstore.wbsbackend.repositories.CommentRepo;
import ecom.bookstore.wbsbackend.repositories.ProductRepo;
import ecom.bookstore.wbsbackend.repositories.ReviewRepo;
import ecom.bookstore.wbsbackend.repositories.UserRepo;
import ecom.bookstore.wbsbackend.services.ImageService;
import ecom.bookstore.wbsbackend.services.ReviewService;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author minh phuong
 * @created 12/09/2022 - 8:21 PM
 */
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  private CommentRepo commentRepo;

  @Autowired
  public void CommentRepo(CommentRepo commentRepo) {
    this.commentRepo = commentRepo;
  }

  private ImageService imageService;

  @Autowired
  public void ImageService(ImageService imageService) {
    this.imageService = imageService;
  }

  private ProductRepo productRepo;

  @Autowired
  public void ProductRepo(ProductRepo productRepo) {
    this.productRepo = productRepo;
  }

  private ReviewMapper reviewMapper;

  @Autowired
  public void ReviewMapper(ReviewMapper reviewMapper) {
    this.reviewMapper = reviewMapper;
  }

  private ReviewRepo reviewRepo;

  @Autowired
  public void ReviewRepo(ReviewRepo reviewRepo) {
    this.reviewRepo = reviewRepo;
  }

  private UserRepo userRepo;

  @Autowired
  public void UserRepo(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  @Override
  public Page<ReviewResponseDTO> getAllReviewsByProduct(
      Long ProductId, boolean isHasChild, Pageable pageable) {
    Product productFound =
        this.productRepo
            .findById(ProductId)
            .orElseThrow(
                () ->
                    new ResourceNotFound(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            Product.class.getSimpleName(),
                            "ID",
                            ProductId)));
    Page<Review> Reviews =
        this.reviewRepo.getAllMainReviewByProductOrUser(productFound, null, pageable);
    if (Reviews.getContent().size() < 1) {
      throw new ResourceNotFound(
          String.format(
              Utils.OBJECT_NOT_FOUND_BY_FIELD,
              Review.class.getSimpleName(),
              Product.class.getSimpleName(),
              ProductId));
    }
    return Reviews.map(Review -> this.reviewMapper.ReviewToReviewResponseDTO(Review, isHasChild));
  }

  @Override
  public Page<ReviewResponseDTO> getAllReviewsByUser(
      Integer userId, boolean isHasChild, Pageable pageable) {
    User userFound =
        this.userRepo
            .findById(userId)
            .orElseThrow(
                () ->
                    new ResourceNotFound(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            User.class.getSimpleName(),
                            "ID",
                            userId)));
    Page<Review> Reviews =
        this.reviewRepo.getAllMainReviewByProductOrUser(null, userFound, pageable);
    if (Reviews.getContent().size() < 1) {
      throw new ResourceNotFound(
          String.format(
              Utils.OBJECT_NOT_FOUND_BY_FIELD,
              Review.class.getSimpleName(),
              Product.class.getSimpleName(),
              userId));
    }
    return Reviews.map(Review -> this.reviewMapper.ReviewToReviewResponseDTO(Review, isHasChild));
  }

  @Override
  public ReviewResponseDTO getReviewById(Long id, boolean isHasChild) {
    Review ReviewFound =
        this.reviewRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFound(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            Review.class.getSimpleName(),
                            "ID",
                            id)));
    return this.reviewMapper.ReviewToReviewResponseDTO(ReviewFound, isHasChild);
  }

  @Override
  public ReviewResponseDTO getReviewByProductAndUser(
      Long ProductId, Integer userId, boolean isHasChild) {
    Product productFound =
        this.productRepo
            .findById(ProductId)
            .orElseThrow(
                () ->
                    new ResourceNotFound(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            Product.class.getSimpleName(),
                            "ID",
                            ProductId)));
    User userFound =
        this.userRepo
            .findById(userId)
            .orElseThrow(
                () ->
                    new ResourceNotFound(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            User.class.getSimpleName(),
                            "ID",
                            userId)));
    List<Review> Reviews = this.reviewRepo.getReviewByProductAndUser(productFound, userFound);
    if (Reviews.size() < 1) {
      throw new ResourceNotFound(
          String.format(
              Utils.OBJECT_NOT_FOUND_BY_TWO_FIELD,
              Review.class.getSimpleName(),
              Product.class.getSimpleName(),
              ProductId,
              User.class.getSimpleName(),
              userId));
    }
    return this.reviewMapper.ReviewToReviewResponseDTO(Reviews.get(0), isHasChild);
  }

  @Override
  public Rating getRatingByProduct(Long ProductId) {
    Product productFound = this.productRepo.findById(ProductId).orElse(null);
    if (productFound != null) {
      List<Review> ReviewList = this.reviewRepo.findAllByProduct(productFound);
      if (!ReviewList.isEmpty()) {
        double star = 0;
        long[] totalVotes = new long[] {0, 0, 0, 0, 0, 0};
        for (Review Review : ReviewList) {
          if (Review.getStar() > 0 && Review.getStar() < 6) {
            star += Review.getStar();
            totalVotes[0]++;
            totalVotes[Review.getStar()]++;
          }
        }

        return new Rating(ProductId, star / totalVotes[0], totalVotes);
      } else {
        return new Rating(ProductId, 0, null);
      }
    }
    return null;
  }

  @Override
  public ReviewResponseDTO createReview(
      String loginKey, ReviewCreationDTO creationDTO, MultipartFile[] imageGalleryFile) {
    this.LOGGER.info(
        String.format(
            Utils.LOG_CREATE_OBJECT,
            Review.class.getSimpleName(),
            Product.class.getSimpleName(),
            creationDTO.getProductId(),
            "Content",
            creationDTO.getContent()));
    Product productFound =
        this.productRepo
            .findById(creationDTO.getProductId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            Product.class.getSimpleName(),
                            "ID",
                            creationDTO.getProductId())));
    User authorFound =
        this.userRepo
            .findByEmail(loginKey)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            User.class.getSimpleName(),
                            "ID",
                            creationDTO.getAuthorId())));
    Review mainReviewFound = null;
    if (creationDTO.getReplyForReviewId() != null) {
      mainReviewFound =
          this.reviewRepo
              .findById(creationDTO.getReplyForReviewId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          String.format(
                              Utils.OBJECT_NOT_FOUND_BY_FIELD,
                              "Main" + Review.class.getSimpleName(),
                              "ID",
                              creationDTO.getReplyForReviewId())));
    }
    if (mainReviewFound == null) {
      List<Review> Reviews = this.reviewRepo.getReviewByProductAndUser(productFound, authorFound);
      if (Reviews.size() > 0) {
        throw new ResourceAlreadyExistsException(
            String.format(
                Utils.OBJECT_EXISTED_BY_TWO_FIELD,
                Review.class.getSimpleName(),
                Product.class.getSimpleName(),
                creationDTO.getProductId(),
                User.class.getSimpleName(),
                authorFound.getUsername()));
      }
      // Create main Review
      Review Review = new Review();
      Review.setContent(creationDTO.getContent());
      Review.setStar(creationDTO.getStar());
      Review.setProduct(productFound);
      Review.setAuthor(authorFound);

      // Set image
      if (imageGalleryFile != null && imageGalleryFile.length > 0) {
        Set<Image> imageGallery = new HashSet<>();
        for (MultipartFile multipartFile : imageGalleryFile) {
          if (!multipartFile.isEmpty()) {
            Image image =
                this.imageService.createImageByMultipartFile(
                    multipartFile, EImageType.IMAGE_REVIEW);
            imageGallery.add(image);
          }
        }
        Review.setImageGallery(imageGallery);
      }
      return this.reviewMapper.ReviewToReviewResponseDTO(this.reviewRepo.save(Review));
    } else {
      // Create child Review
      Comment comment = new Comment();
      comment.setContent(creationDTO.getContent());
      comment.setProduct(productFound);
      comment.setAuthor(authorFound);
      comment.setRelyForUser(mainReviewFound.getAuthor());
      comment.setMainReview(mainReviewFound);
      return this.reviewMapper.commentToReplyReviewResponseDTO(
                      this.commentRepo.save(comment));
    }
  }

  @Override
  public ReviewResponseDTO updateReview(
      Long id, ReviewUpdationDTO updateDTO, MultipartFile[] imageGalleryFile) {
    this.LOGGER.info(
        String.format(Utils.LOG_UPDATE_OBJECT, "Main" + Review.class.getSimpleName(), "ID", id));
    Review ReviewFound =
        this.reviewRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceAlreadyExistsException(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            Review.class.getSimpleName(),
                            "ID",
                            id)));

    ReviewFound.setContent(updateDTO.getContent());
    ReviewFound.setStar(updateDTO.getStar());

    // update image gallery
    if (imageGalleryFile != null && imageGalleryFile.length > 0) {
      // delete old image gallery if it exists
      if (ReviewFound.getImageGallery() != null && ReviewFound.getImageGallery().size() > 0) {
        for (Image image : ReviewFound.getImageGallery()) {
          this.imageService.deleteImageById(image.getId());
        }
      }
      // set new image gallery
      Set<Image> imageGallery = new HashSet<>();
      for (MultipartFile multipartFile : imageGalleryFile) {
        if (!multipartFile.isEmpty()) {
          Image image =
              this.imageService.createImageByMultipartFile(
                  multipartFile, EImageType.IMAGE_PRODUCT_GALLERY);
          imageGallery.add(image);
        }
      }
      ReviewFound.setImageGallery(imageGallery);
    }

    return this.reviewMapper.ReviewToReviewResponseDTO(this.reviewRepo.save(ReviewFound));
  }

  @Override
  public ReviewResponseDTO deleteReviewById(Long id) {
    this.LOGGER.info(
        String.format(Utils.LOG_DELETE_OBJECT, Review.class.getSimpleName(), "ID", id));
    this.reviewRepo
        .findById(id)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    String.format(
                        Utils.OBJECT_NOT_FOUND_BY_FIELD, User.class.getSimpleName(), "ID", id)));

    // delete Review
    this.reviewRepo.deleteById(id);
    return null;
  }
}
