package ecom.bookstore.wbsbackend.mapper;

import ecom.bookstore.wbsbackend.dto.response.SeriesResponseDTO;
import ecom.bookstore.wbsbackend.entities.Series;

/**
 * @author minh phuong
 * @created 08/11/2022 - 4:44 PM
 */
public interface SeriesMapper {
  SeriesResponseDTO seriesToSeriesResponseDTO(Series entity);
}
