package ecom.bookstore.wbsbackend.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author minh phuong
 * @created 07/10/2022 - 10:38 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_genre")
public class Genre {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name", length = 50, nullable = false, unique = true)
  @Size(message = "Invalid email size.", max = 50, min = 1)
  @NotNull(message = "An name is required!")
  private String name;

  @Column(name = "slug", length = 50, nullable = false, unique = true)
  @Size(message = "Invalid slug size.", max = 50, min = 1)
  @NotNull(message = "An name is required!")
  private String slug;

  @Column(name = "description")
  private String description;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "thumbnail_id")
  private Image thumbnail;
}
