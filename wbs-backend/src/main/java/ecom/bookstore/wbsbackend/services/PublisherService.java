package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.dto.response.PublisherResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.SupplierResponseDTO;
import ecom.bookstore.wbsbackend.entities.Publisher;

import java.util.List;

/**
 * @author minh phuong
 * @created 15/11/2022 - 5:53 PM
 */
public interface PublisherService {
  List<PublisherResponseDTO> getAllPublisher();
}
