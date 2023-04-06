package project.view.admin;

import project.config.Config;
import project.controller.UserController;
import project.model.User;
import project.view.LoginRegister;

import java.util.List;

public class UserManagement {
    UserController userController = new UserController();
    List<User> listUsers = userController.getAllUser();

    public UserManagement() {
        System.out.println("-------------------------- Users Management --------------------------");
        System.out.println("1. Show List Users");
        System.out.println("2. Change User Status(Active/Block)");
        System.out.println("3. Change User Role");
        System.out.println("4. Back to Admin Page");
        System.out.println("-------------------------- Users Management --------------------------");
        System.out.println("Enter your choice: ");
        int choice = Integer.parseInt(Config.scanner().nextLine());
        switch (choice) {
            case 1:
                showListUsers(listUsers);
                backToMenu();
                break;
            case 2:
                changeUserStatus();
                break;
            case 3:
                searchUserByName();
                break;
            case 4:
                new AdminView();
                break;
            default:
                System.err.println("Input invalid!");
                backToMenu();
                break;
        }
    }

    public void searchUserByName() {
        showListUsers(listUsers);
        System.out.println("Enter User Name: ");
        String userName = Config.scanner().nextLine();
        System.out.println("----------- Users");
        List<User> searchList = userController.searchByName(userName);
        showListUsers(searchList);
    }

    public void showListUsers(List<User> listUsers) {
        System.out.println("LIST USERS: ");
        for (User user : listUsers) {
            System.out.println("-------------------------------------------");
            System.out.println("UserId: " + user.getId());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Fullname: " + user.getFullName());
            System.out.println("Address: " + ((user.getAddress() == null) ? "<blank>" : user.getAddress()));
            System.out.println("Phone: " + ((user.getPhoneNumber() == null) ? "<blank>" : user.getPhoneNumber()));
            System.out.println("Status: " + (user.isStatus() ? "Active" : "Block"));
            System.out.println("Role: " + user.getRole());
        }
        System.out.println("-------------------------------------------");
    }

    public void changeUserStatus() {
        showListUsers(listUsers);
        System.out.println("Enter Id User: ");
        int userUpdate = Integer.parseInt(Config.scanner().nextLine());
        boolean checkSuccess = userController.changeUserStatus(userUpdate);
        if (checkSuccess) {
            System.out.println("Change Status User Success!");
        } else {
            System.err.println("Change Status User Failed!");
        }
        backToMenu();
    }

    public void backToMenu() {
        System.out.println("Enter 'back' to back to menu: ");
        String back = Config.scanner().nextLine();
        if (back.equalsIgnoreCase("back")) {
            new UserManagement();
        }
    }
}
