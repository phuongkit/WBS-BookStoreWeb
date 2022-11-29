package ecom.bookstore.wbsbackend.services.impls;

import ecom.bookstore.wbsbackend.dto.request.OrderCreationDTO;
import ecom.bookstore.wbsbackend.dto.request.OrderUpdatePaymentDTO;
import ecom.bookstore.wbsbackend.dto.request.OrderUpdateStatusDTO;
import ecom.bookstore.wbsbackend.dto.response.OrderResponseDTO;
import ecom.bookstore.wbsbackend.entities.*;
import ecom.bookstore.wbsbackend.exceptions.ResourceNotFoundException;
import ecom.bookstore.wbsbackend.exceptions.UserNotPermissionException;
import ecom.bookstore.wbsbackend.mapper.LocationMapper;
import ecom.bookstore.wbsbackend.mapper.OrderItemMapper;
import ecom.bookstore.wbsbackend.mapper.OrderMapper;
import ecom.bookstore.wbsbackend.models.enums.EGender;
import ecom.bookstore.wbsbackend.models.enums.EOrderStatus;
import ecom.bookstore.wbsbackend.models.enums.EPayment;
import ecom.bookstore.wbsbackend.models.enums.ERole;
import ecom.bookstore.wbsbackend.repositories.*;
import ecom.bookstore.wbsbackend.services.LocationService;
import ecom.bookstore.wbsbackend.services.OrderItemService;
import ecom.bookstore.wbsbackend.services.OrderService;
import ecom.bookstore.wbsbackend.services.UserService;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author minh phuong
 * @created 11/09/2022 - 10:04 AM
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  public static final String branchName = Order.class.getSimpleName();
  private DiscountRepo discountRepo;

  @Autowired
  public void DiscountRepo(DiscountRepo discountRepo) {
    this.discountRepo = discountRepo;
  }

  private LocationMapper locationMapper;

  @Autowired
  public void LocationMapper(LocationMapper locationMapper) {
    this.locationMapper = locationMapper;
  }

  private LocationService locationService;

  @Autowired
  public void LocationService(LocationService locationService) {
    this.locationService = locationService;
  }

  private OrderItemMapper orderItemMapper;

  @Autowired
  public void OrderItemMapper(OrderItemMapper orderItemMapper) {
    this.orderItemMapper = orderItemMapper;
  }

  private OrderItemService orderItemService;

  @Autowired
  public void OrderItemService(OrderItemService orderItemService) {
    this.orderItemService = orderItemService;
  }

  private OrderMapper orderMapper;

  @Autowired
  public void OrderMapper(OrderMapper orderMapper) {
    this.orderMapper = orderMapper;
  }

  private OrderRepo orderRepo;

  @Autowired
  public void OrderRepo(OrderRepo orderRepo) {
    this.orderRepo = orderRepo;
  }

  private PaymentRepo paymentRepo;

  @Autowired
  public void PaymentRepo(PaymentRepo paymentRepo) {
    this.paymentRepo = paymentRepo;
  }

  private ProductRepo productRepo;

  @Autowired
  public void ProductRepo(ProductRepo productRepo) {
    this.productRepo = productRepo;
  }

  private ShippingMethodRepo shippingMethodRepo;

  @Autowired
  public void ShippingMethodRepo(ShippingMethodRepo shippingMethodRepo) {
    this.shippingMethodRepo = shippingMethodRepo;
  }

  private UserService userService;

  @Autowired
  public void UserService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public Page<OrderResponseDTO> getAllOrders(Pageable pageable) {
    this.LOGGER.info(String.format(Utils.LOG_GET_ALL_OBJECT, branchName));
    Page<Order> orderPage = this.orderRepo.findAll(pageable);
    return orderPage.map(order -> this.orderMapper.orderToOrderResponseDTO(order, null, true));
  }

  @Override
  public Page<OrderResponseDTO> getAllOrdersByUser(
      String loginKey, Integer userId, Pageable pageable) {
    this.LOGGER.info(
        String.format(
            Utils.LOG_GET_ALL_OBJECT_BY_FIELD + Utils.ADD_LOG_FOR_USER,
            branchName,
            "User",
            userId,
            "LoginKey",
            loginKey));
    User userFound = this.userService.getUserByLoginKey(loginKey);
    Page<Order> orderPage = this.orderRepo.findAllByUser(userFound, pageable);
    return orderPage.map(order -> this.orderMapper.orderToOrderResponseDTO(order, null, true));
  }

  @Override
  public OrderResponseDTO getOrderById(String loginKey, Long id) {
    this.LOGGER.info(
        String.format(
            Utils.LOG_GET_OBJECT + Utils.ADD_LOG_FOR_USER,
            branchName,
            "ID",
            id,
            "LoginKey",
            loginKey));
    User userFound = this.userService.getUserByLoginKey(loginKey);
    Order orderFound =
        this.orderRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchName, "ID", id)));
    return this.orderMapper.orderToOrderResponseDTO(orderFound, null, true);
  }

  @Override
  public OrderResponseDTO createOrder(String loginKey, OrderCreationDTO creationDTO) {
    this.LOGGER.info(
        String.format(
            Utils.LOG_CREATE_OBJECT + Utils.ADD_LOG_FOR_USER,
            branchName,
            User.class.getSimpleName() + "ID",
            creationDTO.getUserId(),
            "LoginKey",
            loginKey));
    User userFound = this.userService.getUserByLoginKey(loginKey);
    Order newEntity = new Order();
    newEntity.setUser(userFound);
    newEntity.setGender(
        creationDTO.getGender() == null ? EGender.UNKNOWN : creationDTO.getGender());
    newEntity.setFullName(creationDTO.getFullName());
    newEntity.setEmail(creationDTO.getEmail().length() < 1 ? null : creationDTO.getEmail());
    newEntity.setPhone(creationDTO.getPhone().length() < 1 ? null : creationDTO.getPhone());
    // set address
    newEntity.setLine(creationDTO.getAddress().getHomeAdd());
    Location location = locationMapper.AddressCreationDTOToLocation(creationDTO.getAddress());
    newEntity.setLocation(this.locationService.saveLocation(location));
    // set payment
    Payment paymentFound =
        this.paymentRepo
            .findByName(creationDTO.getPayment())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            Payment.class.getSimpleName(),
                            "Name",
                            creationDTO.getPayment())));
    newEntity.setPayment(paymentFound);
    // set shipping method
    ShippingMethod shippingMethodFound =
        this.shippingMethodRepo
            .findByName(creationDTO.getShippingMethod())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            ShippingMethod.class.getSimpleName(),
                            "Name",
                            creationDTO.getShippingMethod())));
    newEntity.setShippingMethod(shippingMethodFound);
    // set expectedDeliveryTime
    newEntity.setExpectedDeliveryTime(creationDTO.getExpectedDeliveryTime());
    // set transportFee
    if (creationDTO.getTransportFee() != null) {
      newEntity.setTransportFee(creationDTO.getTransportFee());
    } else {
      newEntity.setTransportFee(new BigDecimal(0));
    }

    // set discount
    if (creationDTO.getDiscountCode() != null && creationDTO.getDiscountCode().length() > 0) {
      Discount discountFound =
          this.discountRepo
              .findByCode(creationDTO.getDiscountCode())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          String.format(
                              Utils.OBJECT_NOT_FOUND_BY_FIELD,
                              Discount.class.getSimpleName(),
                              "Code",
                              creationDTO.getDiscountCode())));
      if (Utils.checkValidDiscount(discountFound)) {
        discountFound.setQuantity(discountFound.getQuantity() - 1);
        newEntity.setDiscount(discountFound);
      }
    }

    // initial total price
    newEntity.setTotalPrice(new BigDecimal(0));
    // initial status
    newEntity.setStatus(creationDTO.getStatus() == null ?
                            (creationDTO.getPayment().ordinal() > 0 ? EOrderStatus.ORDER_AWAITING_PAYMENT :
                                EOrderStatus.ORDER_PENDING) : creationDTO.getStatus());
    newEntity.setNote(creationDTO.getNote());

    Order savedEntity = this.orderRepo.save(newEntity);

    // set order item
    savedEntity.setOrderItemSet(
        this.orderItemMapper.orderDetailCreationDTOsToOrderItems(
            savedEntity, creationDTO.getOrderItems()));
    savedEntity.setTotalPrice(Utils.getTotalPriceFromOrderItems(newEntity.getOrderItemSet()));

    return this.orderMapper.orderToOrderResponseDTO(this.orderRepo.save(savedEntity), null, true);
  }

  @Override
  public OrderResponseDTO updateOrder(String loginKey, Long id, OrderCreationDTO creationDTO) {
    this.LOGGER.info(
        String.format(
            Utils.LOG_UPDATE_OBJECT + Utils.ADD_LOG_FOR_USER,
            branchName,
            User.class.getSimpleName() + "ID",
            creationDTO.getUserId(),
            "LoginKey",
            loginKey));
    User userFound = this.userService.getUserByLoginKey(loginKey);
    Order entityFound =
        this.orderRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchName, "ID", id)));

    return this.orderMapper.orderToOrderResponseDTO(this.orderRepo.save(entityFound), null, true);
  }

  @Override
  public OrderResponseDTO updatePaymentOrder(
      String loginKey, Long id, OrderUpdatePaymentDTO updatePaymentDTO) {
    this.LOGGER.info(
        String.format(
            Utils.LOG_UPDATE_OBJECT + Utils.ADD_LOG_FOR_USER,
            branchName,
            "Payment",
            updatePaymentDTO.getPayment(),
            "LoginKey",
            loginKey));
    User userFound = this.userService.getUserByLoginKey(loginKey);
    Order entityFound =
        this.orderRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchName, "ID", id)));
    if (entityFound.getUser() == null
        || Objects.equals(userFound.getId(), entityFound.getUser().getId()) || userFound.getRole()
        .getName() == ERole.ROLE_ADMIN) {
      Payment paymentFound =
          this.paymentRepo
              .findByName(
                  updatePaymentDTO.getPayment() != null
                      ? updatePaymentDTO.getPayment()
                      : EPayment.CASH)
              .orElse(null);
      entityFound.setPayment(paymentFound);
      entityFound.setStatus(
          updatePaymentDTO.getStatus() == null
              ? EOrderStatus.ORDER_PENDING
              : updatePaymentDTO.getStatus());
      if (updatePaymentDTO.getExpectedDeliveryTime() != null) {
        entityFound.setExpectedDeliveryTime(updatePaymentDTO.getExpectedDeliveryTime());
      }
      if (updatePaymentDTO.getTransportFee() != null) {
        entityFound.setTransportFee(updatePaymentDTO.getTransportFee());
      }
      return this.orderMapper.orderToOrderResponseDTO(this.orderRepo.save(entityFound), null, true);
    } else {
      throw new UserNotPermissionException(Utils.USER_NOT_PERMISSION);
    }
  }

  @Override
  public OrderResponseDTO updateStatusOrder(
      String loginKey, Long id, OrderUpdateStatusDTO updateStatusDTO) {
    this.LOGGER.info(
        String.format(
            Utils.LOG_UPDATE_OBJECT_BY_TWO_FIELD + Utils.ADD_LOG_FOR_USER,
            branchName,
            "Id",
            id,
            "Status",
            updateStatusDTO.getStatus(),
            "LoginKey",
            loginKey));
    User userFound = this.userService.getUserByLoginKey(loginKey);
    Order entityFound =
        this.orderRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchName, "ID", id)));
    if (entityFound.getUser() == null
        || Objects.equals(userFound.getId(), entityFound.getUser().getId()) || userFound.getRole()
        .getName() == ERole.ROLE_ADMIN) {
      entityFound.setStatus(updateStatusDTO.getStatus());
      if (updateStatusDTO.getLog() != null
          && !Objects.equals(updateStatusDTO.getLog().trim(), "")) {
        entityFound.setLog(updateStatusDTO.getLog());
      }
      if (updateStatusDTO.getShipOrderCode() != null
          && !Objects.equals(updateStatusDTO.getShipOrderCode().trim(), "")) {
        entityFound.setShipOrderCode(updateStatusDTO.getShipOrderCode());
      }
      if (updateStatusDTO.getExpectedDeliveryTime() != null) {
        entityFound.setExpectedDeliveryTime(updateStatusDTO.getExpectedDeliveryTime());
      }
      if (updateStatusDTO.getTransportFee() != null) {
        entityFound.setTransportFee(updateStatusDTO.getTransportFee());
      }
      return this.orderMapper.orderToOrderResponseDTO(this.orderRepo.save(entityFound), null, true);
    } else {
      throw new UserNotPermissionException(Utils.USER_NOT_PERMISSION);
    }
  }

  @Override public void updatePostPaymentOrder(String payString, String paymentOrderCode, boolean success) {
    this.LOGGER.info(String.format(Utils.LOG_UPDATE_OBJECT_BY_TWO_FIELD, branchName, "PaymentOrderCode", paymentOrderCode, "Sucess", success));
    List<Order> entityList =
        this.orderRepo
            .findAllByPaymentOrderCode(paymentOrderCode);
    if (entityList.size() < 1) {
           throw new ResourceNotFoundException(
                        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchName, "PaymentOrderCode", paymentOrderCode));
    }
    Order order = entityList.get(0);
    if (success) {
      try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date payAt = sdf.parse(payString);
        order.setPayAt(payAt);
      } catch (ParseException e) {
        order.setPayAt(new Date());
      }
      order.setStatus(EOrderStatus.ORDER_PENDING);
    }
    this.orderRepo.save(order);
  }

  @Override
  public OrderResponseDTO deleteOrderById(String loginKey, Long id) {
    this.LOGGER.info(
        String.format(
            Utils.LOG_DELETE_OBJECT + Utils.ADD_LOG_FOR_USER,
            branchName,
            "ID",
            id,
            "LoginKey",
            loginKey));
    User userFound = this.userService.getUserByLoginKey(loginKey);
    Order entityFound =
        this.orderRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchName, "ID", id)));

    if (userFound.getRole().getName() == ERole.ROLE_ADMIN
        || Objects.equals(userFound.getId(), entityFound.getUser().getId())) {

      for (OrderItem orderItem : entityFound.getOrderItemSet()) {
        this.orderItemService.deleteOrderItem(orderItem);
      }

      // delete order
      this.orderRepo.deleteById(id);

      return null;
    } else {
      throw new UserNotPermissionException(Utils.USER_NOT_PERMISSION);
    }
  }
}
