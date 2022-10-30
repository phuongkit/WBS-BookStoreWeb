package ecom.bookstore.wbsbackend.entities;

import ecom.bookstore.wbsbackend.models.enums.ERole;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author minh phuong
 * @created 07/10/2022 - 9:46 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_supplier")
public class Supplier {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name", length = 100, nullable = false, unique = true)
  @Size(message = "Invalid name size.", max = 100, min = 1)
  @NotNull(message = "An name is required!")
  private String name;

  @Column(name = "description", length = 200)
//  @NotNull(message = "An description is required!")
  private String description;

  public Supplier(String name) {
    this.name = name;
  }
}
