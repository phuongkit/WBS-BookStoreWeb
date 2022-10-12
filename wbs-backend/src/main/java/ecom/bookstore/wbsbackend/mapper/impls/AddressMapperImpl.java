package ecom.bookstore.wbsbackend.mapper.impls;

import ecom.bookstore.wbsbackend.dto.response.AddressResponseDTO;
import ecom.bookstore.wbsbackend.entities.Address;
import ecom.bookstore.wbsbackend.entities.Location;
import ecom.bookstore.wbsbackend.mapper.AddressMapper;
import org.springframework.stereotype.Component;

/**
 * @author minh phuong
 * @created 20/09/2022 - 10:00 PM
 */
@Component
public class AddressMapperImpl implements AddressMapper {
  @Override
  public AddressResponseDTO lineAndLocationToAddressResponseDTO(String line, Location location) {
    if (location == null) {
      return null;
    }
    AddressResponseDTO responseDTO = new AddressResponseDTO();
//    responseDTO.setLine(line);
//    responseDTO.setCommune(location.getCommune());
//    responseDTO.setDistrict(location.getDistrict());
//    responseDTO.setProvince(location.getProvince());
    responseDTO.setHomeAdd(line);
    responseDTO.setWard(location.getCommune());
    responseDTO.setDistrict(location.getDistrict());
    responseDTO.setCity(location.getProvince());
    return responseDTO;
  }

  @Override public AddressResponseDTO addressesToAddressResponseDTO(Address address) {
    if (address == null) {
      return null;
    }
    AddressResponseDTO responseDTO = new AddressResponseDTO();
    responseDTO.setHomeAdd(address.getLine());
    responseDTO.setWard(address.getLocation().getCommune());
    responseDTO.setDistrict(address.getLocation().getDistrict());
    responseDTO.setCity(address.getLocation().getProvince());
    return responseDTO;
  }
}
