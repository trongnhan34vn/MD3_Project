package project.view.admin;

import project.config.Config;
import project.controller.InvoiceController;
import project.model.invoice.Invoice;
import project.model.user.User;

import java.util.List;

public class InvoiceManagement {
    InvoiceController invoiceController = new InvoiceController();
    List<Invoice> listInvoices = invoiceController.getAllInvoice();

    public InvoiceManagement() {
        System.out.println("-------------------------- Invoice Management --------------------------");
        System.out.println("1. Show Invoices");
        System.out.println("2. Confirm Invoice");
        System.out.println("3. Confirm Reject Request");
        System.out.println("4. Back To Menu");
        System.out.println("-------------------------- Invoice Management --------------------------");
        while (true) {
            System.out.println("Enter your choice: ");
            int choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    System.err.println("Invalid Requirement! Try again!");
                    break;
            }
        }
    }

    public void showInvoices() {

    }

    public void displayUser(User user) {
        System.out.println("-------------- User --------------");
        System.out.println("Name: " + user.getFullName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Address: " + user.getAddress());
        System.out.println("Phone: " + user.getPhoneNumber());
        System.out.println("-------------- User --------------");
    }

    public void displayInvoice(Invoice invoice) {

    }
}
