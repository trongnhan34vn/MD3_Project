package project.view.user;

import project.config.Config;

import project.controller.*;
import project.model.cart.Cart;
import project.model.cart.CartItem;
import project.model.invoice.Invoice;
import project.model.invoice.InvoiceItem;
import project.model.invoice.InvoiceStatus;
import project.model.product.Catalog;
import project.model.product.Product;
import project.model.user.User;

import java.util.List;

public class UserView {
    CatalogController catalogController = new CatalogController();
    ProductController productController = new ProductController();
    CartController cartController = new CartController();
    List<Catalog> listCatalogs = catalogController.getAllCatalogs();
    UserController userController = new UserController();
    User currentUser = userController.getCurrentUser();
    InvoiceController invoiceController = new InvoiceController();
    Cart copyCart = cartController.getCurrentUserCart();
    List<InvoiceItem> listInvoiceItem = invoiceController.getListInvoiceItem();

    public UserView() {
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
                showInvoice();
                break;
            case 6:
                showInfoUser(currentUser);
                break;
            case 7:
                break;
            default:
                System.err.println("Invalid Requirement! Try again!");
                new UserView();
                break;
        }
    }


    //    show list products theo catalog
    public void showProduct(Product product) {
        System.out.println("Product ID: " + product.getProductId());
        System.out.println("Product Name: " + product.getProductName());
        System.out.println("Description: " + (product.getDescription() == null ? "<blank>" : product.getDescription()));
        System.out.println("Price: " + Config.currencyVN.format(product.getPrice()));
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
                        backToMenu();
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
            backToMenu();
        }
    }

    //    Cart
    public void addToCart() {
        showListProducts();
        System.out.println("Enter product ID to add to cart: ");
        int productId = Integer.parseInt(Config.scanner().nextLine());
        Product selectProduct = productController.findById(productId);
        if (selectProduct == null) {
            System.err.println("ID Not Found!");
            System.out.println("1. Try Again");
            System.out.println("2. Back to Menu");
            while (true) {
                int choice = Integer.parseInt(Config.scanner().nextLine());
                switch (choice) {
                    case 1:
                        addToCart();
                        break;
                    case 2:
                        new UserView();
                        break;
                }
            }
        } else {
            System.out.println("Enter quantity: ");
            int quantity = Integer.parseInt(Config.scanner().nextLine());
            CartItem cartItem = new CartItem(selectProduct, quantity);
            if (cartController.addToCart(cartItem)) {
                System.out.println("Add to Cart Success!");
            }
            System.out.println("1. Continue add to cart");
            System.out.println("2. Show Cart");
            System.out.println("3. Back to Menu");
            while (true) {
                System.out.println("Enter your choice: ");
                int choice = Integer.parseInt(Config.scanner().nextLine());
                switch (choice) {
                    case 1:
                        addToCart();
                        break;
                    case 2:
                        showCart();
                        break;
                    case 3:
                        new UserView();
                        break;
                    default:
                        System.err.println("Invalid Requirement! Try again!");
                        break;
                }
            }
        }
    }

    public void displayCart(Cart cart) {
        if (cart != null) {
            for (CartItem cartItem : cart.getCartItems()) {
                showProduct(cartItem.getProduct());
                System.out.println("Quantity: " + cartItem.getQuantity());
                System.out.println(" ");
            }
            System.out.println("-------------");
            System.out.println("Total: " + Config.currencyVN.format(cartController.getTotal(cart.getCartItems())));
        }
    }

    public void showCart() {
        Cart currentCart = cartController.getCurrentUserCart();
        System.out.println("---------------- Shopping Cart ----------------");
        displayCart(currentCart);
        System.out.println("---------------- Shopping Cart ----------------");
        System.out.println("1. Add more product to cart ");
        System.out.println("2. Change product quantity");
        System.out.println("3. Remove product from cart");
        System.out.println("4. Payment");
        System.out.println("5. Back to Menu");
        while (true) {
            System.out.println("Enter your choice: ");
            int choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    addToCart();
                    break;
                case 2:
                    changeProductQuantity();
                    break;
                case 3:
                    removeProductFromCart();
                    break;
                case 4:
                    paidInvoice();
                    paidInvoiceOut();
                    break;
                case 5:
                    new UserView();
                    break;
                default:
                    System.err.println("Invalid Requirement! Try again!");
                    break;
            }
        }

    }

    public void changeProductQuantity() {
        if (cartController.getCurrentUserCart() != null) {
            //        nhập sản phẩm muốn thay đổi
            System.out.println("Enter product Id to change quantity: ");
            int idProduct = Integer.parseInt(Config.scanner().nextLine());
            if (idProduct <= 0) {
                System.err.println("Invalid Requirement!");
                changeProductQuantity();
            }
//        Kiểm tra cartItem có tồn tại trong giỏ hàng không findByID
            CartItem selectCartItem = cartController.findCartItemById(idProduct);
            if (selectCartItem == null) {
                idNotFound();
            } else {
                System.out.println("Enter quantity: ");
                int quantity = Integer.parseInt(Config.scanner().nextLine());
//            set lại cartItem
                selectCartItem.setQuantity(quantity);
                if (cartController.changeQuantityCartItem(selectCartItem)) {
                    System.out.println("Change Quantity Success!");
                }
                backToMenu();
            }
        } else {
            System.err.println("Cart is empty! Please add product to cart!");
            showCart();
        }
    }

    public void removeProductFromCart() {
        if (cartController.getCurrentUserCart() != null) {
            //        nhập
            System.out.println("Enter product Id to remove:");
            int idProduct = Integer.parseInt(Config.scanner().nextLine());
            if (cartController.removeCartItemById(idProduct)) {
                System.out.println("Remove Item Success!");
                backToMenu();
            } else {
                idNotFound();
            }
        } else {
            System.err.println("Cart is empty! Please add product to cart!");
            showCart();
        }
    }

    //  Invoice
    public void showInvoice() {
//        In ra hoá đơn (thông tin user, thông tin sản phẩm)
        System.out.println("----------------- Invoices -----------------");
        Invoice currentInvoice = invoiceController.findById(currentUser.getId());
        if (currentInvoice != null) {
            displayUser(currentUser);
            List<InvoiceItem> invoiceItems = currentInvoice.getInvoiceItems();
            for (InvoiceItem invoiceItem : invoiceItems) {
                System.out.println("---------------- Invoice " + "#" + invoiceItem.getInvoiceId() + " ----------------");
                displayCart(invoiceItem.getCart());
                if (invoiceItem.getRejectMessage() != null) {
                    System.out.println("Reject Order: " + ((invoiceItem.isInvoiceStatus() == InvoiceStatus.PENDING) ? "PENDING" : (invoiceItem.isInvoiceStatus() == InvoiceStatus.TRUE) ? "CONFIRMED" : "DENIED"));
                    System.out.println("Reason: " + invoiceItem.getRejectMessage());
                }
                System.out.println("---------------- End Invoice ----------------");
            }
        }
        System.out.println("----------------- Invoices -----------------");
        System.out.println("1. Reject Order");
        System.out.println("2. Back to Menu");
        while (true) {
            System.out.println("Enter your choice: ");
            int choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    rejectOrder();
                    break;
                case 2:
                    new UserView();
                    break;
                default:
                    System.err.println("Invalid Requirement! Try again");
                    break;
            }
        }
    }

    public void rejectOrder() {
        System.out.println("Enter order Id: ");
        int orderId = Integer.parseInt(Config.scanner().nextLine());
        InvoiceItem selectInvoice = invoiceController.getInvoiceItemById(orderId);
        if (selectInvoice != null) {
            String rejectMessage = getRejectMessage();
            InvoiceItem newInvoiceItem = new InvoiceItem(selectInvoice.getInvoiceId(), selectInvoice.getCart(), InvoiceStatus.PENDING, rejectMessage);
            if (invoiceController.updateInvoiceItem(newInvoiceItem)) {
                System.out.println("Wait for admin's confirm!");
                backToMenu();
            }
        } else {
            System.err.println("Id Not Found!");
            System.out.println("1. Try Again");
            System.out.println("2. Back To Menu");
            while (true) {
                System.out.println("Enter your choice: ");
                int choice = Integer.parseInt(Config.scanner().nextLine());
                switch (choice) {
                    case 1:
                        rejectOrder();
                        break;
                    case 2:
                        backToMenu();
                        break;
                    default:
                        System.err.println("Invalid Requirement! Try again");
                        break;
                }
            }
        }
    }

    public String getRejectMessage() {
        String rejectMessage = null;
        System.out.println("Choose the reason for rejecting the order: ");
        System.out.println("1. Change the address");
        System.out.println("2. Change the product in the order");
        System.out.println("3. Troublesome payment procedure");
        System.out.println("4. Other");
        int choice = Integer.parseInt(Config.scanner().nextLine());
        switch (choice) {
            case 1:
                rejectMessage = "Change the address";
                break;
            case 2:
                rejectMessage = "Change the product in the order";
                break;
            case 3:
                rejectMessage = "Troublesome payment procedure";
                break;
            case 4:
                rejectMessage = Config.scanner().nextLine();
                break;
        }
        return rejectMessage;
    }

    public void paidInvoice() {
        if (currentUser.getPhoneNumber() == null && currentUser.getAddress() == null) {
            System.err.println("Please update your info: phone number & address. To complete payment");
            System.out.println("1. Account");
            System.out.println("2. Back To Menu");
            System.out.println("Enter your choice: ");
            int choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    showInfoUser(currentUser);
                    break;
                case 2:
                    new UserView();
                    break;
                default:
                    System.err.println("Invalid Requirement!");
                    break;
            }
        } else {
            int id;
            if (listInvoiceItem.isEmpty()) {
                id = 1;
            } else {
                id = listInvoiceItem.get(listInvoiceItem.size() - 1).getInvoiceId() + 1;
            }
            copyCart = cartController.getCurrentUserCart();
            InvoiceItem invoiceItem = new InvoiceItem(id, copyCart);
            boolean stat = invoiceController.createInvoice(invoiceItem);
            if (stat) {
                System.out.println("Create Invoice Success!");
            }
        }
    }

    public void paidInvoiceOut() {
        System.out.println("To Show Invoices or Back to Menu");
        System.out.println("1. Show Invoices ");
        System.out.println("2. Back to Menu");
        while (true) {
            System.out.println("Enter your choice: ");
            int choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    showInvoice();
                    break;
                case 2:
                    new UserView();
                    break;
                default:
                    System.err.println("Invalid Requirement! Try again!");
                    break;
            }
        }
    }

    //  User
    public void displayUser(User user) {
        System.out.println("-------------- User --------------");
        System.out.println("Name: " + user.getFullName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Address: " + user.getAddress());
        System.out.println("Phone: " + user.getPhoneNumber());
        System.out.println("-------------- User --------------");
    }

    public void idNotFound() {
        System.err.println("Id Not Found!");
        System.out.println("1. Try Again");
        System.out.println("2. Back to Menu");
        System.out.println("Enter your choice: ");
        while (true) {
            int choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    changeProductQuantity();
                    break;
                case 2:
                    new UserView();
                    break;
                default:
                    System.err.println("Invalid Requirement!");
                    break;
            }
        }
    }

    public void showInfoUser(User user) {
        System.out.println("--------------------- Info ----------------------");
        System.out.println("Email: " + user.getEmail());
        System.out.println("Password: *********");
        System.out.println("Fullname: " + user.getFullName());
        System.out.println("Address: " + ((user.getAddress() == null) ? "<blank>" : user.getAddress()));
        System.out.println("Phone: " + ((user.getPhoneNumber() == null) ? "<blank>" : user.getPhoneNumber()));
        System.out.println("--------------------- Info ----------------------");
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
                    new UserView();
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
                new UserView();
            }
        }
    }
}
