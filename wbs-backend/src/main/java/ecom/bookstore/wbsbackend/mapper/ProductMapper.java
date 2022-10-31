package ecom.bookstore.wbsbackend.mapper;

import ecom.bookstore.wbsbackend.dto.response.ProductGalleryDTO;
import ecom.bookstore.wbsbackend.dto.response.ProductResponseDTO;
import ecom.bookstore.wbsbackend.entities.Product;

/**
 * @author minh phuong
 * @created 11/09/2022 - 9:18 PM
 */
public interface ProductMapper {
  ProductResponseDTO productToProductResponseDTO(Product entity);

  ProductGalleryDTO productToProductGalleryDTO(Product entity);
}
