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
        return invoiceService.getRejectInvoiceItemById(id);
    }

    public InvoiceItem getInvoiceItemById(int id, int idUser) {
        return invoiceService.getRejectInvoiceItemById(id, idUser);
    }
    public boolean updateInvoiceItem(InvoiceItem invoiceItem, int id) {
        return invoiceService.updateInvoiceItem(invoiceItem, id);
    }
    public List<Invoice> getAllInvoice() {
        return invoiceService.findAll();
    }
    public List<Invoice> getAllRejectInvoice() {
        return invoiceService.getAllRejectInvoice();
    }
    public InvoiceItem getPendingInvoice(int id, int idUser) {
        return invoiceService.getPendingInvoiceItemById(id,idUser);
    }
    public List<Invoice> getAllPendingInvoice() {
        return invoiceService.getAllPendingInvoice();
    }
}
