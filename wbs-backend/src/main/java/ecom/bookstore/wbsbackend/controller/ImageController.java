package ecom.bookstore.wbsbackend.controller;

import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.services.ImageService;
import ecom.bookstore.wbsbackend.services.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static ecom.bookstore.wbsbackend.utils.Utils.PRE_API_IMAGE;

/**
 * @author minh phuong
 * @created 11/10/2022 - 9:42 PM
 */
@RestController
@RequestMapping(PRE_API_IMAGE)
@CrossOrigin(origins = "*")
public class ImageController {

  private ImageService imageService;

  @Autowired
  public void ImageService(ImageService imageService) {
    this.imageService = imageService;
  }

  private ImageStorageService imageStorageService;

  @Autowired
  public void ImageStorageService(ImageStorageService imageStorageService) {
    this.imageStorageService = imageStorageService;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseObject> deleteImage(@PathVariable(name = "id") Long id) {
    return this.imageService.deleteImageById(id);
  }

  @GetMapping(value = "/{image_type}/{name}")
  public ResponseEntity<byte[]> getImageFileByPath(
      @PathVariable(name = "image_type") String image_type,
      @PathVariable(name = "name") String name) {
    try {
      String path = image_type + "/" + name;
      byte[] bytes = this.imageStorageService.readFileContent(path);
      return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(bytes);
    } catch (Exception e) {
      return ResponseEntity.noContent().build();
    }
  }

  @GetMapping(value = "/file/{id}")
  public ResponseEntity<byte[]> getImageFileByPath(@PathVariable(name = "id") Long id) {
    try {
      String path = this.imageService.getImageById(id).getPath();
      byte[] bytes = this.imageStorageService.readFileContent(path);
      return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(bytes);
    } catch (Exception e) {
      return ResponseEntity.noContent().build();
    }
  }
}
