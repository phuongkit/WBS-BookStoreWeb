package ecom.bookstore.wbsbackend.mapper;

import ecom.bookstore.wbsbackend.dto.response.BookGalleryDTO;
import ecom.bookstore.wbsbackend.dto.response.BookResponseDTO;
import ecom.bookstore.wbsbackend.entities.Book;

/**
 * @author minh phuong
 * @created 11/09/2022 - 9:18 PM
 * @project gt-backend
 */
public interface BookMapper {
  BookResponseDTO bookToBookResponseDTO(Book entity);

  BookGalleryDTO bookToBookGalleryDTO(Book entity);
}
