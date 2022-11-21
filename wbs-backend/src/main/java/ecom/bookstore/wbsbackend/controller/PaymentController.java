package ecom.bookstore.wbsbackend.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ecom.bookstore.wbsbackend.dto.request.VNPayCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.services.VNPayService;
import ecom.bookstore.wbsbackend.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author minh phuong
 * @created 17/11/2022 - 4:37 PM
 */
@RestController
@RequestMapping(value = "/api/v1/payment")
@CrossOrigin("*")
public class PaymentController {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  private VNPayService vnPayService;

  @Autowired public void VNPayService(VNPayService vnPayService) {
    this.vnPayService = vnPayService;
  }

  @PostMapping("/vnpay/return")
  public String getVNPayReturn(HttpServletRequest request, HttpServletResponse resp)
      throws IOException {
//    Map fields = new HashMap();
//    for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
//      String fieldName = (String) params.nextElement();
//      String fieldValue = request.getParameter(fieldName);
//      if ((fieldValue != null) && (fieldValue.length() > 0)) {
//        fields.put(fieldName, fieldValue);
//      }
//    }
//    String vnp_SecureHash = request.getParameter("vnp_SecureHash");
//    if (fields.containsKey("vnp_SecureHashType")) {
//      fields.remove("vnp_SecureHashType");
//    }
//    if (fields.containsKey("vnp_SecureHash")) {
//      fields.remove("vnp_SecureHash");
//    }
//    String signValue = Config.hashAllFields(fields);
//    if (signValue.equals(vnp_SecureHash)) {
//      if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
//        return "GD Thanh cong";
//      } else {
//        return "GD Khong thanh cong";
//      }
//
//    } else {
//      return "Chu ky khong hop le";
//    }
    try {
      Map fields = new HashMap();
      for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
        String fieldName = (String) params.nextElement();
        String fieldValue = request.getParameter(fieldName);
        if ((fieldValue != null) && (fieldValue.length() > 0)) {
          fields.put(fieldName, fieldValue);
        }
      }

      String vnp_SecureHash = request.getParameter("vnp_SecureHash");
      if (fields.containsKey("vnp_SecureHashType")) {
        fields.remove("vnp_SecureHashType");
      }
      if (fields.containsKey("vnp_SecureHash")) {
        fields.remove("vnp_SecureHash");
      }

      // Check checksum
      String signValue = Config.hashAllFields(fields);
      this.LOGGER.info(signValue);
      for (Object key : fields.keySet()) {
        System.out.println(key +";"+ fields.get(key));
      }
//      if (signValue.equals(vnp_SecureHash)) {

        boolean checkOrderId = true; // vnp_TxnRef exists in your database
        boolean checkAmount =
            true; // vnp_Amount is valid (Check vnp_Amount VNPAY returns compared to the amount of the code (vnp_TxnRef) in the Your database).
        boolean checkOrderStatus = true; // PaymnentStatus = 0 (pending)

        if (checkOrderId) {
          if (checkAmount) {
            if (checkOrderStatus) {
              if ("00".equals(request.getParameter("vnp_ResponseCode"))) {

                //Here Code update PaymnentStatus = 1 into your Database
              } else {

                // Here Code update PaymnentStatus = 2 into your Database
              }
              return "{\"RspCode\":\"00\",\"Message\":\"Confirm Success\"}";
            } else {

              return "{\"RspCode\":\"02\",\"Message\":\"Order already confirmed\"}";
            }
          } else {
            return "{\"RspCode\":\"04\",\"Message\":\"Invalid Amount\"}";
          }
        } else {
          return "{\"RspCode\":\"01\",\"Message\":\"Order not Found\"}";
        }
//      } else {
//        return "{\"RspCode\":\"97\",\"Message\":\"Invalid Checksum\"}";
//      }
    } catch (
        Exception e) {
      return "{\"RspCode\":\"99\",\"Message\":\"Unknow error\"}";
    }

  }

  @GetMapping("/vnpay-refund")
  public void getVNPayRefund(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    //vnp_Command = refund
    String vnp_TxnRef = req.getParameter("vnp_TxnRef");
    String vnp_TransDate = req.getParameter("vnp_PayDate");
    String email = req.getParameter("vnp_Bill_Email");
    int amount = Integer.parseInt(req.getParameter("vnp_Amount")) * 100;
    String trantype = req.getParameter("trantype");
    String vnp_TmnCode = Config.vnp_TmnCode;
    String vnp_IpAddr = Config.getIpAddress(req);

    Map<String, String> vnp_Params = new HashMap<>();
    vnp_Params.put("vnp_Version", "2.1.0");
    vnp_Params.put("vnp_Command", "refund");
    vnp_Params.put("vnp_Amount", String.valueOf(amount));
    vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
    vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
    vnp_Params.put("vnp_OrderInfo", "Kiem tra ket qua GD OrderId:" + vnp_TxnRef);
    vnp_Params.put("vnp_TransDate", vnp_TransDate);
    vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
    vnp_Params.put("vnp_CreateBy", email);
    vnp_Params.put("vnp_TransactionType", trantype);

    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String vnp_CreateDate = formatter.format(cld.getTime());
    vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
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
    String paymentUrl = Config.vnp_apiUrl + "?" + queryUrl;
    URL url = new URL(paymentUrl);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    BufferedReader in = new BufferedReader(
        new InputStreamReader(connection.getInputStream()));
    String inputLine;
    StringBuilder response = new StringBuilder();
    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();
    String Rsp = response.toString();
    String respDecode = URLDecoder.decode(Rsp, "UTF-8");
    String[] responseData = respDecode.split("&|\\=");
    JsonObject job = new JsonObject();
    job.addProperty("data", Arrays.toString(responseData));
    Gson gson = new Gson();
    resp.getWriter().write(gson.toJson(job));
  }

  @PostMapping("/vnpay/create-payment-url")
  public ResponseObject<?> getPaymentUrlVNPay(
      @RequestBody @Valid VNPayCreationDTO vnPayCreationDTO,
      HttpServletRequest req
  )
      throws IOException {
    String ipAddress = Config.getIpAddress(req);
    return new ResponseObject<>(HttpStatus.OK,
                                "Get url vnpay sucess",
                                this.vnPayService.getPaymentUrlVNPay(ipAddress, vnPayCreationDTO));
  }
}
