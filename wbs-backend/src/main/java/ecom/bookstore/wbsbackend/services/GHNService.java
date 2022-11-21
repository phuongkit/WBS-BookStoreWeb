package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.entities.Order;

import java.io.IOException;
import java.util.Map;

/**
 * @author minh phuong
 * @created 20/11/2022 - 10:03 PM
 */
public interface GHNService {
  Map<String, Object> getInfoShippingFee(Order order) throws IOException;
}
