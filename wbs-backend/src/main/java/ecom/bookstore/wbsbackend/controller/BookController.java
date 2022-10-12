package ecom.bookstore.wbsbackend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author minh phuong
 * @created 11/10/2022 - 9:41 PM
 */
@RestController
@RequestMapping(value = "/api/v1/books")
@CrossOrigin(origins = "*")
public class BookController {
}
