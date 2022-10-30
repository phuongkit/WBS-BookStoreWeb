package ecom.bookstore.wbsbackend.entities;

import ecom.bookstore.wbsbackend.models.enums.ELanguage;
import ecom.bookstore.wbsbackend.models.enums.ERole;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author minh phuong
 * @created 07/10/2022 - 10:19 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_language")
public class Language {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(name = "name", length = 50, nullable = false, unique = true)
  @NotNull(message = "An name is required!")
  private ELanguage name;

  private String syntax;

  public Language(ELanguage name, String syntax) {
    this.name = name;
    this.syntax = syntax;
  }
}
