package ecom.bookstore.wbsbackend.mapper;

import ecom.bookstore.wbsbackend.dto.response.AuthResponse;
import ecom.bookstore.wbsbackend.entities.User;

/**
 * @author minh phuong
 * @created 01/10/2022 - 12:11 PM
 */
public interface AuthMapper {
  AuthResponse userToAuthResponse(User entity, String accessToken);
}
