package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.dto.request.AuthRequest;
import ecom.bookstore.wbsbackend.dto.request.UserCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.dto.response.UserResponseDTO;
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

  ResponseEntity<ResponseObject> registerUser(AuthRequest auth);

  ResponseEntity<ResponseObject> createUser(UserCreationDTO creationDTO, MultipartFile imageFile);

  ResponseEntity<ResponseObject> updateUser(Integer id, UserCreationDTO creationDTO, MultipartFile imageFile);

  ResponseEntity<ResponseObject> deleteUserById(Integer id);
}
