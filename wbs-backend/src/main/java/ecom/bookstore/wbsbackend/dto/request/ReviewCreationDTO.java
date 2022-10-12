package ecom.bookstore.wbsbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author minh phuong
 * @created 12/09/2022 - 8:07 PM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreationDTO {
  private Long bookId;
  private Long authorId;
  private String content;
  private int star;
  private Long replyForReviewId;
}
