package ecom.bookstore.wbsbackend.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author minh phuong
 * @created 16/09/2022 - 8:21 PM
 * @project gt-backend
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_sales")
public class Sales {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 100, nullable = false)
  @NotNull(message = "An name is required!")
  @Size(message = "Invalid name size.", max = 100, min = 5)
  private String name;

  @Column(name = "description", length = 300)
  @Size(message = "Invalid description size.", max = 300)
  private String description;

  @Column(name = "percent", nullable = false)
  @NotNull(message = "An percent is required!")
  @DecimalMin(value = "0.01", message = "Percent must be greater than or equal to 0.01")
  @DecimalMax(value = "1", message = "Percent must be smaller than or equal to 1")
  private Double percent;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "tbl_sale_products",
      joinColumns = @JoinColumn(name = "sale_id"),
      inverseJoinColumns = @JoinColumn(name = "product_id"))
  private Set<Book> books = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "creator")
  private User creator;

  @Column(name = "start_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date startDate;

  @Column(name = "end_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date endDate;

  @ManyToOne(cascade = CascadeType.ALL)
  private Image thumbnail;

  @Column(name = "created_at")
  @CreationTimestamp
  private Date createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Date updatedAt;

  public Sales(String name, String description, Double percent, Set<Book> books, User creator, Date startDate, Date endDate) {
    this.name = name;
    this.description= description;
    this.percent = percent;
    this.books = books;
    this.creator = creator;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
