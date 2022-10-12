package ecom.bookstore.wbsbackend.exceptions;

import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

/**
 * @author minh phuong
 * @created 07/09/2022 - 11:20 PM
 */
@ControllerAdvice
public class GlobalExceptionsHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  protected ResponseEntity<ResponseObject> handlerResourceNotFoundException(
      RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ResponseObject(HttpStatus.NOT_FOUND, exception.getMessage(), null));
  }

  @ExceptionHandler(ResourceAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  @ResponseBody
  protected ResponseEntity<ResponseObject> handlerResourceAlreadyExistsException(
      RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
        .body(new ResponseObject(HttpStatus.NOT_IMPLEMENTED, exception.getMessage(), null));
  }

  @ExceptionHandler({ConstraintViolationException.class,
      FileException.class,
      InvalidFieldException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  protected ResponseEntity<ResponseObject> handleResourceAlreadyExistsException(
      RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ResponseObject(HttpStatus.OK, exception.getMessage(), null));
  }

//  @ExceptionHandler({MissingServletRequestPartException.class, BindException.class})
//  @ResponseStatus(HttpStatus.OK)
//  @ResponseBody
//  protected ResponseEntity<ResponseObject> handleBadRequestException(
//      RuntimeException exception) {
//    return ResponseEntity.status(HttpStatus.OK)
//        .body(new ResponseObject(HttpStatus.BAD_REQUEST, exception.getMessage(), null));
//  }
}
