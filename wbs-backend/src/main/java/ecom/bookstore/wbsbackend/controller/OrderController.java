package ecom.bookstore.wbsbackend.controller;

import ecom.bookstore.wbsbackend.dto.request.OrderCreationDTO;
import ecom.bookstore.wbsbackend.dto.request.OrderUpdatePaymentDTO;
import ecom.bookstore.wbsbackend.dto.request.OrderUpdateStatusDTO;
import ecom.bookstore.wbsbackend.dto.response.OrderResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.entities.Order;
import ecom.bookstore.wbsbackend.models.enums.ERole;
import ecom.bookstore.wbsbackend.services.OrderService;
import ecom.bookstore.wbsbackend.utils.JwtTokenUtil;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static ecom.bookstore.wbsbackend.utils.Utils.DEFAULT_PAGE;
import static ecom.bookstore.wbsbackend.utils.Utils.DEFAULT_SIZE;

/**
 * @author minh phuong
 * @created 08/10/2022 - 4:46 PM
 */
@RestController
@RequestMapping(value = "/api/v1/orders")
@CrossOrigin(origins = "*")
public class OrderController {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  public static final String branchName = Order.class.getSimpleName();
  private JwtTokenUtil jwtTokenUtil;

  @Autowired public void JwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
    this.jwtTokenUtil = jwtTokenUtil;
  }

  private OrderService orderService;

  @Autowired
  public void OrderService(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping
  @RolesAllowed({ERole.Names.ADMIN})
  public ResponseObject<Page<OrderResponseDTO>> getAllOrders(
      @RequestParam(name = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
      @RequestParam(name = "limit", required = false, defaultValue = DEFAULT_SIZE) Integer size,
      @RequestParam(name = "sortField", required = false, defaultValue = "id") String sortField,
      @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir) {
    Sort sort = Sort.by(sortField);
    sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
    Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, size, sort);
    return new ResponseObject<>(
        HttpStatus.OK, "", this.orderService.getAllOrders(pageable));
  }

  @GetMapping("/userId/{userId}")
  @RolesAllowed({ERole.Names.CUSTOMER, ERole.Names.SELLER, ERole.Names.ADMIN})
  public ResponseObject<Page<OrderResponseDTO>> getAllOrdersByUser(
      @RequestParam(name = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
      @RequestParam(name = "limit", required = false, defaultValue = DEFAULT_SIZE) Integer size,
      @RequestParam(name = "sortField", required = false, defaultValue = "id") String sortField,
      @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir,
      @PathVariable(name = "userId") Integer userId, HttpServletRequest request) {
    Sort sort = Sort.by(sortField);
    sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
    Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, size, sort);
    //    this.LOGGER.info(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getAuthorities()));
    String loginKey = jwtTokenUtil.getUserNameFromRequest(request);
    return new ResponseObject<>(
        HttpStatus.OK, "", this.orderService.getAllOrdersByUser(loginKey, userId, pageable));
  }

  @GetMapping("/{id}")
  public ResponseObject<OrderResponseDTO> getOrderById(@PathVariable(name = "id") Long id, HttpServletRequest request) {
    //    this.LOGGER.info(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getAuthorities()));
    String loginKey = jwtTokenUtil.getUserNameFromRequest(request);
    return new ResponseObject<>(
        HttpStatus.OK, "", this.orderService.getOrderById(loginKey, id));
  }

  @PostMapping
  @RolesAllowed({ERole.Names.CUSTOMER, ERole.Names.SELLER, ERole.Names.ADMIN})
  public ResponseObject<OrderResponseDTO> createOrder(@RequestBody @Valid OrderCreationDTO orderCreationDTO,
                                                      HttpServletRequest request) {
    //    this.LOGGER.info(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getAuthorities()));
    String loginKey = jwtTokenUtil.getUserNameFromRequest(request);
    return new ResponseObject<>(HttpStatus.CREATED, String.format(Utils.CREATE_OBJECT_SUCCESSFULLY, branchName),
        this.orderService.createOrder(loginKey, orderCreationDTO));
  }

  @PutMapping("/{id}")
  @RolesAllowed({ERole.Names.CUSTOMER, ERole.Names.SELLER, ERole.Names.ADMIN})
  public ResponseObject<OrderResponseDTO> updateOrder(@PathVariable(name = "id") Long id,
                                                      @RequestBody OrderCreationDTO orderCreationDTO,
                                                      HttpServletRequest request) {
    //    this.LOGGER.info(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getAuthorities()));
    String loginKey = jwtTokenUtil.getUserNameFromRequest(request);
    return new ResponseObject<>(HttpStatus.OK, String.format(Utils.UPDATE_OBJECT_SUCCESSFULLY, branchName),
        this.orderService.updateOrder(loginKey, id, orderCreationDTO));
  }

  @PutMapping("/{id}/payment")
  @RolesAllowed({ERole.Names.CUSTOMER, ERole.Names.SELLER, ERole.Names.ADMIN})
  public ResponseObject<OrderResponseDTO> updatePaymentOrder(@PathVariable(name = "id") Long id,
                                                             @RequestBody OrderUpdatePaymentDTO updatePaymentDTO,
                                                             HttpServletRequest request) {
    //    this.LOGGER.info(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getAuthorities()));
    String loginKey = jwtTokenUtil.getUserNameFromRequest(request);
    return new ResponseObject<>(HttpStatus.OK, String.format(Utils.UPDATE_OBJECT_SUCCESSFULLY, branchName),
        this.orderService.updatePaymentOrder(loginKey, id, updatePaymentDTO));
  }

  @PutMapping("/{id}/status")
  @RolesAllowed({ERole.Names.ADMIN})
  public ResponseObject<OrderResponseDTO> updateStatus(@PathVariable(name = "id") Long id,
                                                             @RequestBody OrderUpdateStatusDTO updateStatusDTO,
                                                             HttpServletRequest request) {
    String loginKey = jwtTokenUtil.getUserNameFromRequest(request);
    return new ResponseObject<>(HttpStatus.OK, String.format(Utils.UPDATE_OBJECT_SUCCESSFULLY, branchName),
                                this.orderService.updateStatusOrder(loginKey, id, updateStatusDTO));
  }

  @DeleteMapping("/{id}")
  @RolesAllowed({ERole.Names.CUSTOMER, ERole.Names.SELLER, ERole.Names.ADMIN})
  public ResponseObject<OrderResponseDTO> deleteOrder(@PathVariable(name = "id") Long id, HttpServletRequest request) {
    //    this.LOGGER.info(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getAuthorities()));
    String loginKey = jwtTokenUtil.getUserNameFromRequest(request);
    return new ResponseObject<>(HttpStatus.OK, String.format(Utils.DELETE_OBJECT_SUCCESSFULLY, branchName),
        this.orderService.deleteOrderById(loginKey, id));
  }
}
