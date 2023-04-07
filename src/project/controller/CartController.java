package project.controller;

import project.model.Cart;
import project.model.CartItem;
import project.service.cart.CartServiceIMPL;
import project.service.cart.ICartService;

import java.util.List;

public class CartController {
    ICartService cartService = new CartServiceIMPL();
    public boolean addToCart(CartItem cartItem) {
        return cartService.addToCart(cartItem);
    }

    public List<Cart> getAllCart() {
        return cartService.findAll();
    }

    public Cart getCurrentUserCart() {
        return cartService.getCurrentUserCart();
    }
}
