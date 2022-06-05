package coinchange.com;

import coinchange.com.shared.exception.DepletedCoinException;
import coinchange.com.shared.exception.InvalidCoinException;
import coinchange.com.shared.interfaces.ICoin;
import coinchange.com.shared.interfaces.ICoinDeposit;
import coinchange.com.shared.interfaces.ICoinState;
import coinchange.com.shared.exception.InsufficientDepositException;

import java.util.*;

/**
 * Implementation of {@link ICoinState}.
 */
public class CoinState implements ICoinState {

    private ICoinDeposit coinDeposit = new CoinDeposit();

    private ICoinDeposit userDeposit = new CoinDeposit();

    private HashSet<ICoin> validCoins = new HashSet<ICoin>();

    private Integer remainingUserDeposit = 0;

    private List<Integer> totalUserExpenditure = new ArrayList<>();

    /**
     * Constructor to initialise the CoinState.
     *
     * @param aDepositCoin the initial coin deposit
     */
    public CoinState(ICoinDeposit aDepositCoin) {
        coinDeposit = aDepositCoin;
        validCoins.addAll(coinDeposit.getDeposit().keySet());
    }

    @Override
    public void insertCoin(ICoin aCoin) throws InvalidCoinException {
        try {
            if (isValidCoin(aCoin)) {
                userDeposit.insertCoin(aCoin);
                remainingUserDeposit = userDeposit.getTotal();
            }
        } catch (InvalidCoinException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ICoinDeposit getChange(ICoin aCoin) throws DepletedCoinException {
        ICoinDeposit totalDepositedCoins = combineCoinDeposit(coinDeposit, userDeposit);
        ICoinDeposit change = userDeposit.getChange(totalDepositedCoins, new CoinDeposit(),
                remainingUserDeposit, remainingUserDeposit, aCoin);

        try {
            if (change.getTotal().intValue() == getRemainingUserDeposit().intValue()) {
                coinDeposit = combineCoinDeposit(coinDeposit, userDeposit);
                for (Map.Entry<ICoin, Integer> entry : change.getDeposit().entrySet()) {
                    if (entry.getValue() > 0) {
                        for (int i = 0; i < entry.getValue(); i++) {
                            coinDeposit.removeCoin(entry.getKey());
                        }
                    }
                }
                resetUserDeposit();
            }
        } catch (DepletedCoinException e) {
            throw new DepletedCoinException("Coin Deposit does not contain coins to provide change.");
        }
        return change;
    }

    @Override
    public boolean isValidCoin(ICoin aCoin) throws InvalidCoinException {
        boolean output = validCoins.contains(aCoin);
        if (output) {
            return true;
        } else {
            throw new InvalidCoinException("Coin Value : " + aCoin.getValue() + "does not exist");
        }
    }

    @Override
    public Set<ICoin> getValidCoins() {
        return (Set<ICoin>) validCoins.clone();
    }

    @Override
    public Integer calculate(Integer aCost) throws InsufficientDepositException {
        if (userDeposit.isSufficientFunds(aCost)) {
            totalUserExpenditure.add(aCost);
        } else {
            throw new InsufficientDepositException(aCost + " is too expensive.");
        }
        return getRemainingUserDeposit();
    }

    @Override
    public Integer getRemainingUserDeposit() {
        Integer totalCost = 0;
        for (Integer cost : totalUserExpenditure) {
            totalCost += cost;
        }
        remainingUserDeposit = userDeposit.getTotal() - totalCost;
        return remainingUserDeposit;
    }

    /**
     * Combines two {@link ICoinDeposit} into one.
     *
     * @param aDeposit       one deposit
     * @param anotherDeposit another deposit
     * @return combine deposits
     */
    private ICoinDeposit combineCoinDeposit(ICoinDeposit aDeposit, ICoinDeposit anotherDeposit) {
        ICoinDeposit combined = new CoinDeposit();

        for (Map.Entry<ICoin, Integer> entry : aDeposit.getDeposit().entrySet()) {
            if (entry.getValue() > 0) {
                for (int i = 0; i < entry.getValue(); i++) {
                    try {
                        combined.insertCoin(entry.getKey());
                    } catch (InvalidCoinException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        for (Map.Entry<ICoin, Integer> entry : anotherDeposit.getDeposit().entrySet()) {
            if (entry.getValue() > 0) {
                for (int i = 0; i < entry.getValue(); i++) {
                    try {
                        combined.insertCoin(entry.getKey());
                    } catch (InvalidCoinException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return combined;
    }

    /**
     * Resets the user deposit once the change has been expelled.
     */
    private void resetUserDeposit() {
        remainingUserDeposit = 0;
        totalUserExpenditure = new ArrayList<>();
        userDeposit = new CoinDeposit();
    }
}
