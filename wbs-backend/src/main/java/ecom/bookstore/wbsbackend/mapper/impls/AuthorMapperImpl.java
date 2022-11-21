package ecom.bookstore.wbsbackend.mapper.impls;

import ecom.bookstore.wbsbackend.dto.response.AuthorResponseDTO;
import ecom.bookstore.wbsbackend.entities.Author;
import ecom.bookstore.wbsbackend.mapper.AuthorMapper;
import org.springframework.stereotype.Component;

/**
 * @author minh phuong
 * @created 21/11/2022 - 7:47 PM
 */
@Component
public class AuthorMapperImpl implements AuthorMapper {
  @Override public AuthorResponseDTO authorToAuthorResponseDTO(Author entity) {
    if (entity == null) {
      return null;
    }
    AuthorResponseDTO responseDTO = new AuthorResponseDTO();
    responseDTO.setId(entity.getId());
    responseDTO.setFullName(entity.getFullName());
    responseDTO.setGender(entity.getGender().ordinal());
    return responseDTO;
  }
}
