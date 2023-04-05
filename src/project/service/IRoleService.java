package project.service;

import project.model.Role;
import project.model.RoleName;

import java.util.List;

public interface IRoleService {
    List<Role> findAll();
    Role findByName(RoleName name);
}
