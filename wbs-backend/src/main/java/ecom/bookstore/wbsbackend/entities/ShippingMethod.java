package ecom.bookstore.wbsbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author minh phuong
 * @created 19/09/2022 - 9:13 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_shipping_method")
public class ShippingMethod {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name", length = 100, nullable = false)
  @NotNull(message = "An name is required!")
  @Size(message = "Invalid name size.", max = 100, min = 5)
  private String name;

  @Column(name = "description", length = 300)
  @Size(message = "Invalid description size.", max = 300)
  private String description;

  @Column(name = "created_at")
  @CreationTimestamp
  private Date createdAt;
}
