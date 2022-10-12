package ecom.bookstore.wbsbackend.models.clazzs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author minh phuong
 * @created 04/10/2022 - 3:24 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomPage<T> {
  private List content;
  private CustomPageable pageable;
  private int totalPages;
  private long totalElements;
  private int size;
  private int number;
  private Sort sort;
  private boolean isFirst;
  private int numberOfElements;
  private boolean isEmpty;

  public CustomPage(Page page) {
    this.content = page.getContent();
    this.pageable = new CustomPageable(page.getPageable());
    this.totalPages = page.getTotalPages();
    this.totalElements = page.getTotalElements();
    this.size = page.getSize();
    this.number = page.getNumber() + 1;
    this.sort = page.getSort();
    this.isFirst = page.isFirst();
    this.numberOfElements = page.getNumberOfElements();
    this.isEmpty = page.isEmpty();
  }
}
