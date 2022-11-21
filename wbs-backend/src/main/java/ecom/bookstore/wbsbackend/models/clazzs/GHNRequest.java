package ecom.bookstore.wbsbackend.models.clazzs;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author minh phuong
 * @created 20/11/2022 - 10:59 PM
 */
@Data
@ToString
public class GHNRequest {
  String to_name;
  String to_phone;
  String to_address;
  int to_district_id;
  String to_ward_code;
  int weight;
  int length;
  int width;
  int height;
  int service_type_id;
  int service_id;
  int payment_type_id;
  String required_note;
  List<GHNItem> items;
}
