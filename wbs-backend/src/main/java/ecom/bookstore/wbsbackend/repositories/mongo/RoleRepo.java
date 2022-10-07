package ecom.bookstore.wbsbackend.repositories.mongo;

import ecom.bookstore.wbsbackend.entities.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author minh phuong
 * @created 07/10/2022 - 6:28 PM
 * @project wbs-backend
 */
@Repository
@Transactional
public interface RoleRepo extends MongoRepository<Role, String> {
}
