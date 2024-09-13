package librarysystem.checkout;

import business.CheckoutEntry;

import java.util.HashMap;

public interface ICheckoutView {

    void showErrorDialog(String text);
    void showSuccessDialog(String text);

    void updateTable(String memberId, HashMap<Integer, CheckoutEntry> cr);
}
