package ecom.bookstore.wbsbackend.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * @author minh phuong
 * @created 08/10/2022 - 8:48 PM
 * @project gt-backend
 */
@Getter
@Setter
public class ResourceNotValidException extends RuntimeException {
    public ResourceNotValidException(String message){
      super(message);
    }

    public ResourceNotValidException(String message, Throwable cause){
      super(message, cause);
    }
}
