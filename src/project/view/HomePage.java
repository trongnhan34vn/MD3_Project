package project.view;

import project.config.Config;
import project.view.user.ProductView;

public class HomePage {
    public HomePage() {
        System.out.println("-------------------------- HomePage --------------------------");
        System.out.println("1. Show List Products");
        System.out.println("2. Login & Register");
        System.out.println("3. Exit");
        System.out.println("-------------------------- HomePage --------------------------");
        System.out.println("Enter your choice: ");
        int choice = Integer.parseInt(Config.scanner().nextLine());
        switch (choice) {
            case 1:
//                Show List Products
                new ProductView().showListProducts();
                backToMenu();
                break;
            case 2:
//               đến Login & Register
                new LoginRegister();
                break;
            case 3:
                System.exit(0);
        }
    }

    public void backToMenu() {
        while (true) {
            System.out.println("Enter 'back' to back to menu: ");
            String back = Config.scanner().nextLine();
            if(back.equalsIgnoreCase("back")) {
                new HomePage();
                break;
            }
        }
    }
}
