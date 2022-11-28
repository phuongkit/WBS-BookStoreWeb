package ecom.bookstore.wbsbackend.controller;

import ecom.bookstore.wbsbackend.dto.request.AuthRequest;
import ecom.bookstore.wbsbackend.dto.response.AuthResponse;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.entities.User;
import ecom.bookstore.wbsbackend.mapper.AuthMapper;
import ecom.bookstore.wbsbackend.services.UserService;
import ecom.bookstore.wbsbackend.utils.JwtTokenUtil;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author minh phuong
 * @created 07/09/2022 - 11:13 PM
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/auth")

public class AuthController {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  @Autowired
  AuthenticationManager authManager;
  private AuthMapper authMapper;

  @Autowired public void AuthMapper(AuthMapper authMapper) {
    this.authMapper = authMapper;
  }

  @Autowired
  UserService userService;

  private JwtTokenUtil jwtTokenUtil;

  @Autowired public void JwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
    this.jwtTokenUtil = jwtTokenUtil;
  }

  @PostMapping("/login")
  public ResponseObject<?> login(@RequestBody @Valid AuthRequest request, HttpServletRequest servletRequest) {
    if (request.isOtp()) {
      try {
        userService.loadUserByUsername(request.getPhone());
      } catch (UsernameNotFoundException e) {
        this.userService.registerUser(request, false);
      }
      request.setPassword(Utils.DEFAULT_PASSWORD);
    }
    try {
      Authentication authentication =
          this.authManager.authenticate(
              new UsernamePasswordAuthenticationToken(request.isOtp() ? request.getPhone() : request.getEmail(),
                                                      request.getPassword()));
      String accessToken = this.jwtTokenUtil.generateAccessToken(authentication);
      User user = (User) authentication.getPrincipal();
      AuthResponse response = this.authMapper.userToAuthResponse(user, accessToken);

      return new ResponseObject<>(HttpStatus.OK, "Login Successfully", response);

    } catch (BadCredentialsException ex) {
      return new ResponseObject<>(HttpStatus.UNAUTHORIZED, "Bad login information");
    }
  }

  @PostMapping("/register")
  public ResponseObject<?> register(
      @RequestBody @Valid AuthRequest request,
      @RequestParam(name = "seller", required = false, defaultValue = "false") boolean isSeller
      ) {
    this.userService.registerUser(request, isSeller);
    try {
      Authentication authentication =
          this.authManager.authenticate(
              new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

      String accessToken = jwtTokenUtil.generateAccessToken(authentication);
      User user = (User) authentication.getPrincipal();
      AuthResponse response = this.authMapper.userToAuthResponse(user, accessToken);

      return new ResponseObject<>(HttpStatus.OK, "Register Successfully", response);
    } catch (BadCredentialsException ex) {
      return new ResponseObject<>(HttpStatus.UNAUTHORIZED, "Bad login information");
    }
  }

//  @PostMapping("/register-grant-permission")
//  @RolesAllowed({ERole.Names.CUSTOMER, ERole.Names.ADMIN})
//  public ResponseObject<?> registerGrantPermission(@RequestBody @Valid ShopCreationDTO creationDTO) {
//    this.userService.registerUser(request);
//    try {
//      Authentication authentication =
//          this.authManager.authenticate(
//              new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
//
//      User user = (User) authentication.getPrincipal();
//      String accessToken = jwtTokenUtil.generateAccessToken(user);
////      AuthResponse response = new AuthResponse(user.getPhone(), accessToken);
//      AuthResponse response = this.authMapper.userToAuthResponse(user, accessToken);
//
//      return new ResponseObject<>(HttpStatus.OK, "Register Successfully", response);
//    } catch (BadCredentialsException ex) {
//      return new ResponseObject<>(HttpStatus.UNAUTHORIZED, "Bad login information");
//    }
//  }


  @PostMapping("/check-access-token")
  public ResponseObject<?> checkToken(HttpServletRequest request) {
    try {
      String accessToken = jwtTokenUtil.getAccessToken(request);
      if (jwtTokenUtil.validateAccessToken(accessToken)) {
        return new ResponseObject<>(HttpStatus.OK, "Access token is valid", null);
      }
    } catch (Exception ex) {
      LOGGER.error(ex.getMessage());
    }
    return new ResponseObject<>(HttpStatus.UNAUTHORIZED, "Access token is non valid", null);
  }
}
