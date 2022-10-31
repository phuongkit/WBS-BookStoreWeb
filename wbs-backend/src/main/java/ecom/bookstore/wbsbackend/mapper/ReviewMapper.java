package ecom.bookstore.wbsbackend.mapper;

import ecom.bookstore.wbsbackend.dto.response.ReplyReviewResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.ReviewResponseDTO;
import ecom.bookstore.wbsbackend.entities.Comment;
import ecom.bookstore.wbsbackend.entities.Review;

/**
 * @author minh phuong
 * @created 12/09/2022 - 9:07 PM
 */
public interface ReviewMapper {
  ReviewResponseDTO ReviewToReviewResponseDTO(Review entity, boolean... isFull);

  ReviewResponseDTO commentToReplyReviewResponseDTO(Comment entity);
}
