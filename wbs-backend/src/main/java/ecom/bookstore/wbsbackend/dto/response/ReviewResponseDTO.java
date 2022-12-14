package ecom.bookstore.wbsbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author minh phuong
 * @created 12/09/2022 - 8:08 PM
 * @project gt-backend
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDTO {
  private Long id;
  private Long productId;
  private String content;
  private int star;
  private UserSimpleResponseDTO creator;
  private ChildReviewResponseDTO[] childFeedbacks;
  private String[] imageGallery;
  private String timeDistance;
  private boolean isUpdated;
}
