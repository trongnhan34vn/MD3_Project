package project.controller;

import project.dto.request.SignUpDTO;
import project.dto.response.ResponseMessage;
import project.model.User;
import project.service.IRoleService;
import project.service.IUserService;
import project.service.RoleServiceIMPL;
import project.service.UserServiceIMPL;

import java.util.List;

public class UserController {
    IUserService userService = new UserServiceIMPL();
    IRoleService roleService = new RoleServiceIMPL();

    public List<User> getAllUser() {
        return userService.findAll();
    }

    public ResponseMessage register(SignUpDTO user) {
        return null;
    }
}
