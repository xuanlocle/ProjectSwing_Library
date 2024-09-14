package business.exception;

import java.io.Serializable;

public class CheckoutException extends Exception implements Serializable {

    private static final long serialVersionUID = 8683053943311690519L;

    public CheckoutException() {
        super();
    }

    public CheckoutException(String msg) {
        super(msg);
    }

    public CheckoutException(Throwable t) {
        super(t);
    }

}
