package project.service.user;

import project.model.user.User;
import project.service.IGenericService;

import java.util.List;

public interface IUserService extends IGenericService<User> {
    boolean existByEmail(String email);
    boolean checkLogin(String email, String password);
    User getCurrentUser() ;
    boolean changeUserStatus(int id);
    List<User> searchUserName(String search);
}
