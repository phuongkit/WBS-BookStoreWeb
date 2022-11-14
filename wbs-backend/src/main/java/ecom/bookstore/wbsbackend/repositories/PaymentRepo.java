package ecom.bookstore.wbsbackend.repositories;

import ecom.bookstore.wbsbackend.entities.Payment;
import ecom.bookstore.wbsbackend.models.enums.EPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author minh phuong
 * @created 08/10/2022 - 8:16 PM
 */
@Repository
@Transactional
public interface PaymentRepo extends JpaRepository<Payment, Integer> {
  Optional<Payment> findByName(EPayment name);
}
