package test;

import business.logic.IValidator;
import business.logic.impl.Validator;
import business.exception.ValidatorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidatorTest {
    static IValidator validator = new Validator();

    @Test
    void testFirstNameValidation() {
        assertDoesNotThrow(() -> validator.validateFirstName("Ngoc Cuong"));

        // Empty first name
        assertThrows(ValidatorException.class, () -> validator.validateFirstName(""));

        // null pointer first name
        assertThrows(ValidatorException.class, () -> validator.validateFirstName(null));

        // First name contain numbers
        assertThrows(ValidatorException.class, () -> validator.validateFirstName("Cuong 123"));

        // First name contain invalid characters
        assertThrows(ValidatorException.class, () -> validator.validateFirstName("Ngoc-Cuong"));
    }

    @Test
    void testLastNameValidation() {
        assertDoesNotThrow(() -> validator.validateLastName("Nguyen"));

        // Empty last name
        assertThrows(ValidatorException.class, () -> validator.validateLastName(""));

        // Null last name
        assertThrows(ValidatorException.class, () -> validator.validateLastName(null));

        // Last name contains numbers
        assertThrows(ValidatorException.class, () -> validator.validateLastName("Nguyen 123"));

        // Last name contains invalid characters
        assertThrows(ValidatorException.class, () -> validator.validateLastName("Nguyen-"));
    }

    @Test
    void testPhoneValidation() {
        assertDoesNotThrow(() -> validator.validatePhoneNumber("123-456-7890"));

        // Empty phone number
        assertThrows(ValidatorException.class, () -> validator.validatePhoneNumber(""));

        // Null phone number
        assertThrows(ValidatorException.class, () -> validator.validatePhoneNumber(null));

        // Invalid length phone number
        assertThrows(ValidatorException.class, () -> validator.validatePhoneNumber("123-456-78910"));

        // Incorrect dashing line
        assertThrows(ValidatorException.class, () -> validator.validatePhoneNumber("123-4567-890"));

        // Missing dashing line
        assertThrows(ValidatorException.class, () -> validator.validatePhoneNumber("1234567890"));
    }

    @Test
    void testStreetValidation() {
        assertDoesNotThrow(() -> validator.validateStreet("1000 N 4th St."));

        // Null street address
        assertThrows(ValidatorException.class, () -> validator.validateStreet(null));

        // Empty street address
        assertThrows(ValidatorException.class, () -> validator.validateStreet(""));
    }

    @Test
    void testCityValidation() {
        assertDoesNotThrow(() -> validator.validateCity("1000 N 4th St."));

        // Null street address
        assertThrows(ValidatorException.class, () -> validator.validateCity(null));

        // Empty street address
        assertThrows(ValidatorException.class, () -> validator.validateCity(""));
    }

    @Test
    void testStateValidation() {
        assertDoesNotThrow(() -> validator.validateState("Iowa"));

        // Null street address
        assertThrows(ValidatorException.class, () -> validator.validateState(null));

        // Empty street address
        assertThrows(ValidatorException.class, () -> validator.validateState(""));
    }
}
