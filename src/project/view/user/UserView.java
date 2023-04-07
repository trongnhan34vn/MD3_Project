package project.view.user;

import project.config.Config;
import project.controller.CartController;
import project.controller.CatalogController;
import project.controller.ProductController;
import project.controller.UserController;
import project.model.*;


import java.util.ArrayList;
import java.util.List;

public class UserView {
    CatalogController catalogController = new CatalogController();
    ProductController productController = new ProductController();
    CartController cartController = new CartController();
    List<Catalog> listCatalogs = catalogController.getAllCatalogs();

    public UserView() {
        System.out.println("-------------------------- GUITAR PLUS --------------------------");
        System.out.println("1. Show List Products");
        System.out.println("2. Search Products By Name");
        System.out.println("3. Add to Cart");
        System.out.println("4. Show Cart");
        System.out.println("5. Payment");
        System.out.println("6. Log Out");
        System.out.println("-------------------------- GUITAR PLUS --------------------------");
        System.out.println("Enter your choice: ");
        int choice = Integer.parseInt(Config.scanner().nextLine());
        switch (choice) {
            case 1:
                showListProducts();
                backToMenu();
                break;
            case 2:
                searchProductByName();
                break;
            case 3:
                addToCart();
                break;
            case 4:
                showCart();
                backToMenu();
                break;
            case 5:
                break;
            case 6:
                break;
        }
    }

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

    public void showList(List<Product> products) {
        for (Product product : products) {
            System.out.println("Product ID: " + product.getProductId());
            System.out.println("Product Name: " + product.getProductName());
            System.out.println("Description: " + ((product.getDescription() == null) ? "<blank>" : product.getDescription()));
            System.out.println("Quantity: " + product.getQuantity());
            System.out.println("Price: " + product.getPrice());
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
            int choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    searchProductByName();
                    break;
                case 2:
                    backToMenu();
                    break;
            }
        } else {
            System.out.println("-----------------------------------------");
            showList(listSearch);
            System.out.println("-----------------------------------------");
            backToMenu();
        }
    }

    public void addToCart() {
        showListProducts();
        System.out.println("Enter Id product want to add to cart: ");
        int idProduct = Integer.parseInt(Config.scanner().nextLine());
        Product productSelect = productController.findById(idProduct);
        if (productSelect == null) {
            System.err.println("Id Not Found! Please try again!");
            System.out.println("1. Try Again!");
            System.out.println("2. Back to Menu");
            System.out.println("Enter your choice: ");
            int choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    addToCart();
                    break;
                case 2:
                    backToMenu();
                    break;
            }
        }
        System.out.println("Enter the quantity: ");
        int quantity = Integer.parseInt(Config.scanner().nextLine());
        CartItem cartItem = new CartItem(productSelect, quantity);
        if (cartController.addToCart(cartItem)) {
            System.out.println("Add to Cart Success!");
        } else {
            System.out.println("Add to Cart Failed!");
        }
        backToMenu();
    }

    public void showCart() {
        Cart currentUserCart = cartController.getCurrentUserCart();
        System.out.println("---------------- Shopping Cart ----------------");
        for (CartItem cartItem : currentUserCart.getListCartItem()) {
            showProduct(cartItem.getProduct());
            System.out.println("Quantity: " + cartItem.getQuantity());
            System.out.println(" ");
        }
        System.out.println("---------------- Shopping Cart ----------------");
    }

    public void showProduct(Product product) {
        System.out.println("Product Name: " + product.getProductName());
        System.out.println("Description: " + (product.getDescription() == null ? "<blank>" : product.getDescription()));
        System.out.println("Price: " + product.getPrice());
    }

    public void backToMenu() {
        System.out.println("Enter 'back' to back to menu: ");
        String back = Config.scanner().nextLine();
        if (back.equalsIgnoreCase("back")) {
            new UserView();
        }
    }
}
