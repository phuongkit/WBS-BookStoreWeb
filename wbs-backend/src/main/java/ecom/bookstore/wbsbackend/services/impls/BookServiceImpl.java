package ecom.bookstore.wbsbackend.services.impls;

import ecom.bookstore.wbsbackend.dto.request.BookCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.BookResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author minh phuong
 * @created 11/10/2022 - 9:47 PM
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {
  @Override public ResponseEntity<Page<BookResponseDTO>> getAllBooks(String keyword, Pageable pageable) {
    return null;
  }

  @Override public ResponseEntity<Page<BookResponseDTO>> getAllBooksByCategory(Integer categoryId, Pageable pageable) {
    return null;
  }

  @Override public ResponseEntity<ResponseObject> getBookById(Long bookId) {
    return null;
  }

  @Override public ResponseEntity<ResponseObject> getBookBySlug(String bookSlug) {
    return null;
  }

  @Override public ResponseEntity<ResponseObject> createBook(BookCreationDTO creationDTO) {
    return null;
  }

  @Override public ResponseEntity<ResponseObject> updateBook(Long bookId, BookCreationDTO creationDTO) {
    return null;
  }

  @Override public ResponseEntity<ResponseObject> deleteBookById(Long bookId) {
    return null;
  }
}
