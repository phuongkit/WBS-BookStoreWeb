package ecom.bookstore.wbsbackend.exceptions;

/**
 * @author minh phuong
 * @created 20/10/2022 - 1:58 PM
 */
public class ResourceNotFound extends RuntimeException {
  public ResourceNotFound(String message){
    super(message);
  }

  public ResourceNotFound(String message, Throwable cause){
    super(message, cause);
  }
}
