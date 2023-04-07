package project.service.user;

import project.config.Config;
import project.data.Path;
import project.model.Role;
import project.model.RoleName;
import project.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserServiceIMPL implements IUserService {
    List<User> listUsers = new Config<User>().readFromFile(Path.USER_PATH);

    {
        Role role = new Role(2, RoleName.ADMIN);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        boolean isCheck = false;
        for (User user: listUsers) {
            if (user.getId() == 0 || user.getEmail().equalsIgnoreCase("admin@gmail.com")) {
                isCheck = true;
            }
        }
        if (!isCheck) {
            listUsers.add(new User(0, "admin@gmail.com", "pikachu123", "admin", roleSet));
        }
    }

    @Override
    public List<User> findAll() {
        return listUsers;
    }

    @Override
    public void save(User user) {
        listUsers.add(user);
        new Config<User>().writeToFile(listUsers, Path.USER_PATH);
    }

    @Override
    public User findById(int id) {
        for (User user : listUsers) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        listUsers.removeIf(user -> user.getId() == id);
    }

    @Override
    public boolean existByEmail(String email) {
        for (User user : listUsers) {
            if (user.getEmail().equals(email.trim())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkLogin(String email, String password) {
        List<User> listLoginUsers = new ArrayList<>();
        for (User user : listUsers) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                listLoginUsers.add(user);
                new Config<User>().writeToFile(listLoginUsers, Path.USER_LOGIN_PATH);
                return true;
            }
        }
        return false;
    }

    @Override
    public User getCurrentUser() {
        User currentUser;
        List<User> listCurrentUsers = new Config<User>().readFromFile(Path.USER_LOGIN_PATH);
        if (listCurrentUsers.size() > 0) {
            currentUser = listCurrentUsers.get(0);
            return currentUser;
        }
        return null;
    }

    @Override
    public boolean changeUserStatus(int id) {
        for (User user: listUsers) {
            if (user.getId() == id) {
                user.setStatus(!user.isStatus());
                new Config<User>().writeToFile(listUsers, Path.USER_PATH);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<User> searchUserName(String search) {
        List<User> searchList = new ArrayList<>(listUsers);
        searchList.removeIf(user -> !user.getFullName().toLowerCase().contains(search));
        return searchList;
    }

}
