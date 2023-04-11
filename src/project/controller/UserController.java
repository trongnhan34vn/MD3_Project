package project.controller;

import project.dto.request.SignInDTO;
import project.dto.request.SignUpDTO;
import project.dto.response.ResponseMessage;
import project.model.user.Role;
import project.model.user.RoleName;
import project.model.user.User;
import project.service.role.IRoleService;
import project.service.user.IUserService;
import project.service.role.RoleServiceIMPL;
import project.service.user.UserServiceIMPL;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserController {
    IUserService userService = new UserServiceIMPL();
    IRoleService roleService = new RoleServiceIMPL();
    public List<User> getAllUser() {
        return userService.findAll();
    }
    public ResponseMessage register(SignUpDTO sign) {
        if (userService.existByEmail(sign.getEmail())) {
            return new ResponseMessage("Email is existed!");
        }
        Set<String> strRole = sign.getStrRole();

        Set<Role> roleSet = new HashSet<>();

        strRole.forEach(role -> {
            switch (role) {
                case "admin":
//                    roleSet là 1 HashSet
//                    set roll cho đối tượng
                    roleSet.add(roleService.findByName(RoleName.ADMIN));
                    break;
                case "pm":
                    roleSet.add(roleService.findByName(RoleName.PM));
                    break;
                default:
                    roleSet.add(roleService.findByName(RoleName.USER));
                    break;
            }
        });
        User user = new User(sign.getId(), sign.getEmail(), sign.getPassword(), sign.getFullName(), roleSet);
        userService.save(user);
        return new ResponseMessage("Register Success!");
    }
    public ResponseMessage login(SignInDTO signInDTO) {
        if (userService.checkLogin(signInDTO.getEmail(), signInDTO.getPassword())) {
            getCurrentUser();
            return new ResponseMessage("Login Success!");
        } else {
            return new ResponseMessage("Login Faild!");
        }
    }
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }
    public boolean changeUserStatus(int id) {
        return userService.changeUserStatus(id);
    }
    public List<User> searchByName (String search) {
        return userService.searchUserName(search);
    }
    public void updateInfo(User user) {
        userService.save(user);
    }
    public User findById(int id) {
        return userService.findById(id);
    }
    public void logOut() {
        userService.logOut();
    }
}
