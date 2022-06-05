package coinchange.com.shared.interfaces;

import coinchange.com.Coin;

/**
 * This class represents a coin token.
 */
public interface ICoin extends Comparable<Coin> {

    /**
     * The token value.
     *
     * @return integer value of token
     */
    Integer getValue();

    int compareTo(Coin coin);
}
