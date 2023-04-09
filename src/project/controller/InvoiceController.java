package project.controller;

import project.model.invoice.Invoice;
import project.model.invoice.InvoiceItem;
import project.service.invoice.IInvoiceService;
import project.service.invoice.InvoiceServiceIMPL;

public class InvoiceController {
    IInvoiceService invoiceService = new InvoiceServiceIMPL();
    public boolean createInvoice(InvoiceItem invoiceItem) {
        return invoiceService.createInvoice(invoiceItem);
    }

    public Invoice findById(int id) {
        return invoiceService.findById(id);
    }
}
