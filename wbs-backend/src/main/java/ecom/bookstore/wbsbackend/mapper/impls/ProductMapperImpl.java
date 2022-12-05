package ecom.bookstore.wbsbackend.mapper.impls;

import ecom.bookstore.wbsbackend.dto.response.GenreResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.ProductGalleryDTO;
import ecom.bookstore.wbsbackend.dto.response.ProductResponseDTO;
import ecom.bookstore.wbsbackend.entities.*;
import ecom.bookstore.wbsbackend.mapper.GenreMapper;
import ecom.bookstore.wbsbackend.mapper.ProductMapper;
import ecom.bookstore.wbsbackend.mapper.SeriesMapper;
import ecom.bookstore.wbsbackend.models.clazzs.Rating;
import ecom.bookstore.wbsbackend.services.ReviewService;
import ecom.bookstore.wbsbackend.services.SaleService;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static ecom.bookstore.wbsbackend.utils.Utils.IMAGE_DEFAULT_PATH;

/**
 * @author minh phuong
 * @created 11/09/2022 - 9:18 PM
 */
@Component
public class ProductMapperImpl implements ProductMapper {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  private GenreMapper genreMapper;
  @Autowired public void GenreMapper(GenreMapper genreMapper) {
    this.genreMapper  = genreMapper;
  }
  private ReviewService reviewService;

  @Autowired
  public void ReviewService(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  private SaleService saleService;

  @Autowired
  public void SaleService(SaleService saleService) {
    this.saleService = saleService;
  }
  private SeriesMapper seriesMapper;
  @Autowired public void SeriesMapper(SeriesMapper seriesMapper) {
    this.seriesMapper = seriesMapper;
  }

  @Override
  public ProductResponseDTO productToProductResponseDTO(Product entity) {
    if (entity == null) {
      return null;
    }
    ProductResponseDTO responseDTO = new ProductResponseDTO();
    responseDTO.setId(entity.getId());
    if (entity.getThumbnail() != null) {
      responseDTO.setImg(Utils.getUrlFromPathImage(entity.getThumbnail().getPath()));
    }
    responseDTO.setName(entity.getName());
    responseDTO.setSlug(entity.getSlug());
    responseDTO.setAvailableQuantity(entity.getQuantity());
    //    responseDTO.setSoldQuantity();
    responseDTO.setOriginPrice(entity.getListPrice());
    Sale sale = this.saleService.getMostOptimalSaleByProduct(entity.getId());
    if (sale != null) {
      BigDecimal salePrice = Utils.getPriceProduct(entity, sale);
      responseDTO.setSalePrice(salePrice);
      responseDTO.setSale(sale.getPercent());
    }  else {
      responseDTO.setSalePrice(responseDTO.getOriginPrice());
    }
    Rating rating = this.reviewService.getRatingByProduct(entity.getId());
    if (rating != null) {
      responseDTO.setStar(rating.getStar());
      responseDTO.setTotalVote(rating.getTotalVote());
      responseDTO.setVote(rating.getVoteDetails());
    } else {
      responseDTO.setStar(0);
      responseDTO.setTotalVote(0);
    }
    if (entity.getCategory() != null) {
      responseDTO.setCategoryName(entity.getCategory().getName());
      responseDTO.setCategorySlug(entity.getCategory().getSlug());
    }
    responseDTO.setMinAge(entity.getMinAge());
    responseDTO.setMaxAge(entity.getMaxAge());
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
    if (entity.getTranslators() != null && entity.getTranslators().size() > 0) {
      String[] translators = new String[entity.getTranslators().size()];
      int i =0;
      for (Translator translator : entity.getTranslators()) {
        translators[i] = translator.getFullName();
        i++;
      }
      responseDTO.setTranslators(translators);
    }
    if (entity.getSeries() != null) {
      responseDTO.setSeries(seriesMapper.seriesToSeriesResponseDTO(entity.getSeries()));
    }
    responseDTO.setLanguage(entity.getLanguage().getName());
    responseDTO.setPublishYear(entity.getPublishYear());
    responseDTO.setReprintYear(entity.getReprintYear());
    responseDTO.setWeight(entity.getWeight());
    responseDTO.setPackagingHeight(entity.getPackagingHeight());
    responseDTO.setPackagingLength(entity.getPackagingLength());
    responseDTO.setPackagingWidth(entity.getPackagingWidth());
    responseDTO.setNumPages(entity.getNumPages());
    responseDTO.setLayout(entity.getBookLayout());
    if (entity.getGenres() != null && entity.getGenres().size() > 0) {
      GenreResponseDTO[] genres = new GenreResponseDTO[entity.getGenres().size()];
      int i =0;
      for (Genre genre : entity.getGenres()){
        genres[i] = genreMapper.genreToGenreResponseDTO(genre);
        i++;
      }
      responseDTO.setGenres(genres);
    }
    if (entity.getImageGallery() != null && entity.getImageGallery().size() > 0) {
      String[] gallery = new String[entity.getImageGallery().size()];
      int i = 0;
      for (Image image : entity.getImageGallery()) {
        gallery[i] = Utils.getUrlFromPathImage(image.getPath());
        i++;
      }
      responseDTO.setGallery(gallery);
    }
    if (entity.getLocation() != null) {
      responseDTO.setLocation(Utils.getLocationStringFromLocation(entity.getLocation()));
    }
    responseDTO.setDescription(entity.getDescription());
    responseDTO.setStatus(entity.getStatus());
    responseDTO.setCreatedAt(entity.getCreatedAt());
    responseDTO.setUpdatedAt(entity.getUpdatedAt());
    return responseDTO;
  }

  @Override
  public ProductGalleryDTO productToProductGalleryDTO(Product entity) {
    if (entity == null) {
      return null;
    }
    ProductGalleryDTO responseDTO = new ProductGalleryDTO();
    responseDTO.setId(entity.getId());
    if (entity.getThumbnail() != null) {
      responseDTO.setImg(Utils.getUrlFromPathImage(entity.getThumbnail().getPath()));
    } else {
      responseDTO.setImg(Utils.getUrlFromPathImage(IMAGE_DEFAULT_PATH));
    }
    responseDTO.setName(entity.getName());
    responseDTO.setSlug(entity.getSlug());
    responseDTO.setAvailableQuantity(entity.getQuantity());
//    responseDTO.setSoldQuantity();
    responseDTO.setOriginPrice(entity.getListPrice());
    Sale sale = this.saleService.getMostOptimalSaleByProduct(entity.getId());
    if (sale != null) {
      BigDecimal salePrice = Utils.getPriceProduct(entity, sale);
      responseDTO.setSalePrice(salePrice);
      responseDTO.setSale(sale.getPercent());
    } else {
      responseDTO.setSalePrice(responseDTO.getOriginPrice());
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
    Rating rating = this.reviewService.getRatingByProduct(entity.getId());
    if (rating != null) {
      responseDTO.setStar(rating.getStar());
      responseDTO.setTotalVote(rating.getTotalVote());
    } else {
      responseDTO.setStar(0);
      responseDTO.setTotalVote(0);
    }
    if (entity.getCategory() != null) {
      responseDTO.setCategoryName(entity.getCategory().getName());
      responseDTO.setCategorySlug(entity.getCategory().getSlug());
    }
    responseDTO.setWeight(entity.getWeight());
    responseDTO.setPackagingLength(entity.getPackagingLength());
    responseDTO.setPackagingWidth(entity.getPackagingWidth());
    responseDTO.setPackagingHeight(entity.getPackagingHeight());
    return responseDTO;
  }
}
