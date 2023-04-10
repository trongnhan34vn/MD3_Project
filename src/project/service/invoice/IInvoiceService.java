package project.service.invoice;

import project.model.invoice.Invoice;
import project.model.invoice.InvoiceItem;
import project.model.user.User;
import project.service.IGenericService;

import java.util.List;

public interface IInvoiceService extends IGenericService<Invoice> {
    boolean createInvoice(InvoiceItem invoiceItem);
    List<InvoiceItem> findAllInvoiceItems();
    Invoice getCurrrentInvoice();
    InvoiceItem getInvoiceItem(int id);
    boolean updateInvoiceItem(InvoiceItem invoiceItem);
    List<InvoiceItem> getReject(List<InvoiceItem> list);
}
