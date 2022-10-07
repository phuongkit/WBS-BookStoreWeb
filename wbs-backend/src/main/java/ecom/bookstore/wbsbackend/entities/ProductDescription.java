package ecom.bookstore.wbsbackend.entities;

import ecom.bookstore.wbsbackend.entities.keys.ProductDescriptionKey;
import lombok.*;

import javax.persistence.*;

/**
 * @author minh phuong
 * @created 11/09/2022 - 8:01 PM
 * @project gt-backend
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_product_description")
public class ProductDescription {
  @EmbeddedId
  private ProductDescriptionKey id;

  @ManyToOne
  @MapsId("productId")
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne
  @MapsId("descriptionId")
  @JoinColumn(name = "description_id")
  private Description description;

  @Column(length = 255, nullable = false)
  private String value;
}
