package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.dto.request.AuthRequest;
import ecom.bookstore.wbsbackend.dto.request.UserCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.dto.response.UserResponseDTO;
import ecom.bookstore.wbsbackend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author minh phuong
 * @created 07/09/2022 - 11:07 PM
 */
public interface UserService extends UserDetailsService {
  Page<UserResponseDTO> getAllUsers(String keyword, Pageable pageable);

  UserResponseDTO getUserById(Integer id);

  UserResponseDTO getUserByAccessToken(String accessToken);

  User getUserByLoginKey(String loginKey);

  UserResponseDTO registerUser(AuthRequest auth, boolean isSeller);

  UserResponseDTO createUser(UserCreationDTO creationDTO, MultipartFile imageFile);

  UserResponseDTO updateUser(Integer id, UserCreationDTO creationDTO, MultipartFile imageFile);

  UserResponseDTO deleteUserById(Integer id);
}
