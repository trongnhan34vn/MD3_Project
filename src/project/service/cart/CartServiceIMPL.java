package project.service.cart;

import project.config.Config;
import project.data.Path;
import project.model.cart.Cart;
import project.model.cart.CartItem;
import project.model.user.User;
import project.service.product.IProductService;
import project.service.product.ProductServiceIMPL;

import java.util.ArrayList;
import java.util.List;

public class CartServiceIMPL implements ICartService {
    List<Cart> listCarts = new Config<Cart>().readFromFile(Path.CART_PATH);

    IProductService productService = new ProductServiceIMPL();
    User currentUser;

    {
        List<User> listCurrentUsers = new Config<User>().readFromFile(Path.USER_LOGIN_PATH);
        if (listCurrentUsers.size() > 0) {
            currentUser = listCurrentUsers.get(0);
        }
    }

    Cart currentCart;
    List<CartItem> listCartItem;

    {
        if (listCarts != null) {
            currentCart = getCurrentUserCart();
            if (currentCart != null) {
                listCartItem = currentCart.getCartItems();
            }
        }
    }

    @Override
    public List<Cart> findAll() {
        return listCarts;
    }

    @Override
    public void save(Cart cart) {
        for (Cart cart1 : listCarts) {
            if (cart1.getUser().equals(cart.getUser())) {
                listCarts.set(listCarts.indexOf(findById(cart.getUser().getId())), cart);
                break;
            }
        }
        listCarts.add(cart);
        new Config<Cart>().writeToFile(listCarts, Path.CART_PATH);
    }

    @Override
    public Cart findById(int id) {
        for (Cart cart : listCarts) {
            if (cart.getUser().getId() == id) {
                return cart;
            }
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        listCarts.removeIf(cart -> cart.getUser().getId() == id);
        new Config<Cart>().writeToFile(listCarts, Path.CART_PATH);
    }

    @Override
    public boolean addToCart(CartItem cartItem) {
        Cart currentUserCart = findById(currentUser.getId());
        if (currentUserCart == null) {
            List<CartItem> listCUserCartItem = new ArrayList<>();
            listCUserCartItem.add(cartItem);
            Cart newCart = new Cart(currentUser, listCUserCartItem);
            save(newCart);
            return true;
        } else {
            for (CartItem item : currentUserCart.getCartItems()) {
                if (cartItem.getProduct().getProductId() == item.getProduct().getProductId()) {
                    item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                    save(currentUserCart);
                    return true;
                }
            }
            List<CartItem> listCUserCartItem = currentUserCart.getCartItems();
            listCUserCartItem.add(cartItem);
            currentUserCart.setCartItems(listCUserCartItem);
            save(currentUserCart);
            return true;
        }
    }

    @Override
    public Cart getCurrentUserCart() {
        for (Cart cart : listCarts) {
            if (cart.getUser().getId() == currentUser.getId()) {
                return cart;
            }
        }
        return null;
    }

    @Override
    public CartItem findCartItemById(int id) {
        for (CartItem cartItem : listCartItem) {
            if (cartItem.getProduct().getProductId() == id) {
                return cartItem;
            }
        }
        return null;
    }

    @Override
    public float getTotal(List<CartItem> list) {
        int total = 0;
        for (CartItem cartItem : list) {
            total += cartItem.getQuantity() * cartItem.getProduct().getPrice();
        }
        return total;
    }


    @Override
    public boolean changeQuantityCartItem(CartItem cartItem) {
        listCartItem.set(listCartItem.indexOf(findCartItemById(cartItem.getProduct().getProductId())), cartItem);
        new Config<Cart>().writeToFile(listCarts, Path.CART_PATH);
        return true;
    }

    @Override
    public boolean removeCartItemById(int id) {
        if (findCartItemById(id) == null) {
            return false;
        }
        listCartItem.removeIf(cartItem -> cartItem.getProduct().getProductId() == id);
        new Config<Cart>().writeToFile(listCarts, Path.CART_PATH);
        return true;
    }

    @Override
    public boolean setProductQuantity(List<CartItem> list) {
        for (CartItem cartItem : list) {
            cartItem.getProduct().setQuantity(cartItem.getProduct().getQuantity() - cartItem.getQuantity());
            productService.save(cartItem.getProduct());
        }
        return true;
    }
}
