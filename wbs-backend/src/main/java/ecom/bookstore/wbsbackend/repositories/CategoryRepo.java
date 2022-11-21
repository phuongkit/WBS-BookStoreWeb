package ecom.bookstore.wbsbackend.repositories;

import ecom.bookstore.wbsbackend.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author minh phuong
 * @created 11/09/2022 - 10:02 AM
 */
@Repository
@Transactional
public interface CategoryRepo extends JpaRepository<Category, Integer> {
//  @Query(
//      value =
//          "select c from Category c where " +
//              "lower(c.name) like lower(concat('%', :keyword,'%'))")
//  List<Category> findAll(String keyword, Pageable pageable);


  @Query(value = "select c from Category c where c.parentCategory is null")
  List<Category> findAllParentCategory();

  Optional<Category> findByName(String name);

  Optional<Category> findBySlug(String slug);
}
