package ecom.bookstore.wbsbackend.services.impls;

import ecom.bookstore.wbsbackend.dto.request.AuthRequest;
import ecom.bookstore.wbsbackend.dto.request.UserCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.UserResponseDTO;
import ecom.bookstore.wbsbackend.entities.*;
import ecom.bookstore.wbsbackend.entities.keys.AddressKey;
import ecom.bookstore.wbsbackend.exceptions.InvalidFieldException;
import ecom.bookstore.wbsbackend.exceptions.ResourceAlreadyExistsException;
import ecom.bookstore.wbsbackend.exceptions.ResourceNotFoundException;
import ecom.bookstore.wbsbackend.exceptions.UserNotPermissionException;
import ecom.bookstore.wbsbackend.mapper.UserMapper;
import ecom.bookstore.wbsbackend.models.enums.*;
import ecom.bookstore.wbsbackend.repositories.RoleRepo;
import ecom.bookstore.wbsbackend.repositories.UserRepo;
import ecom.bookstore.wbsbackend.services.AddressService;
import ecom.bookstore.wbsbackend.services.ImageService;
import ecom.bookstore.wbsbackend.services.LocationService;
import ecom.bookstore.wbsbackend.services.UserService;
import ecom.bookstore.wbsbackend.utils.CodeConfig;
import ecom.bookstore.wbsbackend.utils.GenerateUtil;
import ecom.bookstore.wbsbackend.utils.JwtTokenUtil;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author minh phuong
 * @created 07/09/2022 - 11:07 PM
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  public static final String branchName = User.class.getSimpleName();

  private AddressService addressService;

  @Autowired public void AddressService(AddressService addressService) {
    this.addressService = addressService;
  }

  private ImageService imageService;

  @Autowired public void ImageService(ImageService imageService) {
    this.imageService = imageService;
  }

  private JwtTokenUtil jwtTokenUtil;

  @Autowired public void JwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
    this.jwtTokenUtil = jwtTokenUtil;
  }

  private LocationService locationService;

  @Autowired public void LocationService(LocationService locationService) {
    this.locationService = locationService;
  }

  private PasswordEncoder passwordEncoder;

  @Autowired public void PasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  private RoleRepo roleRepo;

  @Autowired public void RoleRepo(RoleRepo roleRepo) {
    this.roleRepo = roleRepo;
  }
  private UserMapper userMapper;

  @Autowired public void UserMapper(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  private UserRepo userRepo;

  @Autowired public void UserRepo(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  @Override public UserDetails loadUserByUsername(String loginKey) throws UsernameNotFoundException {
    if (Utils.isPattern(loginKey) == EPattern.PHONE) {
      return this.userRepo.findByPhone(loginKey)
          .orElseThrow(() -> new UsernameNotFoundException(Utils.USER_NOT_PRESENT));
    } else {
      return this.userRepo.findByEmail(loginKey)
          .orElseThrow(() -> new UsernameNotFoundException(Utils.USER_NOT_PRESENT));
    }
  }

  @Override public Page<UserResponseDTO> getAllUsers(String keyword, Pageable pageable) {
    this.LOGGER.info(
        String.format(Utils.LOG_GET_ALL_OBJECT_BY_FIELD, branchName, "Keyword", keyword));
    Page<User> users = this.userRepo.findAll(keyword, pageable);
    if (users.getContent().size() < 1) {
      throw new ResourceNotFoundException(String.format(Utils.OBJECT_NOT_FOUND, branchName));
    }
    return users.map(user -> this.userMapper.userToUserResponseDTO(user));
  }

  @Override public UserResponseDTO getUserById(Integer id) {
    this.LOGGER.info(String.format(Utils.LOG_GET_OBJECT, branchName, "ID", id));
    return this.userMapper.userToUserResponseDTO(this.userRepo.findById(id).orElseThrow(
        () -> new ResourceNotFoundException(
            String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchName, "ID", id))));
  }

  @Override public UserResponseDTO getUserByAccessToken(String accessToken) {
    this.LOGGER.info(String.format(Utils.LOG_GET_OBJECT, branchName, "accessToken", accessToken));
    String loginKey = jwtTokenUtil.getUserNameFromJwtToken(accessToken);
    User entityFound;
    if (Utils.isPattern(loginKey) == EPattern.PHONE) {
      entityFound = this.userRepo
          .findByPhone(loginKey)
          .orElseThrow(
              () ->
                  new ResourceNotFoundException(
                      String.format(
                          Utils.OBJECT_NOT_FOUND_BY_FIELD,
                          User.class.getSimpleName(),
                          "accessToken",
                          accessToken)));
    } else {
      entityFound = this.userRepo
          .findByEmail(loginKey)
          .orElseThrow(
              () ->
                  new ResourceNotFoundException(
                      String.format(
                          Utils.OBJECT_NOT_FOUND_BY_FIELD,
                          User.class.getSimpleName(),
                          "accessToken",
                          accessToken)));
    }
    return this.userMapper.userToUserResponseDTO(entityFound);
  }

  @Override public User getUserByLoginKey(String loginKey) {
    User userFound = null;
    if (Utils.isPattern(loginKey) == EPattern.PHONE) {
      userFound = this.userRepo
          .findByPhone(loginKey)
          .orElseThrow(
              () ->
                  new ResourceNotFoundException(
                      Utils.USER_NOT_PRESENT));
    } else {
      userFound = this.userRepo
          .findByEmail(loginKey)
          .orElseThrow(
              () ->
                  new ResourceNotFoundException(
                      Utils.USER_NOT_PRESENT));
    }
    return userFound;
  }

  @Override public UserResponseDTO registerUser(AuthRequest auth, boolean isSeller) {
    this.LOGGER.info(String.format(Utils.LOG_REGISTER_OBJECT, branchName, "Phone", auth.getPhone()));
    if (auth.getEmail() == null && auth.getPhone() == null) {
      throw new InvalidFieldException(String.format(Utils.BOTH_FIELDS_NOT_BLANK, "Phone", "Email"));
    } else if (auth.isOtp()) {
      if (auth.getPhone() == null) {
        throw new InvalidFieldException(String.format(Utils.FIELD_NOT_BLANK, "Phone"));
      }
      if (Utils.isPattern(auth.getPhone()) != EPattern.PHONE) {
        throw new InvalidFieldException(String.format(Utils.FIELD_NOT_VALID, "Phone"));
      }
      if (auth.getPhone() != null && !isPhoneUnique(auth.getPhone(), true)) {
        throw new ResourceAlreadyExistsException(String.format(Utils.OBJECT_EXISTED, "Phone"));
      }
    } else {
      if (auth.getEmail() == null) {
        throw new InvalidFieldException(String.format(Utils.FIELD_NOT_BLANK, "Email"));
      }
      if (Utils.isPattern(auth.getEmail()) != EPattern.EMAIl) {
        throw new InvalidFieldException(String.format(Utils.FIELD_NOT_VALID, "Email"));
      }
      if (auth.getPassword() == null) {
        throw new InvalidFieldException(String.format(Utils.FIELD_NOT_BLANK, "Password"));
      }
      if (auth.getEmail() != null && !isEmailUnique(auth.getEmail(), true)) {
        throw new ResourceAlreadyExistsException(String.format(Utils.OBJECT_EXISTED, "Email"));
      }
    }
    User user = new User();
    user.setPhone(auth.getPhone());
    user.setEmail(auth.getEmail());
    if (auth.getPassword() != null) {
      user.setPassword(encodePassword(auth.getPassword()));
    } else {
      user.setPassword(encodePassword(Utils.DEFAULT_PASSWORD));
    }
    String usernameGenerate;
    do {
      usernameGenerate = GenerateUtil.generate(CodeConfig.length(Utils.LENGTH_USERNAME_GENERATE));
    } while (!isUsernameUnique(usernameGenerate, true));
    user.setUsername(usernameGenerate);
//      user.setChangedUsername(false);
    user.setPhoneVerified(auth.isOtp());
    user.setEmailVerified(false);
    user.setEnabled(true);
    user.setGender(EGender.UNKNOWN);
    Optional<Role> roleFound = this.roleRepo.findByName(isSeller ? ERole.ROLE_SELLER : ERole.ROLE_CUSTOMER);

    roleFound.ifPresent(user::setRole);

    return this.userMapper.userToUserResponseDTO(this.userRepo.save(user));
  }

  @Override public UserResponseDTO createUser(UserCreationDTO creationDTO, MultipartFile imageFile) {
    this.LOGGER.info(
        String.format(Utils.LOG_CREATE_OBJECT, branchName, "Email", creationDTO.getEmail()));

    if (creationDTO.getEmail() == null) {
      throw new InvalidFieldException(String.format(Utils.FIELD_NOT_BLANK, "Email"));
    }
    if (creationDTO.getPassword() == null) {
      throw new InvalidFieldException(String.format(Utils.FIELD_NOT_BLANK, "Password"));
    }
    if (creationDTO.getPhone() != null && !isPhoneUnique(creationDTO.getPhone(), true)) {
      throw new ResourceAlreadyExistsException(String.format(Utils.OBJECT_EXISTED, "Phone"));
    }
    if (creationDTO.getEmail() != null && !isEmailUnique(creationDTO.getEmail(), true)) {
      throw new ResourceAlreadyExistsException(String.format(Utils.OBJECT_EXISTED, "Email"));
    }
    if (!isUsernameUnique(creationDTO.getUsername(), true)) {
      throw new ResourceAlreadyExistsException(String.format(Utils.OBJECT_EXISTED, "Username"));
    }

    User newUser = new User();
    if (creationDTO.getUsername() == null) {
      String usernameGenerate;
      do {
        usernameGenerate = GenerateUtil.generate(CodeConfig.length(Utils.LENGTH_USERNAME_GENERATE));
      } while (!isUsernameUnique(usernameGenerate, true));
      newUser.setUsername(usernameGenerate);
    } else {
      newUser.setUsername(creationDTO.getUsername());
    }
    if (creationDTO.getPassword() == null) {
      creationDTO.setPassword(encodePassword(Utils.DEFAULT_PASSWORD));
    } else {
      newUser.setPassword(encodePassword(creationDTO.getPassword()));
    }
    newUser.setFirstName(creationDTO.getFirstName());
    newUser.setLastName(creationDTO.getLastName());
    newUser.setEmail(creationDTO.getEmail());
    newUser.setEmailVerified(creationDTO.isEmailVerified());
    newUser.setPhone(creationDTO.getPhone());
    newUser.setPhoneVerified(creationDTO.isPhoneVerified());
    newUser.setIdentityCard(creationDTO.getIdentityCard());
    newUser.setBirthDate(creationDTO.getBirthDate());
    newUser.setGender(creationDTO.getGender());
    newUser.setEnabled(creationDTO.isEnabled());
//    newUser.setRole(getRolesByRoleName(creationDTO.getRole()));
    Role roleFound = this.roleRepo.findByName(creationDTO.getRole()).orElseThrow(() -> new ResourceNotFoundException(
        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, Role.class.getSimpleName(), "Name", creationDTO.getRole())));

    newUser.setRole(roleFound);

    // set image
    if (imageFile != null && !imageFile.isEmpty()) {
      Image avatar = this.imageService.createImageByMultipartFile(imageFile, EImageType.IMAGE_USER);
      newUser.setAvatar(avatar);
    }

    User savedUser = this.userRepo.save(newUser);

    // set address
    if (creationDTO.getAddress() != null) {
      Location location = new Location();
      location.setCommune(creationDTO.getAddress().getWard());
      location.setDistrict(creationDTO.getAddress().getDistrict());
      location.setProvince(creationDTO.getAddress().getCity());
      location = this.locationService.saveLocation(location);
      if (location != null) {
        Address address = new Address();
        address.setId(new AddressKey(savedUser.getId(), location.getId()));
        address.setUser(savedUser);
        address.setLocation(location);
        address.setLine(creationDTO.getAddress().getHomeAdd());
        address.setAddressType(EAddressType.ADDRESS_HOME);
        address.setDefault(true);
        savedUser.addAddress(this.addressService.saveAddress(address));
      }
    }

    return this.userMapper.userToUserResponseDTO(savedUser);
  }

  @Override public UserResponseDTO updateUser(
      Integer id, UserCreationDTO creationDTO,
      MultipartFile imageFile
  ) {
    this.LOGGER.info(String.format(Utils.LOG_UPDATE_OBJECT, branchName, "ID", id));
    User userFound = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(
        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchName, "ID", id)));

    if (creationDTO.getPassword() == null) {
      throw new InvalidFieldException(String.format(Utils.FIELD_NOT_BLANK, "Password"));
    }
    if (creationDTO.getPhone() != null && (userFound.getPhone() == null || (userFound.getPhone() != null && Objects.equals(
        userFound.getPhone(), creationDTO.getPhone()))) && !isPhoneUnique(creationDTO.getPhone(), false)) {
      throw new ResourceAlreadyExistsException(String.format(Utils.OBJECT_EXISTED, "Phone"));
    }
    if (creationDTO.getEmail() != null && (userFound.getEmail() == null || (userFound.getEmail() != null && Objects.equals(
        userFound.getEmail(), creationDTO.getEmail()))) && !isEmailUnique(creationDTO.getEmail(), false)) {
      throw new ResourceAlreadyExistsException(String.format(Utils.OBJECT_EXISTED, "Email"));
    }
    if (creationDTO.getUsername() != null && !isUsernameUnique(creationDTO.getUsername(), false)) {
      throw new ResourceAlreadyExistsException(String.format(Utils.OBJECT_EXISTED, "Username"));
    }

    if (creationDTO.getUsername() != null) {
      userFound.setUsername(creationDTO.getUsername());
    }
    if (creationDTO.getPassword() != null) {
      userFound.setPassword(encodePassword(creationDTO.getPassword()));
    }
    userFound.setFirstName(creationDTO.getFirstName());
    userFound.setLastName(creationDTO.getLastName());
    userFound.setEmail(creationDTO.getEmail());
    userFound.setEmailVerified(creationDTO.isEmailVerified());
    userFound.setPhone(creationDTO.getPhone());
    userFound.setPhoneVerified(creationDTO.isPhoneVerified());
    userFound.setIdentityCard(creationDTO.getIdentityCard());
    userFound.setBirthDate(creationDTO.getBirthDate());
    userFound.setGender(creationDTO.getGender());
    // update address
    if (creationDTO.getAddress() != null) {
      Location location = new Location();
      location.setCommune(creationDTO.getAddress().getWard());
      location.setDistrict(creationDTO.getAddress().getDistrict());
      location.setProvince(creationDTO.getAddress().getCity());
      location = this.locationService.saveLocation(location);
      if (location != null) {
        Address address = new Address();
        address.setUser(userFound);
        address.setLocation(location);
        address.setId(new AddressKey(userFound.getId(), location.getId()));
        address.setLine(creationDTO.getAddress().getHomeAdd());
        address.setDefault(true);
        userFound.addAddress(this.addressService.saveAddress(address));
      }
    }
    userFound.setEnabled(creationDTO.isEnabled());
