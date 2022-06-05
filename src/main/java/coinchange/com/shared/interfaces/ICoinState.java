package coinchange.com.shared.interfaces;

import coinchange.com.shared.exception.InvalidCoinException;
import coinchange.com.shared.exception.DepletedCoinException;
import coinchange.com.shared.exception.InsufficientDepositException;

import java.util.Set;

/**
 * This class is used to manipulate the underlying coin changer.
 */
public interface ICoinState {

    /**
     * Insert coin to user coin deposit.
     *
     * @param aCoin a valid coin
     * @throws InvalidCoinException {@link InvalidCoinException}
     */
    void insertCoin(ICoin aCoin) throws InvalidCoinException, InvalidCoinException;

    /**
     * Retrieve Change output.
     *
     * @param aCoin the largest coin value
     * @return the coins as a coin deposit
     * @throws DepletedCoinException {@link DepletedCoinException}
     */
    ICoinDeposit getChange(ICoin aCoin) throws DepletedCoinException, DepletedCoinException;

    /**
     * Determine if inserted coin is valid.
     *
     * @param aCoin the coin that is being checked
     * @return true if valid coin
     * @throws InvalidCoinException {@link InvalidCoinException}
     */
    boolean isValidCoin(ICoin aCoin) throws InvalidCoinException;

    /**
     * Calculates the user deposit that remains for the user to spend.
     *
     * @param aCost the cost of the item
     * @return remaining user deposit
     * @throws InsufficientDepositException {@link InsufficientDepositException}
     */
    Integer calculate(Integer aCost) throws InsufficientDepositException, InsufficientDepositException;

    /**
     * The remaining user deposit after expenditures excluded.
     *
     * @return the remaining user deposit.
     */
    Integer getRemainingUserDeposit();

    /**
     * The valid list of coins that can be inserted into the machine.
     *
     * @return the list of valid coins
     */
    Set<ICoin> getValidCoins();
}
