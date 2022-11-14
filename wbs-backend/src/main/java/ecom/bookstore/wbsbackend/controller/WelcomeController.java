package ecom.bookstore.wbsbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author minh phuong
 * @created 01/10/2022 - 10:09 PM
 */
@RestController
public class WelcomeController {
  @GetMapping("/")
  public String hello() {
    return "welcome to website";
  }
}
