package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.dto.response.AuthorResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.SupplierResponseDTO;

import java.util.List;

/**
 * @author minh phuong
 * @created 15/11/2022 - 5:52 PM
 */
public interface AuthorService {
  List<AuthorResponseDTO> getAllAuthor();
}
