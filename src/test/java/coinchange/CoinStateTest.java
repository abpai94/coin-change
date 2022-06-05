package coinchange;

import coinchange.com.Coin;
import coinchange.com.CoinDeposit;
import coinchange.com.CoinState;
import coinchange.com.shared.exception.InvalidCoinException;
import coinchange.com.shared.interfaces.ICoin;
import coinchange.com.shared.interfaces.ICoinDeposit;
import coinchange.com.shared.interfaces.ICoinState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Test class to for all CoinState Methods.
 */
public class CoinStateTest {

    ICoin twopound;
    ICoin onepound;
    ICoin fiftypence;
    ICoin twentypence;
    ICoin tenpence;
    ICoin fivepence;
    ICoin twopence;
    ICoin onepence;

    ICoinDeposit initialDeposit = new CoinDeposit();

    ICoinState coinState;

    /**
     * Set-up class before test.
     *
     * @throws InvalidCoinException {@link InvalidCoinException}
     */
    @Before
    public void setUp() throws InvalidCoinException {
        twopound = new Coin(200);
        onepound = new Coin(100);
        fiftypence = new Coin(50);
        twentypence = new Coin(20);
        tenpence = new Coin(10);
        fivepence = new Coin(5);
        twopence = new Coin(2);
        onepence = new Coin(1);

        for (int i = 0; i < 10; i++) {
            initialDeposit.insertCoin(twopound);
            initialDeposit.insertCoin(onepound);
            initialDeposit.insertCoin(fiftypence);
            initialDeposit.insertCoin(twentypence);
            initialDeposit.insertCoin(tenpence);
            initialDeposit.insertCoin(fivepence);
            initialDeposit.insertCoin(twopence);
            initialDeposit.insertCoin(onepence);
        }

        coinState = new CoinState(initialDeposit);
    }

    /**
     * Test an invalid coin exception is thrown.
     */
    @Test
    public void testCoinInvalid() {
        boolean thrown = false;

        ICoin aCoin = new Coin(25);
        try {
            coinState.isValidCoin(aCoin);
        } catch (InvalidCoinException e) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    /**
     * Test a valid coin is tested.
     */
    @Test
    public void testCoinValid() {
        boolean valid = false;

        try {
            valid = coinState.isValidCoin(twopound);
        } catch (InvalidCoinException e) {
            e.printStackTrace();
        }

        assertTrue(valid);
    }

}
