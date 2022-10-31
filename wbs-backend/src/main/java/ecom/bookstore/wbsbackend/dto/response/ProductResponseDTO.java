package ecom.bookstore.wbsbackend.dto.response;

import ecom.bookstore.wbsbackend.models.clazzs.RatingDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author minh phuong
 * @created 11/09/2022 - 10:15 AM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
  private Long id;
  private String img;
  private String name;
  private BigDecimal originPrice;
  private BigDecimal salePrice;
  private double sale;
  private Integer quantity;
  private String slug;
  private double star;
  private long totalVote;
  private String categoryName;
  private String categorySlug;
  private String[] authors;
  private String location;
  private String[] gallery;
  private String description;
  private RatingDetail[] vote;
}
