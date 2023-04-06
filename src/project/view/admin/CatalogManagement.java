package project.view.admin;

import project.config.Config;

public class CatalogManagement {
    public CatalogManagement() {
        System.out.println("-------------------------- Catalog Management --------------------------");
        System.out.println("1. Show List Catalogs");
        System.out.println("2. Create Catalog");
        System.out.println("3. Update Catalog");
        System.out.println("4. Delete Catalog");
        System.out.println("5. Search Catalog by Name");
        System.out.println("6. Back to Admin");
        System.out.println("-------------------------- Catalog Management --------------------------");
        System.out.println("Enter your choice: ");
        int choice = Integer.parseInt(Config.scanner().nextLine());
        switch (choice) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
        }
    }
}
