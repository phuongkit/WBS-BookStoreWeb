package ecom.bookstore.wbsbackend.entities;

import ecom.bookstore.wbsbackend.models.enums.EGender;
import ecom.bookstore.wbsbackend.utils.Utils;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * @author minh phuong
 * @created 07/09/2022 - 11:08 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_user")
public class User implements UserDetails {
  private static final Logger LOGGER = LoggerFactory.getLogger(User.class);
  /** */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "username", length = 20, nullable = false, unique = true)
  @NotNull(message = "An username is required!")
  private String username;

  //  private boolean isChangedUsername;

  @Column(name = "password", length = 64, nullable = false)
  @NotNull(message = "An password is required!")
  private String password;

  @Column(name = "first_name", length = 45)
  //  @NotNull(message = "An firstname is required!")
  private String firstName;

  @Column(name = "last_name", length = 45)
  //  @NotNull(message = "An lastname is required!")
  private String lastName;

  @Column(name = "email", length = 320, unique = true, nullable = false)
  @Size(message = "Invalid email size.", max = 320, min = 10)
//  @NotNull(message = "An email is required!")
  @Pattern(regexp = (Utils.REGEX_EMAIL), message = "Invalid email")
  private String email;

  @Column(name = "is_email_verified", nullable = false)
  @NotNull(message = "An isEmailVerified is required!")
  private boolean isEmailVerified;

  @Column(name = "phone", length = 13, unique = true)
  @Size(message = "Invalid phone size.", max = 13, min = 9)
  //  @NotNull(message = "An phone is required!")
  @Pattern(regexp = (Utils.REGEX_PHONE), message = "Invalid phone")
  private String phone;

  @Column(name = "is_phone_verified", nullable = false)
  @NotNull(message = "An isPhoneVerified is required!")
  private boolean isPhoneVerified;

  @Column(name = "identity_card", length = 12, unique = true)
  @Size(message = "Invalid identityCard size.", max = 12, min = 9)
  private String identityCard;

  @Column(name = "birth_date")
  @Temporal(TemporalType.DATE)
  private Date birthDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender", length = 50, nullable = false)
  @NotNull(message = "An gender is required!")
  private EGender gender;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<Address> addresses = new HashSet<>();

  public void addAddress(Address address) {
    this.addresses.add(address);
  }

  public void removeAddress(Address address) {
    this.addresses.remove(address);
  }

  @Column(name = "enabled", nullable = false)
  @NotNull(message = "An enabled is required!")
  private boolean enabled;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "avatar_id")
  private Image avatar;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "role_id")
  private Role role;

  @Column(name = "created_at")
  @CreationTimestamp
  private Date createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Date updatedAt;

  @Column(name = "last_login")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastLogin;

  //  @Override
  //  public Collection<? extends GrantedAuthority> getAuthorities() {
  //    List<SimpleGrantedAuthority> authories = new ArrayList<>();
  //    for (Role role : roles) {
  //      authories.add(new SimpleGrantedAuthority(role.getName().toString()));
  //    }
  //
  //    return authories;
  //  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> authories = new ArrayList<>();
    authories.add(new SimpleGrantedAuthority(role.getName() + ""));
    return authories;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}
