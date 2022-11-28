package ecom.bookstore.wbsbackend.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * @author minh phuong
 * @created 27/11/2022 - 8:43 PM
 */
public class OAuth2AuthenticationProcessingException extends AuthenticationException {
  public OAuth2AuthenticationProcessingException(String msg, Throwable t) {
    super(msg, t);
  }

  public OAuth2AuthenticationProcessingException(String msg) {
    super(msg);
  }
}