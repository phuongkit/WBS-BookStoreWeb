package ecom.bookstore.wbsbackend.entities;

import ecom.bookstore.wbsbackend.models.enums.EGender;
import ecom.bookstore.wbsbackend.models.enums.ERole;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author minh phuong
 * @created 07/10/2022 - 9:45 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_author")
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "full_name", length = 100, nullable = false)
  @NotNull(message = "An full name is required!")
  private String fullName;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender", length = 50, nullable = false)
  @NotNull(message = "An gender is required!")
  private EGender gender;

  public Author(String fullName) {
    this.fullName = fullName;
    this.gender = EGender.UNKNOWN;
  }

  public Author(String fullName, EGender gender) {
    this.fullName = fullName;
    this.gender = gender;
  }
}
