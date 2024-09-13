package test;

import business.CheckoutRecord;
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


    @Test
    public void testFetchData_withNonNullData() {
        // Arrange
        HashMap<Integer, CheckoutRecord> mockCheckoutRecords = new HashMap<>();
        mockCheckoutRecords.put(1, new CheckoutRecord(null, null)); // Sample data
        when(mockDataAccess.readCheckoutRecordMap()).thenReturn(mockCheckoutRecords);

        // Act
        checkoutPresenter.fetchData();

        // Assert
        verify(mockView).updateTable(mockCheckoutRecords);
    }

    @Test
    public void testFetchData_withNullData() {
        // Arrange
        when(mockDataAccess.readCheckoutRecordMap()).thenReturn(null);

        // Act
        checkoutPresenter.fetchData();

        // Assert
        verify(mockView, never()).updateTable(any());  // Ensure updateTable() is not called
    }

    @Test
    public void testHandleCheckoutAction_whenMemberIdIsEmpty() {
        // Act
        checkoutPresenter.handleCheckoutAction("", "978-0-596-52068-7");

        // Assert
        verify(mockView).showErrorDialog("Member ID is empty");
    }

    @Test
    public void testHandleCheckoutAction_whenIsbnIsEmpty() {
        // Act
        checkoutPresenter.handleCheckoutAction("1004", "");

        // Assert
        verify(mockView).showErrorDialog("ISBN is empty");
    }

    @Test
    public void testHandleCheckoutAction_whenBookIsAvailable() {
        // Arrange
        when(mockDataAccess.isCheckoutAvailable("1004", "978-0-596-52068-7")).thenReturn(true);

        // Act
        checkoutPresenter.handleCheckoutAction("1004", "978-0-596-52068-7");

        // Assert
        verify(mockDataAccess).checkoutBook("1004", "978-0-596-52068-7");
        verify(mockView).showSuccessDialog("Checkout successful");
        verify(mockView).updateTable(mockDataAccess.readCheckoutRecordMap());
    }

    @Test
    public void testHandleCheckoutAction_whenBookIsNotAvailable() {
        // Arrange
        when(mockDataAccess.isCheckoutAvailable("1004", "978-0-596-52068-7")).thenReturn(false);

        // Act
        checkoutPresenter.handleCheckoutAction("1004", "978-0-596-52068-7");

        // Assert
        verify(mockView).showErrorDialog("The book 978-0-596-52068-7 is not available");
        verify(mockDataAccess, never()).checkoutBook(anyString(), anyString());
    }

    @Test
    public void testCheckoutBook() {
        // Act
        checkoutPresenter.checkoutBook("1004", "978-0-596-52068-7");

        // Assert
        verify(mockDataAccess).checkoutBook("1004", "978-0-596-52068-7");
    }
}