package ecom.bookstore.wbsbackend.exceptions;

/**
 * @author minh phuong
 * @created 13/10/2022 - 4:57 PM
 */
public class UserNotPermissionException extends RuntimeException{
  public UserNotPermissionException(String message){
    super(message);
  }

  public UserNotPermissionException(String message, Throwable cause){
    super(message, cause);
  }
}
