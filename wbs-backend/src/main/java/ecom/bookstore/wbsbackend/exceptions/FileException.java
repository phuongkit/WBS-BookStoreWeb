package ecom.bookstore.wbsbackend.exceptions;

/**
 * @author minh phuong
 * @created 09/09/2022 - 7:20 PM
 */
public class FileException extends RuntimeException{
  public FileException() {
  }

  public FileException(String message) {
    super(message);
  }

  public FileException(String message, Throwable cause) {
    super(message, cause);
  }
}
