package project.view;

import project.config.Config;
import project.controller.UserController;
import project.model.User;

import java.util.List;

public class LoginRegister {
    UserController userController = new UserController();
    List<User> listUsers = userController.getAllUser();

    public LoginRegister() {
        System.out.println("-------------------------- Login & Register --------------------------");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Back to Homepage");
        System.out.println("-------------------------- Login & Register --------------------------");
        System.out.println("Enter your choice: ");
        int choice = Integer.parseInt(Config.scanner().nextLine());
        switch (choice) {
            case 1:
//                Login
                break;
            case 2:
                register();
//                Register
                break;
            case 3:
                new HomePage();
                break;
        }
    }

    public void login() {

    }

    public void register() {
        int id;
        if (listUsers.isEmpty()) {
            id = 1;
        } else {
            id = listUsers.get(listUsers.size() - 1).getId() + 1;
        }
        System.out.println("Enter your email: ");
        String email = Config.scanner().nextLine();
        System.out.println("Enter your password: ");
        String password = Config.scanner().nextLine();
        System.out.println("Enter your re-password: ");
        String rePassword = Config.scanner().nextLine();
        System.out.println("Enter your name: ");
        String fullName = Config.scanner().nextLine();
    }
}
