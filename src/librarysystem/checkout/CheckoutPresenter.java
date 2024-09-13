package librarysystem.checkout;

import business.CheckoutRecord;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import java.util.HashMap;

public class CheckoutPresenter implements ICheckoutPresenter {
    private ICheckoutView mView;
    private DataAccess mDataAccess;

    public CheckoutPresenter(ICheckoutView checkoutView) {
        mView = checkoutView;
        mDataAccess = new DataAccessFacade();
    }

    //only for test
    public CheckoutPresenter(ICheckoutView checkoutView, DataAccess dataAccess) {
        mView = checkoutView;
        mDataAccess = dataAccess;
    }

    @Override
    public void fetchData() {
        HashMap<Integer, CheckoutRecord> cr = mDataAccess.readCheckoutRecordMap();
//        System.out.println("checkour records size: " + cr.size());

        if (cr != null) {
            mView.updateTable(cr);
        }
    }

    @Override
    public void handleCheckoutAction(String memberId, String isbn) {
        System.out.println("Clicked Checkout");
        if (memberId.isEmpty()) {
            mView.showErrorDialog("Member ID is empty");
            return;
        }
        if (isbn.isEmpty()) {
            mView.showErrorDialog("ISBN is empty");
            return;
        }

        //test
        //user: 1004
        //isbn: 978-0-596-52068-7
        if (mDataAccess.isCheckoutAvailable(memberId, isbn)) {
            checkoutBook(memberId, isbn);
            mView.showSuccessDialog("Checkout successful");
            mView.updateTable(mDataAccess.readCheckoutRecordMap());
        } else {
            mView.showErrorDialog("The book " + isbn + " is not available");
        }
    }

    @Override
    public void checkoutBook(String memberId, String isbn) {

        mDataAccess.checkoutBook(memberId, isbn);

    }

}

