package test;

import business.logic.ICheckout;
import business.logic.impl.CheckoutService;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

class CheckoutTest {

    static DataAccess dataAccess;
    static ICheckout controller;

    @BeforeAll
    static void setup() {
        dataAccess = new DataAccessFacade(true);
        controller = new CheckoutService(dataAccess);
    }

    @AfterEach
    void clean() {
        dataAccess.cleanUpTestStorage();
    }
}