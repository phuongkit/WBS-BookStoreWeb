package ecom.bookstore.wbsbackend.repositories;

import ecom.bookstore.wbsbackend.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author minh phuong
 * @created 07/10/2022 - 10:56 PM
 * @project wbs-backend
 */
@Repository
@Transactional
public interface SupplierRepo extends JpaRepository<Supplier, Integer> {
}
