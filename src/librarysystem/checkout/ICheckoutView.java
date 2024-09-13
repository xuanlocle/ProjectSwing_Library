package librarysystem.checkout;

import business.CheckoutRecord;

import java.util.HashMap;

public interface ICheckoutView {

    void showErrorDialog(String text);
    void showSuccessDialog(String text);

    void updateTable(HashMap<Integer, CheckoutRecord> cr);
}
