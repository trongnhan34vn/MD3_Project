package project.view.user;

import project.config.Config;
import project.controller.CartController;
import project.controller.ProductController;
import project.model.cart.Cart;
import project.model.cart.CartItem;
import project.model.product.Product;

public class CartView {
    ProductController productController = new ProductController();
    CartController cartController = new CartController();
    //    Cart
    public void addToCart() {
        new ProductView().showListProducts();
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
                        new UserView().menu();
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
                        new UserView().menu();
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
                new ProductView().showProduct(cartItem.getProduct());
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
                    new InvoiceView().paidInvoice();
                    new InvoiceView().paidInvoiceOut();
                    break;
                case 5:
                    new UserView().menu();
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
                new UserView().menu();
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
                new UserView().menu();
            } else {
                idNotFound();
            }
        } else {
            System.err.println("Cart is empty! Please add product to cart!");
            showCart();
        }
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
                    new UserView().menu();
                    break;
                default:
                    System.err.println("Invalid Requirement!");
                    break;
            }
        }
    }
}
