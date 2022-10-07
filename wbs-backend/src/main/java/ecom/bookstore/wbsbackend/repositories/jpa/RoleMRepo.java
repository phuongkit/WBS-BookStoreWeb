package ecom.bookstore.wbsbackend.repositories.jpa;

import ecom.bookstore.wbsbackend.entities.Role;
import ecom.bookstore.wbsbackend.entities.RoleM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author minh phuong
 * @created 07/10/2022 - 6:35 PM
 * @project wbs-backend
 */
@Repository
@Transactional
public interface RoleMRepo extends JpaRepository<RoleM, Integer > {
}
