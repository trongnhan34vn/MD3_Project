package project.controller;

import project.model.cart.Cart;
import project.model.cart.CartItem;
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
    public Cart findById(int id) {
        return cartService.findById(id);
    }

    public CartItem findCartItemById(int id) {
        return cartService.findCartItemById(id);
    }

    public boolean changeQuantityCartItem (CartItem cartItem) {
        return cartService.changeQuantityCartItem(cartItem);
    }

    public boolean removeCartItemById (int id) {
        return cartService.removeCartItemById(id);
    }

    public float getTotal(List<CartItem> list) {
        return cartService.getTotal(list);
    }

    public boolean setProductQuantity(List<CartItem> list) {
        return cartService.setProductQuantity(list);
    }
}
