package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.dto.request.PublisherCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.PublisherResponseDTO;

import java.util.List;

/**
 * @Publisher minh phuong
 * @created 15/11/2022 - 5:53 PM
 */
public interface PublisherService {
  List<PublisherResponseDTO> getAllPublisher();
  PublisherResponseDTO getPublisherById(Integer id);
  PublisherResponseDTO createPublisher(PublisherCreationDTO creationDTO);
  PublisherResponseDTO updatePublisher(Integer id, PublisherCreationDTO creationDTO);
  PublisherResponseDTO deletePublisherById(Integer id);
}
