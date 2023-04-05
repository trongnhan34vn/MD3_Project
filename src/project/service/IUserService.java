package project.service;

import project.model.User;

public interface IUserService extends IGenericService<User> {
    boolean existByEmail(String email);
    boolean checkLogin(String email, String password);
    User getCurrentUser() ;
}
