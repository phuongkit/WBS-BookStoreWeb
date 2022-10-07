package ecom.bookstore.wbsbackend.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author minh phuong
 * @created 09/09/2022 - 1:38 PM
 * @project gt-backend
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_discount")
public class Discount {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 100, nullable = false)
  @NotNull(message = "An name is required!")
  @Size(message = "Invalid name size.", max = 100, min = 5)
  private String name;

  @Column(name = "description", length = 300)
  @Size(message = "Invalid description size.", max = 300)
  private String description;

  @Column(name = "quantity", nullable = false)
  @NotNull(message = "An quantity is required!")
  @DecimalMin(value = "0", message = "Quantity must be greater than or equal to 0.")
  private Integer quantity;

  @Column(name = "percent")
  @DecimalMin(value = "0.01", message = "Percent must be greater than or equal to 0.01.")
  @DecimalMax(value = "1", message = "Percent must be smaller than or equal to 1.")
  private Double percent;

  @Column(name = "capped_at")
  @DecimalMin(value = "0", message = "Capped At must be greater than or equal to 0.")
  @DecimalMax(value = "100000000", message = "Capped At must be smaller than or equal to 100 000 000.")
  private BigDecimal cappedAt;

  @Column(name = "price")
  @DecimalMin(value = "0", message = "Price must be  than or equal to 0.")
  private BigDecimal price;

  @Column(name = "min_spend", nullable = false)
  @NotNull(message = "An minSpeed is required!")
  @DecimalMin(value = "0", message = "Min Spend must be greater than or equal to 0.")
  private BigDecimal minSpend;

  @Column(name = "code", length = 10, nullable = false)
  @NotNull(message = "An code is required!")
  @Size(message = "Invalid code size.", max = 10, min = 5)
  private String code;

  @ManyToOne
  @JoinColumn(name = "creator_id")
  private User creator;

  @Column(name = "start_date")
  @Temporal(TemporalType.DATE)
  private Date startDate;

  @Column(name = "end_date")
  @Temporal(TemporalType.DATE)
  private Date endDate;

  @OneToOne(cascade = CascadeType.ALL)
  private Image thumbnail;

  @Column(name = "created_at")
  @CreationTimestamp
  private Date createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Date updatedAt;}
