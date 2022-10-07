package ecom.bookstore.wbsbackend.entities;

import ecom.bookstore.wbsbackend.models.enums.EGender;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author minh phuong
 * @created 07/10/2022 - 10:33 PM
 * @project wbs-backend
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_translator")
public class Translator {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "first_name", length = 45)
  @NotNull(message = "An firstname is required!")
  private String firstName;

  @Column(name = "last_name", length = 45)
  @NotNull(message = "An lastname is required!")
  private String lastName;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender", length = 50, nullable = false, unique = true)
  @NotNull(message = "An gender is required!")
  private EGender gender;
}
