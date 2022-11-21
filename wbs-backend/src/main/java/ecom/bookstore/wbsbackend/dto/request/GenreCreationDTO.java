package ecom.bookstore.wbsbackend.dto.request;

import ecom.bookstore.wbsbackend.entities.Image;
import ecom.bookstore.wbsbackend.utils.Utils;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author minh phuong
 * @created 07/10/2022 - 10:38 PM
 */
@Data
@ToString
public class GenreCreationDTO {
  private String name;
  private String description;
}
