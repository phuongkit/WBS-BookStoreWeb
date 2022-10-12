package ecom.bookstore.wbsbackend.entities;

import ecom.bookstore.wbsbackend.models.enums.EImageType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author minh phuong
 * @created 09/09/2022 - 2:04 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_image")
public class Image {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "path", nullable = false)
  @NotNull(message = "An path is required!")
  private String path;

  @Enumerated(EnumType.STRING)
  @Column(name = "image_type", length = 50, nullable = false)
  @NotNull(message = "An imageType is required!")
  private EImageType imageType;

  public Image(String path, EImageType imageType) {
    this.path = path;
    this.imageType = imageType;
  }
}
