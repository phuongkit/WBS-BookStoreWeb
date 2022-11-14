package ecom.bookstore.wbsbackend.mapper.impls;

import ecom.bookstore.wbsbackend.dto.response.GenreResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.SeriesResponseDTO;
import ecom.bookstore.wbsbackend.entities.Author;
import ecom.bookstore.wbsbackend.entities.Genre;
import ecom.bookstore.wbsbackend.mapper.GenreMapper;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.springframework.stereotype.Component;

/**
 * @author minh phuong
 * @created 08/11/2022 - 4:45 PM
 */
@Component
public class GenreMapperImpl implements GenreMapper {
  @Override public GenreResponseDTO genreToGenreResponseDTO(Genre entity) {
    if (entity == null) {
      return null;
    }
    GenreResponseDTO responseDTO = new GenreResponseDTO();
    responseDTO.setId(entity.getId());
    responseDTO.setName(entity.getName());
    responseDTO.setSlug(entity.getSlug());
    responseDTO.setDescription(entity.getDescription());
    if (entity.getThumbnail() != null) {
      responseDTO.setThumbnail(Utils.getUrlFromPathImage(entity.getThumbnail().getPath()));
    }
    return responseDTO;
  }
}
