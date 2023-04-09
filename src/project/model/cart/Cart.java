package project.model.cart;

import project.model.user.User;

import java.io.Serializable;
import java.util.List;

public class Cart implements Serializable {
    private User user;
    private List<CartItem> cartItems;

    public Cart() {
    }

    public Cart(User user, List<CartItem> cartItems) {
        this.user = user;
        this.cartItems = cartItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "user=" + user +
                ", cartItems=" + cartItems +
                '}';
    }
}
