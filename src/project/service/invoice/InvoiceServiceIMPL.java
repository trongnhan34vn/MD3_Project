package project.service.invoice;

import project.config.Config;
import project.data.Path;
import project.model.invoice.Invoice;
import project.model.invoice.InvoiceItem;
import project.model.invoice.InvoiceStatus;
import project.model.user.User;
import project.service.cart.CartServiceIMPL;
import project.service.cart.ICartService;
import project.service.user.IUserService;
import project.service.user.UserServiceIMPL;

import java.util.ArrayList;
import java.util.List;

public class InvoiceServiceIMPL implements IInvoiceService {
    ICartService cartService = new CartServiceIMPL();
    IUserService userService = new UserServiceIMPL();
    User currentUser = userService.getCurrentUser();
    List<Invoice> listInvoices = new Config<Invoice>().readFromFile(Path.INVOICE_PATH);

    @Override
    public List<Invoice> findAll() {
        return listInvoices;
    }

    @Override
    public void save(Invoice invoice) {
        for (Invoice invoice1 : listInvoices) {
            if (invoice1.getUser().equals(invoice.getUser())) {
                listInvoices.set(listInvoices.indexOf(findById(invoice.getUser().getId())), invoice);
                break;
            }
        }
        listInvoices.add(invoice);
        new Config<Invoice>().writeToFile(listInvoices, Path.INVOICE_PATH);
    }

    @Override
    public Invoice findById(int id) {
        for (Invoice invoice : listInvoices) {
            if (invoice.getUser().getId() == id) {
                return invoice;
            }
        }
        return null;
    }

    @Override
    public void deleteById(int id) {

    }


    @Override
    public boolean createInvoice(InvoiceItem invoiceItem) {
        Invoice currentInvoice = findById(currentUser.getId());
        List<InvoiceItem> listCurUserInvoice;
        if (currentInvoice == null) {
            listCurUserInvoice = new ArrayList<>();
            listCurUserInvoice.add(invoiceItem);
            Invoice newInvoice = new Invoice(currentUser, listCurUserInvoice);
            save(newInvoice);
        } else {
            listCurUserInvoice = currentInvoice.getInvoiceItems();
            listCurUserInvoice.add(invoiceItem);
            currentInvoice.setInvoiceItems(listCurUserInvoice);
            save(currentInvoice);
        }
        cartService.deleteById(currentUser.getId());
        return true;
    }


    @Override
    public List<InvoiceItem> findAllInvoiceItems() {
        Invoice currentInvoice = getCurrrentInvoice();
        if (currentInvoice == null) {
            return new ArrayList<>();
        }
        return currentInvoice.getInvoiceItems();
    }

    @Override
    public Invoice getCurrrentInvoice() {
        for (Invoice invoice : listInvoices) {
            if (invoice.getUser().getId() == currentUser.getId()) {
                return invoice;
            }
        }
        return null;
    }

    @Override
    public InvoiceItem getInvoiceItem(int id) {
        for (InvoiceItem invoiceItem : getCurrrentInvoice().getInvoiceItems()) {
            if (invoiceItem.getInvoiceId() == id) {
                return invoiceItem;
            }
        }
        return null;
    }

    @Override
    public boolean updateInvoiceItem(InvoiceItem invoiceItem) {
        List<InvoiceItem> listInvoiceItem = getCurrrentInvoice().getInvoiceItems();
        listInvoiceItem.set(listInvoiceItem.indexOf(getInvoiceItem(invoiceItem.getInvoiceId())),invoiceItem);
        new Config<Invoice>().writeToFile(listInvoices,Path.INVOICE_PATH);
        return true;
    }

    @Override
    public List<InvoiceItem> getReject(List<InvoiceItem> list) {
        List<InvoiceItem> rejectList = new ArrayList<>(list);
        rejectList.removeIf(invoiceItem -> invoiceItem.isInvoiceStatus() == InvoiceStatus.PENDING);
        return rejectList;
    }
}
