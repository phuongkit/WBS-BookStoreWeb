package ecom.bookstore.wbsbackend.dto.response;

import ecom.bookstore.wbsbackend.models.clazzs.RatingDetail;
import ecom.bookstore.wbsbackend.models.enums.ELanguage;
import ecom.bookstore.wbsbackend.models.enums.EProductLayout;
import ecom.bookstore.wbsbackend.models.enums.EProductStatus;
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
  private String slug;
  private BigDecimal originPrice;
  private BigDecimal salePrice;
  private double sale;
  private Integer availableQuantity;
  private Integer soldQuantity;
  private double star;
  private RatingDetail[] vote;
  private long totalVote;
  private String categoryName;
  private String categorySlug;
  private Integer minAge;
  private Integer maxAge;
  private String supplier;
  private String publisher;
  private String[] authors;
  private String[] translators;
  private SeriesResponseDTO series;
  private ELanguage language;
  private Integer publishYear;
  private Integer reprintYear;
  private Integer weight;
  private Double packagingLength;
  private Double packagingWidth;
  private Double packagingHeight;
  private Integer numPages;
  private EProductLayout layout;
  private GenreResponseDTO[] genres;
  private String location;
  private String[] gallery;
  private String description;
  private EProductStatus status;
}
