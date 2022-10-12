package ecom.bookstore.wbsbackend.mapper.impls;

import ecom.bookstore.wbsbackend.dto.response.ReplyReviewResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.ReviewResponseDTO;
import ecom.bookstore.wbsbackend.entities.Comment;
import ecom.bookstore.wbsbackend.entities.Image;
import ecom.bookstore.wbsbackend.entities.Review;
import ecom.bookstore.wbsbackend.mapper.ReviewMapper;
import ecom.bookstore.wbsbackend.mapper.UserMapper;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author minh phuong
 * @created 12/10/2022 - 3:51 PM
 */
@Component
public class ReviewMapperImpl implements ReviewMapper {
  private UserMapper userMapper;
  @Autowired public void UserMapper(UserMapper userMapper) {
    this.userMapper = userMapper;
  }
  @Override public ReviewResponseDTO ReviewToReviewResponseDTO(Review entity, boolean... isFull) {
    if (entity == null) {
      return null;
    }
    ReviewResponseDTO responseDTO = new ReviewResponseDTO();
    responseDTO.setId(entity.getId());
    responseDTO.setContent(entity.getContent());
    responseDTO.setStar(entity.getStar());
    if (entity.getBook() != null) {
      responseDTO.setBookId(entity.getBook().getId());
    }
    if (entity.getAuthor() != null) {
      responseDTO.setCreator(this.userMapper.userToUserSimpleResponseDTO(entity.getAuthor()));
    }

    if (entity.getImageGallery() != null && entity.getImageGallery().size() > 0) {
      String[] gallery = new String[entity.getImageGallery().size()];
      int i = 0;
      for (Image image : entity.getImageGallery()) {
        gallery[i] = image.getPath();
        i++;
      }
      responseDTO.setImageGallery(gallery);
    }

    if (isFull.length > 0 && isFull[0]) {
      if (entity.getReplyReviews() != null && entity.getReplyReviews().size() > 0) {
        ReplyReviewResponseDTO[] ReplyReviews = new ReplyReviewResponseDTO[entity.getReplyReviews().size()];
        int i = 0;
        for (Comment replyReview : entity.getReplyReviews()) {
          ReplyReviews[i] = this.commentToReplyReviewResponseDTO(replyReview);
          i++;
        }
        responseDTO.setReplyReviews(ReplyReviews);
      }
    }

    Utils.TimeDistance timeDistance = Utils.getTimeDistance(entity.getCreatedAt(), entity.getUpdatedAt());
    responseDTO.setTimeDistance(timeDistance.getTimeDistance());
    responseDTO.setUpdated(timeDistance.isUpdated());
    return responseDTO;
  }

  @Override public ReplyReviewResponseDTO commentToReplyReviewResponseDTO(Comment entity) {
    if (entity == null) {
      return null;
    }
    ReplyReviewResponseDTO responseDTO = new ReplyReviewResponseDTO();
    if (entity.getMainReview() != null) {
      responseDTO.setReplyForReviewId(entity.getMainReview().getId());
    }
    responseDTO.setId(entity.getId());
    responseDTO.setCreator(this.userMapper.userToUserSimpleResponseDTO(entity.getAuthor()));
    responseDTO.setContent(entity.getContent());
    Utils.TimeDistance timeDistance = Utils.getTimeDistance(entity.getCreatedAt(), entity.getUpdatedAt());
    responseDTO.setTimeDistance(timeDistance.getTimeDistance());
    responseDTO.setUpdated(timeDistance.isUpdated());
    return responseDTO;
  }
}
