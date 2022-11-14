package ecom.bookstore.wbsbackend.mapper.impls;

import ecom.bookstore.wbsbackend.dto.request.AddressCreationDTO;
import ecom.bookstore.wbsbackend.entities.Location;
import ecom.bookstore.wbsbackend.mapper.LocationMapper;
import org.springframework.stereotype.Component;

/**
 * @author minh phuong
 * @created 08/10/2022 - 8:09 PM
 */
@Component
public class LocationMapperImpl implements LocationMapper {
  @Override public Location AddressCreationDTOToLocation(AddressCreationDTO creationDTO) {
    if (creationDTO == null) {
      return null;
    }
    Location entity = new Location();
    entity.setCommune(creationDTO.getWard());
    entity.setDistrict(creationDTO.getDistrict());
    entity.setProvince(creationDTO.getCity());

    return entity;
  }
}
