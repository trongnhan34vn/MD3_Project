package project.model.invoice;

import project.model.cart.Cart;

import java.io.Serializable;

public class InvoiceItem implements Serializable {
    private int invoiceId;
    private Cart cart;
    private InvoiceStatus invoiceStatus;
    private String rejectMessage;

    public InvoiceItem() {
    }

    public InvoiceItem(int invoiceId, Cart cart, InvoiceStatus invoiceStatus, String rejectMessage) {
        this.invoiceId = invoiceId;
        this.cart = cart;
        this.invoiceStatus = invoiceStatus;
        this.rejectMessage = rejectMessage;
    }

    public InvoiceItem(int invoiceId, Cart cart) {
        this.invoiceId = invoiceId;
        this.cart = cart;
    }

    public String getRejectMessage() {
        return rejectMessage;
    }

    public void setRejectMessage(String rejectMessage) {
        this.rejectMessage = rejectMessage;
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

    public InvoiceStatus isInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }
}
