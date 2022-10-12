package ecom.bookstore.wbsbackend.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author minh phuong
 * @created 09/09/2022 - 1:39 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_location")
public class Location {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String commune;

  private String district;

  @Column(name = "province", nullable = false)
  @NotNull(message = "An province is required!")
  private String province;

  public Location(String province) {
    this.province = province;
  }

  public Location(String commune, String district, String province) {
    this.commune = commune;
    this.district = district;
    this.province = province;
  }
}
