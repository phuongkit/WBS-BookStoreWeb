package ecom.bookstore.wbsbackend.mapper.impls;

import ecom.bookstore.wbsbackend.dto.response.TranslatorResponseDTO;
import ecom.bookstore.wbsbackend.entities.Translator;
import ecom.bookstore.wbsbackend.mapper.TranslatorMapper;
import org.springframework.stereotype.Component;

/**
 * @author minh phuong
 * @created 21/11/2022 - 7:46 PM
 */
@Component
public class TranslatorMapperImpl implements TranslatorMapper{
  @Override public TranslatorResponseDTO translatorToTranslatorResponseDTO(Translator entity) {
    if (entity == null) {
      return null;
    }
    TranslatorResponseDTO responseDTO = new TranslatorResponseDTO();
    responseDTO.setId(entity.getId());
    responseDTO.setFullName(entity.getFullName());
    responseDTO.setGender(entity.getGender().ordinal());
    return responseDTO;
  }
}
