package coinchange.com.shared.interfaces;

import coinchange.com.shared.exception.InvalidCoinException;
import coinchange.com.shared.exception.DepletedCoinException;
import coinchange.com.shared.exception.InsufficientDepositException;

import java.util.Map;

/**
 * This class is the individual deposits utilised to store the {@link ICoin} instances.
 */
public interface ICoinDeposit extends Comparable<ICoinDeposit> {

    /**
     * Insert coin into deposit.
     *
     * @param aCoin the coin
     * @throws InvalidCoinException {@link InvalidCoinException}
     */
    void insertCoin(ICoin aCoin) throws InvalidCoinException, InvalidCoinException;

    /**
     * Remove coin from deposit.
     *
     * @param aCoin the coin
     * @return the coin being removed
     * @throws DepletedCoinException {@link DepletedCoinException}
     */
    ICoin removeCoin(ICoin aCoin) throws DepletedCoinException, DepletedCoinException;

    /**
     * Gets the {@link Map} that stores the coins.
     *
     * @return the deposit
     */
    Map<ICoin, Integer> getDeposit();

    /**
     * The total value of the deposit.
     *
     * @return the total
     */
    Integer getTotal();

    /**
     * Determines if the deposit contains sufficient funds to extract.
     *
     * @param aValue the value being deducted
     * @return true if sufficient
     * @throws InsufficientDepositException {@link InsufficientDepositException}
     */
    boolean isSufficientFunds(Integer aValue) throws InsufficientDepositException, InsufficientDepositException;

    /**
     * Calculates the change for the user.
     *
     * @param aTotalDeposit the total collection of coins
     * @param aChange the change being expelled
     * @param remainingUserDeposit the remaining value of user deposit
     * @param initialUserDeposit the initial value of user deposit
     * @param aCoin the largest coin
     * @return the change in the form of {@link ICoinDeposit}
     */
    ICoinDeposit getChange(ICoinDeposit aTotalDeposit, ICoinDeposit aChange, Integer remainingUserDeposit,
                           Integer initialUserDeposit, ICoin aCoin);
}
