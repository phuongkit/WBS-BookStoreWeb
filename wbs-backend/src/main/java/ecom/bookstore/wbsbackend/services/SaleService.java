package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.dto.request.SaleCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.dto.response.SaleResponseDTO;
import ecom.bookstore.wbsbackend.entities.Sales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author minh phuong
 * @created 16/09/2022 - 9:04 PM
 */
public interface SaleService {
  Page<SaleResponseDTO> getAllSales(boolean isHasChild, Pageable pageable);

  Page<SaleResponseDTO> searchAllSales(String name, Double fromPercent, Double toPercent, Date fromDate,
                                       Date toDate, boolean isHasChild, Pageable pageable);

  SaleResponseDTO getSaleById(Long id, boolean isHasChild);

  Sales getMostOptimalSaleByBook(Long bookId);

  ResponseEntity<ResponseObject> createSale(SaleCreationDTO creationDTO, MultipartFile thumbnailFile);

  ResponseEntity<ResponseObject> updateSale(Long id, SaleCreationDTO creationDTO, MultipartFile thumbnailFile);

  ResponseEntity<ResponseObject> deleteSaleById(Long id);
}
