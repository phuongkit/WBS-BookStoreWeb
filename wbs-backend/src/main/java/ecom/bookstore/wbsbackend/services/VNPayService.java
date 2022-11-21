package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.dto.request.VNPayCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.VNPayResponseDTO;

import java.io.UnsupportedEncodingException;

/**
 * @author minh phuong
 * @created 20/11/2022 - 6:58 PM
 */
public interface VNPayService {
  VNPayResponseDTO getPaymentUrlVNPay(String ipAddress, VNPayCreationDTO creationDTO) throws UnsupportedEncodingException;
}
