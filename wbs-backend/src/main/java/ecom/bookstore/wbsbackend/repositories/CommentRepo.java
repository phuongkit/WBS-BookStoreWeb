package ecom.bookstore.wbsbackend.repositories;

import ecom.bookstore.wbsbackend.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author minh phuong
 * @created 08/10/2022 - 12:06 AM
 * @project wbs-backend
 */
@Repository
@Transactional
public interface CommentRepo extends JpaRepository<Comment, Long> {
}
