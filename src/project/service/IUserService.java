package project.service;

import project.model.Role;
import project.model.User;

import java.util.List;
import java.util.Set;

public interface IUserService extends IGenericService<User> {
    boolean existByEmail(String email);
    boolean checkLogin(String email, String password);
    User getCurrentUser() ;
    boolean changeUserStatus(int id);

    List<User> searchUserName(String search);
}
