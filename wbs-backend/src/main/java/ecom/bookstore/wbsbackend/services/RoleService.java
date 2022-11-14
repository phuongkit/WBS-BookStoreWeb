package ecom.bookstore.wbsbackend.services;

import ecom.bookstore.wbsbackend.entities.Role;

import java.util.List;

/**
 * @author minh phuong
 * @created 08/09/2022 - 8:34 PM
 */
public interface RoleService {
  List<Role> getAllRole();

  Role getRoleById(Integer id);

  Role createRole(Role role);

  Role updateRole(Integer id, Role role);

  Role deleteRoleById(Integer id);
}
