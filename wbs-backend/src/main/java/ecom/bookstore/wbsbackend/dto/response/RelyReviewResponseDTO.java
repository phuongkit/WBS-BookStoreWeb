package ecom.bookstore.wbsbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author minh phuong
 * @created 13/09/2022 - 6:07 PM
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RelyReviewResponseDTO {
  private Long replyForFeedbackId;
  private Long id;
  private UserSimpleResponseDTO creator;
  private String content;
  private UserSimpleResponseDTO relyForUser;
  private String[] imageGallery;
  private String timeDistance;
  private boolean isUpdated;
}
