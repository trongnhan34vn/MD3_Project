package project.view;

import project.config.Config;
import project.controller.UserController;
import project.dto.request.SignInDTO;
import project.dto.request.SignUpDTO;
import project.dto.response.ResponseMessage;
import project.model.Role;
import project.model.RoleName;
import project.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoginRegister {
    UserController userController = new UserController();
    List<User> listUsers = userController.getAllUser();
    User currentUser = userController.getCurrentUser();
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
                login();
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
        System.out.println("Enter your email: ");
        String email = Config.scanner().nextLine();
        System.out.println("Enter your password: ");
        String password = Config.scanner().nextLine();
        SignInDTO signInDTO = new SignInDTO(email, password);
//        lấy phản hồi từ DTO
        ResponseMessage responseMessage = userController.login(signInDTO);
//        Nếu phản hồi là Login Succ -> Login suc
        if (responseMessage.getMessage().equals("Login Success!")) {
            System.out.println("Login Success!");
            System.out.println(currentUser.getRole());
        } else {
            System.err.println("Login Failed! Please try again!");
            System.out.println("1. Try Again!");
            System.out.println("2. Back to menu");
            System.out.println("Enter your choice: ");
            int choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    backToMenu();
                    break;
            }
        }
    }

    public SignUpDTO inputRegister() {
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
        System.out.println("Enter your name: ");
        String fullName = Config.scanner().nextLine();
//        Tạo hashSet để truyền vào khởi tạo đối tượng SignUpSTD
        Set<String> roleSet = new HashSet<>();
        String role = "user";
        roleSet.add(role);

        return new SignUpDTO(id, email, password, fullName, roleSet);
    }

    public void register() {
        ResponseMessage responseMessage = userController.register(inputRegister());
        if (responseMessage.getMessage().equals("Email is existed!")) {
            System.err.println("Email is existed! Please try again");
            System.out.println("1. Try Again!");
            System.out.println("2. Back to menu");
            System.out.println("Enter your choice: ");
            int choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    backToMenu();
                    break;
            }
        } else {
            System.out.println("Register Success!");
            System.out.println("1. Login");
            System.out.println("2. Back to menu");
            System.out.println("Enter your choice: ");
            int choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    backToMenu();
                    break;
            }
        }
    }

    public void backToMenu() {
        System.out.println("Enter 'back' to back to menu: ");
        String back = Config.scanner().nextLine();
        if (back.equalsIgnoreCase("back")) {
            new LoginRegister();
        }
    }
}
