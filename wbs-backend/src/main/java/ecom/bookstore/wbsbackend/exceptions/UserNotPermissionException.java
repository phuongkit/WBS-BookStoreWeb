package ecom.bookstore.wbsbackend.exceptions;

import ecom.bookstore.wbsbackend.utils.Utils;

/**
 * @author minh phuong
 * @created 13/10/2022 - 4:57 PM
 */
public class UserNotPermissionException extends RuntimeException{
  public UserNotPermissionException() {
    super(Utils.USER_NOT_PERMISSION);
  }
  public UserNotPermissionException(String message){
    super(message);
  }

  public UserNotPermissionException(String message, Throwable cause){
    super(message, cause);
  }
}
