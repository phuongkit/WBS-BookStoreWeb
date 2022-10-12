package ecom.bookstore.wbsbackend.repositories;

import ecom.bookstore.wbsbackend.entities.Role;
import ecom.bookstore.wbsbackend.models.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author minh phuong
 * @created 07/10/2022 - 10:56 PM
 */
@Repository
@Transactional
public interface RoleRepo extends JpaRepository<Role, Integer> {
  Optional<Role> findByName(ERole name);
}
