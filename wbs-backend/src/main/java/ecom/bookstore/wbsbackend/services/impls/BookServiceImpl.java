package ecom.bookstore.wbsbackend.services.impls;

import ecom.bookstore.wbsbackend.dto.request.BookCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.BookResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.entities.Book;
import ecom.bookstore.wbsbackend.entities.Category;
import ecom.bookstore.wbsbackend.entities.Image;
import ecom.bookstore.wbsbackend.exceptions.ResourceAlreadyExistsException;
import ecom.bookstore.wbsbackend.exceptions.ResourceNotFoundException;
import ecom.bookstore.wbsbackend.mapper.BookMapper;
import ecom.bookstore.wbsbackend.models.enums.EImageType;
import ecom.bookstore.wbsbackend.repositories.BookRepo;
import ecom.bookstore.wbsbackend.repositories.CategoryRepo;
import ecom.bookstore.wbsbackend.services.BookService;
import ecom.bookstore.wbsbackend.services.ImageService;
import ecom.bookstore.wbsbackend.services.LocationService;
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
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author minh phuong
 * @created 11/10/2022 - 9:47 PM
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  private CategoryRepo categoryRepo;

  @Autowired
  public void CategoryRepository(CategoryRepo categoryRepo) {
    this.categoryRepo = categoryRepo;
  }

  private ImageService imageService;

  @Autowired
  public void ImageService(ImageService imageService) {
    this.imageService = imageService;
  }

  private LocationService locationService;

  @Autowired
  public void LocationService(LocationService locationService) {
    this.locationService = locationService;
  }

  private BookMapper bookMapper;

  @Autowired
  public void BookMapper(BookMapper bookMapper) {
    this.bookMapper = bookMapper;
  }

  private BookRepo bookRepo;

  @Autowired
  public void BookRepository(BookRepo bookRepo) {
    this.bookRepo = bookRepo;
  }

  @Override
  public ResponseEntity<Page<BookResponseDTO>> getAllBooks(String keyword, Pageable pageable) {
    return null;
  }

  @Override
  public ResponseEntity<Page<BookResponseDTO>> getAllBooksByCategory(
      Integer categoryId, Pageable pageable) {
    return null;
  }

  @Override
  public ResponseEntity<ResponseObject> getBookById(Long id) {
    this.LOGGER.info(String.format(Utils.LOG_GET_OBJECT, Book.class.getSimpleName(), "ID", id));
    Book entityFound =
        this.bookRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            Book.class.getSimpleName(),
                            "ID",
                            id)));
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            new ResponseObject(
                HttpStatus.CREATED,
                String.format(Utils.CREATE_OBJECT_SUCCESSFULLY, Book.class.getSimpleName()),
                this.bookMapper.bookToBookResponseDTO(entityFound)));
  }

  @Override
  public ResponseEntity<ResponseObject> getBookBySlug(String slug) {
    this.LOGGER.info(String.format(Utils.LOG_GET_OBJECT, Book.class.getSimpleName(), "Slug", slug));
    Book entityFound =
        this.bookRepo
            .findBySlug(slug)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            Book.class.getSimpleName(),
                            "Slug",
                            slug)));
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            new ResponseObject(
                HttpStatus.CREATED,
                String.format(Utils.CREATE_OBJECT_SUCCESSFULLY, Book.class.getSimpleName()),
                this.bookMapper.bookToBookResponseDTO(entityFound)));
  }

  @Override
  public ResponseEntity<ResponseObject> createBook(
      BookCreationDTO creationDTO, MultipartFile thumbnailFile, MultipartFile[] imageGalleryFile) {
    this.LOGGER.info(
        String.format(
            Utils.LOG_CREATE_OBJECT, Book.class.getSimpleName(), "Name", creationDTO.getName()));
    // check Book name is existed
    Optional<Book> BookFound = this.bookRepo.findByName(creationDTO.getName());
    if (BookFound.isPresent()) {
      throw new ResourceAlreadyExistsException(
          String.format(
              Utils.OBJECT_EXISTED_BY_FIELD,
              Book.class.getSimpleName(),
              "Name",
              creationDTO.getName()));
    }

    // check category input is valid
    Category categoryFound =
        this.categoryRepo
            .findById(creationDTO.getCategoryId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            Category.class.getSimpleName(),
                            "ID",
                            creationDTO.getCategoryId())));

    Book Book = new Book();
    Book.setName(creationDTO.getName());
    Book.setSlug(
        Utils.toSlug(Book.getName()) + "." + UUID.randomUUID().toString().replace("-", ""));
    Book.setDescription(creationDTO.getDescription());
    Book.setStandCost(creationDTO.getStandCost());
    Book.setListPrice(creationDTO.getListPrice());
    Book.setQuantity(creationDTO.getQuantity());
    Book.setStatus(creationDTO.getStatus());

    Book.setCategory(categoryFound);

    // Set thumbnail
    if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
      Image thumbnailImage =
          this.imageService.createImageByMultipartFile(thumbnailFile, EImageType.IMAGE_BOOK);
      Book.setThumbnail(thumbnailImage);
    }

    // Set images
    if (imageGalleryFile != null && imageGalleryFile.length > 0) {
      Set<Image> imageGallery = new HashSet<>();
      for (MultipartFile multipartFile : imageGalleryFile) {
        if (!multipartFile.isEmpty()) {
          Image image =
              this.imageService.createImageByMultipartFile(
                  multipartFile, EImageType.IMAGE_BOOK_GALLERY);
          imageGallery.add(image);
        }
      }
      Book.setImageGallery(imageGallery);
    }

    Book saved = this.bookRepo.save(Book);

    BookResponseDTO responseDTO = this.bookMapper.bookToBookResponseDTO(Book);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            new ResponseObject(
                HttpStatus.CREATED,
                String.format(Utils.CREATE_OBJECT_SUCCESSFULLY, Book.class.getSimpleName()),
                responseDTO));
  }

  @Override
  public ResponseEntity<ResponseObject> updateBook(
      Long id,
      BookCreationDTO creationDTO,
      MultipartFile thumbnailFile,
      MultipartFile[] imageGalleryFile) {
    this.LOGGER.info(String.format(Utils.LOG_UPDATE_OBJECT, Book.class.getSimpleName(), "ID", id));
    // check Book is existed
    Book BookFound =
        this.bookRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceAlreadyExistsException(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            Book.class.getSimpleName(),
                            "ID",
                            id)));

    // check category input is valid
    Category categoryFound =
        this.categoryRepo
            .findById(creationDTO.getCategoryId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            Category.class.getSimpleName(),
                            "ID",
                            creationDTO.getCategoryId())));

    BookFound.setName(creationDTO.getName());
    BookFound.setSlug(
        Utils.toSlug(BookFound.getName()) + "." + UUID.randomUUID().toString().replace("-", ""));
    BookFound.setDescription(creationDTO.getDescription());
    BookFound.setStandCost(creationDTO.getStandCost());
    BookFound.setListPrice(creationDTO.getListPrice());
    BookFound.setQuantity(creationDTO.getQuantity());
    BookFound.setStatus(creationDTO.getStatus());
    BookFound.setCategory(categoryFound);

    // update thumbnail
    if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
      // delete old thumbnail if it exists
      if (BookFound.getThumbnail() != null) {
        this.imageService.deleteImageById(BookFound.getThumbnail().getId());
      }
      // set new thumbnail
      Image thumbnailImage =
          this.imageService.createImageByMultipartFile(thumbnailFile, EImageType.IMAGE_BOOK);
      BookFound.setThumbnail(thumbnailImage);
    }

    // update image gallery
    if (imageGalleryFile != null && imageGalleryFile.length > 0) {
      // delete old image gallery if it exists
      if (BookFound.getImageGallery() != null && BookFound.getImageGallery().size() > 0) {
        for (Image image : BookFound.getImageGallery()) {
          this.imageService.deleteImageById(image.getId());
        }
      }
      // set new image gallery
      Set<Image> imageGallery = new HashSet<>();
      for (MultipartFile multipartFile : imageGalleryFile) {
        if (!multipartFile.isEmpty()) {
          Image image =
              this.imageService.createImageByMultipartFile(
                  multipartFile, EImageType.IMAGE_BOOK_GALLERY);
          imageGallery.add(image);
        }
      }
      BookFound.setImageGallery(imageGallery);
    }

    BookResponseDTO responseDTO =
        this.bookMapper.bookToBookResponseDTO(this.bookRepo.save(BookFound));

    return ResponseEntity.status(HttpStatus.OK)
        .body(
            new ResponseObject(
                HttpStatus.OK,
                String.format(Utils.CREATE_OBJECT_SUCCESSFULLY, Book.class.getSimpleName()),
                responseDTO));
  }

  @Override
  public ResponseEntity<ResponseObject> deleteBookById(Long id) {
    this.LOGGER.info(String.format(Utils.LOG_DELETE_OBJECT, Book.class.getSimpleName(), "ID", id));
    Optional<Book> BookFound = this.bookRepo.findById(id);
    if (BookFound.isPresent()) {
      // delete thumbnail
      Image thumbnail = BookFound.get().getThumbnail();
      if (thumbnail != null) {
        this.imageService.deleteImageById(thumbnail.getId());
      }

      // delete image gallery
      Set<Image> imageGallery = BookFound.get().getImageGallery();
      if (imageGallery != null && imageGallery.size() > 0) {
        for (Image image : imageGallery) {
          this.imageService.deleteImageById(image.getId());
        }
      }

      // delete Book
      this.bookRepo.deleteById(id);
      return ResponseEntity.status(HttpStatus.OK)
          .body(
              new ResponseObject(
                  HttpStatus.OK,
                  String.format(Utils.DELETE_OBJECT_SUCCESSFULLY, Book.class.getSimpleName()),
                  null));
    }
    throw new ResourceNotFoundException(
        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, Book.class.getSimpleName(), "ID", id));
  }
}
