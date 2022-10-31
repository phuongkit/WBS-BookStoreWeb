package ecom.bookstore.wbsbackend.entities;

import ecom.bookstore.wbsbackend.models.enums.ERole;
import ecom.bookstore.wbsbackend.models.enums.ESeries;
import ecom.bookstore.wbsbackend.utils.Utils;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * @author minh phuong
 * @created 07/10/2022 - 9:46 PM
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

  @Column(name = "name", length = 500, nullable = false, unique = true)
  @Size(message = "Invalid name size.", max = 500, min = 1)
  @NotNull(message = "An name is required!")
  private String name;

  @Column(name = "slug", length = 500, nullable = false, unique = true)
  @Size(message = "Invalid slug size.", max = 500, min = 1)
  @NotNull(message = "An slug is required!")
  private String slug;

  @Column(name = "description", length = 500)
//  @NotNull(message = "An description is required!")
  private String description;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "tbl_series_authors",
      joinColumns = @JoinColumn(name = "series_id"),
      inverseJoinColumns = @JoinColumn(name = "author_id"))
  private Set<Author> authors = new HashSet<>();

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "supplier_id")
  private Supplier supplier;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "publisher_id")
  private Publisher publisher;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", length = 50, nullable = false)
  @NotNull(message = "An type is required!")
  private ESeries type;

  public Series(String name, Set<Author> authors, Supplier supplier, Publisher publisher, ESeries type) {
    this.name = name;
    this.slug = Utils.toSlug(name);
    this.authors = authors;
    this.supplier = supplier;
    this.publisher = publisher;
    this.type = type;
  }

  public static Series createCombo(String name, Set<Author> authors, Supplier supplier, Publisher publisher) {
    return new Series(name, authors, supplier, publisher, ESeries.COMBO);
  }

  public static Series createSeries(String name, Set<Author> authors, Publisher publisher) {
    return new Series(name, authors, null, publisher, ESeries.SERIES);
  }
}
