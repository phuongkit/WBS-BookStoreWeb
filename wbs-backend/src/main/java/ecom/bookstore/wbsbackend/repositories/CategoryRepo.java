package ecom.bookstore.wbsbackend.repositories;

import ecom.bookstore.wbsbackend.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author minh phuong
 * @created 11/09/2022 - 10:02 AM
 */
@Repository
@Transactional
public interface CategoryRepo extends SearchRepository<Category, Integer> {
  @Query(
      value =
          "select c from Category c where " +
              "lower(c.name) like lower(concat('%', :keyword,'%'))")
  Page<Category> findAll(String keyword, Pageable pageable);

  Optional<Category> findByName(String name);

  Optional<Category> findBySlug(String slug);
}
