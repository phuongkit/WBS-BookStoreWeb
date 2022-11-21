package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.dto.request.TranslatorCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.TranslatorResponseDTO;

import java.util.List;

/**
 * @Translator minh phuong
 *
 * @created 21/11/2022 - 7:41 PM
 */
public interface TranslatorService {
  List<TranslatorResponseDTO> getAllTranslator();

  TranslatorResponseDTO getTranslatorById(Integer id);

  TranslatorResponseDTO createTranslator(TranslatorCreationDTO creationDTO);

  TranslatorResponseDTO updateTranslator(Integer id, TranslatorCreationDTO creationDTO);

  TranslatorResponseDTO deleteTranslatorById(Integer id);
}
