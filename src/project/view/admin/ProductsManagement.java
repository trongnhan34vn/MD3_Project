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
                backToMenu();
                break;
            case 2:
                createProduct();
                break;
            case 3:
                updateProduct();
                break;
            case 4:
                deleteProduct();
                break;
            case 5:
                searchProductByName();
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
    }

    public void showListCatalogs() {
        System.out.println("--------------- Catalogs ---------------");
        for (Catalog catalog: listCatalogs) {
            System.out.println("----------------------------------");
            System.out.println("Catalog ID: " + catalog.getCatalogId());
            System.out.println("Catalog Name: " + catalog.getCatalogName());
            System.out.println("----------------------------------");
        }
        System.out.println("--------------- Catalogs ---------------");
    }

    public void createProduct() {
        System.out.println("Enter the product number: ");
        int mountProducts = Integer.parseInt(Config.scanner().nextLine());
        System.out.println("Enter all products info: ");
        showListCatalogs();
        for (int i = 0; i < mountProducts; i++) {
            System.out.println("Enter Product " + (i+1));
            int productId;
            if (listProduct.isEmpty()) {
                productId = 1;
            } else {
                productId = listProduct.get(listProduct.size() - 1).getProductId() + 1;
            }
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
                            new ProductsManagement();
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

    public void updateProduct() {
        showListProducts(listProduct);
        System.out.println("Enter the product Id: ");
        int productId = Integer.parseInt(Config.scanner().nextLine());
        if (productController.findById(productId) != null) {
            showListCatalogs();
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
            System.out.println("Enter the product description: ");
            String description = Config.scanner().nextLine();
            System.out.println("Enter the product quantity: ");
            int quantity = Integer.parseInt(Config.scanner().nextLine());
            System.out.println("Enter the product price: ");
            float price = Float.parseFloat(Config.scanner().nextLine());
            System.out.println("Enter the product status: ");
            boolean status = Boolean.parseBoolean(Config.scanner().nextLine());
            Product newProduct = new Product(productId, productName, description, newCatalog, quantity, price, status);
            productController.updateProduct(newProduct);
            System.out.println("Update Success!");
            backToMenu();
        } else {
            System.err.println("Id Not Found!");
            System.out.println("1. Try Again");
            System.out.println("2. Back to Menu");
            int choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    updateProduct();
                    break;
                case 2:
                    backToMenu();
                    break;
            }
        }
    }

    public void deleteProduct() {
        showListProducts(listProduct);
        System.out.println("Enter the product Id: ");
        int delId = Integer.parseInt(Config.scanner().nextLine());
        if (productController.findById(delId) == null) {
            System.err.println("ID Not Found!");
        } else {
            productController.deleteProduct(delId);
            System.out.println("Delete Product Success!");
        }
        backToMenu();
    }

    public void searchProductByName() {
        showListProducts(listProduct);
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
            showListProducts(listSearch);
            backToMenu();
        }
    }


    public void backToMenu() {
        while (true) {
            System.out.println("Enter 'back' to back to menu: ");
            String back = Config.scanner().nextLine();
            if (back.equalsIgnoreCase("back")) {
                new ProductsManagement();
            }
        }
    }
}
