//package ecom.bookstore.wbsbackend.entities;
//
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import javax.persistence.*;
//import javax.validation.constraints.DecimalMin;
//import javax.validation.constraints.NotNull;
//import java.math.BigDecimal;
//import java.util.Date;
//
///**
// * @author minh phuong
// * @created 19/09/2022 - 9:27 PM
// * @project gt-backend
// */
//@Getter
//@Setter
//@ToString
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name="tbl_order_item")
//public class OrderItem {
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  private Long id;
//
//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "order_id", nullable = false)
//  private Order order;
//
//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "product_id", nullable = false)
//  private Product product;
//
//  @Column(name = "price", nullable = false)
//  @NotNull(message = "An price is required!")
//  @DecimalMin(value = "0", message = "Quantity must be greater than or equal to 0.")
//  private BigDecimal price;
//
//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "sale_id")
//  private Sale sale;
//
//  @Column(name = "quantity", nullable = false)
//  @NotNull(message = "An quantity is required!")
//  @DecimalMin(value = "0", message = "Quantity must be greater than or equal to 0.")
//  private Integer quantity;
//
//  @Column(name = "note", length = 500)
//  private String note;
//
//  @Column(name = "created_at")
//  @CreationTimestamp
//  private Date createdAt;
//
//  @Column(name = "updated_at")
//  @UpdateTimestamp
//  private Date updatedAt;
//}
