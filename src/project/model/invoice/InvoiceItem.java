package project.model.invoice;

import project.model.cart.Cart;

import java.io.Serializable;

public class InvoiceItem implements Serializable {
    private Cart cart;
    private boolean invoiceStatus;

    public InvoiceItem() {
    }

    public InvoiceItem(Cart cart, boolean invoiceStatus) {
        this.cart = cart;
        this.invoiceStatus = invoiceStatus;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public boolean isInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(boolean invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }
}
