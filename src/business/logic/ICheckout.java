package business.logic;

import business.CheckoutEntry;
import business.port.ICheckoutView;

import java.util.HashMap;

public interface ICheckout {
    HashMap<Integer, CheckoutEntry> getAllCheckoutEntries();
    void handleCheckoutAction(String memberId, String isbn, ICheckoutView action);
    void checkoutBook(String memberId, String isbn);
}
