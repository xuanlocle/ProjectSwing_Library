package librarysystem.checkout;

public interface ICheckoutPresenter {
    void handleCheckoutAction(String memberId, String isbn);
    void checkoutBook(String memberId, String isbn);

    void fetchData();
}
