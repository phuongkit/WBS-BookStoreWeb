package ecom.bookstore.wbsbackend.mapper;

import ecom.bookstore.wbsbackend.dto.request.CartDetailCreationDTO;
import ecom.bookstore.wbsbackend.dto.request.OrderDetailCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.OrderDetailResponseDTO;
import ecom.bookstore.wbsbackend.entities.Order;
import ecom.bookstore.wbsbackend.entities.OrderItem;
import ecom.bookstore.wbsbackend.entities.User;

import java.util.List;
import java.util.Set;

/**
 * @author minh phuong
 * @created 20/09/2022 - 9:34 PM
 */
public interface OrderItemMapper {
  Set<OrderItem> orderDetailCreationDTOsToOrderItems(Order order, List<OrderDetailCreationDTO> creationDTOList);
  Set<OrderItem> cartDetailCreationDTOsToOrderItems(User user, List<CartDetailCreationDTO> creationDTOList);
  OrderDetailResponseDTO orderItemToOrderDetailResponseDTO(OrderItem entity);
}
