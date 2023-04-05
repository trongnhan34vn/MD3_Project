package project.view;

import project.config.Config;

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
                break;
            case 2:
//               đến Login & Register

                break;
            case 3:
                System.exit(0);
        }
    }
}
