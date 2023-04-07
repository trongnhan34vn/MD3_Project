package project.service.cart;

import project.model.Cart;
import project.model.CartItem;
import project.model.User;
import project.service.IGenericService;

public interface ICartService extends IGenericService<Cart> {
    boolean addToCart(CartItem cartItem);
    Cart getCurrentUserCart();
}
