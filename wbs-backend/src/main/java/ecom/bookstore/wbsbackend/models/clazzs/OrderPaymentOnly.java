package ecom.bookstore.wbsbackend.models.clazzs;

import ecom.bookstore.wbsbackend.entities.Order;
import ecom.bookstore.wbsbackend.entities.Payment;
import ecom.bookstore.wbsbackend.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author minh phuong
 * @created 13/10/2022 - 4:42 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaymentOnly extends Order {
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
  private Payment payment;

  @Column(name = "pay_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date payAt;
}
