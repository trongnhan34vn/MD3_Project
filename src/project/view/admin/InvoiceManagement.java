package project.view.admin;

import project.config.Config;
import project.controller.CartController;
import project.controller.InvoiceController;
import project.controller.UserController;
import project.model.cart.CartItem;
import project.model.invoice.Invoice;
import project.model.invoice.InvoiceItem;
import project.model.invoice.InvoiceStatus;
import project.model.invoice.RejectStatus;

import project.model.user.User;
import project.view.user.CartView;


import java.util.List;


public class InvoiceManagement {
    InvoiceController invoiceController = new InvoiceController();
    UserController userController = new UserController();
    List<Invoice> listInvoices = invoiceController.getAllInvoice();
    CartController cartController = new CartController();

    public InvoiceManagement() {
        System.out.println("-------------------------- Invoice Management --------------------------");
        System.out.println("1. Show Invoices");
        System.out.println("2. Confirm Invoice ");
//        System.out.println("3. Confirm Reject Request "+ "("+ invoiceController.getAllRejectInvoice().size() +")");
        System.out.println("3. Confirm Reject Request");
        System.out.println("4. Back To Menu");
        System.out.println("-------------------------- Invoice Management --------------------------");
        while (true) {
            System.out.println("Enter your choice: ");
            int choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    showInvoices();
                    backToMenu();
                    break;
                case 2:
                    confirmInvoice();
                    break;
                case 3:
                    confirmRejectRequest();
                    break;
                case 4:
                    new AdminView();
                    break;
                default:
                    System.err.println("Invalid Requirement! Try again!");
                    break;
            }
        }
    }

    public void confirmInvoice() {
        System.out.println("Enter user ID: ");
        int idUser = Integer.parseInt(Config.scanner().nextLine());
        if (userController.findById(idUser) != null) {
            System.out.println("Choose Invoice by ID: ");
            int idInvoice = Integer.parseInt(Config.scanner().nextLine());
            InvoiceItem selectInvoice = invoiceController.getInvoiceItemById(idInvoice);
            if (selectInvoice == null) {
                System.err.println("ID Not Found!");
                tryAgainBackMenu();
            } else {
//                displayInvoices(selectInvoice);
                selectConfirmInvoice(selectInvoice);
                if (invoiceController.updateInvoiceItem(selectInvoice, idUser)) {
                    System.out.println("Confirmed Success!");
                }
                backToMenu();
            }
        } else {
            System.err.println("ID Not Found!");
            tryAgainBackMenu();
        }
    }

    private void selectConfirmInvoice(InvoiceItem selectInvoice) {
        System.out.println("Confirm Rejection: ");
        System.out.println("1. Accept");
        System.out.println("2. Denied");
        int choice = Integer.parseInt(Config.scanner().nextLine());
        boolean stop = true;
        while (stop) {
            switch (choice) {
                case 1:
                    selectInvoice.setInvoiceStatus(InvoiceStatus.TRUE);
                    setProductsQuantity(selectInvoice);
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
    }

    public void setProductsQuantity(InvoiceItem selectInvoice) {
        List<CartItem> listCartItem = selectInvoice.getCart().getCartItems();
        cartController.setProductQuantity(listCartItem);
    }

    public void showInvoices() {
        System.out.println("---------------------- Invoices ----------------------");
        for (Invoice invoice:listInvoices) {
            displayUser(invoice.getUser());
            displayInvoices(invoice.getInvoiceItems());
        }
        System.out.println("---------------------- Invoices ----------------------");
    }

    private static void displayInvoices(List<InvoiceItem> listInvoice) {
        for (InvoiceItem invoiceItem : listInvoice) {
            System.out.println("---------- Invoice" + " #" + invoiceItem.getInvoiceId() + " ----------");
            new CartView().displayCart(invoiceItem.getCart());
            System.out.println("Status: " + ((invoiceItem.isInvoiceStatus() == InvoiceStatus.PENDING) ? "PENDING" : (invoiceItem.isInvoiceStatus() == InvoiceStatus.TRUE) ? "ACCEPTED" : "DENIED"));
            System.out.println("Reject Order: " + ((invoiceItem.getRejectStatus() == RejectStatus.PENDING) ? "PENDING" : (invoiceItem.getRejectStatus() == RejectStatus.TRUE) ? "ACCEPTED" : "DENIED"));
            System.out.println("Reject Message: " + invoiceItem.getRejectMessage());
            System.out.println("---------- Invoice" + " #" + invoiceItem.getInvoiceId() + " ----------");
        }
    }

    private static void displayUser(User invoiceUser) {
        System.out.println("------------ User ------------");
        System.out.println("User Id: " + invoiceUser.getId());
        System.out.println("Name: " + invoiceUser.getFullName());
        System.out.println("Email: " + invoiceUser.getEmail());
        System.out.println("Address: " + invoiceUser.getAddress());
        System.out.println("Phone Number: " + invoiceUser.getPhoneNumber());
        System.out.println("------------ User ------------");
    }

    public void confirmRejectRequest() {
        System.out.println("Enter user ID: ");
        int idUser = Integer.parseInt(Config.scanner().nextLine());
        if (userController.findById(idUser) != null) {
            System.out.println("Choose Invoice by ID: ");
            int idInvoice = Integer.parseInt(Config.scanner().nextLine());
            InvoiceItem selectInvoice = invoiceController.getInvoiceItemById(idInvoice);
            if (selectInvoice == null) {
                System.err.println("ID Not Found!");
                tryAgainBackMenu();
            } else {
//                displayInvoices(selectInvoice);
                selectConfirmReject(selectInvoice);
                if (invoiceController.updateInvoiceItem(selectInvoice, idUser)) {
                    System.out.println("Confirmed Success!");
                }
                backToMenu();
            }
        } else {
            System.err.println("ID Not Found!");
            tryAgainBackMenu();
        }
    }

    private void selectConfirmReject(InvoiceItem selectInvoice) {
        System.out.println("Confirm Rejection: ");
        System.out.println("1. Accept");
        System.out.println("2. Denied");
        int choice = Integer.parseInt(Config.scanner().nextLine());
        boolean stop = true;
        while (stop) {
            switch (choice) {
                case 1:
                    selectInvoice.setRejectStatus(RejectStatus.TRUE);
                    selectInvoice.setInvoiceStatus(InvoiceStatus.FALSE);
                    stop = false;
                    break;
                case 2:
                    selectInvoice.setRejectStatus(RejectStatus.FALSE);
                    selectInvoice.setInvoiceStatus(InvoiceStatus.TRUE);
                    setProductsQuantity(selectInvoice);
                    stop = false;
                    break;
                default:
                    System.err.println("Invalid Requirement!");
            }
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
