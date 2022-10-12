package ecom.bookstore.wbsbackend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author minh phuong
 * @created 16/09/2022 - 8:46 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponseDTO {
  private Long id;
  private String name;
  private String description;
  private Double percent;
  private BookGalleryDTO[] bookGalleries;
  private UserSimpleResponseDTO creator;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date startDate;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date endDate;
  private String thumbnail;
}
