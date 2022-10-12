package ecom.bookstore.wbsbackend.repositories;

import ecom.bookstore.wbsbackend.entities.Book;
import ecom.bookstore.wbsbackend.entities.Review;
import ecom.bookstore.wbsbackend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author minh phuong
 * @created 07/10/2022 - 11:00 PM
 */
@Repository
@Transactional
public interface ReviewRepo extends JpaRepository<Review, Long> {
  @Query(
      value =
          "select fb from Review fb where "
              + "(:Book is null or fb.book = :book) "
              + "and (:author is null or fb.author = :author) "
              + "order by fb.createdAt desc")
  Page<Review> getAllMainReviewByBookOrUser(Book book, User author, Pageable pageable);

  @Query(
      value =
          "select fb from Review fb where "
              + "(:Book is null or fb.book = :book) "
              + "and (:author is null or fb.author = :author) "
              + "order by fb.createdAt asc")
  List<Review> getReviewByBookAndUser(Book book, User author);

  List<Review> findAllByBook(Book Book);
}
