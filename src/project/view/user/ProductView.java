package project.view.user;

import project.config.Config;
import project.controller.CatalogController;
import project.controller.ProductController;
import project.model.product.Catalog;
import project.model.product.Product;

import java.util.List;

public class ProductView {
    CatalogController catalogController = new CatalogController();
    List<Catalog> listCatalogs = catalogController.getAllCatalogs();
    ProductController productController = new ProductController();

    public void showListProducts() {
        for (Catalog catalog : listCatalogs) {
            List<Product> searchList = productController.searchProductByCatalog(catalog.getCatalogName());
            if (searchList.isEmpty()) {
                continue;
            }
            System.out.println("---------- " + catalog.getCatalogName() + "-start" + " ----------");
            showList(searchList);
            System.out.println("---------- " + catalog.getCatalogName() + "-end " + " ----------");
        }
    }

    public void showProduct(Product product) {
        System.out.println("Product ID: " + product.getProductId());
        System.out.println("Product Name: " + product.getProductName());
        System.out.println("Description: " + (product.getDescription() == null ? "<blank>" : product.getDescription()));
        System.out.println("Price: " + Config.currencyVN.format(product.getPrice()));
    }

    public void showList(List<Product> products) {
        for (Product product : products) {
            System.out.println("Product ID: " + product.getProductId());
            System.out.println("Product Name: " + product.getProductName());
            System.out.println("Description: " + ((product.getDescription() == null) ? "<blank>" : product.getDescription()));
            System.out.println("Quantity: " + product.getQuantity());
            System.out.println("Price: " + Config.currencyVN.format(product.getPrice()));
            System.out.println("Status: " + (product.isProductStatus() ? "stocking" : "out of stock"));
            System.out.print("\n");
        }
    }

    public void searchProductByName() {
        showListProducts();
        System.out.println("Enter the product name: ");
        String search = Config.scanner().nextLine();
        List<Product> listSearch = productController.searchProductByName(search);
        if (listSearch.isEmpty()) {
            System.err.println("Not Found!");
            System.out.println("1. Try Again");
            System.out.println("2. Back to Menu");
            while (true) {
                System.out.println("Enter your choice: ");
                int choice = Integer.parseInt(Config.scanner().nextLine());
                switch (choice) {
                    case 1:
                        searchProductByName();
                        break;
                    case 2:
                        new UserView().menu();
                        break;
                    default:
                        System.err.println("Invalid Requirement! Try again!");
                        break;
                }
            }

        } else {
            System.out.println("-----------------------------------------");
            showList(listSearch);
            System.out.println("-----------------------------------------");
            new UserView().menu();
        }
    }


}
