package ecom.bookstore.wbsbackend.entities;

import ecom.bookstore.wbsbackend.models.enums.EGender;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author minh phuong
 * @created 07/10/2022 - 10:33 PM
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

  @Column(name = "full_name", length = 100)
  @NotNull(message = "An fullname is required!")
  private String fullName;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender", length = 50, nullable = false)
  @NotNull(message = "An gender is required!")
  private EGender gender;

  public Translator(String fullName) {
    this.fullName = fullName;
    this.gender = EGender.UNKNOWN;
  }

  public Translator(String fullName, EGender gender) {
    this.fullName = fullName;
    this.gender = gender;
  }
}
