package project.view.user;

import project.config.Config;
import project.controller.CartController;
import project.controller.InvoiceController;
import project.controller.UserController;
import project.model.cart.Cart;
import project.model.invoice.Invoice;
import project.model.invoice.InvoiceItem;
import project.model.invoice.InvoiceStatus;
import project.model.user.User;

import java.util.List;

public class InvoiceView {
    CartController cartController = new CartController();
    InvoiceController invoiceController = new InvoiceController();
    UserController userController = new UserController();
    Cart copyCart = cartController.getCurrentUserCart();
    List<InvoiceItem> listInvoiceItem = invoiceController.getListInvoiceItem();
    User currentUser = userController.getCurrentUser();

    public void paidInvoice() {
        if (currentUser.getPhoneNumber() == null && currentUser.getAddress() == null) {
            System.err.println("Please update your info: phone number & address. To complete payment");
            System.out.println("1. Account");
            System.out.println("2. Back To Menu");
            System.out.println("Enter your choice: ");
            int choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    new UserView().showInfoUser(currentUser);
                    break;
                case 2:
                    new UserView().menu();
                    break;
                default:
                    System.err.println("Invalid Requirement!");
                    break;
            }
        } else {
            int id;
            if (listInvoiceItem.isEmpty()) {
                id = 1;
            } else {
                id = listInvoiceItem.get(listInvoiceItem.size() - 1).getInvoiceId() + 1;
            }
            copyCart = cartController.getCurrentUserCart();
            InvoiceItem invoiceItem = new InvoiceItem(id, copyCart);
            boolean stat = invoiceController.createInvoice(invoiceItem);
            if (stat) {
                System.out.println("Create Invoice Success!");
            }
        }
    }

    public void paidInvoiceOut() {
        System.out.println("To Show Invoices or Back to Menu");
        System.out.println("1. Show Invoices ");
        System.out.println("2. Back to Menu");
        while (true) {
            System.out.println("Enter your choice: ");
            int choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    showInvoice();
                    break;
                case 2:
                    new UserView().menu();
                    break;
                default:
                    System.err.println("Invalid Requirement! Try again!");
                    break;
            }
        }
    }

    public void showInvoice() {
        System.out.println("----------------- Invoices -----------------");
        Invoice currentInvoice = invoiceController.findById(currentUser.getId());
        if (currentInvoice != null) {
            new UserView().displayUser(currentUser);
            List<InvoiceItem> invoiceItems = currentInvoice.getInvoiceItems();
            for (InvoiceItem invoiceItem : invoiceItems) {
                System.out.println("---------------- Invoice " + "#" + invoiceItem.getInvoiceId() + " ----------------");
                new CartView().displayCart(invoiceItem.getCart());
                if (invoiceItem.getRejectMessage() != null) {
                    System.out.println("Reject Order: " + ((invoiceItem.isInvoiceStatus() == InvoiceStatus.PENDING) ? "PENDING" : (invoiceItem.isInvoiceStatus() == InvoiceStatus.TRUE) ? "ACCEPTED" : "DENIED"));
                    System.out.println("Reason: " + invoiceItem.getRejectMessage());
                }
                System.out.println("---------------- End Invoice ----------------");
            }
        }
        System.out.println("----------------- Invoices -----------------");
        displayInvoiceFuncMenu();
    }

    private void displayInvoiceFuncMenu() {
        System.out.println("1. Reject Order");
        System.out.println("2. Back to Menu");
        while (true) {
            System.out.println("Enter your choice: ");
            int choice = Integer.parseInt(Config.scanner().nextLine());
            switch (choice) {
                case 1:
                    rejectOrder();
                    break;
                case 2:
                    new UserView().menu();
                    break;
                default:
                    System.err.println("Invalid Requirement! Try again");
                    break;
            }
        }
    }

    public void rejectOrder() {
        System.out.println("Enter order Id: ");
        int orderId = Integer.parseInt(Config.scanner().nextLine());
        InvoiceItem selectInvoice = invoiceController.getInvoiceItemById(orderId);
        if (selectInvoice != null) {
            String rejectMessage = getRejectMessage();
            InvoiceItem newInvoiceItem = new InvoiceItem(selectInvoice.getInvoiceId(), selectInvoice.getCart(), InvoiceStatus.PENDING, rejectMessage);
            if (invoiceController.updateInvoiceItem(newInvoiceItem)) {
                System.out.println("Wait for admin's confirm!");
                new UserView().menu();
            }
        } else {
            System.err.println("Id Not Found!");
            System.out.println("1. Try Again");
            System.out.println("2. Back To Menu");
            while (true) {
                System.out.println("Enter your choice: ");
                int choice = Integer.parseInt(Config.scanner().nextLine());
                switch (choice) {
                    case 1:
                        rejectOrder();
                        break;
                    case 2:
                        new UserView().menu();
                        break;
                    default:
                        System.err.println("Invalid Requirement! Try again");
                        break;
                }
            }
        }
    }

    public String getRejectMessage() {
        String rejectMessage = null;
        System.out.println("Choose the reason for rejecting the order: ");
        System.out.println("1. Change the address");
        System.out.println("2. Change the product in the order");
        System.out.println("3. Troublesome payment procedure");
        System.out.println("4. Other");
        int choice = Integer.parseInt(Config.scanner().nextLine());
        switch (choice) {
            case 1:
                rejectMessage = "Change the address";
                break;
            case 2:
                rejectMessage = "Change the product in the order";
                break;
            case 3:
                rejectMessage = "Troublesome payment procedure";
                break;
            case 4:
                rejectMessage = Config.scanner().nextLine();
                break;
        }
        return rejectMessage;
    }
}
