package project.view.admin;

import project.config.Config;
import project.controller.ProductController;
import project.dto.request.ProductDTO;
import project.model.Catalog;
import project.model.Product;

import java.util.List;

public class ProductsManagement {
    ProductController productController = new ProductController();
    List<Product> listProduct = productController.getAllProducts();
    List<Catalog> listCatalogs = productController.getAllCatalogs();

    public ProductsManagement() {
        System.out.println("-------------------------- Products Management --------------------------");
        System.out.println("1. Show List Products");
        System.out.println("2. Create Product");
        System.out.println("3. Update Product");
        System.out.println("4. Delete Product");
        System.out.println("5. Search Product by Name");
        System.out.println("6. Back to Admin");
        System.out.println("-------------------------- Products Management --------------------------");
        System.out.println("Enter your choice: ");
        int choice = Integer.parseInt(Config.scanner().nextLine());
        switch (choice) {
            case 1:
                showListProducts(listProduct);
                break;
            case 2:
                createProduct(listCatalogs);
                break;
            case 3:
                updateProduct(listCatalogs);
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                new AdminView();
                break;
        }
    }

    public void showListProducts(List<Product> list) {
        System.out.println("--------------- Products ---------------");
        for (Product product : list) {
            System.out.println("----------------------------------");
            System.out.println("Product ID: " + product.getProductId());
            System.out.println("Product Name: " + product.getProductName());
            System.out.println("Description: " + ((product.getDescription() == null) ? "<blank>" : product.getDescription()));
            System.out.println("Quantity: " + product.getQuantity());
            System.out.println("Price: " + product.getPrice());
            System.out.println("Status: " + (product.isProductStatus() ? "stocking" : "out of stock"));
            System.out.println("----------------------------------");
        }
        System.out.println("--------------- Products ---------------");
        backToMenu();
    }

    public void createProduct(List<Catalog> catalogs) {
        System.out.println("Enter the product number: ");
        int mountProducts = Integer.parseInt(Config.scanner().nextLine());
        System.out.println("Enter all products info: ");
        for (int i = 0; i < mountProducts; i++) {
            int productId;
            if (listProduct.isEmpty()) {
                productId = 1;
            } else {
                productId = listProduct.get(listProduct.size() - 1).getProductId() + 1;
            }

            new CatalogManagement().showListCatalog(catalogs);
            boolean gap = true;
            Catalog newCatalog = null;
            while (gap) {
                System.out.println("Choose catalog by Id: ");
                int catalogId = Integer.parseInt(Config.scanner().nextLine());
                Catalog catalog = productController.findCatalogById(catalogId);
                if (catalog == null) {
                    System.err.println("ID Not Found! Please try again!");
                    System.out.println("1. Try again");
                    System.out.println("2. Back to Menu");
                    int choice = Integer.parseInt(Config.scanner().nextLine());
                    switch (choice) {
                        case 1:
                            break;
                        case 2:
                            return;
                    }
                } else {
                    gap = false;
                    newCatalog = catalog;
                }
            }
            System.out.println("Enter the product name: ");
            String productName = Config.scanner().nextLine();
            System.out.println("Enter the product quantity: ");
            int quantity = Integer.parseInt(Config.scanner().nextLine());
            System.out.println("Enter the product price: ");
            float price = Float.parseFloat(Config.scanner().nextLine());
            ProductDTO productDTO = new ProductDTO(productId, productName, quantity, price, newCatalog);
            productController.createProduct(productDTO);
        }
        System.out.println("Create " + mountProducts + " products success!");
        backToMenu();
    }

    public void updateProduct(List<Catalog> listCatalogs) {
        showListProducts(listProduct);
        System.out.println("Enter the product Id: ");
        int productId = Integer.parseInt(Config.scanner().nextLine());
        if (productController.findById(productId) != null) {
            System.out.println("Enter the product name: ");
            String productName = Config.scanner().nextLine();
            System.out.println("Enter the product description: ");
            String description = Config.scanner().nextLine();
            System.out.println("Enter the product quantity: ");
            int quantity = Integer.parseInt(Config.scanner().nextLine());
            System.out.println("Enter the product price: ");
            float price = Float.parseFloat(Config.scanner().nextLine());
            System.out.println("Enter the product status: ");
            boolean status = Boolean.parseBoolean(Config.scanner().nextLine());
            boolean gap = true;
            Catalog newCatalog = null;
            while (gap) {
                System.out.println("Choose catalog by Id: ");
                int catalogId = Integer.parseInt(Config.scanner().nextLine());
                Catalog catalog = productController.findCatalogById(catalogId);
                if (catalog == null) {
                    System.err.println("ID Not Found! Please try again!");
                    System.out.println("1. Try again");
                    System.out.println("2. Back to Menu");
                    int choice = Integer.parseInt(Config.scanner().nextLine());
                    switch (choice) {
                        case 1:
                            break;
                        case 2:
                            return;
                    }
                } else {
                    gap = false;
                    newCatalog = catalog;
                }
            }
            Product newProduct = new Product(productId, productName, description, newCatalog, quantity, price, status);
            productController.updateProduct(newProduct);
        } else {
            System.err.println("Id Not Found!");
            System.out.println("1. Try Again");
            System.out.println("2. Back to Menu");
            int choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    updateProduct(listCatalogs);
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
            new ProductController();
        }
    }
}
