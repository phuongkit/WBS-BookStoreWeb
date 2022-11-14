package ecom.bookstore.wbsbackend.mapper.impls;

import ecom.bookstore.wbsbackend.dto.response.ProductGalleryDTO;
import ecom.bookstore.wbsbackend.dto.response.SaleResponseDTO;
import ecom.bookstore.wbsbackend.entities.Product;
import ecom.bookstore.wbsbackend.entities.Sale;
import ecom.bookstore.wbsbackend.mapper.ProductMapper;
import ecom.bookstore.wbsbackend.mapper.SaleMapper;
import ecom.bookstore.wbsbackend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author minh phuong
 * @created 16/09/2022 - 8:47 PM
 */
@Component
public class SaleMapperImpl implements SaleMapper {
  private ProductMapper productMapper;

  @Autowired
  public void BookMapper(ProductMapper productMapper) {
    this.productMapper = productMapper;
  }

  private UserMapper userMapper;

  @Autowired
  public void UserMapper(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  @Override
  public SaleResponseDTO saleToSaleResponseDTO(Sale entity, boolean... isFull) {
    if (entity == null) {
      return null;
    }
    SaleResponseDTO responseDTO = new SaleResponseDTO();
    responseDTO.setId(entity.getId());
    responseDTO.setName(entity.getName());
    responseDTO.setDescription(entity.getDescription());
    responseDTO.setPercent(entity.getPercent());
    if (isFull.length > 0 && isFull[0] && entity.getProducts() != null) {
      ProductGalleryDTO[] BookGalleries = new ProductGalleryDTO[entity.getProducts().size()];
      int i = 0;
      for (Product product : entity.getProducts()) {
        BookGalleries[i] = new ProductGalleryDTO();
        BookGalleries[i] = this.productMapper.productToProductGalleryDTO(product);
        i++;
      }
      responseDTO.setBookGalleries(BookGalleries);
    }
    if (entity.getCreator() != null) {
      responseDTO.setCreator(this.userMapper.userToUserSimpleResponseDTO(entity.getCreator()));
    }
    responseDTO.setStartDate(entity.getStartDate());
    responseDTO.setEndDate(entity.getEndDate());
    if (entity.getThumbnail() != null) {
      responseDTO.setThumbnail(entity.getThumbnail().getPath());
    }
    return responseDTO;
  }
}
