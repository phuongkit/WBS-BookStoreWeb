package ecom.bookstore.wbsbackend.services.impls;

import ecom.bookstore.wbsbackend.dto.request.ReviewCreationDTO;
import ecom.bookstore.wbsbackend.dto.request.ReviewUpdationDTO;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.dto.response.ReviewResponseDTO;
import ecom.bookstore.wbsbackend.entities.*;
import ecom.bookstore.wbsbackend.exceptions.ResourceAlreadyExistsException;
import ecom.bookstore.wbsbackend.exceptions.ResourceNotFoundException;
import ecom.bookstore.wbsbackend.mapper.ReviewMapper;
import ecom.bookstore.wbsbackend.models.clazzs.Rating;
import ecom.bookstore.wbsbackend.models.enums.EImageType;
import ecom.bookstore.wbsbackend.repositories.BookRepo;
import ecom.bookstore.wbsbackend.repositories.CommentRepo;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  @Autowired public void CommentRepo(CommentRepo commentRepo) {
    this.commentRepo = commentRepo;
  }
  private ReviewMapper reviewMapper;

  @Autowired public void ReviewMapper(ReviewMapper reviewMapper) {
    this.reviewMapper = reviewMapper;
  }

  private ReviewRepo reviewRepo;

  @Autowired public void ReviewRepo(ReviewRepo reviewRepo) {
    this.reviewRepo = reviewRepo;
  }

  private ImageService imageService;

  @Autowired public void ImageService(ImageService imageService) {
    this.imageService = imageService;
  }

  private BookRepo bookRepo;

  @Autowired public void BookRepo(BookRepo bookRepo) {
    this.bookRepo = bookRepo;
  }

  private UserRepo userRepo;

  @Autowired public void UserRepo(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  @Override public Page<ReviewResponseDTO> getAllReviewsByBook(Long bookId, boolean isHasChild,
                                                                   Pageable pageable) {
    Book BookFound = this.bookRepo.findById(bookId).orElseThrow(() -> new ResourceNotFoundException(
        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, Book.class.getSimpleName(), "ID", bookId)));
    Page<Review> Reviews = this.reviewRepo.getAllMainReviewByBookOrUser(BookFound, null, pageable);
    if (Reviews.getContent().size() < 1) {
      throw new ResourceNotFoundException(
          String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, Review.class.getSimpleName(), Book.class.getSimpleName(),
              bookId));
    }
    return Reviews.map(Review -> this.reviewMapper.ReviewToReviewResponseDTO(Review, isHasChild));
  }

  @Override public Page<ReviewResponseDTO> getAllReviewsByUser(Integer userId, boolean isHasChild, Pageable pageable) {
    User userFound = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, User.class.getSimpleName(), "ID", userId)));
    Page<Review> Reviews = this.reviewRepo.getAllMainReviewByBookOrUser(null, userFound, pageable);
    if (Reviews.getContent().size() < 1) {
      throw new ResourceNotFoundException(
          String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, Review.class.getSimpleName(), Book.class.getSimpleName(),
              userId));
    }
    return Reviews.map(Review -> this.reviewMapper.ReviewToReviewResponseDTO(Review, isHasChild));
  }

  @Override public ReviewResponseDTO getReviewById(Long id, boolean isHasChild) {
    Review ReviewFound = this.reviewRepo.findById(id).orElseThrow(() -> new ResourceAlreadyExistsException(
        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, Review.class.getSimpleName(), "ID", id)));
    return this.reviewMapper.ReviewToReviewResponseDTO(ReviewFound, isHasChild);
  }

  @Override public ReviewResponseDTO getReviewByBookAndUser(Long bookId, Integer userId, boolean isHasChild) {
    Book BookFound = this.bookRepo.findById(bookId).orElseThrow(() -> new ResourceNotFoundException(
        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, Book.class.getSimpleName(), "ID", bookId)));
    User userFound = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, User.class.getSimpleName(), "ID", userId)));
    List<Review> Reviews = this.reviewRepo.getReviewByBookAndUser(BookFound, userFound);
    if (Reviews.size() < 1) {
      throw new ResourceNotFoundException(
          String.format(Utils.OBJECT_NOT_FOUND_BY_TWO_FIELD, Review.class.getSimpleName(),
              Book.class.getSimpleName(), bookId, User.class.getSimpleName(), userId));
    }
    return this.reviewMapper.ReviewToReviewResponseDTO(Reviews.get(0), isHasChild);
  }

  @Override public Rating getRatingByBook(Long BookId) {
    Book bookFound = this.bookRepo.findById(BookId).orElse(null);
    if (bookFound != null) {
      List<Review> ReviewList = this.reviewRepo.findAllByBook(bookFound);
      if (!ReviewList.isEmpty()) {
        double star = 0;
        long[] totalVotes = new long[]{0, 0, 0, 0, 0, 0};
        for (Review Review : ReviewList) {
          if (Review.getStar() > 0 && Review.getStar() < 6) {
            star += Review.getStar();
            totalVotes[0]++;
            totalVotes[Review.getStar()]++;
          }
        }

        return new Rating(BookId, star / totalVotes[0], totalVotes);
      } else {
        return new Rating(BookId, 0, null);
      }
    }
    return null;
  }

  @Override public ResponseEntity<ResponseObject> createReview(String loginKey, ReviewCreationDTO creationDTO,
                                                               MultipartFile[] imageGalleryFile) {
    this.LOGGER.info(String.format(Utils.LOG_CREATE_OBJECT, Review.class.getSimpleName(),
        Book.class.getSimpleName(), creationDTO.getBookId(), "Content", creationDTO.getContent()));
    Book BookFound =
        this.bookRepo
            .findById(creationDTO.getBookId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            Book.class.getSimpleName(),
                            "ID",
                            creationDTO.getBookId())));
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
      mainReviewFound = this.reviewRepo.findById(creationDTO.getReplyForReviewId()).orElseThrow(() ->
          new ResourceNotFoundException(
              String.format(
                  Utils.OBJECT_NOT_FOUND_BY_FIELD,
                  "Main" + Review.class.getSimpleName(),
                  "ID",
                  creationDTO.getReplyForReviewId())));
    }
    if (mainReviewFound == null) {
      List<Review> Reviews = this.reviewRepo.getReviewByBookAndUser(BookFound, authorFound);
      if (Reviews.size() > 0) {
        throw new ResourceAlreadyExistsException(
            String.format(Utils.OBJECT_EXISTED_BY_TWO_FIELD, Review.class.getSimpleName(),
                Book.class.getSimpleName(), creationDTO.getBookId(), User.class.getSimpleName(),
                authorFound.getUsername()));
      }
      // Create main Review
      Review Review = new Review();
      Review.setContent(creationDTO.getContent());
      Review.setStar(creationDTO.getStar());
      Review.setBook(BookFound);
      Review.setAuthor(authorFound);

      // Set image
      if (imageGalleryFile != null && imageGalleryFile.length > 0) {
        Set<Image> imageGallery = new HashSet<>();
        for (MultipartFile multipartFile : imageGalleryFile) {
          if (!multipartFile.isEmpty()) {
            Image image = this.imageService.createImageByMultipartFile(multipartFile, EImageType.IMAGE_REVIEW);
            imageGallery.add(image);
          }
        }
        Review.setImageGallery(imageGallery);
      }
      return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(HttpStatus.CREATED,
          String.format(Utils.CREATE_OBJECT_SUCCESSFULLY, Review.class.getSimpleName()),
          this.reviewMapper.ReviewToReviewResponseDTO(this.reviewRepo.save(Review))));
    } else {
      // Create child Review
      Comment comment = new Comment();
      comment.setContent(creationDTO.getContent());
      comment.setBook(BookFound);
      comment.setAuthor(authorFound);
      comment.setRelyForUser(mainReviewFound.getAuthor());
      comment.setMainReview(mainReviewFound);
      return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(HttpStatus.CREATED,
          String.format(Utils.CREATE_OBJECT_SUCCESSFULLY, Review.class.getSimpleName()),
          this.reviewMapper.commentToReplyReviewResponseDTO(this.commentRepo.save(comment))));
    }
  }

  @Override public ResponseEntity<ResponseObject> updateReview(Long id, ReviewUpdationDTO updateDTO,
                                                                     MultipartFile[] imageGalleryFile) {
    this.LOGGER.info(String.format(Utils.LOG_UPDATE_OBJECT, "Main" + Review.class.getSimpleName(), "ID", id));
    Review ReviewFound = this.reviewRepo.findById(id).orElseThrow(() -> new ResourceAlreadyExistsException(
        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, Review.class.getSimpleName(), "ID", id)));

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
          Image image = this.imageService.createImageByMultipartFile(multipartFile, EImageType.IMAGE_BOOK_GALLERY);
          imageGallery.add(image);
        }
      }
      ReviewFound.setImageGallery(imageGallery);
    }

    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK,
        String.format(Utils.UPDATE_MAIN_OBJECT_SUCCESSFULLY, Review.class.getSimpleName()),
        this.reviewMapper.ReviewToReviewResponseDTO(this.reviewRepo.save(ReviewFound))));
  }

  @Override public ResponseEntity<ResponseObject> deleteReviewById(Long id) {
    this.LOGGER.info(String.format(Utils.LOG_DELETE_OBJECT, Review.class.getSimpleName(), "ID", id));
    this.reviewRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(
        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, User.class.getSimpleName(), "ID", id)));

    // delete Review
    this.reviewRepo.deleteById(id);
    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK,
        String.format(Utils.DELETE_OBJECT_SUCCESSFULLY, Review.class.getSimpleName()), null));
  }
}
