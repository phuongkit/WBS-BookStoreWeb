package ecom.bookstore.wbsbackend.mapper.impls;

import ecom.bookstore.wbsbackend.dto.response.SeriesResponseDTO;
import ecom.bookstore.wbsbackend.entities.Author;
import ecom.bookstore.wbsbackend.entities.Series;
import ecom.bookstore.wbsbackend.mapper.SeriesMapper;
import org.springframework.stereotype.Component;

/**
 * @author minh phuong
 * @created 08/11/2022 - 4:46 PM
 */
@Component
public class SeriesMapperImpl implements SeriesMapper {
  @Override public SeriesResponseDTO seriesToSeriesResponseDTO(Series entity) {
    if (entity == null) {
      return null;
    }
    SeriesResponseDTO responseDTO = new SeriesResponseDTO();
    responseDTO.setId(entity.getId());
    responseDTO.setName(entity.getName());
    responseDTO.setSlug(entity.getSlug());
    responseDTO.setDescription(entity.getDescription());
    if (entity.getSupplier() != null) {
      responseDTO.setSupplier(entity.getSupplier().getName());
    }
    if (entity.getPublisher() != null) {
      responseDTO.setPublisher(entity.getPublisher().getName());
    }
    if (entity.getAuthors() != null && entity.getAuthors().size() > 0) {
      String[] authors = new String[entity.getAuthors().size()];
      int i= 0;
      for (Author author : entity.getAuthors()) {
        authors[i] = author.getFullName();
        i++;
      }
      responseDTO.setAuthors(authors);
    }
    responseDTO.setType(entity.getType());
    return responseDTO;
  }
}
