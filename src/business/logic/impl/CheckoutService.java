package business.logic.impl;

import business.CheckoutEntry;
import business.exception.CheckoutException;
import business.port.ICheckoutView;
import business.logic.ICheckout;
import dataaccess.DataAccess;

import java.util.HashMap;

public class CheckoutService implements ICheckout {
    DataAccess dataAccess;

    public CheckoutService() {}
    public CheckoutService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public HashMap<Integer, CheckoutEntry> getAllCheckoutEntries() {
        return dataAccess.getAllCheckoutEntries();
    }

    @Override
    public void handleCheckoutAction(String memberId, String isbn, ICheckoutView action) {
        if (memberId.isEmpty()) {
            action.showErrorDialog("Member ID is empty");
            return;
        }
        if (isbn.isEmpty()) {
            action.showErrorDialog("ISBN is empty");
            return;
        }

        //test
        //user: 1004
        //isbn: 978-0-596-52068-7
        try {
            if (dataAccess.isCheckoutAvailable(memberId, isbn)) {
                checkoutBook(memberId, isbn);
                action.showSuccessDialog("Checkout successful");
                action.updateTable(memberId, dataAccess.getCheckoutEntriesByMemberId(memberId));
            } else {
                action.showErrorDialog("The book " + isbn + " is not available");
            }
        } catch (CheckoutException e) {
            action.showErrorDialog(e.getMessage());
        }
    }

    @Override
    public void checkoutBook(String memberId, String isbn) {
        dataAccess.checkoutBook(memberId, isbn);
    }
}
