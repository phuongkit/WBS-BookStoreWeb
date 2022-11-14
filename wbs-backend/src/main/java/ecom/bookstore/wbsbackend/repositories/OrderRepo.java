package ecom.bookstore.wbsbackend.repositories;

import ecom.bookstore.wbsbackend.entities.Order;
import ecom.bookstore.wbsbackend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author minh phuong
 * @created 11/09/2022 - 10:03 AM
 */
@Repository
@Transactional
public interface OrderRepo extends JpaRepository<Order, Long> {
  Page<Order> findAllByUser(User user, Pageable pageable);
}
