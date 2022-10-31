package ecom.bookstore.wbsbackend.repositories;

import ecom.bookstore.wbsbackend.entities.Product;
import ecom.bookstore.wbsbackend.entities.Category;
import ecom.bookstore.wbsbackend.entities.Genre;
import ecom.bookstore.wbsbackend.entities.Location;
import ecom.bookstore.wbsbackend.models.enums.EProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author minh phuong
 * @created 07/10/2022 - 10:54 PM
 */
@Repository
@Transactional
public interface ProductRepo extends JpaRepository<Product, Long> {
  Optional<Product> findByName(String name);

  Optional<Product> findBySlug(String slug);

  @Query(
      value =
          "select p from Product p "
              + "where "
              + "(:keyword is null "
              + "or length(:keyword) < 1 "
              + "or (lower(p.name) like lower(concat('%', :keyword,'%'))) "
              + "or (p.category is not null and lower(p.category.name) like lower(concat('%', :keyword,'%')))) "
              + "and (:category is null or (p.category is not null and p.category = :category)) "
              + "and (:location is null or p.location is null or p.location = :location) "
              + "and p.listPrice > :minPrice "
              + "and p.listPrice < :maxPrice")
  Page<Product> filterProduct(
      @Param("keyword") String keyword,
      @Param("category") Category category,
      @Param("location") Location location,
      @Param("minPrice") BigDecimal minPrice,
      @Param("maxPrice") BigDecimal maxPrice,
      Pageable pageable);

  Page<Product> findAllByStatus(EProductStatus status, Pageable pageable);

  Page<Product> findAllByGenres(Genre genre, Pageable pageable);

  Page<Product> findAllByCategory(Category category, Pageable pageable);

  @Query(value = "select p from Product p " +
      "where " +
      "(:category is null or (p.category is not null and p.category = :category)) " +
      "and (:location is null or p.location is null or p.location = :location) "
      + "and p.listPrice > :minPrice "
      + "and p.listPrice < :maxPrice")
  Page<Product> findAllByCategoryOrLocation(
      @Param("category") Category category,
      @Param("location") Location location,
      BigDecimal minPrice,
      BigDecimal maxPrice,
      Pageable pageable);
}
