package coinchange.com.shared.exception;

import coinchange.com.shared.interfaces.ICoinDeposit;

/**
 * Exception thrown when the {@link ICoinDeposit} is full.
 */
public class FullDepositException extends Exception {

    /**
     * Constructor to return an error message.
     *
     * @param aErrorMessage the message
     */
    public FullDepositException(String aErrorMessage) {
        super(aErrorMessage);
    }
}
