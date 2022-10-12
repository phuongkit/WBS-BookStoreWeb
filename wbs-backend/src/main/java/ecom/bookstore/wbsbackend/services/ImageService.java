package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.entities.Image;
import ecom.bookstore.wbsbackend.models.enums.EImageType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author minh phuong
 * @created 10/09/2022 - 12:11 PM
 */
public interface ImageService {
  List<Image> getAllImage();

  Image getImageById(Long id);

  ResponseEntity<ResponseObject> createImage(Image Image);

  Image createImageByMultipartFile(MultipartFile multipartFile, EImageType imageType);

  ResponseEntity<ResponseObject> updateImage(Long id, Image Image);

  ResponseEntity<ResponseObject> deleteImageById(Long id);
}
