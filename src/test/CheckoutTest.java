package test;

import business.ControllerInterface;

import business.SystemController;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

class CheckoutTest {

    static DataAccess dataAccess;
    static ControllerInterface controller;

    @BeforeAll
    static void setup() {
        dataAccess = new DataAccessFacade(true);
        controller = new SystemController(dataAccess);
    }

    @AfterEach
    void clean() {
        dataAccess.cleanUpTestStorage();
    }

//    @Test
//    void testGetAllCheckoutEntries(){
//
//
//    }
//
//    @Test
//    void testCheckout() {
//        controller.checkoutBook("", "");
//
//    }
//
//    @Test
//    void getCheckoutEntriesByMemberId() {
//    }
//
//    @Test
//    void isCheckoutAvailable() {
//    }
//
//    @Test
//    void checkoutBook() {
//    }
}