package project.model.invoice;

import project.model.cart.Cart;

import java.io.Serializable;

public class InvoiceItem implements Serializable {
    private int invoiceId;
    private Cart cart;
    private RejectStatus rejectStatus;
    private String rejectMessage;
    private InvoiceStatus invoiceStatus;

    public InvoiceItem() {
    }

    public InvoiceItem(int invoiceId, Cart cart, RejectStatus rejectStatus, String rejectMessage, InvoiceStatus invoiceStatus) {
        this.invoiceId = invoiceId;
        this.cart = cart;
        this.rejectStatus = RejectStatus.PENDING;
        this.rejectMessage = rejectMessage;
        this.invoiceStatus = InvoiceStatus.PENDING;
    }

    public InvoiceStatus isInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public RejectStatus getRejectStatus() {
        return rejectStatus;
    }

    public void setRejectStatus(RejectStatus rejectStatus) {
        this.rejectStatus = rejectStatus;
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


}