//    userFound.setRoles(getRolesByRoleName(creationDTO.getRoles()));
    Role roleFound = this.roleRepo.findByName(creationDTO.getRole()).orElseThrow(() -> new ResourceNotFoundException(
        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, Role.class.getSimpleName(), "Name", creationDTO.getRole())));

    userFound.setRole(roleFound);

    // update is image
    if (imageFile != null && !imageFile.isEmpty()) {
      if (userFound.getAvatar() != null) {
        this.imageService.deleteImageById(userFound.getAvatar().getId());
      }
      Image avatar = this.imageService.createImageByMultipartFile(imageFile, EImageType.IMAGE_USER);
      userFound.setAvatar(avatar);
    }

    return this.userMapper.userToUserResponseDTO(this.userRepo.save(userFound));

  }

  @Override public UserResponseDTO updateStatusUser(Integer id, boolean enable) {
    this.LOGGER.info(String.format(Utils.LOG_UPDATE_OBJECT_BY_TWO_FIELD, branchName, "Id", id, "Enable", enable));
    User userFound = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(
        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchName, "ID", id)));
    if (userFound.getRole().getName() == ERole.ROLE_ADMIN) {
      throw new UserNotPermissionException();
    } else {
      userFound.setEnabled(enable);
    }
    return this.userMapper.userToUserResponseDTO(this.userRepo.save(userFound));
  }

  @Override public UserResponseDTO deleteUserById(Integer id) {
    this.LOGGER.info(String.format(Utils.LOG_DELETE_OBJECT, branchName, "ID", id));
    User userFound = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(
        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchName, "ID", id)));
    // delete image
    Image avatar = userFound.getAvatar();
    if (avatar != null) {
      this.imageService.deleteImageById(avatar.getId());
    }

    // delete User
    this.userRepo.deleteById(id);
    return null;
  }

//  private Set<Role> getRolesByRoleName(ERole[] roleNames) {
//    Set<Role> roles = new HashSet<>();
//    for (ERole role : roleNames) {
//      Optional<Role> roleFound = this.roleRepo.findByName(role);
//      if (!roleFound.isPresent()) {
//        throw new ResourceNotFoundException(
//            String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, Role.class.getSimpleName(), "Name", role));
//      }
//      roles.add(roleFound.get());
//    }
//    return roles;
//  }

  private String encodePassword(String rawPassword) {
    return this.passwordEncoder.encode(rawPassword);
  }

  public boolean isUsernameUnique(String username, boolean isCreating) {
    List<User> users = this.userRepo.findUsersByUsername(username);

    return (isCreating && users.size() < 1) || (!isCreating && users.size() < 2);
  }

  public boolean isEmailUnique(String email, boolean isCreating) {
    List<User> users = this.userRepo.findUsersByEmail(email);

    return (isCreating && users.size() < 1) || (!isCreating && users.size() < 2);
  }

  public boolean isPhoneUnique(String phone, boolean isCreating) {
    List<User> users = this.userRepo.findUsersByPhone(phone);

    return (isCreating && users.size() < 1) || (!isCreating && users.size() < 2);
  }
}
