package ecom.bookstore.wbsbackend.repositories;

import ecom.bookstore.wbsbackend.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author minh phuong
 * @created 11/09/2022 - 10:03 AM
 */
@Repository
@Transactional
public interface DiscountRepo extends JpaRepository<Discount, Long> {
  Optional<Discount> findByCode(String code);
}
