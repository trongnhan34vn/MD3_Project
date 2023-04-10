package project.controller;

import project.model.invoice.Invoice;
import project.model.invoice.InvoiceItem;
import project.service.invoice.IInvoiceService;
import project.service.invoice.InvoiceServiceIMPL;

import java.util.List;

public class InvoiceController {
    IInvoiceService invoiceService = new InvoiceServiceIMPL();
    public boolean createInvoice(InvoiceItem invoiceItem) {
        return invoiceService.createInvoice(invoiceItem);
    }
    public Invoice findById(int id) {
        return invoiceService.findById(id);
    }
    public List<InvoiceItem> getListInvoiceItem() {
        return invoiceService.findAllInvoiceItems();
    }
    public InvoiceItem getInvoiceItemById(int id) {
        return invoiceService.getInvoiceItem(id);
    }
    public boolean updateInvoiceItem(InvoiceItem invoiceItem) {
        return invoiceService.updateInvoiceItem(invoiceItem);
    }
    public List<Invoice> getAllInvoice() {
        return invoiceService.findAll();
    }
}
