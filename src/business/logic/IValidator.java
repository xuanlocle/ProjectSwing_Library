package business.logic;

import business.exception.ValidatorException;

public interface IValidator {
    public void validateFirstName(String firstName) throws ValidatorException;
    public void validateLastName(String lastName) throws ValidatorException;
    public void validatePhoneNumber(String phoneNumber) throws ValidatorException;
    public void validateStreet(String street) throws ValidatorException;
    public void validateCity(String city) throws ValidatorException;
    public void validateState(String state) throws ValidatorException;
    public void validateZipCode(String zipCode) throws ValidatorException;
}
