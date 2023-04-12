package project.service.invoice;

import project.model.invoice.Invoice;
import project.model.invoice.InvoiceItem;
import project.service.IGenericService;

import java.util.List;

public interface IInvoiceService extends IGenericService<Invoice> {
    boolean createInvoice(InvoiceItem invoiceItem);
    List<InvoiceItem> findAllInvoiceItems();
    Invoice getCurrrentInvoice();
    InvoiceItem getRejectInvoiceItemById(int id);
    InvoiceItem getRejectInvoiceItemById(int id, int idUser);
    boolean updateInvoiceItem(InvoiceItem invoiceItem, int id);
    List<Invoice> getAllRejectInvoice();
    InvoiceItem getPendingInvoiceItemById(int id, int idUser);
    List<Invoice> getAllPendingInvoice();
}
