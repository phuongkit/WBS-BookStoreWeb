package ecom.bookstore.wbsbackend.mapper;

import ecom.bookstore.wbsbackend.dto.response.DiscountResponseDTO;
import ecom.bookstore.wbsbackend.entities.Discount;

/**
 * @author minh phuong
 * @created 20/09/2022 - 9:54 PM
 * @project gt-backend
 */
public interface DiscountMapper {
  DiscountResponseDTO discountToDiscountResponseDTO(Discount entity);
}
