package ecom.bookstore.wbsbackend.entities;

import ecom.bookstore.wbsbackend.models.enums.EProductLayout;
import ecom.bookstore.wbsbackend.models.enums.EProductStatus;
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
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_product")
public class Product {
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

  @Column(name = "description", length = 15000)
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
  private EProductStatus status = EProductStatus.PRODUCT_TRADING;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  private Integer minAge;

  private Integer maxAge;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "supplier_id", nullable = false)
  private Supplier supplier;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "tbl_product_authors",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "author_id"))
  private Set<Author> authors = new HashSet<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "tbl_product_translators",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "translator_id"))
  private Set<Translator> translators = new HashSet<>();

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "series_id")
  private Series series;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "language_id")
  private Language language;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "publisher_id", nullable = false)
  private Publisher publisher;

  private Integer publishYear;

  private Integer reprintYear;

  private Integer weight;

  private Double packagingLength;

  private Double packagingWidth;

  private Double packagingHeight;

  private Integer numPages;

  @Enumerated(EnumType.STRING)
  @Column(name = "book_layout", length = 50, nullable = false)
  @NotNull(message = "An book layout is required!")
  private EProductLayout bookLayout = EProductLayout.PAPERBACK;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "tbl_product_genres",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "genre_id"))
  private Set<Genre> genres = new HashSet<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "tbl_sale_products",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "sale_id"))
  private Set<Sale> saleGallery = new HashSet<>();

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "thumbnail")
  private Image thumbnail;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "tbl_product_images",
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

  public Product(
      String name,
      BigDecimal standCost,
      BigDecimal listPrice,
      Integer quantity,
      Category category,
      Integer minAge,
      Integer maxAge,
      Supplier supplier,
      Set<Author> authors,
      Set<Translator> translators,
      Series series,
      Language language,
      Publisher publisher,
      Integer publishYear,
      Integer reprintYear,
      Integer weight,
      Double packagingLength,
      Double packagingWidth,
      Double packagingHeight,
      Integer numPages,
      EProductLayout bookLayout,
      Set<Genre> genres,
      Location location,
      Image thumbnail,
      Set<Image> imageGallery,
      String description) {
    this.name = name;
    this.slug = Utils.toSlug(name) + "." + UUID.randomUUID().toString().replace("-", "");
    this.standCost = standCost;
    this.listPrice = listPrice;
    this.quantity = quantity;
    this.status = EProductStatus.PRODUCT_TRADING;
    this.category = category;
    this.maxAge = maxAge;
    this.minAge = minAge;
    this.supplier = supplier;
    this.authors = authors;
    this.translators = translators;
    this.series = series;
    this.language = language;
    this.publisher = publisher;
    this.publishYear = publishYear;
    this.reprintYear = reprintYear;
    this.weight = weight;
    this.packagingLength = packagingLength;
    this.packagingWidth = packagingWidth;
    this.packagingHeight = packagingHeight;
    this.numPages = numPages;
    this.bookLayout = bookLayout != null ? bookLayout : EProductLayout.PAPERBACK;
    this.genres = genres;
    this.location = location;
    this.thumbnail = thumbnail;
    this.imageGallery = imageGallery;
    this.description = description;
  }

  public Product(
      String name,
      BigDecimal listPrice,
      Category category,
      Integer minAge,
      Integer maxAge,
      Supplier supplier,
      Set<Author> authors,
      Set<Translator> translators,
      Series series,
      Language language,
      Publisher publisher,
      Integer publishYear,
      Integer reprintYear,
      Integer weight,
      Double packagingLength,
      Double packagingWidth,
      Double packagingHeight,
      Integer numPages,
      EProductLayout bookLayout,
      Set<Genre> genres,
      Location location,
      Image thumbnail,
      String description) {
    this.name = name;
    this.slug = Utils.toSlug(name) + "." + UUID.randomUUID().toString().replace("-", "");
    this.standCost = listPrice.multiply(new BigDecimal("0.9"));
    this.listPrice = listPrice;
    this.quantity = 1000;
    this.status = EProductStatus.PRODUCT_TRADING;
    this.category = category;
    this.maxAge = maxAge;
    this.minAge = minAge;
    this.supplier = supplier;
    this.authors = authors;
    this.translators = translators;
    this.series = series;
    this.language = language;
    this.publisher = publisher;
    this.publishYear = publishYear != null ? publishYear : reprintYear;
    this.reprintYear = reprintYear;
    this.weight = weight;
    this.packagingLength = packagingLength;
    this.packagingWidth = packagingWidth;
    this.packagingHeight = packagingHeight;
    this.numPages = numPages;
    this.bookLayout = bookLayout != null ? bookLayout : EProductLayout.PAPERBACK;
    this.genres = genres;
    this.location = location;
    this.thumbnail = thumbnail;
    this.description = description;
  }
}
