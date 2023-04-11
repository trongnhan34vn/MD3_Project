package project.service.invoice;

import project.model.invoice.Invoice;
import project.model.invoice.InvoiceItem;
import project.service.IGenericService;

import java.util.List;

public interface IInvoiceService extends IGenericService<Invoice> {
    boolean createInvoice(InvoiceItem invoiceItem);
    List<InvoiceItem> findAllInvoiceItems();
    Invoice getCurrrentInvoice();
    InvoiceItem getInvoiceItemById(int id);
    boolean updateInvoiceItem(InvoiceItem invoiceItem, int id);
    List<Invoice> getAllPending();
    List<Invoice> getAllRejectInvoice();
}
