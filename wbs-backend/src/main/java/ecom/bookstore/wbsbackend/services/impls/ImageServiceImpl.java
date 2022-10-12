package ecom.bookstore.wbsbackend.services.impls;

import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.entities.Image;
import ecom.bookstore.wbsbackend.exceptions.ResourceAlreadyExistsException;
import ecom.bookstore.wbsbackend.exceptions.ResourceNotFoundException;
import ecom.bookstore.wbsbackend.models.enums.EImageType;
import ecom.bookstore.wbsbackend.repositories.ImageRepo;
import ecom.bookstore.wbsbackend.services.ImageService;
import ecom.bookstore.wbsbackend.services.ImageStorageService;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * @author minh phuong
 * @created 10/09/2022 - 12:13 PM
 */
@Service
@Transactional
public class ImageServiceImpl implements ImageService {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  @Autowired private ImageRepo imageRepo;

  @Autowired private ImageService imageService;

  @Autowired private ImageStorageService storageService;

  @Override
  public List<Image> getAllImage() {
    return null;
  }

  @Override
  public Image getImageById(Long id) {
    this.LOGGER.info(String.format(Utils.LOG_GET_OBJECT, Image.class.getSimpleName(), "ID", id));
    Image imageFound =
        this.imageRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceAlreadyExistsException(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            Image.class.getSimpleName(),
                            "ID",
                            id)));
    return imageFound;
  }

  @Override
  public ResponseEntity<ResponseObject> createImage(Image Image) {
    return null;
  }

  @Override
  public Image createImageByMultipartFile(MultipartFile multipartFile, EImageType imageType) {
    if (multipartFile != null && !multipartFile.isEmpty()) {
      String pathFile = this.storageService.storeFile(multipartFile, imageType);
      Image image = new Image();
      image.setPath(pathFile);
      image.setImageType(imageType);
      Image savedImage = this.imageRepo.save(image);
      return savedImage;
    }
    return null;
  }

  @Override
  public ResponseEntity<ResponseObject> updateImage(Long id, Image Image) {
    return null;
  }

  @Override
  public ResponseEntity<ResponseObject> deleteImageById(Long id) {
    LOGGER.info(String.format(Utils.LOG_DELETE_OBJECT, Image.class.getSimpleName(), "ID", id));
    Optional<Image> imageFound = this.imageRepo.findById(id);
    if (imageFound.isPresent()) {
      // delete Image entity
      this.imageRepo.deleteById(id);

      // delete Image file
      this.storageService.deleteFile(imageFound.get().getPath());
      return ResponseEntity.status(HttpStatus.OK)
          .body(
              new ResponseObject(
                  HttpStatus.OK,
                  String.format(Utils.DELETE_OBJECT_SUCCESSFULLY, Image.class.getSimpleName()),
                  null));
    }
    throw new ResourceNotFoundException(
        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, Image.class.getSimpleName(), "ID", id));
  }
}
