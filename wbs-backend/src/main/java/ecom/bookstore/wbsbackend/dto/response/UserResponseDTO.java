package ecom.bookstore.wbsbackend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import ecom.bookstore.wbsbackend.models.enums.EGender;
import lombok.*;

import java.util.Date;

/**
 * @author minh phuong
 * @created 09/09/2022 - 7:03 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
  private Integer id;
  private String username;
//  private boolean isChangedUsername;
  private String firstName;
  private String lastName;
  private String fullName;
  private String email;
  private boolean isEmailVerified;
  private String phone;
  private boolean isPhoneVerified;
  private String identityCard;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date birthDate;
  private int gender;
  private AddressResponseDTO address;
  private boolean enabled;
  private String avatar;
//  private ERole[] roles;
  private int role;
  private Date createdAt;
  private Date updatedAt;
  private Date lastLogin;
}
