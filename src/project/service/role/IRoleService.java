package project.service.role;

import project.model.user.Role;
import project.model.user.RoleName;

import java.util.List;

public interface IRoleService {
    List<Role> findAll();
    Role findByName(RoleName name);
}
