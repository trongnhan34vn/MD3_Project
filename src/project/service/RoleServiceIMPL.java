package project.service;

import project.model.Role;
import project.model.RoleName;

import java.util.ArrayList;
import java.util.List;

public class RoleServiceIMPL implements IRoleService {
    public static List<Role> listRoles = new ArrayList<>();

    static {
        listRoles.add(new Role(1, RoleName.USER));
        listRoles.add(new Role(2, RoleName.ADMIN));
        listRoles.add(new Role(3, RoleName.PM));
    }

    @Override
    public List<Role> findAll() {
        return listRoles;
    }

    @Override
    public Role findByName(RoleName name) {
        for (Role role: listRoles) {
            if (role.getRoleName() == name) {
                return role;
            }
        }
        return null;
    }
}
