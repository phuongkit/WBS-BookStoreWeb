package ecom.bookstore.wbsbackend.mapper.impls;

import ecom.bookstore.wbsbackend.dto.response.BookGalleryDTO;
import ecom.bookstore.wbsbackend.dto.response.BookResponseDTO;
import ecom.bookstore.wbsbackend.entities.Book;
import ecom.bookstore.wbsbackend.entities.Image;
import ecom.bookstore.wbsbackend.entities.Sales;
import ecom.bookstore.wbsbackend.mapper.BookMapper;
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
public class BookMapperImpl implements BookMapper {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
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

  @Override
  public BookResponseDTO bookToBookResponseDTO(Book entity) {
    if (entity == null) {
      return null;
    }
    BookResponseDTO responseDTO = new BookResponseDTO();
    responseDTO.setId(entity.getId());
    if (entity.getThumbnail() != null) {
      responseDTO.setImg(Utils.getUrlFromPathImage(entity.getThumbnail().getPath()));
    }
    responseDTO.setTitle(entity.getName());
    responseDTO.setQuantity(entity.getQuantity());
    //    responseDTO.setUrl();
    responseDTO.setSlug(entity.getSlug());
    //    responseDTO.getPromotion()
    Sales sale = this.saleService.getMostOptimalSaleByBook(entity.getId());
    if (sale != null) {
      BigDecimal salePrice = entity.getListPrice().multiply(BigDecimal.valueOf(sale.getPercent()));
      responseDTO.setPrice(salePrice);
      responseDTO.setDiscount(sale.getPercent());
      responseDTO.setTag(sale.getName());
    } else {
      responseDTO.setPrice(entity.getListPrice());
    }
    Rating rating = this.reviewService.getRatingByBook(entity.getId());
    if (rating != null) {
      responseDTO.setStar(rating.getStar());
      responseDTO.setTotalVote(rating.getTotalVote());
      responseDTO.setVote(rating.getVoteDetails());
    } else {
      responseDTO.setStar(0);
      responseDTO.setTotalVote(0);
    }
    if (entity.getCategory() != null) {
      responseDTO.setCategory(entity.getCategory().getName());
      responseDTO.setCategorySlug(entity.getCategory().getSlug());
    }
    //    responseDTO.setLocation();
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
    responseDTO.setInfo(entity.getDescription());
    return responseDTO;
  }

  @Override
  public BookGalleryDTO bookToBookGalleryDTO(Book entity) {
    if (entity == null) {
      return null;
    }
    BookGalleryDTO responseDTO = new BookGalleryDTO();
    responseDTO.setId(entity.getId());
    if (entity.getThumbnail() != null) {
      responseDTO.setImg(Utils.getUrlFromPathImage(entity.getThumbnail().getPath()));
    } else {
      responseDTO.setImg(Utils.getUrlFromPathImage(IMAGE_DEFAULT_PATH));
    }
    responseDTO.setTitle(entity.getName());
    responseDTO.setQuantity(entity.getQuantity());
    //    responseDTO.setUrl();
    responseDTO.setSlug(entity.getSlug());
    //    responseDTO.getPromotion()
    //    responseDTO.setLocation();
    Sales sales = this.saleService.getMostOptimalSaleByBook(entity.getId());
    if (sales != null) {
      BigDecimal salePrice = entity.getListPrice().multiply(BigDecimal.valueOf(sales.getPercent()));
      responseDTO.setPrice(salePrice);
      responseDTO.setDiscount(sales.getPercent());
      responseDTO.setTag(sales.getName());
    } else {
      responseDTO.setPrice(entity.getListPrice());
    }
    Rating rating = this.reviewService.getRatingByBook(entity.getId());
    if (rating != null) {
      responseDTO.setStar(rating.getStar());
      responseDTO.setTotalVote(rating.getTotalVote());
    } else {
      responseDTO.setStar(0);
      responseDTO.setTotalVote(0);
    }
    return responseDTO;
  }
}
