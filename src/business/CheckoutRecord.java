package business;

import java.io.Serializable;
import java.util.List;

public final class CheckoutRecord implements Serializable {

    private static final long serialVersionUID = 7274497633871237909L;
    private List<CheckoutEntry> checkoutEntries; // List of entries

    // Constructor
    public CheckoutRecord(List<CheckoutEntry> checkoutEntries) {
        this.checkoutEntries = checkoutEntries;
    }

    public List<CheckoutEntry> getCheckoutEntries() {
        return checkoutEntries;
    }

    public void setCheckoutEntries(List<CheckoutEntry> checkoutEntries) {
        this.checkoutEntries = checkoutEntries;
    }

}
