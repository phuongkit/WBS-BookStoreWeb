package ecom.bookstore.wbsbackend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

/**
 * @author minh phuong
 * @created 07/09/2022 - 11:06 PM
 * @project gt-backend
 */
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class ResponseObject<T> {
  private Object status;
  private String message;
  private T data;

  public ResponseObject(Object status, String message, @Nullable T data) {
    this.status = status;
    this.message = message;
    this.data = data;
  }

  public ResponseObject(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
    this.data = null;
  }
}
