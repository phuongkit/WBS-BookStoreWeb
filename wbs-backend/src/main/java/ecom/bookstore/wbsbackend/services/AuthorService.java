package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.dto.request.AuthorCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.AuthorResponseDTO;

import java.util.List;

/**
 * @author minh phuong
 * @created 15/11/2022 - 5:52 PM
 */
public interface AuthorService {
  List<AuthorResponseDTO> getAllAuthor();
  AuthorResponseDTO getAuthorById(Integer id);
  AuthorResponseDTO createAuthor(AuthorCreationDTO creationDTO);
  AuthorResponseDTO updateAuthor(Integer id, AuthorCreationDTO creationDTO);
  AuthorResponseDTO deleteAuthorById(Integer id);
}
