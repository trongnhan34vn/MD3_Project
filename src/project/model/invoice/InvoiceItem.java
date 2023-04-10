package project.model.invoice;

import project.model.cart.Cart;

import java.io.Serializable;

public class InvoiceItem implements Serializable {
    private int invoiceId;
    private Cart cart;
    private boolean invoiceStatus;

    public InvoiceItem() {
    }

    public InvoiceItem(int invoiceId, Cart cart, boolean invoiceStatus) {
        this.invoiceId = invoiceId;
        this.cart = cart;
        this.invoiceStatus = invoiceStatus;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
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
