package ecom.bookstore.wbsbackend.dto.request;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author minh phuong
 * @created 07/10/2022 - 9:45 PM
 */
@Data
@ToString
public class PublisherCreationDTO {
  private String name;
  private String description;
}
