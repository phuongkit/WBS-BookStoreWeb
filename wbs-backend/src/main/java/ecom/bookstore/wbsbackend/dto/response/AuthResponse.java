package ecom.bookstore.wbsbackend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import ecom.bookstore.wbsbackend.models.enums.EGender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author minh phuong
 * @created 08/09/2022 - 8:04 PM
 * @project gt-backend
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
  private String phone;
  private String email;
  private String accessToken;

  private Integer id;
  private String username;
//  private boolean isChangedUsername;
  private String firstName;
  private String lastName;
  private boolean isEmailVerified;
  private boolean isPhoneVerified;
  private String identityCard;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date birthDate;
  private EGender gender;
  private AddressResponseDTO address;
  private boolean enabled;
  private String avatar;
  private int role;
  private Date createdAt;
  private Date updatedAt;
  private Date lastLogin;
}
