package business.exception;

import java.io.Serializable;

public class ValidatorException extends RuntimeException implements Serializable {
    public ValidatorException(String message) {
        super(message);
    }
}
