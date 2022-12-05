package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.models.clazzs.GroupOrderByDate;
import ecom.bookstore.wbsbackend.models.enums.ETimeDistance;

import java.util.Date;
import java.util.List;

/**
 * @author minh phuong
 * @created 03/12/2022 - 12:08 PM
 */
public interface StatisticService {
  List<GroupOrderByDate> statisticOrderByCreateDate(String loginKey, Date startDate, Date endDate, ETimeDistance timeDistance);
}
