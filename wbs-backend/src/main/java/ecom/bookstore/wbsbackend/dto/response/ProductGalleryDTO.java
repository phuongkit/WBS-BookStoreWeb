package ecom.bookstore.wbsbackend.dto.response;

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
public class ProductGalleryDTO {
  private Long id;
  private String img;
  private String name;
  private String slug;
  private Integer availableQuantity;
  private Integer soldQuantity;
  private BigDecimal originPrice;
  private BigDecimal salePrice;
  private double sale;
  private String[] authors;
  private double star;
  private long totalVote;
  private String categoryName;
  private String categorySlug;
}
