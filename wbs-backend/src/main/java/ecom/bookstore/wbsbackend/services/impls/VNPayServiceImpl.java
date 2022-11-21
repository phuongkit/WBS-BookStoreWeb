package ecom.bookstore.wbsbackend.services.impls;

import ecom.bookstore.wbsbackend.dto.request.VNPayCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.VNPayResponseDTO;
import ecom.bookstore.wbsbackend.services.VNPayService;
import ecom.bookstore.wbsbackend.utils.Config;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author minh phuong
 * @created 20/11/2022 - 6:59 PM
 */
@Service
@Transactional
public class VNPayServiceImpl implements VNPayService {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  public static final String branchName = "VNPAY";

  @Override public VNPayResponseDTO getPaymentUrlVNPay(String ipAddress, VNPayCreationDTO creationDTO)
      throws UnsupportedEncodingException {
    this.LOGGER.info(String.format(Utils.LOG_CREATE_OBJECT_BY_TWO_FIELD,
                                   branchName,
                                   "orderId",
                                   creationDTO.getOrderId(),
                                   "totalPrice",
                                   creationDTO.getTotalPrice()));
    VNPayResponseDTO responseDTO = new VNPayResponseDTO();
    String vnp_TmnCode = Config.vnp_TmnCode;
    String locate = "vn";
    if (creationDTO.getLocate() != null && !creationDTO.getLocate().isEmpty() && !Objects.equals(creationDTO.getLocate()
                                                                                                   .trim(), "")) {
      locate = creationDTO.getLocate();
    }
    String vnp_TxnRef = Config.getRandomNumber(8);

    String vnp_OrderInfo = String.format(Utils.PAYMENT_ORDER,
                                         creationDTO.getFullName(),
                                         creationDTO.getOrderId(),
                                         creationDTO.getTotalPrice(),
                                         branchName);
    String orderType = "1";//req.getParameter("ordertype");
    String vnp_IpAddr = ipAddress;
    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
    responseDTO.setCreateDate(cld.getTime());
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String vnp_CreateDate = formatter.format(cld.getTime());
    cld.add(Calendar.MINUTE, 30);
    responseDTO.setExpireDate(cld.getTime());
    String vnp_ExpireDate = formatter.format(cld.getTime());

    Map vnp_Params = new HashMap<>();

    vnp_Params.put("vnp_Version", "2.1.0");
    vnp_Params.put("vnp_Command", "pay");
    vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
    vnp_Params.put("vnp_Locale", locate);
    vnp_Params.put("vnp_CurrCode", "VND");
    vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
    vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
    vnp_Params.put("vnp_OrderType", orderType);
    vnp_Params.put("vnp_Amount", String.valueOf(creationDTO.getTotalPrice().multiply(new BigInteger("100"))));
    vnp_Params.put("vnp_ReturnUrl", creationDTO.getRedirectUrl());
    vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
    vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
    vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
    if (creationDTO.getBankCode() != null && !Objects.equals(creationDTO.getBankCode().trim(), "")) {
      vnp_Params.put("vnp_BankCode", creationDTO.getBankCode());
    }

    //Build data to hash and querystring
    List fieldNames = new ArrayList(vnp_Params.keySet());
    Collections.sort(fieldNames);
    StringBuilder hashData = new StringBuilder();
    StringBuilder query = new StringBuilder();
    Iterator itr = fieldNames.iterator();
    while (itr.hasNext()) {
      String fieldName = (String) itr.next();
      String fieldValue = (String) vnp_Params.get(fieldName);
      if ((fieldValue != null) && (fieldValue.length() > 0)) {
        //Build hash data
        hashData.append(fieldName);
        hashData.append('=');
        hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
        //Build query
        query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
        query.append('=');
        query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
        if (itr.hasNext()) {
          query.append('&');
          hashData.append('&');
        }
      }
    }
    String queryUrl = query.toString();
    String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
    queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
    this.LOGGER.info(vnp_SecureHash);
    String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
    responseDTO.setPayUrl(paymentUrl);
    return responseDTO;
  }
}
