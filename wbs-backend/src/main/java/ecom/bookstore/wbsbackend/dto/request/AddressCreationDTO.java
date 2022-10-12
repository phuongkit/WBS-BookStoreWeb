package ecom.bookstore.wbsbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author minh phuong
 * @created 11/10/2022 - 10:07 PM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressCreationDTO {
  //  private String line;
//  private String commune;
//  private String district;
//  private String province;
  private String homeAdd;
  private String ward;
  private String district;
  private String city;
}
