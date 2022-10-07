package ecom.bookstore.wbsbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author minh phuong
 * @created 11/09/2022 - 4:57 PM
 * @project gt-backend
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
