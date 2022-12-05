package ecom.bookstore.wbsbackend.repositories;

import ecom.bookstore.wbsbackend.entities.Order;
import ecom.bookstore.wbsbackend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author minh phuong
 * @created 11/09/2022 - 10:03 AM
 */
@Repository
@Transactional
public interface OrderRepo extends JpaRepository<Order, Long> {
  Page<Order> findAllByUser(User user, Pageable pageable);

  List<Order> findAllByPaymentOrderCode(String paymentOrderCode);
  @Query("select o from Order o where " +
      "(:startDate is not null and o.createdAt >= :startDate) " +
      "and (:endDate is not null and o.createdAt <= :endDate)" +
      "order by o.createdAt desc")
  List<Order> findAllAndRangePayDate(
      @Param("startDate") Date startDate,
      @Param("endDate") Date endDate);
}
