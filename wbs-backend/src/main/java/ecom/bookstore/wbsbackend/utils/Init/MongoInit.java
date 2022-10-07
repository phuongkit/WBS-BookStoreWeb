package ecom.bookstore.wbsbackend.utils.Init;

import ecom.bookstore.wbsbackend.entities.Role;
import ecom.bookstore.wbsbackend.models.enums.ERole;
import ecom.bookstore.wbsbackend.repositories.mongo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author minh phuong
 * @created 07/10/2022 - 7:57 PM
 * @project wbs-backend
 */
@Component
@EnableMongoRepositories
public class MongoInit {
  @Autowired
  private RoleRepo roleRepo;
  @Transactional
  public void init() {
    Role roleAdmin = new Role(ERole.ROLE_ADMIN, "Admin role is the administrator's role");
    roleAdmin = this.roleRepo.save(roleAdmin);

    Role roleSeller =
        new Role(ERole.ROLE_SELLER, "Seller is a person who sells goods to consumers.");
    roleSeller = this.roleRepo.save(roleSeller);

    Role roleCustomer =
        new Role(
            ERole.ROLE_CUSTOMER,
            "Customers are the people who have the conditions to make purchasing decisions. They are the beneficiaries of the characteristics and quality of the product or service.");
    roleCustomer = this.roleRepo.save(roleCustomer);

    Role roleAssistant = new Role(ERole.ROLE_ASSISTANT, "Manage questions and reviews");
    roleAssistant = this.roleRepo.save(roleAssistant);

    Role roleEditor =
        new Role(ERole.ROLE_EDITOR, "Manage categories, brands, products, articles and menus");
    roleEditor = this.roleRepo.save(roleEditor);

    Role roleShipper =
        new Role(ERole.ROLE_SHIPPER, "View products, view orders and update order status");
    roleShipper = this.roleRepo.save(roleShipper);

    Role roleSalesPerson =
        new Role(
            ERole.ROLE_SALESPERSON,
            "Manage product price, customers, shipping, orders and sales report");
    roleSalesPerson = this.roleRepo.save(roleSalesPerson);
  }
}
