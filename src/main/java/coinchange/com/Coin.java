package coinchange.com;

import coinchange.com.shared.interfaces.ICoin;

/**
 * Implementation of the class {@link ICoin}.
 */
public class Coin implements ICoin {

    private Integer value = 0;

    /**
     * Constructor to initialise the object.
     *
     * @param aValue the coin's value
     */
    public Coin(Integer aValue) {
        this.setValue(aValue);
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    /**
     * Sets the value of the coin.
     *
     * @param aValue the value of the coin
     */
    private void setValue(Integer aValue) {
        value = aValue;
    }

    @Override
    public int compareTo(Coin coin) {
        return -(this.getValue().compareTo(coin.getValue()));
    }

    @Override
    public String toString() {
        return "Coin Value : " + getValue();
    }
}
