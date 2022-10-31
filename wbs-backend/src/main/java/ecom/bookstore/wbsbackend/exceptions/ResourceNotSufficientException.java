package ecom.bookstore.wbsbackend.exceptions;

/**
 * @author minh phuong
 * @created 09/10/2022 - 8:39 PM
 * @project gt-backend
 */
public class ResourceNotSufficientException extends RuntimeException{
  public ResourceNotSufficientException(String message){
    super(message);
  }

  public ResourceNotSufficientException(String message, Throwable cause){
    super(message, cause);
  }
}
