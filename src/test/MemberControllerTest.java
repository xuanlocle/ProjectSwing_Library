package test;

import business.logic.IUserMgmt;
import business.exception.ValidatorException;
import business.logic.impl.UserMgmtService;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MemberControllerTest {
    DataAccess dataAccess;
    IUserMgmt controller;

    @BeforeEach
    void setup() {
        dataAccess = new DataAccessFacade(true);
        controller = new UserMgmtService(dataAccess);
    }

    @AfterEach
    void clean() {
        dataAccess.cleanUpTestStorage();
    }

    @Test
    void testAddMember() {
        String firstName = "Ngoc Cuong";
        String lastName = "Nguyen";
        String phone = "641-233-2222";
        String street = "1000 N 4th St";
        String city = "Fairfield";
        String state = "Iowa";
        String zip = "52557";

        assertDoesNotThrow(() -> {
            controller.addLibraryMember(firstName, lastName, phone, street, city, state, zip);
        });
    }

    @Test
    void testAddMemberInvalidFirstName() {
        String firstName = "Ngoc Cuong 2";
        String lastName = "Nguyen";
        String phone = "641-233-2222";
        String street = "1000 N 4th St";
        String city = "Fairfield";
        String state = "Iowa";
        String zip = "52557";

        assertThrows(ValidatorException.class, () -> {
            controller.addLibraryMember(firstName, lastName, phone, street, city, state, zip);
        });
    }

    @Test
    void testAddMemberInvalidLastName() {
        String firstName = "Ngoc Cuong";
        String lastName = "";
        String phone = "641-233-2222";
        String street = "1000 N 4th St";
        String city = "Fairfield";
        String state = "Iowa";
        String zip = "52557";

        assertThrows(ValidatorException.class, () -> {
            controller.addLibraryMember(firstName, lastName, phone, street, city, state, zip);
        });
    }

    @Test
    void testAddMemberInvalidPhone() {
        String firstName = "Ngoc Cuong";
        String lastName = "Nguyen";
        String phone = "6412332222";
        String street = "1000 N 4th St";
        String city = "Fairfield";
        String state = "Iowa";
        String zip = "52557";

        assertThrows(ValidatorException.class, () -> {
            controller.addLibraryMember(firstName, lastName, phone, street, city, state, zip);
        });
    }

    @Test
    void testAddMemberInvalidStreet() {
        String firstName = "Ngoc Cuong";
        String lastName = "Nguyen";
        String phone = "641-233-2222";
        String street = null;
        String city = "Fairfield";
        String state = "Iowa";
        String zip = "52557";

        assertThrows(ValidatorException.class, () -> {
            controller.addLibraryMember(firstName, lastName, phone, street, city, state, zip);
        });
    }


}
