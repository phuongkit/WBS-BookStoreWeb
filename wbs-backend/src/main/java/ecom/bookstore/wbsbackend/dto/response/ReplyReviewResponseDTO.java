package ecom.bookstore.wbsbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author minh phuong
 * @created 12/10/2022 - 3:52 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyReviewResponseDTO {
  private Long replyForReviewId;
  private Long id;
  private UserSimpleResponseDTO creator;
  private String content;
  private UserSimpleResponseDTO replyForUser;
  private String[] imageGallery;
  private String timeDistance;
  private boolean isUpdated;
}
