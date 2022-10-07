package ecom.bookstore.wbsbackend.models.clazzs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author minh phuong
 * @created 04/10/2022 - 3:30 PM
 * @project gt-backend
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomPageable {
  private Sort sort;
  private long offset;
  private int pageSize;
  private int pageNumber;
  private boolean isPaged;
  private boolean isUnpaged;

  public CustomPageable(Pageable pageable) {
    this.sort = pageable.getSort();
    this.offset = pageable.getOffset();
    this.pageSize = pageable.getPageSize();
    this.pageNumber = pageable.getPageNumber() + 1;
    this.isPaged = pageable.isPaged();
    this.isUnpaged = pageable.isUnpaged();
  }
}
