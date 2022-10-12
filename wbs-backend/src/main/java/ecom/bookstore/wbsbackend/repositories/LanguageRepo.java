package ecom.bookstore.wbsbackend.repositories;

import ecom.bookstore.wbsbackend.entities.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author minh phuong
 * @created 07/10/2022 - 11:01 PM
 */
@Repository
@Transactional
public interface LanguageRepo extends JpaRepository<Language, Integer> {
}
