package ecom.bookstore.wbsbackend.entities;

import ecom.bookstore.wbsbackend.models.enums.ERole;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author minh phuong
 * @created 07/10/2022 - 9:46 PM
 * @project wbs-backend
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

  @Enumerated(EnumType.STRING)
  @Column(name = "name", length = 50, nullable = false, unique = true)
  @NotNull(message = "An name is required!")
  private ERole name;

  @Column(name = "description", length = 200, nullable = false)
  @NotNull(message = "An description is required!")
  private String description;
}
