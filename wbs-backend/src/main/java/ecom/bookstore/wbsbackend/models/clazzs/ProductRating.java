package ecom.bookstore.wbsbackend.models.clazzs;

import lombok.*;

/**
 * @author minh phuong
 * @created 19/09/2022 - 9:32 AM
 * @project gt-backend
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductRating {
  private Long productId;
  private double star;
  private long totalVote;
  private RatingDetail[] voteDetails;

  public ProductRating(Long productId, double star, long[] totalVotes) {
    this.productId = productId;
    this.star = star;
    voteDetails = new RatingDetail[5];
    if (totalVotes != null) {
      this.totalVote = totalVotes[0];
      for (int i = 1; i < 5; i++) {
        voteDetails[i-1] = new RatingDetail(i, totalVotes[i], (int)(totalVotes[i] / (double)totalVotes[0] * 100));
      }
      voteDetails[4] = new RatingDetail(5, totalVotes[5], 100 - voteDetails[0].getPercent() - voteDetails[1].getPercent() - voteDetails[2].getPercent() -voteDetails[3].getPercent());
    } else {
      this.totalVote = 0;
      for (int i = 1; i < 6; i++) {
        voteDetails[i-1] = new RatingDetail(1, 0, 0);
      }
    }
  }
}
