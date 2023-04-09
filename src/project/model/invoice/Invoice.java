package project.model.invoice;

import project.model.cart.Cart;
import project.model.user.User;

import java.io.Serializable;
import java.util.List;

public class Invoice implements Serializable {
    private User user;
    private List<InvoiceItem> invoiceItems;

    public Invoice() {
    }

    public Invoice(User user, List<InvoiceItem> invoiceItems) {
        this.user = user;
        this.invoiceItems = invoiceItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }
}
