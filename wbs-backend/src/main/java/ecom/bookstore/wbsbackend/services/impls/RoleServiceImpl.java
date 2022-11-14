package ecom.bookstore.wbsbackend.services.impls;

import ecom.bookstore.wbsbackend.entities.Role;
import ecom.bookstore.wbsbackend.exceptions.ResourceAlreadyExistsException;
import ecom.bookstore.wbsbackend.exceptions.ResourceNotFoundException;
import ecom.bookstore.wbsbackend.repositories.RoleRepo;
import ecom.bookstore.wbsbackend.services.RoleService;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author minh phuong
 * @created 08/09/2022 - 8:34 PM
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  public static final String branchName = Role.class.getSimpleName();

  @Autowired
  private RoleRepo roleRepo;

  @Override
  public List<Role> getAllRole() {
    //    LOGGER.info("Fetching list product...");
    List<Role> roles = this.roleRepo.findAll();
    if (roles.isEmpty()) {
      throw new ResourceNotFoundException("Role");
    }
    return roles;
  }

  @Override
  public Role getRoleById(Integer id) {
    return null;
  }

  @Override
  public Role createRole(Role role) {
    Optional<Role> roleFound = this.roleRepo.findByName(role.getName());
    if (!roleFound.isPresent()) {
      this.roleRepo.save(role);
      return role;
    } else {
      throw new ResourceAlreadyExistsException(
          String.format(Utils.OBJECT_EXISTED_BY_FIELD, branchName, "Name", role.getName()));
    }
  }

  @Override
  public Role updateRole(Integer id, Role role) {
    return null;
  }

  @Override
  public Role deleteRoleById(Integer id) {
    return null;
  }
}
