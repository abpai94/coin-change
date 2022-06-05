package coinchange.com.shared.exception;

import coinchange.com.shared.interfaces.ICoin;
import coinchange.com.shared.interfaces.ICoinDeposit;

/**
 * Exception thrown when the {@link ICoinDeposit} does not have
 * sufficient deposit of a specific {@link ICoin}.
 */
public class InsufficientDepositException extends Exception {

    /**
     * Constructor to return an error message.
     *
     * @param aErrorMessage the message
     */
    public InsufficientDepositException(String aErrorMessage) {
        super(aErrorMessage);
    }
}
