package ecom.bookstore.wbsbackend.entities;

import ecom.bookstore.wbsbackend.models.enums.ECartItemStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author minh phuong
 * @created 09/09/2022 - 1:39 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_cart_item")
public class CartItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "product_id")
  private Product product;

  @Column(name = "quantity", nullable = false)
  @NotNull(message = "An quantity is required!")
  @DecimalMin(value = "1", message = "Quantity must be greater 0")
  private Integer quantity;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", length = 50, nullable = false)
  @NotNull(message = "An status is required!")
  private ECartItemStatus status;

  @Column(name = "created_at")
  @CreationTimestamp
  private Date createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Date updatedAt;
}
