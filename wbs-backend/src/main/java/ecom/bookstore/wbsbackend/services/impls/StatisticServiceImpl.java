package ecom.bookstore.wbsbackend.services.impls;

import ecom.bookstore.wbsbackend.entities.Order;
import ecom.bookstore.wbsbackend.mapper.OrderMapper;
import ecom.bookstore.wbsbackend.models.clazzs.GroupOrderByDate;
import ecom.bookstore.wbsbackend.models.clazzs.SimpleOrder;
import ecom.bookstore.wbsbackend.models.enums.ETimeDistance;
import ecom.bookstore.wbsbackend.repositories.OrderRepo;
import ecom.bookstore.wbsbackend.services.StatisticService;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author minh phuong
 * @created 05/12/2022 - 10:47 AM
 */
@Service
@Transactional
public class StatisticServiceImpl implements StatisticService {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  public static final String branchName = "Statistic";
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
  @Override public List<GroupOrderByDate> statisticOrderByCreateDate(
      String loginKey,
      Date startDate,
      Date endDate,
      ETimeDistance timeDistance
  ) {
    List<Order> orderList = this.orderRepo.findAllAndRangePayDate(startDate, endDate);
    Map<String, List<Order>> groupOrderByDate = new HashMap<>();
    for (Order order : orderList) {
      Calendar calendar = Utils.dateToCalendar(order.getCreatedAt());
      calendar.set(Calendar.HOUR_OF_DAY, 0);
      if (timeDistance == ETimeDistance.MONTH) {
        calendar.set(Calendar.DAY_OF_MONTH, 0);
      }
      String keyDate = Utils.getStringFromCalendar(calendar, timeDistance);
      List<Order> orderByDays = groupOrderByDate.get(keyDate);
      if (orderByDays == null || orderByDays.size() == 0) {
        groupOrderByDate.put(keyDate, new ArrayList<>(Collections.singleton(order)));
      } else {
        orderByDays.add(order);
        groupOrderByDate.put(keyDate, orderByDays);
      }
    }
    List<GroupOrderByDate> groupOrderByDates = new ArrayList<>();
    for (Map.Entry<String, List<Order>> entry : groupOrderByDate.entrySet()) {
      GroupOrderByDate groupOrder = new GroupOrderByDate();
      groupOrder.setDateStatistic(entry.getKey());
      BigDecimal totalPrice=  new BigDecimal(0);
      List<SimpleOrder> orderDetails = new ArrayList<>();
      for (Order order : entry.getValue()) {
        totalPrice = totalPrice.add(order.getTotalPrice());
        SimpleOrder simpleOrder = new SimpleOrder(order.getId(), order.getTotalPrice(), order.getTransportFee(), new BigDecimal(0), order.getTotalPrice());
        orderDetails.add(simpleOrder);
      }
      groupOrder.setTotalPrice(totalPrice);
      groupOrder.setOrderDetails(orderDetails);
      groupOrderByDates.add(groupOrder);
    }
    return groupOrderByDates;
  }
}
