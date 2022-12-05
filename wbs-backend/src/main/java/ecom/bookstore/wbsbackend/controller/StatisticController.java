package ecom.bookstore.wbsbackend.controller;

import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.models.clazzs.GroupOrderByDate;
import ecom.bookstore.wbsbackend.models.enums.ERole;
import ecom.bookstore.wbsbackend.models.enums.ETimeDistance;
import ecom.bookstore.wbsbackend.services.StatisticService;
import ecom.bookstore.wbsbackend.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author minh phuong
 * @created 05/12/2022 - 10:44 AM
 */
@RestController
@RequestMapping(value = "/api/v1/statistics")
@CrossOrigin(origins = "*")
public class StatisticController {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  public static final String branchName = "Statistic";
  private JwtTokenUtil jwtTokenUtil;

  @Autowired public void JwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
    this.jwtTokenUtil = jwtTokenUtil;
  }

  private StatisticService statisticService;

  @Autowired public void StatisticService(StatisticService statisticService) {
    this.statisticService = statisticService;
  }

  @GetMapping("/order")
  @RolesAllowed({ERole.Names.ADMIN})
  public ResponseObject<List<GroupOrderByDate>> statisticOrder(
      @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
      @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
      @RequestParam(value = "timeDistance", required = false, defaultValue = "DAY") ETimeDistance timeDistance,
      HttpServletRequest request
  ) {
    String loginKey = jwtTokenUtil.getUserNameFromRequest(request);
    return new ResponseObject<>(HttpStatus.OK, "", this.statisticService.statisticOrderByCreateDate(loginKey, startDate, endDate, timeDistance));
  }
}
