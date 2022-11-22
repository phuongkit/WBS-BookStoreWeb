package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.dto.request.OrderCreationDTO;
import ecom.bookstore.wbsbackend.dto.request.OrderUpdatePaymentDTO;
import ecom.bookstore.wbsbackend.dto.request.OrderUpdateStatusDTO;
import ecom.bookstore.wbsbackend.dto.response.OrderResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author minh phuong
 * @created 11/09/2022 - 10:00 AM
 */
public interface OrderService {
  Page<OrderResponseDTO> getAllOrders(Pageable pageable);
  Page<OrderResponseDTO> getAllOrdersByUser(String loginKey, Integer userId, Pageable pageable);

  OrderResponseDTO getOrderById(String loginKey, Long id);

  OrderResponseDTO createOrder(String loginKey, OrderCreationDTO creationDTO);

  OrderResponseDTO updateOrder(String loginKey, Long id, OrderCreationDTO creationDTO);
  OrderResponseDTO updatePaymentOrder(String loginKey, Long id, OrderUpdatePaymentDTO updatePaymentDTO);
  OrderResponseDTO updateStatusOrder(String loginKey, Long id, OrderUpdateStatusDTO updateStatusDTO);
  void updatePostPaymentOrder(String payString, String paymentOrderCode, boolean success);
  OrderResponseDTO deleteOrderById(String loginKey, Long id);
}
