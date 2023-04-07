package project.service.cart;

import project.config.Config;
import project.data.Path;
import project.model.Cart;
import project.model.CartItem;
import project.model.User;

import java.util.ArrayList;
import java.util.List;

public class CartServiceIMPL implements ICartService {
    List<Cart> listCart = new Config<Cart>().readFromFile(Path.CART_PATH);
    User currentUser;

    {

        List<User> listCurrentUsers = new Config<User>().readFromFile(Path.USER_LOGIN_PATH);
        if (listCurrentUsers.size() > 0) {
            currentUser = listCurrentUsers.get(0);
        }
    }

    @Override
    public List<Cart> findAll() {
        return listCart;
    }

    @Override
    public void save(Cart cart) {
//        khi listcart da co cart cua user
        for (Cart cart1 : listCart) {
            if (cart1.getUser().equals(cart.getUser())) {
                listCart.set(listCart.indexOf(findById(cart.getUser().getId())), cart);
                break;
            }
        }
//        Khi listCart chua co cart nao
        listCart.add(cart);
        new Config<Cart>().writeToFile(listCart, Path.CART_PATH);
    }

    @Override
    public Cart findById(int id) {
        for (Cart cart : listCart) {
            if (cart.getUser().getId() == id) {
                return cart;
            }
        }
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public boolean addToCart(CartItem cartItem) {
        Cart currentUserCart = findById(currentUser.getId());
//        Neu currentUser chua co cart -> tao cart cho User hien tai.
        if (currentUserCart == null) {
            List<CartItem> listItem = new ArrayList<>();
            listItem.add(cartItem);
            Cart newCart = new Cart(currentUser, listItem);
            save(newCart);
            return true;
        } else {
//            Neu cart da ton tai
            for (CartItem cart : currentUserCart.getListCartItem()) {
//                Duyet cart
//                Neu san pham da ton tai + so luong
                if (cart.getProduct().getProductId() == cartItem.getProduct().getProductId()) {
                    cart.setQuantity(cart.getQuantity() + cartItem.getQuantity());
                    save(currentUserCart);
                    return true;
                }
            }
//            lay danh sach san pham cua cart -> them san pham
            List<CartItem> listCartItems = currentUserCart.getListCartItem();
            listCartItems.add(cartItem);
//            set lai currentUser cart
            currentUserCart.setListCartItem(listCartItems);
            save(currentUserCart);
            return true;
        }
    }

    @Override
    public Cart getCurrentUserCart() {
        for (Cart cart : listCart) {
            if (cart.getUser().getId() == currentUser.getId()) {
                return cart;
            }
        }
        return null;
    }
}
