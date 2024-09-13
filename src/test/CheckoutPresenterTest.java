package test;

import business.CheckoutEntry;
import dataaccess.DataAccess;
import librarysystem.checkout.CheckoutPresenter;
import librarysystem.checkout.ICheckoutView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.mockito.Mockito.*;

class CheckoutPresenterTest {

    private CheckoutPresenter checkoutPresenter;

    @Mock
    private ICheckoutView mockView;

    @Mock
    private DataAccess mockDataAccess;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        checkoutPresenter = new CheckoutPresenter(mockView, mockDataAccess);
    }

    @AfterEach
    void clean() {
        mockDataAccess.cleanUpTestStorage();
    }


    // Test fetchData method when checkout records are available
    @Test
    void testFetchData_withEntries() {
        HashMap<Integer, CheckoutEntry> checkoutEntries = new HashMap<>();
        checkoutEntries.put(1, new CheckoutEntry(null, null)); // Mocked data

        // Mock behavior
        when(mockDataAccess.getAllCheckoutEntries()).thenReturn(checkoutEntries);

        // Call the method
        checkoutPresenter.fetchData();

        // Verify interactions
        verify(mockDataAccess).getAllCheckoutEntries();
        verify(mockView).updateTable(null, checkoutEntries);
    }

    // Test fetchData method when there are no checkout records
    @Test
    void testFetchData_noEntries() {
        // Mock behavior with an empty HashMap
        when(mockDataAccess.getAllCheckoutEntries()).thenReturn(new HashMap<>());

        // Call the method
        checkoutPresenter.fetchData();

        // Verify interactions
        verify(mockDataAccess).getAllCheckoutEntries();
        verify(mockView).updateTable(null, new HashMap<>());
    }

    // Test handleCheckoutAction when memberId is empty
    @Test
    void testHandleCheckoutAction_emptyMemberId() {
        String isbn = "978-0-596-52068-7";

        // Call the method with an empty memberId
        checkoutPresenter.handleCheckoutAction("", isbn);

        // Verify that an error dialog is shown
        verify(mockView).showErrorDialog("Member ID is empty");
    }

    // Test handleCheckoutAction when isbn is empty
    @Test
    void testHandleCheckoutAction_emptyIsbn() {
        String memberId = "1004";

        // Call the method with an empty isbn
        checkoutPresenter.handleCheckoutAction(memberId, "");

        // Verify that an error dialog is shown
        verify(mockView).showErrorDialog("ISBN is empty");
    }

    // Test handleCheckoutAction when checkout is available
    @Test
    void testHandleCheckoutAction_checkoutAvailable() {
        String memberId = "1004";
        String isbn = "978-0-596-52068-7";

        // Mock the behavior of mockDataAccess
        when(mockDataAccess.isCheckoutAvailable(memberId, isbn)).thenReturn(true);

        // Call the method
        checkoutPresenter.handleCheckoutAction(memberId, isbn);

        // Verify that the book is checked out and appropriate dialogs are shown
        verify(mockDataAccess).checkoutBook(memberId, isbn);
        verify(mockView).showSuccessDialog("Checkout successful");
        verify(mockView).updateTable(eq(memberId), any(HashMap.class));
    }

    // Test handleCheckoutAction when checkout is not available
    @Test
    void testHandleCheckoutAction_checkoutNotAvailable() {
        String memberId = "1004";
        String isbn = "978-0-596-52068-7";

        // Mock the behavior of mockDataAccess
        when(mockDataAccess.isCheckoutAvailable(memberId, isbn)).thenReturn(false);

        // Call the method
        checkoutPresenter.handleCheckoutAction(memberId, isbn);

        // Verify that an error dialog is shown
        verify(mockView).showErrorDialog("The book " + isbn + " is not available");
    }

    // Test checkoutBook method
    @Test
    void testCheckoutBook() {
        String memberId = "1004";
        String isbn = "978-0-596-52068-7";

        // Call the method
        checkoutPresenter.checkoutBook(memberId, isbn);

        // Verify that the book is checked out
        verify(mockDataAccess).checkoutBook(memberId, isbn);
    }
}