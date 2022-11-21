package ecom.bookstore.wbsbackend.mapper;

import ecom.bookstore.wbsbackend.dto.response.TranslatorResponseDTO;
import ecom.bookstore.wbsbackend.entities.Translator;

/**
 * @author minh phuong
 * @created 21/11/2022 - 7:45 PM
 */
public interface TranslatorMapper {
  TranslatorResponseDTO translatorToTranslatorResponseDTO(Translator entity);
}
