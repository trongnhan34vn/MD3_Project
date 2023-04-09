package project.service.cart;

import project.model.cart.Cart;
import project.model.cart.CartItem;
import project.service.IGenericService;

import java.util.List;

public interface ICartService extends IGenericService<Cart> {
    boolean addToCart(CartItem cartItem);
    Cart getCurrentUserCart();
    CartItem findCartItemById(int id);
    float getTotal(List<CartItem> list);
    boolean changeQuantityCartItem(CartItem cartItem);
    boolean removeCartItemById(int id);

}
