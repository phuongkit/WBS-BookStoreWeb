package ecom.bookstore.wbsbackend.mapper.impls;

import ecom.bookstore.wbsbackend.dto.response.OrderDetailResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.OrderResponseDTO;
import ecom.bookstore.wbsbackend.entities.Order;
import ecom.bookstore.wbsbackend.entities.OrderItem;
import ecom.bookstore.wbsbackend.mapper.*;
import ecom.bookstore.wbsbackend.models.clazzs.OrderPaymentOnly;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author minh phuong
 * @created 20/09/2022 - 9:34 PM
 * @project gt-backend
 */
@Component
public class OrderMapperImpl implements OrderMapper {
  private AddressMapper addressMapper;

  @Autowired
  public void AddressMapper(AddressMapper addressMapper) {
    this.addressMapper = addressMapper;
  }

  private DiscountMapper discountMapper;

  @Autowired
  public void DiscountMapper(DiscountMapper discountMapper) {
    this.discountMapper = discountMapper;
  }

  private OrderItemMapper orderItemMapper;

  @Autowired
  public void OrderItemMapper(OrderItemMapper orderItemMapper) {
    this.orderItemMapper = orderItemMapper;
  }

  private UserMapper userMapper;

  @Autowired
  public void UserMapper(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  @Override
  public OrderResponseDTO orderToOrderResponseDTO(Order entity, Integer shopId, boolean... isFull) {
    if (entity == null) {
      return null;
    }
    OrderResponseDTO responseDTO = new OrderResponseDTO();
    responseDTO.setId(entity.getId());
    if (entity.getUser() != null) {
      responseDTO.setUser(this.userMapper.userToUserSimpleResponseDTO(entity.getUser()));
    }
    responseDTO.setGender(entity.getGender().ordinal());
    responseDTO.setFullName(entity.getFullName());
    responseDTO.setTotalPriceProduct(Utils.getTotalPriceFromOrderItems(entity.getOrderItemSet()));
    responseDTO.setTransportFee(entity.getTransportFee());
    responseDTO.setTotalPrice(responseDTO.getTotalPriceProduct().add(responseDTO.getTransportFee()));
    if (entity.getPayment() != null) {
      responseDTO.setPayment(entity.getPayment().getName().ordinal());
    }
    if (entity.getShippingMethod() != null) {
      responseDTO.setShippingMethod(entity.getShippingMethod().getName().ordinal());
    }
    responseDTO.setEmail(entity.getEmail());
    responseDTO.setPhone(entity.getPhone());
    if (entity.getDiscount() != null) {
      responseDTO.setDiscount(
          this.discountMapper.discountToDiscountResponseDTO(entity.getDiscount()));
    }
    if (entity.getLocation() != null) {
      responseDTO.setAddress(
          this.addressMapper.lineAndLocationToAddressResponseDTO(
              entity.getLine(), entity.getLocation()));
    }
    responseDTO.setStatus(entity.getStatus().ordinal());
    responseDTO.setPayAt(entity.getPayAt());
    responseDTO.setNote(entity.getNote());
    if (isFull.length > 0 && isFull[0]) {
      if (!entity.getOrderItemSet().isEmpty()) {
        OrderDetailResponseDTO[] orderItems =
            new OrderDetailResponseDTO[entity.getOrderItemSet().size()];
        int i = 0;
        for (OrderItem orderItem : entity.getOrderItemSet()) {
            orderItems[i] = new OrderDetailResponseDTO();
            orderItems[i] = this.orderItemMapper.orderItemToOrderDetailResponseDTO(orderItem);
            i++;
        }
        responseDTO.setOrderItems(orderItems);
      }
    }
    responseDTO.setLog(entity.getLog());
    responseDTO.setShipOrderCode(entity.getShipOrderCode());
    responseDTO.setExpectedDeliveryTime(entity.getExpectedDeliveryTime());
    responseDTO.setCreatedAt(entity.getCreatedAt());
    responseDTO.setUpdatedAt(entity.getUpdatedAt());
    return responseDTO;
  }

  @Override public OrderPaymentOnly orderToOrderPaymentOnly(Order entity) {
    if (entity == null) {
      return null;
    }
    return new OrderPaymentOnly(entity.getId(), entity.getUser(), entity.getPayment(), entity.getPayAt());
  }
}
