package ecom.bookstore.wbsbackend.repositories;

import ecom.bookstore.wbsbackend.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author minh phuong
 * @created 07/10/2022 - 11:00 PM
 * @project wbs-backend
 */
@Repository
@Transactional
public interface ReviewRepo extends JpaRepository<Review, Long> {
}
