package ecom.bookstore.wbsbackend.mapper;

import ecom.bookstore.wbsbackend.dto.response.OrderResponseDTO;
import ecom.bookstore.wbsbackend.entities.Order;
import ecom.bookstore.wbsbackend.models.clazzs.OrderPaymentOnly;

/**
 * @author minh phuong
 * @created 20/09/2022 - 9:34 PM
 * @project gt-backend
 */
public interface OrderMapper {
  OrderResponseDTO orderToOrderResponseDTO(Order entity, Integer shopId, boolean...isFull);

  OrderPaymentOnly orderToOrderPaymentOnly(Order entity);
}
