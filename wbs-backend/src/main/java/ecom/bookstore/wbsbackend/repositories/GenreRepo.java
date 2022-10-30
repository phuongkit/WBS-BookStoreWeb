package ecom.bookstore.wbsbackend.repositories;

import ecom.bookstore.wbsbackend.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author minh phuong
 * @created 07/10/2022 - 10:55 PM
 */
@Repository
@Transactional
public interface GenreRepo extends JpaRepository<Genre, Integer> {
  Optional<Genre> findByName(String name);
}
