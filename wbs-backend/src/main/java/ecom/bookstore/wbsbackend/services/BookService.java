package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.dto.request.BookCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.BookResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author minh phuong
 * @created 11/10/2022 - 9:45 PM
 */
public interface BookService {
  ResponseEntity<Page<BookResponseDTO>> getAllBooks(String keyword, Pageable pageable);

  ResponseEntity<Page<BookResponseDTO>> getAllBooksByCategory(Integer categoryId, Pageable pageable);

  ResponseEntity<ResponseObject> getBookById(Long bookId);

  ResponseEntity<ResponseObject> getBookBySlug(String bookSlug);

  ResponseEntity<ResponseObject> createBook(BookCreationDTO creationDTO);

  ResponseEntity<ResponseObject> updateBook(Long bookId, BookCreationDTO creationDTO);

  ResponseEntity<ResponseObject> deleteBookById(Long bookId);
}
