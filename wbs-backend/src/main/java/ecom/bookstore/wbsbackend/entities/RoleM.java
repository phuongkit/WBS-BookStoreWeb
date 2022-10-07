package ecom.bookstore.wbsbackend.entities;

import ecom.bookstore.wbsbackend.models.enums.ERole;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author minh phuong
 * @created 07/09/2022 - 11:15 PM
 * @project gt-backend
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_role")
@Document(value = "tbl_role")
public class RoleM {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
//  @Column(nullable = false)
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(name = "name", length = 50, nullable = false, unique = true)
  @NotNull(message = "An name is required!")
  private ERole name;

  @Column(name = "description", length = 200, nullable = false)
  @NotNull(message = "An description is required!")
  private String description;

  public RoleM(ERole name, String description) {
    this.name = name;
    this.description = description;
  }

  public RoleM(Role role) {
    this.id = Integer.parseInt(role.getId());
    this.name = role.getName();
    this.description = role.getDescription();
  }
}
