package ecom.bookstore.wbsbackend.mapper.impls;

import ecom.bookstore.wbsbackend.dto.response.BookGalleryDTO;
import ecom.bookstore.wbsbackend.dto.response.SaleResponseDTO;
import ecom.bookstore.wbsbackend.entities.Book;
import ecom.bookstore.wbsbackend.entities.Sales;
import ecom.bookstore.wbsbackend.mapper.BookMapper;
import ecom.bookstore.wbsbackend.mapper.SaleMapper;
import ecom.bookstore.wbsbackend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author minh phuong
 * @created 16/09/2022 - 8:47 PM
 * @project gt-backend
 */
@Component
public class SaleMapperImpl implements SaleMapper {
  private BookMapper bookMapper;

  @Autowired
  public void BookMapper(BookMapper bookMapper) {
    this.bookMapper = bookMapper;
  }

  private UserMapper userMapper;

  @Autowired
  public void UserMapper(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  @Override
  public SaleResponseDTO saleToSaleResponseDTO(Sales entity, boolean... isFull) {
    if (entity == null) {
      return null;
    }
    SaleResponseDTO responseDTO = new SaleResponseDTO();
    responseDTO.setId(entity.getId());
    responseDTO.setName(entity.getName());
    responseDTO.setDescription(entity.getDescription());
    responseDTO.setPercent(entity.getPercent());
    if (isFull.length > 0 && isFull[0] && entity.getBooks() != null) {
      BookGalleryDTO[] BookGalleries = new BookGalleryDTO[entity.getBooks().size()];
      int i = 0;
      for (Book book : entity.getBooks()) {
        BookGalleries[i] = new BookGalleryDTO();
        BookGalleries[i] = this.bookMapper.bookToBookGalleryDTO(book);
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
