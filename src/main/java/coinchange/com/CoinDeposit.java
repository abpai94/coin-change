package coinchange.com;

import coinchange.com.shared.exception.DepletedCoinException;
import coinchange.com.shared.exception.InsufficientDepositException;
import coinchange.com.shared.exception.InvalidCoinException;
import coinchange.com.shared.interfaces.ICoin;
import coinchange.com.shared.interfaces.ICoinDeposit;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implementation of {@link ICoinDeposit}.
 */
public class CoinDeposit implements ICoinDeposit {

    private Map<ICoin, Integer> deposit = new TreeMap<>();

    private Integer total = 0;

    @Override
    public void insertCoin(ICoin aCoin) throws InvalidCoinException {
        if (this.getDeposit().containsKey(aCoin)) {
            this.getDeposit().put(aCoin, this.getDeposit().get(aCoin) + 1);
        } else {
            this.getDeposit().put(aCoin, 1);
        }
    }

    @Override
    public ICoin removeCoin(ICoin aCoin) throws DepletedCoinException {
        if (this.getDeposit().get(aCoin) > 0) {
            this.getDeposit().put(aCoin, this.getDeposit().get(aCoin) - 1);
            return aCoin;
        } else {
            throw new DepletedCoinException("Coin : " + aCoin.getValue() + " does not exist.");
        }
    }

    @Override
    public Map<ICoin, Integer> getDeposit() {
        return this.deposit;
    }

    @Override
    public Integer getTotal() {
        Integer totalValue = 0;
        for (Map.Entry<ICoin, Integer> entry : this.getDeposit().entrySet()) {
            totalValue += entry.getValue() * entry.getKey().getValue();
        }
        total = totalValue;
        return totalValue;
    }

    @Override
    public int compareTo(ICoinDeposit coinDeposit) {
        return this.getTotal().compareTo(coinDeposit.getTotal());
    }

    @Override
    public boolean isSufficientFunds(Integer aValue) throws InsufficientDepositException {
        Integer value = this.getTotal() - aValue;
        if (value >= 0) {
            return true;
        } else {
            throw new InsufficientDepositException("User deposit : " + this.getTotal()
                    + " is not sufficient. Insert : " + value);
        }
    }

    @Override
    public ICoinDeposit getChange(ICoinDeposit aTotalDeposit, ICoinDeposit aChange,
                                  Integer remainingUserDeposit, Integer initialUserDeposit,
                                  ICoin aCurrentCoin) {
        if (remainingUserDeposit == 0) {
            aTotalDeposit.getDeposit().remove(aCurrentCoin);
            return aChange;
        }

        if (remainingUserDeposit < 0) {
            if (aChange.getDeposit().size() > 0) {
                try {
                    remainingUserDeposit += aChange.removeCoin(aCurrentCoin).getValue();
                    aTotalDeposit.getDeposit().remove(aCurrentCoin);
                } catch (DepletedCoinException e) {
                    e.printStackTrace();
                }
                return getChange(aTotalDeposit, aChange, remainingUserDeposit, initialUserDeposit, aCurrentCoin);
            }
        }

        Iterator<ICoin> aCoins = aTotalDeposit.getDeposit().keySet().iterator();

        while (aCoins.hasNext()) {
            if (aTotalDeposit.getDeposit().isEmpty() || initialUserDeposit.equals(aChange.getTotal())) {
                return aChange;
            } else {
                ICoin aCoin = aCoins.next();
                if (aTotalDeposit.getDeposit().get(aCoin) > 0) {
                    remainingUserDeposit -= aCoin.getValue();
                    try {
                        aChange.insertCoin(aCoin);
                    } catch (InvalidCoinException e) {
                        e.printStackTrace();
                    }
                    try {
                        aTotalDeposit.removeCoin(aCoin);
                    } catch (DepletedCoinException e) {
                        aTotalDeposit.getDeposit().remove(aCoin);
                    }
                    return getChange(aTotalDeposit, aChange, remainingUserDeposit, initialUserDeposit, aCoin);
                }
            }
        }

        return aChange;
    }

    @Override
    public String toString() {
        String output = "";
        for (Map.Entry<ICoin, Integer> entry : this.getDeposit().entrySet()) {
            output += entry.getKey().toString() + " Units : " + entry.getValue() + "\n";
        }
        output += "Total Deposit : " + getTotal();
        return output;
    }
}
