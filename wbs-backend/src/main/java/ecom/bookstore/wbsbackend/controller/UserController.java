package ecom.bookstore.wbsbackend.controller;

import ecom.bookstore.wbsbackend.dto.request.UserCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.dto.response.UserResponseDTO;
import ecom.bookstore.wbsbackend.entities.User;
import ecom.bookstore.wbsbackend.models.enums.ERole;
import ecom.bookstore.wbsbackend.services.UserService;
import ecom.bookstore.wbsbackend.utils.JwtTokenUtil;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static ecom.bookstore.wbsbackend.utils.Utils.DEFAULT_PAGE;
import static ecom.bookstore.wbsbackend.utils.Utils.USERS_PER_PAGE;

/**
 * @author minh phuong
 * @created 08/09/2022 - 8:00 PM
 */
@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  public static final String branchName = User.class.getSimpleName();
  private JwtTokenUtil jwtTokenUtil;

  @Autowired public void JwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
    this.jwtTokenUtil = jwtTokenUtil;
  }

  private UserService userService;

  @Autowired public void UserService(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  @RolesAllowed({ERole.Names.ADMIN})
  public ResponseObject<Page<UserResponseDTO>> getAllUsers(
      @RequestParam(name = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
      @RequestParam(name = "limit", required = false, defaultValue = USERS_PER_PAGE) Integer size,
      @RequestParam(name = "sortField", required = false, defaultValue = "id") String sortField,
      @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir,
      @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword
  ) {
    Sort sort = Sort.by(sortField);
    sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
    Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, size, sort);
    return new ResponseObject<>(
        HttpStatus.OK, "", userService.getAllUsers(keyword, pageable));
  }

  @GetMapping("/{id}")
  public ResponseObject<UserResponseDTO> getUserById(@PathVariable(name = "id") Integer id) {
    return new ResponseObject<>(
        HttpStatus.OK, "", this.userService.getUserById(id));
  }

  @GetMapping("/access-token")
//  @RolesAllowed({ERole.Names.CUSTOMER, ERole.Names.SELLER, ERole.Names.ADMIN})
  public ResponseObject<UserResponseDTO> getUserByAccessToken(HttpServletRequest request) {
    String accessToken = jwtTokenUtil.getAccessToken(request);
    UserResponseDTO responseDTO = null;
    if (accessToken != null) {
      responseDTO = this.userService.getUserByAccessToken(accessToken);
    }
    return new ResponseObject<>(
        HttpStatus.OK, "", responseDTO);
  }

  //  @RequestMapping(method = RequestMethod.POST,
//      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  @PostMapping
  public ResponseObject<UserResponseDTO> createUser(
      @RequestPart("data") @Valid UserCreationDTO userCreationDTO,
      @RequestPart(value = "image", required = false) MultipartFile imageFile
  ) {
    return new ResponseObject<>(HttpStatus.CREATED, String.format(Utils.CREATE_OBJECT_SUCCESSFULLY, branchName),
                                this.userService.createUser(userCreationDTO, imageFile));
  }

  @PutMapping("/{id}")
  public ResponseObject<UserResponseDTO> updateUser(
      @RequestPart("data") @Valid UserCreationDTO userCreationDTO,
      @RequestPart(value = "image", required = false) MultipartFile imageFile,
      @PathVariable(name = "id") Integer id
  ) {
    return new ResponseObject<>(HttpStatus.OK, String.format(Utils.UPDATE_OBJECT_SUCCESSFULLY, branchName),
                                this.userService.updateUser(id, userCreationDTO, imageFile));
  }

  @PutMapping("/{id}/status")
  @RolesAllowed({ERole.Names.ADMIN})
  public ResponseObject<UserResponseDTO> updateStatusUser(
      @RequestParam(name = "enabled", required = false, defaultValue = "false") boolean enable,
      @PathVariable(name = "id") Integer id
  ) {
    return new ResponseObject<>(HttpStatus.OK, String.format(Utils.UPDATE_OBJECT_SUCCESSFULLY, branchName),
                                this.userService.updateStatusUser(id, enable));
  }

  @DeleteMapping("/{id}")
  @RolesAllowed({ERole.Names.ADMIN})
  public ResponseObject<UserResponseDTO> deleteUser(@PathVariable(name = "id") Integer id) {
    return new ResponseObject<>(HttpStatus.OK, String.format(Utils.DELETE_OBJECT_SUCCESSFULLY, branchName),
                                this.userService.deleteUserById(id));
  }
}
