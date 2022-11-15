package ecom.bookstore.wbsbackend.mapper;

import ecom.bookstore.wbsbackend.dto.response.PublisherResponseDTO;
import ecom.bookstore.wbsbackend.entities.Publisher;

/**
 * @author minh phuong
 * @created 15/11/2022 - 5:57 PM
 */
public interface PublisherMapper {
  PublisherResponseDTO publisherToPublisherResponseDTO(Publisher entity);
}
