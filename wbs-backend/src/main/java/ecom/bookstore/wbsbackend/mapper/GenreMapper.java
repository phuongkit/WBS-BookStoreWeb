package ecom.bookstore.wbsbackend.mapper;

import ecom.bookstore.wbsbackend.dto.response.GenreResponseDTO;
import ecom.bookstore.wbsbackend.entities.Genre;

/**
 * @author minh phuong
 * @created 08/11/2022 - 4:43 PM
 */
public interface GenreMapper {
  GenreResponseDTO genreToGenreResponseDTO(Genre entity);
}
