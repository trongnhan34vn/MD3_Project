package project.view.admin;

import project.config.Config;
import project.controller.InvoiceController;
import project.controller.UserController;
import project.model.invoice.Invoice;
import project.model.invoice.InvoiceItem;
import project.model.invoice.InvoiceStatus;
import project.model.user.User;
import project.view.user.CartView;
import project.view.user.UserView;

import java.util.List;

public class InvoiceManagement {
    InvoiceController invoiceController = new InvoiceController();
    UserController userController = new UserController();
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
                    showInvoices();
                    break;
                case 2:
                    confirmRejectRequest();
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
        System.out.println("---------------------- Invoices ----------------------");
        for (Invoice invoice : listInvoices) {
            displayUser(invoice);
            for (InvoiceItem invoiceItem : invoice.getInvoiceItems()) {
                displayInvoices(invoiceItem);
            }
        }
        System.out.println("---------------------- Invoices ----------------------");
        backToMenu();
    }

    private static void displayInvoices(InvoiceItem invoiceItem) {
        System.out.println("---------- Invoice" + " #" + invoiceItem.getInvoiceId() + " ----------");
        new CartView().displayCart(invoiceItem.getCart());
        System.out.println("Reject Request Status: " + invoiceItem.isInvoiceStatus());
        System.out.println("Reject Message: " + invoiceItem.getRejectMessage());
        System.out.println("---------- Invoice" + " #" + invoiceItem.getInvoiceId() + " ----------");
    }

    private static void displayUser(Invoice invoice) {
        System.out.println("------------ User ------------");
        User invoiceUser = invoice.getUser();
        System.out.println("User Id: " + invoiceUser.getId());
        System.out.println("Name: " + invoiceUser.getFullName());
        System.out.println("Email: " + invoiceUser.getEmail());
        System.out.println("Address: " + invoiceUser.getAddress());
        System.out.println("Phone Number: " + invoiceUser.getPhoneNumber());
        System.out.println("------------ User ------------");
    }


    public void confirmRejectRequest() {
        showInvoices();
        System.out.println("Enter user ID: ");
        int idUser = Integer.parseInt(Config.scanner().nextLine());
        if (userController.findById(idUser) != null) {
            Invoice selectUserInvoice = invoiceController.findById(idUser);
            List<InvoiceItem> listNotice = invoiceController.getReject(selectUserInvoice.getInvoiceItems());
            for (InvoiceItem invoiceItem:listNotice) {
                displayInvoices(invoiceItem);
            }
            System.out.println("Choose Invoice by ID: ");
            int idInvoice = Integer.parseInt(Config.scanner().nextLine());
            InvoiceItem selectInvoice = invoiceController.getInvoiceItemById(idInvoice);
            if (selectInvoice == null) {
                System.err.println("ID Not Found!");
                tryAgainBackMenu();
            } else {
                displayInvoices(selectInvoice);
                System.out.println("Confirm Rejection: ");
                System.out.println("1. Accept");
                System.out.println("2. Denied");
                int choice = Integer.parseInt(Config.scanner().nextLine());
                boolean stop = true;
                while (stop) {
                    switch (choice) {
                        case 1:
                            selectInvoice.setInvoiceStatus(InvoiceStatus.TRUE);
                            stop = false;
                            break;
                        case 2:
                            selectInvoice.setInvoiceStatus(InvoiceStatus.FALSE);
                            stop = false;
                            break;
                        default:
                            System.err.println("Invalid Requirement!");
                    }
                }
                invoiceController.updateInvoiceItem(selectInvoice);
            }
        } else {
            System.err.println("ID Not Found!");
            tryAgainBackMenu();
        }
    }

    private void tryAgainBackMenu() {
        System.out.println("1. Try Again");
        System.out.println("2. Back to Menu");
        while (true) {
            System.out.println("Enter your choice: ");
            int choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    confirmRejectRequest();
                    break;
                case 2:
                    new InvoiceManagement();
                    break;
                case 3:
                    System.err.println("Invalid Requirement!");
                    break;
            }
        }
    }


    public void backToMenu() {
        while (true) {
            System.out.println("Enter 'back' to back to menu: ");
            String back = Config.scanner().nextLine();
            if (back.equalsIgnoreCase("back")) {
                new InvoiceManagement();
            }
        }
    }
}
