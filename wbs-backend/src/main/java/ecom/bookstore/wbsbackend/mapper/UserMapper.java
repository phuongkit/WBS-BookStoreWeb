package ecom.bookstore.wbsbackend.mapper;

import ecom.bookstore.wbsbackend.dto.response.UserResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.UserSimpleResponseDTO;
import ecom.bookstore.wbsbackend.entities.User;

/**
 * @author minh phuong
 * @created 10/09/2022 - 11:52 AM
 */
public interface UserMapper {
  UserResponseDTO userToUserResponseDTO(User entity);

  UserSimpleResponseDTO userToUserSimpleResponseDTO(User entity);
}
