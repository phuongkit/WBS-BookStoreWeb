package ecom.bookstore.wbsbackend.mapper;

import ecom.bookstore.wbsbackend.dto.response.AddressResponseDTO;
import ecom.bookstore.wbsbackend.entities.Address;
import ecom.bookstore.wbsbackend.entities.Location;

/**
 * @author minh phuong
 * @created 20/09/2022 - 9:58 PM
 */
public interface AddressMapper {
  AddressResponseDTO lineAndLocationToAddressResponseDTO(String line, Location location);

  AddressResponseDTO addressesToAddressResponseDTO(Address address);
}
