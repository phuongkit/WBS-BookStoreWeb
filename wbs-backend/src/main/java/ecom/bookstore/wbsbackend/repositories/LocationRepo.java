package ecom.bookstore.wbsbackend.repositories;

import ecom.bookstore.wbsbackend.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author minh phuong
 * @created 07/10/2022 - 11:01 PM
 */
@Repository
@Transactional
public interface LocationRepo extends JpaRepository<Location, Integer> {
  @Query("select l from Location l where " +
      "(:commune is null or (l.commune is not null and l.commune = :commune)) " +
      "and (:district is null or (l.district is not null and l.district = :district)) " +
      "and (:province is not null and l.province = :province)")
  List<Location> findAllByCommuneAndDistrictAndProvince(
      @Param("commune") String commune,
      @Param("district") String district,
      @Param("province") String province);
}
