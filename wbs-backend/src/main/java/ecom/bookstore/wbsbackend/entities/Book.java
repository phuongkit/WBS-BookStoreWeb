package ecom.bookstore.wbsbackend.entities;

import ecom.bookstore.wbsbackend.models.enums.EBookLayout;
import ecom.bookstore.wbsbackend.models.enums.EBookStatus;
import ecom.bookstore.wbsbackend.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author minh phuong
 * @created 09/09/2022 - 1:38 PM
 * @project gt-backend
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_product")
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 255, unique = true, nullable = false)
  @NotNull(message = "An name is required!")
  @Size(message = "Invalid name size.", max = 255, min = 5)
  private String name;

  @Column(name = "slug", length = 255, unique = true, nullable = false)
  @Size(message = "Invalid slug size.", max = 300, min = 5)
  @NotNull(message = "An name is required!")
  private String slug;

  @Column(name = "description", length = 5000)
  private String description;

  @Column(name = "stand_cost", nullable = false)
  @NotNull(message = "An standCost is required!")
  @DecimalMin(value = "0", message = "Stand Cost must be greater than or equal to 0.")
  private BigDecimal standCost;

  @Column(name = "list_price", nullable = false)
  @NotNull(message = "An listPrice is required!")
  @DecimalMin(value = "0", message = "List Price must be greater than or equal to 0.")
  private BigDecimal listPrice;

  @Column(name = "quantity", nullable = false)
  @NotNull(message = "An quantity is required!")
  @DecimalMin(value = "0", message = "Quantity must be greater than or equal to 0.")
  private Integer quantity;

  @Enumerated(EnumType.STRING)
  @Column(length = 50, nullable = false)
  @NotNull(message = "An status is required!")
  private EBookStatus status;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  private int minAge;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "supplier_id", nullable = false)
  private Supplier supplier;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "tbl_product_authors",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "author_id"))
  private Set<Author> authors = new HashSet<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "tbl_product_translators",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "translator_id"))
  private Set<Translator> translators = new HashSet<>();

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "series_id", nullable = false)
  private Series series;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "language_id", nullable = false)
  private Language language;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "publisher_id", nullable = false)
  private Publisher publisher;

  private int publishYear;

  private int weight;

  private double packagingLength;

  private double packagingWidth;

  private double packagingHeight;

  private int numPages;

  private EBookLayout bookLayout;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "tbl_product_genres",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "genre_id"))
  private Set<Genre> genres = new HashSet<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "tbl_sale_products",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "sale_id"))
  private Set<Sales> saleGallery = new HashSet<>();

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "thumbnail")
  private Image thumbnail;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "tbl_product_images",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "image_id"))
  private Set<Image> imageGallery = new HashSet<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "location_id")
  private Location location;

  @Column(name = "created_at")
  @CreationTimestamp
  private Date createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Date updatedAt;

  public Book(String name, BigDecimal standCost, BigDecimal listPrice, Integer quantity, Category category, Location location, Image thumbnail) {
    this.name = name;
    this.slug = Utils.toSlug(name) + "." + UUID.randomUUID().toString().replace("-", "");
    this.standCost = standCost;
    this.listPrice = listPrice;
    this.quantity = quantity;
    this.status = EBookStatus.PRODUCT_TRADING;
    this.category = category;
    this.location = location;
    this.thumbnail = thumbnail;
  }
}