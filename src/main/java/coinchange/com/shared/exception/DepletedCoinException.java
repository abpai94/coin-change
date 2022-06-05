package coinchange.com.shared.exception;

import coinchange.com.shared.interfaces.ICoin;

/**
 * Exception thrown when the {@link ICoin} of certain value is empty.
 */
public class DepletedCoinException extends Exception {

    /**
     * Constructor to return an error message.
     *
     * @param aErrorMessage the message
     */
    public DepletedCoinException(String aErrorMessage) {
        super(aErrorMessage);
    }
}
