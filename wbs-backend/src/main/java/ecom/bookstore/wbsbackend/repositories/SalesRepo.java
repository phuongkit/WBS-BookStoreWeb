package ecom.bookstore.wbsbackend.repositories;

import ecom.bookstore.wbsbackend.entities.Product;
import ecom.bookstore.wbsbackend.entities.Sales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author minh phuong
 * @created 16/09/2022 - 9:02 PM
 */
@Repository
@Transactional
public interface SalesRepo extends JpaRepository<Sales, Long> {
  @Query(
      "select s "
          + "from Sales s "
          + "where "
          + "(:title is null or (lower(s.name) like lower(concat('%', :title,'%')))) and "
          + "(:fromPercent is null or s.percent >= :fromPercent) and "
          + "(:toPercent is null or s.percent <= :toPercent) and "
          + "(:fromDate is null or s.startDate >= :fromDate) and "
          + "(:toDate is null or s.endDate <= :toDate)")
  Page<Sales> search(
      @Param("title") String title,
      @Param("fromPercent") Double fromPercent,
      @Param("toPercent") Double toPercent,
      @Param("fromDate") Date fromDate,
      @Param("toDate") Date toDate,
      Pageable pageable);

  @Query(
      "select s from Sales s inner join s.products p where "
          + "(p = :product) " +
          "and s.startDate <= current_timestamp " +
          "and s.endDate >= current_timestamp " +
          "order by s.percent desc, s.endDate desc")
  List<Sales> findMostOptimalSaleByBook(Product product);
}
