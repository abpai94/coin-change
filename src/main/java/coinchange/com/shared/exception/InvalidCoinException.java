package coinchange.com.shared.exception;

import coinchange.com.shared.interfaces.ICoin;

/**
 * Exception thrown when the {@link ICoin} is invalid.
 */
public class InvalidCoinException extends Exception {

    /**
     * Constructor to return an error message.
     *
     * @param aErrorMessage the message
     */
    public InvalidCoinException(String aErrorMessage) {
        super(aErrorMessage);
    }
}
