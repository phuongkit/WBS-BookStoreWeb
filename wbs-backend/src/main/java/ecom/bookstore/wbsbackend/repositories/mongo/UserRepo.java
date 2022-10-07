package ecom.bookstore.wbsbackend.repositories.mongo;

import ecom.bookstore.wbsbackend.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author minh phuong
 * @created 07/10/2022 - 6:07 PM
 * @project wbs-backend
 */
@Repository
@Transactional
public interface UserRepo extends MongoRepository<User, Long> {
}
