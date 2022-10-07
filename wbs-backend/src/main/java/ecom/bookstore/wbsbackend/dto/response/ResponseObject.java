package ecom.bookstore.wbsbackend.dto.response;

import org.springframework.http.HttpStatus;

/**
 * @author minh phuong
 * @created 07/09/2022 - 11:06 PM
 * @project gt-backend
 */
public class ResponseObject {
  private HttpStatus status;
  private String message;
  private Object data;

  public ResponseObject() {
  }

  public ResponseObject(HttpStatus status, String message, Object data) {
    this.status = status;
    this.message = message;
    this.data = data;
  }

  public ResponseObject(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
    this.data = null;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
