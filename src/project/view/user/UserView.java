package project.view.user;

import project.config.Config;
import project.controller.UserController;
import project.model.user.User;
import project.view.HomePage;

public class UserView {
    UserController userController = new UserController();
    User currentUser = userController.getCurrentUser();

    public UserView() {

    }

    public void menu() {
        System.out.println("-------------------------- GUITAR PLUS --------------------------");
        System.out.println("1. Show List Products");
        System.out.println("2. Search Products By Name");
        System.out.println("3. Add to Cart");
        System.out.println("4. Show Cart");
        System.out.println("5. Show Invoices");
        System.out.println("6. Account");
        System.out.println("7. Log Out");
        System.out.println("-------------------------- GUITAR PLUS --------------------------");
        System.out.println("Enter your choice: ");
        int choice = Integer.parseInt(Config.scanner().nextLine());
        switch (choice) {
            case 1:
                new ProductView().showListProducts();
                backToMenu();
                break;
            case 2:
                new ProductView().searchProductByName();
                break;
            case 3:
                new CartView().addToCart();
                break;
            case 4:
                new CartView().showCart();
                backToMenu();
                break;
            case 5:
                new InvoiceView().showInvoice();
                break;
            case 6:
                currentUser= userController.getCurrentUser();
                showInfoUser(currentUser);
                break;
            case 7:
                userController.logOut();
                new HomePage();
                break;
            default:
                System.err.println("Invalid Requirement! Try again!");
                new UserView().menu();
                break;
        }
    }

    public void displayUser(User user) {
        System.out.println("-------------- User --------------");
        System.out.println("Name: " + user.getFullName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Address: " + user.getAddress());
        System.out.println("Phone: " + user.getPhoneNumber());
        System.out.println("-------------- User --------------");
    }

    public void showInfoUser(User user) {
        System.out.println("--------------------- Info ----------------------");
        System.out.println("Email: " + user.getEmail());
        System.out.println("Password: *********");
        System.out.println("Fullname: " + user.getFullName());
        System.out.println("Address: " + ((user.getAddress() == null) ? "<blank>" : user.getAddress()));
        System.out.println("Phone: " + ((user.getPhoneNumber() == null) ? "<blank>" : user.getPhoneNumber()));
        System.out.println("--------------------- Info ----------------------");
        displayUserFuncMenu(user);
    }

    private void displayUserFuncMenu(User user) {
        System.out.println("1. Update Info");
        System.out.println("2. Change Password");
        System.out.println("3. Back To Menu");
        System.out.println("Enter your choice: ");
        while (true) {
            int choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    updateInfoUser(user);
                    break;
                case 2:
                    changePassword(user);
                    break;
                case 3:
                    new UserView().menu();
                    break;
                default:
                    System.err.println("Invalid Requirement! Try again!");
                    break;
            }
        }
    }

    public void updateInfoUser(User user) {
        System.out.println("Enter your update full name: ");
        String fullName = Config.scanner().nextLine();
        System.out.println("Enter your address: ");
        String address = Config.scanner().nextLine();
        System.out.println("Enter your phone: ");
        String phone = Config.scanner().nextLine();
        User newUser = new User(user.getId(), user.getEmail(), user.getPassword(), fullName, phone, address, user.getRole(), user.isStatus());
        userController.updateInfo(newUser);
        System.out.println("Update Success!");
        backToMenu();
    }

    public void changePassword(User user) {
        System.out.println("Enter current password: ");
        String password = Config.scanner().nextLine();
        if (password.equals(currentUser.getPassword())) {
            System.out.println("Enter new password: ");
            String newPass = Config.scanner().nextLine();
            User newUser = new User(user.getId(), user.getEmail(), newPass, user.getFullName(), user.getPhoneNumber(), user.getAddress(), user.getRole(), user.isStatus());
            updateInfoUser(newUser);
            System.out.println("Change password success!");
            backToMenu();
        } else {
            System.err.println("Wrong Password! Try again!");
            System.out.println("1. Try Again");
            System.out.println("2. Back to Menu");
            while (true) {
                System.out.println("Enter your choice: ");
                int choice = Integer.parseInt(Config.scanner().nextLine());
                switch (choice) {
                    case 1:
                        changePassword(user);
                        break;
                    case 2:
                        updateInfoUser(user);
                        break;
                    default:
                        System.err.println("Invalid Requirement!");
                        break;
                }
            }
        }
    }

    public void backToMenu() {
        while (true) {
            System.out.println("Enter 'back' to back to menu: ");
            String back = Config.scanner().nextLine();
            if (back.equalsIgnoreCase("back")) {
                new UserView().menu();
            }
        }
    }
}
