package ecom.bookstore.wbsbackend.repositories;

import ecom.bookstore.wbsbackend.entities.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author minh phuong
 * @created 18/10/2022 - 5:13 PM
 */
@Repository
@Transactional
public interface PublisherRepo extends JpaRepository<Publisher, Integer> {
  Optional<Publisher> findByName(String name);
}
