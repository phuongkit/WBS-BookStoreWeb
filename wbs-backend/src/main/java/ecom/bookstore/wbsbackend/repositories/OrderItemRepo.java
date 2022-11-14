package ecom.bookstore.wbsbackend.repositories;

import ecom.bookstore.wbsbackend.entities.OrderItem;
import ecom.bookstore.wbsbackend.entities.Product;
import ecom.bookstore.wbsbackend.entities.User;
import ecom.bookstore.wbsbackend.models.enums.EOrdertemStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author minh phuong
 * @created 14/10/2022 - 10:55 AM
 */
@Repository
@Transactional
public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
  Optional<OrderItem> findByUserAndProductAndStatus(User user, Product product, EOrdertemStatus status);

  List<OrderItem> findByUserAndStatus(User user, EOrdertemStatus status, Pageable pageable);

  Optional<OrderItem> findByIdAndStatus(Long id, EOrdertemStatus status);
}
