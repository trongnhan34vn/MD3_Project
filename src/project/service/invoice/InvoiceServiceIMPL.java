package project.service.invoice;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import project.config.Config;
import project.data.Path;
import project.model.invoice.Invoice;
import project.model.invoice.InvoiceItem;
import project.model.invoice.InvoiceStatus;
import project.model.invoice.RejectStatus;
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
    public InvoiceItem getRejectInvoiceItemById(int id) {
        for (InvoiceItem invoiceItem : getCurrrentInvoice().getInvoiceItems()) {
            if (invoiceItem.getInvoiceId() == id) {
                return invoiceItem;
            }
        }
        return null;
    }

    @Override
    public InvoiceItem getRejectInvoiceItemById(int id, int idUser) {
        if (findById(idUser) != null) {
            for (InvoiceItem invoiceItem : findById(idUser).getInvoiceItems()) {
                if (invoiceItem.getInvoiceId() == id && invoiceItem.getRejectMessage() != null) {
                    return invoiceItem;
                }
            }
        }
        return null;
    }


    @Override
    public boolean updateInvoiceItem(InvoiceItem invoiceItem, int userId) {
        List<InvoiceItem> listInvoiceItem = findById(userId).getInvoiceItems();
        InvoiceItem updateInvoice = null;
        for (InvoiceItem item : listInvoiceItem) {
            if (item.getInvoiceId() == invoiceItem.getInvoiceId()) {
                updateInvoice = item;
                break;
            }
        }
        listInvoiceItem.set(listInvoiceItem.indexOf(updateInvoice), invoiceItem);
        new Config<Invoice>().writeToFile(listInvoices, Path.INVOICE_PATH);
        return true;
    }
    @Override
    public List<Invoice> getAllPendingInvoice() {
        List<Invoice> invoices = new ArrayList<>();
        for (Invoice invoice:listInvoices) {
            List<InvoiceItem> invoiceItems = invoice.getInvoiceItems();
            for (InvoiceItem invoiceItem :invoiceItems) {
                if (invoiceItem.isInvoiceStatus()==InvoiceStatus.PENDING) {
                    List<InvoiceItem> listPending = new ArrayList<>();
                    listPending.add(invoiceItem);
                    Invoice existUser = isExistUserInOrder(invoice.getUser(),invoices);
                    if(existUser == null) {
                        invoices.add(new Invoice(invoice.getUser(),listPending));
                    } else {
                        int index = getIndexInvoiceByUser(invoice.getUser(), invoices);
                        List<InvoiceItem> currentListInvoiceItem = invoices.get(index).getInvoiceItems();
                        currentListInvoiceItem.add(invoiceItem);
                        invoices.get(index).setInvoiceItems(currentListInvoiceItem);
                    }
                }
            }
        }
        return invoices;
    }

    @Override
    public List<Invoice> getAllRejectInvoice() {
        List<Invoice> invoices = new ArrayList<>();
        for (Invoice invoice : listInvoices) {
            List<InvoiceItem> temp = invoice.getInvoiceItems();
            for (InvoiceItem invoiceItem : temp) {
                if (invoiceItem.getRejectMessage() != null) {
                    List<InvoiceItem> listReject = new ArrayList<>();
                    listReject.add(invoiceItem);
                    Invoice checkExistUser = isExistUserInOrder(invoice.getUser(), invoices);
                    if (checkExistUser == null) {
                        invoices.add(new Invoice(invoice.getUser(), listReject));
                    } else {
                        int index = getIndexInvoiceByUser(invoice.getUser(), invoices);
                        List<InvoiceItem> currentListInvoiceItem = invoices.get(index).getInvoiceItems();
                        currentListInvoiceItem.add(invoiceItem);
                        invoices.get(index).setInvoiceItems(currentListInvoiceItem);
                    }
                }
            }
        }
        return invoices;
    }

    private int getIndexInvoiceByUser(User user, List<Invoice> invoices) {
        for (Invoice invoice : invoices) {
            if (invoice.getUser().getId() == user.getId()) {
                return invoices.indexOf(invoice);
            }
        }
        return -1;
    }

    private Invoice isExistUserInOrder(User user, List<Invoice> invoices) {
        for (Invoice invoice : invoices) {
            if (invoice.getUser().getId() == user.getId()) {
                return invoice;
            }
        }
        return null;
    }

    @Override
    public InvoiceItem getPendingInvoiceItemById(int id, int idUser) {
        if (findById(idUser) != null) {
            for (InvoiceItem invoiceItem : findById(idUser).getInvoiceItems()) {
                if (invoiceItem.getInvoiceId() == id && invoiceItem.isInvoiceStatus() == InvoiceStatus.PENDING) {
                    return invoiceItem;
                }
            }
        }
        return null;
    }
}
