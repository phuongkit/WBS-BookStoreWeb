package ecom.bookstore.wbsbackend.mapper;

import ecom.bookstore.wbsbackend.dto.request.AddressCreationDTO;
import ecom.bookstore.wbsbackend.entities.Location;


/**
 * @author minh phuong
 * @created 08/10/2022 - 8:08 PM
 */
public interface LocationMapper {
  Location AddressCreationDTOToLocation(AddressCreationDTO creationDTO);
}
