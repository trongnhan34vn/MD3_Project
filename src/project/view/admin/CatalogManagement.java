package project.view.admin;

import project.config.Config;
import project.controller.CatalogController;
import project.model.Catalog;
import java.util.List;

public class CatalogManagement {
    CatalogController catalogController = new CatalogController();
    List<Catalog> listCatalogs = catalogController.getAllCatalogs();
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
                showListCatalog(listCatalogs);
                backToMenu();
                break;
            case 2:
                createCatalog();
                break;
            case 3:
                updateCatalog();
                break;
            case 4:
                deleteCatalog();
                break;
            case 5:
                searchCatalogByName();
                break;
            case 6:
                new AdminView();
                break;
        }
    }

    public void showListCatalog(List<Catalog> list) {
        System.out.println("--------------- Catalogs ---------------");
        for (Catalog catalog: list) {
            System.out.println("----------------------------------");
            System.out.println("Catalog ID: " + catalog.getCatalogId());
            System.out.println("Catalog Name: " + catalog.getCatalogName());
            System.out.println("----------------------------------");
        }
        System.out.println("--------------- Catalogs ---------------");
        backToMenu();
    }

    public void createCatalog() {
        System.out.println("Enter the catalog number: ");
        int mountCata = Integer.parseInt(Config.scanner().nextLine());
        for (int i = 0; i < mountCata; i++) {
            System.out.println("Enter info of new Catalog: ");
            int cataId;
            if (listCatalogs.isEmpty()) {
                cataId = 1;
            } else {
                cataId = listCatalogs.get(listCatalogs.size() - 1).getCatalogId() + 1;
            }
            System.out.println("Enter catalog name: ");
            String cataName = Config.scanner().nextLine();
            Catalog newCatalog = new Catalog(cataId, cataName);
            catalogController.createCatalog(newCatalog);
        }
        System.out.println("Create " + mountCata + " catalogs success!");
        backToMenu();
    }

    public void updateCatalog() {
        System.out.println("Enter Id Catalog: ");
        int idCata = Integer.parseInt(Config.scanner().nextLine());
        Catalog updateCata = catalogController.findById(idCata);
        if (updateCata == null) {
            System.err.println("ID Not Found! Please try again or back to Menu!");
            System.out.println("1. Try Again");
            System.out.println("2. Back to Menu");
            int choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    updateCatalog();
                    break;
                case 2:
                    backToMenu();
                    break;
            }
        } else {
            System.out.println("Enter name to change: ");
            String cataName = Config.scanner().nextLine();
            Catalog newCatalog = new Catalog(idCata, cataName);
            catalogController.updateCatalog(newCatalog);
            System.out.println("Update Catalog Success!");
            backToMenu();
        }
    }

    public void deleteCatalog() {
        System.out.println("Enter Id to delete: ");
        int idCata = Integer.parseInt(Config.scanner().nextLine());
        Catalog deleteCata = catalogController.findById(idCata);
        if (deleteCata == null) {
            System.err.println("ID Not Found! Please try again or back to Menu!");
            System.out.println("1. Try Again");
            System.out.println("2. Back to Menu");
            int choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    deleteCatalog();
                    break;
                case 2:
                    backToMenu();
                    break;
            }
        } else {
            catalogController.deleteCatalog(idCata);
            System.out.println("Delete Catalog Success!");
            backToMenu();
        }
    }

    public void searchCatalogByName() {
        System.out.println("Search: ");
        String search = Config.scanner().nextLine();
        List<Catalog> searchList = catalogController.searchByName(search);
        if (searchList.isEmpty()) {
            System.err.println("Not Found!");
            System.out.println("1. Try Again");
            System.out.println("2. Back to Menu");
            int choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    searchCatalogByName();
                    break;
                case 2:
                    backToMenu();
                    break;
            }
        } else {
            showListCatalog(searchList);
            backToMenu();
        }
    }

    public void backToMenu() {
        System.out.println("Enter 'back' to back to menu: ");
        String back = Config.scanner().nextLine();
        if (back.equalsIgnoreCase("back")) {
            new CatalogManagement();
        }
    }
}
