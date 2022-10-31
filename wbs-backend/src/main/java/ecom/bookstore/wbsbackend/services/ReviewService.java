package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.dto.request.ReviewCreationDTO;
import ecom.bookstore.wbsbackend.dto.request.ReviewUpdationDTO;
import ecom.bookstore.wbsbackend.dto.response.ReviewResponseDTO;
import ecom.bookstore.wbsbackend.models.clazzs.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author minh phuong
 * @created 12/09/2022 - 8:21 PM
 */
public interface ReviewService {
  Page<ReviewResponseDTO> getAllReviewsByProduct(Long productId, boolean isHasChild, Pageable pageable);

  Page<ReviewResponseDTO> getAllReviewsByUser(Integer userId, boolean isHasChild, Pageable pageable);
  ReviewResponseDTO getReviewByProductAndUser(Long productId, Integer userId, boolean isHasChild);

  ReviewResponseDTO getReviewById(Long id, boolean isHasChi);

  Rating getRatingByProduct(Long ProductId);

  ReviewResponseDTO createReview(String loginKey, ReviewCreationDTO creationDTO, MultipartFile[] imageGalleryFile);

  ReviewResponseDTO updateReview(Long id, ReviewUpdationDTO updateDTO, MultipartFile[] imageGalleryFile);

  ReviewResponseDTO deleteReviewById(Long id);
}
