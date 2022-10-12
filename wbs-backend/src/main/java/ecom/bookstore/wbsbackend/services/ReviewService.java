package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.dto.request.ReviewCreationDTO;
import ecom.bookstore.wbsbackend.dto.request.ReviewUpdationDTO;
import ecom.bookstore.wbsbackend.dto.response.RelyReviewResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.dto.response.ReviewResponseDTO;
import ecom.bookstore.wbsbackend.models.clazzs.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author minh phuong
 * @created 12/09/2022 - 8:21 PM
 */
public interface ReviewService {
  Page<ReviewResponseDTO> getAllMainReviewsByBook(Long bookId, boolean isHasChild, Pageable pageable);

  Page<ReviewResponseDTO> getAllReviewsByUser(Integer userId, boolean isHasChild, Pageable pageable);

  Page<RelyReviewResponseDTO> getAllRelyReviewsByMainReview(Long mainReviewId, Pageable pageable);

  ReviewResponseDTO getReviewById(Long id, boolean isHasChi);

  ReviewResponseDTO getReviewByBookAndUser(Long bookId, Long userId, boolean isHasChild);

  Rating getRatingByBook(Long BookId);

  ResponseEntity<ResponseObject> createReview(String loginKey, ReviewCreationDTO creationDTO, MultipartFile[] imageGalleryFile);

  ResponseEntity<ResponseObject> updateMainReview(Long id, ReviewUpdationDTO updationDTO, MultipartFile[] imageGalleryFile);

  ResponseEntity<ResponseObject> deleteReviewById(Long id);
}
