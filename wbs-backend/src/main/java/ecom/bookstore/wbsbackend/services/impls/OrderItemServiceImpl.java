package ecom.bookstore.wbsbackend.services.impls;

import ecom.bookstore.wbsbackend.entities.OrderItem;
import ecom.bookstore.wbsbackend.repositories.OrderItemRepo;
import ecom.bookstore.wbsbackend.repositories.ProductRepo;
import ecom.bookstore.wbsbackend.services.OrderItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author minh phuong
 * @created 14/10/2022 - 10:54 AM
 */
@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  public static final String branchName = OrderItem.class.getSimpleName();
  private OrderItemRepo orderItemRepo;

  @Autowired public void OrderItemRepo(OrderItemRepo orderItemRepo) {
    this.orderItemRepo = orderItemRepo;
  }

  private ProductRepo productRepo;


  @Autowired
  public void ProductRepo(ProductRepo productRepo) {
    this.productRepo = productRepo;
  }

  @Override public void deleteOrderItem(OrderItem orderItem) {
    orderItem.getProduct().setQuantity(orderItem.getQuantity() + orderItem.getProduct().getQuantity());
    this.productRepo.save(orderItem.getProduct());
    this.orderItemRepo.deleteById(orderItem.getId());
  }
}
