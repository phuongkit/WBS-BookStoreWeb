package ecom.bookstore.wbsbackend.dto.request;

import ecom.bookstore.wbsbackend.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

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

  public AddressCreationDTO(Set<Address> addresses) {
    if (addresses.iterator().hasNext()) {
      Address address = addresses.iterator().next();
      if (address != null) {
        this.homeAdd = address.getLine();
        this.ward = address.getLocation().getCommune();
        this.district = address.getLocation().getDistrict();
        this.city = address.getLocation().getProvince();
      }
    }
  }
}
