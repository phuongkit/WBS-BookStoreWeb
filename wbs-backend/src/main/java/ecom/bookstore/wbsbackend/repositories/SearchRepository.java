package ecom.bookstore.wbsbackend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author minh phuong
 * @created 10/09/2022 - 2:59 PM
 */
@NoRepositoryBean
public interface SearchRepository<T, ID> extends PagingAndSortingRepository<T, ID> {
  public Page<T> findAll(String keyword, Pageable pageable);
}
