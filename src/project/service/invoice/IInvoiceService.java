package project.service.invoice;

import project.model.invoice.Invoice;
import project.model.invoice.InvoiceItem;
import project.service.IGenericService;

public interface IInvoiceService extends IGenericService<Invoice> {
    boolean createInvoice(InvoiceItem invoiceItem);
}
