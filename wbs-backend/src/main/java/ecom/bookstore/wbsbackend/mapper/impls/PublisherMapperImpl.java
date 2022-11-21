package ecom.bookstore.wbsbackend.mapper.impls;

import ecom.bookstore.wbsbackend.dto.response.PublisherResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.SupplierResponseDTO;
import ecom.bookstore.wbsbackend.entities.Publisher;
import ecom.bookstore.wbsbackend.mapper.PublisherMapper;
import org.springframework.stereotype.Component;

/**
 * @author minh phuong
 * @created 21/11/2022 - 7:49 PM
 */
@Component
public class PublisherMapperImpl implements PublisherMapper {

  @Override public PublisherResponseDTO publisherToPublisherResponseDTO(Publisher entity) {
    if (entity == null) {
      return null;
    }
    PublisherResponseDTO responseDTO = new PublisherResponseDTO();
    responseDTO.setId(entity.getId());
    responseDTO.setName(entity.getName());
    responseDTO.setDescription(entity.getDescription());
    return responseDTO;
  }
}
