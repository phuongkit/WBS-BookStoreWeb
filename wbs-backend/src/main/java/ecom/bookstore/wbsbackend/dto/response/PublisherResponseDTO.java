package ecom.bookstore.wbsbackend.dto.response;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author minh phuong
 * @created 15/11/2022 - 5:46 PM
 */
@Data
public class PublisherResponseDTO {
  private Integer id;
  private String name;
  private String description;
}
