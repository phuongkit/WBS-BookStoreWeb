package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.dto.request.BookCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.BookResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author minh phuong
 * @created 11/10/2022 - 9:45 PM
 */
public interface BookService {
  ResponseEntity<Page<BookResponseDTO>> getAllBooks(String keyword, Pageable pageable);

  ResponseEntity<Page<BookResponseDTO>> getAllBooksByCategory(Integer categoryId, Pageable pageable);

  ResponseEntity<ResponseObject> getBookById(Long id);

  ResponseEntity<ResponseObject> getBookBySlug(String slug);

  ResponseEntity<ResponseObject> createBook(BookCreationDTO creationDTO,
                                            MultipartFile thumbnailFile,
                                            MultipartFile[] imageGalleryFile);

  ResponseEntity<ResponseObject> updateBook(Long id, BookCreationDTO creationDTO,
                                            MultipartFile thumbnailFile,
                                            MultipartFile[] imageGalleryFile);

  ResponseEntity<ResponseObject> deleteBookById(Long id);
}
