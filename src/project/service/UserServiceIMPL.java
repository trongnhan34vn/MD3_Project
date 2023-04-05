package project.service;

import project.config.Config;
import project.data.Path;
import project.model.User;

import java.util.List;

public class UserServiceIMPL implements IUserService{
    List<User> listUsers = new Config<User>().readFromFile(Path.USER_PATH);
    @Override
    public List<User> findAll() {
        return listUsers;
    }

    @Override
    public void save(User user) {
        listUsers.add(user);
    }

    @Override
    public User findById(int id) {
        for (User user: listUsers) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        for (User user: listUsers) {
            if (user.getId() == id) {
                listUsers.remove(user);
            }
        }
    }

    @Override
    public boolean existByEmail(String email) {
        return false;
    }
}
