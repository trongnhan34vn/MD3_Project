package project.view.admin;

import project.config.Config;
import project.view.HomePage;

public class AdminView {
    public AdminView() {
        System.out.println("-------------------------- Admin --------------------------");
        System.out.println("1. Catalog Management");
        System.out.println("2. Products Management");
        System.out.println("3. Users Management");
        System.out.println("4. Invoice Management");
        System.out.println("5. Log Out");
        System.out.println("-------------------------- Admin --------------------------");
        System.out.println("Enter your choice: ");
        int choice = Integer.parseInt(Config.scanner().nextLine());
        switch (choice) {
            case 1:
                new CatalogManagement();
                break;
            case 2:
                new ProductsManagement();
                break;
            case 3:
                new UserManagement();
                break;
            case 4:
                new InvoiceManagement();
                break;
            case 5:
                new HomePage();
                break;
        }
    }
}
