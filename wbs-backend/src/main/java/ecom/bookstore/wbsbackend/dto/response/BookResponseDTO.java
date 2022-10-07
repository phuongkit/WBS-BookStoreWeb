package ecom.bookstore.wbsbackend.dto.response;

import ecom.bookstore.wbsbackend.models.clazzs.RatingDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author minh phuong
 * @created 11/09/2022 - 10:15 AM
 * @project gt-backend
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {
  private Long id;
  private String img;
  private String title;
  private BigDecimal price;
  private Integer quantity;
  private String url;
  private String slug;
  private String promotion;
  private double discount;
  private String tag;
  private double star;
  private long totalVote;
  private String category;
  private String categorySlug;
  private String location;
  private String[] gallery;
  private String info;
  private RatingDetail[] vote;
}
