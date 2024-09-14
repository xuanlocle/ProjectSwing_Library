package business.logic.impl;

import business.exception.ValidatorException;
import business.logic.IValidator;

import java.util.regex.Pattern;

public class Validator implements IValidator {
    @Override
    public void validateFirstName(String firstName) throws ValidatorException {
        if (firstName == null || firstName.isEmpty()) {
            throw new ValidatorException("First name cannot be empty");
        }
        Pattern notAllowedCharacterPattern = Pattern.compile("[^a-z ]", Pattern.CASE_INSENSITIVE);
        if (notAllowedCharacterPattern.matcher(firstName).find()) {
            throw new ValidatorException("First name should contains characters and space only");
        };
    }

    @Override
    public void validateLastName(String lastName) throws ValidatorException {
        if (lastName == null || lastName.isEmpty()) {
            throw new ValidatorException("Last name cannot be empty");
        }
        Pattern notAllowedCharacterPattern = Pattern.compile("[^a-z ]", Pattern.CASE_INSENSITIVE);
        if (notAllowedCharacterPattern.matcher(lastName).find()) {
            throw new ValidatorException("Last name should contains characters and space only");
        };
    }

    @Override
    public void validatePhoneNumber(String phoneNumber) throws ValidatorException {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new ValidatorException("Phone number cannot be empty");
        }
        Pattern notAllowedCharacterPattern = Pattern.compile("[^-0-9]", Pattern.CASE_INSENSITIVE);
        if (notAllowedCharacterPattern.matcher(phoneNumber).find()) {
            throw new ValidatorException("Phone number contains invalid characters");
        };
        Pattern numberPattern = Pattern.compile("^[0-9]{3}-[0-9]{3}-[0-9]{4}$", Pattern.CASE_INSENSITIVE);
        if (!numberPattern.matcher(phoneNumber).find()) {
            throw new ValidatorException("Phone number format should be XXX-XXX-XXXX");
        }
    }

    @Override
    public void validateStreet(String street) throws ValidatorException {
        if (street == null || street.isEmpty()) {
            throw new ValidatorException("Street cannot be empty");
        }
    }

    @Override
    public void validateCity(String city) throws ValidatorException {
        if (city == null || city.isEmpty()) {
            throw new ValidatorException("City cannot be empty");
        }
    }

    @Override
    public void validateState(String state) throws ValidatorException {
        if (state == null || state.isEmpty()) {
            throw new ValidatorException("State cannot be empty");
        }
    }

    @Override
    public void validateZipCode(String zipCode) throws ValidatorException {
        if (zipCode == null || zipCode.isEmpty()) {
            throw new ValidatorException("Zip code cannot be empty");
        }
        Pattern notAllowedCharacterPattern = Pattern.compile("[^0-9]", Pattern.CASE_INSENSITIVE);
        if (notAllowedCharacterPattern.matcher(zipCode).find()) {
            throw new ValidatorException("Zipcode contains invalid characters");
        };
    }
}
