package ecom.bookstore.wbsbackend.mapper.impls;

import ecom.bookstore.wbsbackend.dto.response.DiscountResponseDTO;
import ecom.bookstore.wbsbackend.entities.Discount;
import ecom.bookstore.wbsbackend.mapper.DiscountMapper;
import org.springframework.stereotype.Component;

/**
 * @author minh phuong
 * @created 20/09/2022 - 9:55 PM
 * @project gt-backend
 */
@Component
public class DiscountMapperImpl implements DiscountMapper {
  @Override
  public DiscountResponseDTO discountToDiscountResponseDTO(Discount entity) {
    return null;
  }
}
