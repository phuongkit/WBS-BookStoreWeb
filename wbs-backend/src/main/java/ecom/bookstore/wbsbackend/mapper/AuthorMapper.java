package ecom.bookstore.wbsbackend.mapper;

import ecom.bookstore.wbsbackend.dto.response.AuthorResponseDTO;
import ecom.bookstore.wbsbackend.entities.Author;

/**
 * @author minh phuong
 * @created 15/11/2022 - 5:57 PM
 */
public interface AuthorMapper {
  AuthorResponseDTO authorToAuthorResponseDTO(Author entity);
}
