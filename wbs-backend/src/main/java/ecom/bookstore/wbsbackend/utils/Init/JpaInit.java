package ecom.bookstore.wbsbackend.utils.Init;

import ecom.bookstore.wbsbackend.entities.Role;
import ecom.bookstore.wbsbackend.entities.RoleM;
import ecom.bookstore.wbsbackend.models.enums.ERole;
import ecom.bookstore.wbsbackend.repositories.jpa.RoleMRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author minh phuong
 * @created 07/10/2022 - 7:58 PM
 * @project wbs-backend
 */
@Component
@EnableJpaRepositories
public class JpaInit {
  @Autowired
  private RoleMRepo roleMRepo;

  @Transactional
  public void init() {
    Role roleAdmin = new Role(ERole.ROLE_ADMIN, "Admin role is the administrator's role");
    this.roleMRepo.save(new RoleM(roleAdmin));

    Role roleSeller =
        new Role(ERole.ROLE_SELLER, "Seller is a person who sells goods to consumers.");
    this.roleMRepo.save(new RoleM(roleSeller));

    Role roleCustomer =
        new Role(
            ERole.ROLE_CUSTOMER,
            "Customers are the people who have the conditions to make purchasing decisions. They are the beneficiaries of the characteristics and quality of the product or service.");
    this.roleMRepo.save(new RoleM(roleCustomer));

    Role roleAssistant = new Role(ERole.ROLE_ASSISTANT, "Manage questions and reviews");
    this.roleMRepo.save(new RoleM(roleAssistant));

    Role roleEditor =
        new Role(ERole.ROLE_EDITOR, "Manage categories, brands, products, articles and menus");
    this.roleMRepo.save(new RoleM(roleEditor));

    Role roleShipper =
        new Role(ERole.ROLE_SHIPPER, "View products, view orders and update order status");
    this.roleMRepo.save(new RoleM(roleShipper));

    Role roleSalesPerson =
        new Role(
            ERole.ROLE_SALESPERSON,
            "Manage product price, customers, shipping, orders and sales report");
    this.roleMRepo.save(new RoleM(roleSalesPerson));
  }
}
