package ecom.bookstore.wbsbackend.entities;

import ecom.bookstore.wbsbackend.utils.Utils;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * @author minh phuong
 * @created 09/09/2022 - 1:38 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_category")
public class Category {
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

  @ManyToOne
  @JoinColumn(name="parent_id")
  private Category parentCategory;

  @OneToMany(mappedBy = "parentCategory", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  private Set<Category> categories = new HashSet<>();

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "thumbnail_id")
  private Image thumbnail;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "icon_id")
  private Image icon;

  @PreRemove
  private void preRemove() {
    categories.forEach( child -> child.setParentCategory(null));
  }

  public Category(String name, Image thumbnail, Image icon) {
    this.name = name;
    this.slug = Utils.toSlug(name);
    this.thumbnail = thumbnail;
    this.icon = icon;
  }

  public Category(String name, Category parentCategory) {
    this.name = name;
    this.slug = Utils.toSlug(name);
    this.parentCategory = parentCategory;
  }
}
