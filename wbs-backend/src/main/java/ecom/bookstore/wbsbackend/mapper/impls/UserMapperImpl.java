package ecom.bookstore.wbsbackend.mapper.impls;

import ecom.bookstore.wbsbackend.dto.response.UserResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.UserSimpleResponseDTO;
import ecom.bookstore.wbsbackend.entities.Address;
import ecom.bookstore.wbsbackend.entities.User;
import ecom.bookstore.wbsbackend.mapper.AddressMapper;
import ecom.bookstore.wbsbackend.mapper.UserMapper;
import ecom.bookstore.wbsbackend.models.enums.ERole;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author minh phuong
 * @created 10/09/2022 - 3:38 PM
 */
@Component
public class UserMapperImpl implements UserMapper {
  private AddressMapper addressMapper;

  @Autowired public void AddressMapper(AddressMapper addressMapper) {
    this.addressMapper = addressMapper;
  }

  @Override
  public UserResponseDTO userToUserResponseDTO(User entity) {
    if (entity == null) {
      return null;
    }
    UserResponseDTO responseDTO = new UserResponseDTO();
    responseDTO.setId(entity.getId());
    responseDTO.setUsername(entity.getUsername());
//    responseDTO.setChangedUsername(entity.isChangedUsername());
    responseDTO.setFirstName(entity.getFirstName());
    responseDTO.setLastName(entity.getLastName());
    responseDTO.setFullName(Utils.getFullNameFromLastNameAndFirstName(entity.getLastName(), entity.getFirstName()));
    responseDTO.setEmail(entity.getEmail());
    responseDTO.setEmailVerified(entity.isEmailVerified());
    responseDTO.setPhone(entity.getPhone());
    responseDTO.setPhoneVerified(entity.isPhoneVerified());
    responseDTO.setIdentityCard(entity.getIdentityCard());
    responseDTO.setBirthDate(entity.getBirthDate());
    responseDTO.setGender(entity.getGender().ordinal());

    if (entity.getAddresses() != null && entity.getAddresses().size() > 0) {
      Address defaultAddress = null;
      for (Address address : entity.getAddresses()) {
        if (defaultAddress == null || address.isDefault()) {
          defaultAddress = address;
        }
      }
      responseDTO.setAddress(addressMapper.addressesToAddressResponseDTO(defaultAddress));
    }

    responseDTO.setEnabled(entity.isEnabled());
    if (entity.getAvatar() != null) {
      responseDTO.setAvatar(entity.getAvatar().getPath());
    }
//    int roleSize = entity.getRoles().size();
//    int[] roles = new ERole[roleSize];
//    int i = 0;
//    for (Role role : entity.getRoles()) {
//      roles[i] = role.getName();
//      i++;
//    }
//    responseDTO.setRoles(roles);
    responseDTO.setRole(entity.getRole().getName().ordinal());
    responseDTO.setCreatedAt(entity.getCreatedAt());
    responseDTO.setUpdatedAt(entity.getUpdatedAt());
    responseDTO.setLastLogin(entity.getLastLogin());
    return responseDTO;
  }

  @Override
  public UserSimpleResponseDTO userToUserSimpleResponseDTO(User entity) {
    if (entity == null) {
      return null;
    }
    UserSimpleResponseDTO responseDTO = new UserSimpleResponseDTO();
    responseDTO.setId(entity.getId());
    responseDTO.setUsername(entity.getUsername());
    responseDTO.setFullName(Utils.getFullNameFromLastNameAndFirstName(entity.getLastName(), entity.getFirstName()));
    responseDTO.setEmail(entity.getEmail());
    responseDTO.setPhone(entity.getPhone());
    if (entity.getAvatar() != null) {
      responseDTO.setAvatar(entity.getAvatar().getPath());
    }
    responseDTO.setAdmin(entity.getRole().getName() == ERole.ROLE_ADMIN);
    return responseDTO;
  }
}
