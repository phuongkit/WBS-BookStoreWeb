package ecom.bookstore.wbsbackend.entities;

import ecom.bookstore.wbsbackend.models.enums.ERole;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

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
@Table(name="tbl_series")
public class Series {
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

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "tbl_product_authors",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "author_id"))
  private Set<Author> authors = new HashSet<>();

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "publisher_id", nullable = false)
  private Publisher publisher;
}
