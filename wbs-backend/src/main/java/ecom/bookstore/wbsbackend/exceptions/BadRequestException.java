package ecom.bookstore.wbsbackend.exceptions;

/**
 * @author minh phuong
 * @created 27/11/2022 - 8:44 PM
 */
public class BadRequestException extends RuntimeException {
  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(String message, Throwable cause) {
    super(message, cause);
  }
}