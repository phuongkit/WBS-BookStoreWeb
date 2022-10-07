package ecom.bookstore.wbsbackend.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author minh phuong
 * @created 12/09/2022 - 4:58 PM
 * @project gt-backend
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_comment")
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "content", length = 500, nullable = false)
  @NotNull(message = "An content is required!")
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Book book;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id", nullable = false)
  private User author;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rely_for_user_id")
  private User relyForUser;

  @ManyToOne
  @JoinColumn(name = "main_Review_id")
  private Review mainReview;


  @Column(name = "created_at")
  @CreationTimestamp
  private Date createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Date updatedAt;

//  public Comment(Product product, User author, String content, User relyForUser, Comment mainComment,
//                 Review mainReview) {
//    this.content = content;
//    this.product = product;
//    this.author = author;
//    this.mainComment = mainComment;
//    this.mainReview = mainReview;
//    if (mainReview == null) {
//      this.commentType = ECommentType.COMMENT;
//      if (mainComment != null) {
//        this.relyForUser = mainComment.getAuthor();
//      }
//    } else {
//      this.relyForUser = mainComment.getAuthor();
//      this.commentType = ECommentType.Review;
//    }
//  }
//

  public void setChildReview(Review mainReview, User author, String content) {
    this.content = content;
    this.book = mainReview.getBook();
    this.author = author;
    this.mainReview = mainReview;
    this.relyForUser = mainReview.getAuthor();
  }
}
