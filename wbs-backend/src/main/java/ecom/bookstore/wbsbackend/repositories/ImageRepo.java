package ecom.bookstore.wbsbackend.repositories;

import ecom.bookstore.wbsbackend.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author minh phuong
 * @created 07/10/2022 - 11:00 PM
 */
@Repository
@Transactional
public interface ImageRepo extends JpaRepository<Image, Long> {
}
