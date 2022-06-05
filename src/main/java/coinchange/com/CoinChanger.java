package coinchange.com;

import coinchange.com.shared.exception.DepletedCoinException;
import coinchange.com.shared.exception.InsufficientDepositException;
import coinchange.com.shared.exception.InvalidCoinException;
import coinchange.com.shared.interfaces.ICoin;
import coinchange.com.shared.interfaces.ICoinDeposit;
import coinchange.com.shared.interfaces.ICoinState;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Class to allow the user to interact with the coin change backend.
 */
@SpringBootApplication
public class CoinChanger {

    public static String WELCOME = "Welcome to the Vending Machine";

    public static String INSTRUCTION_ENTER_COIN =
            "The commands to operate this vending machine are as follows : " +
                    "\"insert:<integer>\" \"spend:<integer>\" \"change\" \"deposit\" \"quit\" \n" +
                    "Use the following valid penny inputs 200, 100, 50, 20, 10, 5, 2, 1.";

    public static String USER_DEPOSIT = "User Deposit : ";

    public static String CHANGE_RETURNED = "Change Returned";

    public static String HELP = "help";

    public static String SPEND = "spend:";

    public static String INSERT = "insert:";

    public static String CHANGE = "change";

    public static String DEPOSIT = "deposit";

    public static String QUIT = "quit";

    public static String INVALID_INPUT = " is not a valid input.";

    public static String INVALID_COIN_INPUT = " is not a valid coin input.";

    public static String INVALID_CHANGE = "Sorry change not available, Spend some more!";

    public static String HIGH_EXPENDITURE = "Sorry! You don't have enough money. " +
            "Buy something cheaper or Insert more money";

    public ICoin twopound = new Coin(200);
    public ICoin onepound = new Coin(100);
    public ICoin fiftypence = new Coin(50);
    public ICoin twentypence = new Coin(20);
    public ICoin tenpence = new Coin(10);
    public ICoin fivepence = new Coin(5);
    public ICoin twopence = new Coin(2);
    public ICoin onepence = new Coin(1);

    public static ICoinState coinState;

    /**
     * Initialises the Coin State.
     *
     * @throws InvalidCoinException {@link InvalidCoinException}
     */
    public void initialise() throws InvalidCoinException {
        ICoinDeposit initialDeposit = new CoinDeposit();

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
     * Inserts the user's coin into the machine.
     *
     * @param aCoin the coin
     * @throws InvalidCoinException {@link InvalidCoinException}
     */
    public void insertCoin(Integer aCoin) throws InvalidCoinException {
        boolean valid = false;
        for (ICoin coin : coinState.getValidCoins()) {
            if (coin.getValue().equals(aCoin)) {
                try {
                    coinState.insertCoin(coin);
                    valid = true;
                } catch (InvalidCoinException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!valid) {
            throw new InvalidCoinException("Coin of value : " + aCoin + " doesn't exist");
        }
    }

    /**
     * Calculates the remaining user deposit.
     *
     * @param aCost the cost of the object
     * @return the remaining user deposit
     * @throws InsufficientDepositException {@link InsufficientDepositException}
     */
    public Integer calculate(Integer aCost) throws InsufficientDepositException {
        Integer remainingUserDeposit = coinState.calculate(aCost);
        return remainingUserDeposit;
    }

    /**
     * Gets the remaining change left in the user's deposit.
     *
     * @return the remaining change
     * @throws DepletedCoinException {@link DepletedCoinException}
     */
    public ICoinDeposit getChange() throws DepletedCoinException {
        Integer aInitialState = coinState.getRemainingUserDeposit();
        ICoinDeposit aChange = coinState.getChange(twopound);
        if (aChange.getTotal().intValue() != aInitialState.intValue()) {
            throw new DepletedCoinException("Coin Deposit does not contain coins to provide change.");
        }
        return aChange;
    }

    public static void main(String[] args) {
        SpringApplication.run(CoinChanger.class, args);
        CoinChanger changer = new CoinChanger();
        try {
            changer.initialise();
        } catch (InvalidCoinException e) {
            e.printStackTrace();
        }
        Scanner scan = new Scanner(new InputStreamReader(System.in));
        System.out.println(WELCOME);
        System.out.println(INSTRUCTION_ENTER_COIN);
        while (scan.hasNext()) {
            String input = scan.next();
            if (input.contains(SPEND)) {
                try {
                    Integer value = Integer.parseInt(String.valueOf(
                            input.split(":")[1]));
                    changer.calculate(value);
                    System.out.println(USER_DEPOSIT + coinState.
                            getRemainingUserDeposit());
                } catch (NumberFormatException e) {
                    System.out.println("\"" + input.split(":")[1] + "\"" + INVALID_INPUT);
                } catch (InsufficientDepositException e) {
                    System.out.println(HIGH_EXPENDITURE);
                }
            } else if (input.equals(HELP)) {
                System.out.println(INSTRUCTION_ENTER_COIN);
            } else if (input.contains(INSERT)) {
                try {
                    Integer value = Integer.parseInt(String.valueOf(
                            input.split(":")[1]));
                    changer.insertCoin(value);
                    System.out.println(USER_DEPOSIT + coinState.
                            getRemainingUserDeposit());
                } catch (NumberFormatException e) {
                    System.out.println("\"" + input.split(":")[1] + "\"" + INVALID_INPUT);
                } catch (InvalidCoinException e) {
                    System.out.println("\"" + input.split(":")[1] + "\"" + INVALID_COIN_INPUT);
                }
            } else if (input.equals(CHANGE)) {
                System.out.println(CHANGE_RETURNED);
                try {
                    System.out.println(changer.getChange());
                } catch (DepletedCoinException e) {
                    System.out.println(INVALID_CHANGE);
                }
            } else if (input.equals(DEPOSIT)) {
                System.out.println(USER_DEPOSIT + coinState.getRemainingUserDeposit());
            } else if (input.equals(QUIT)) {
                System.exit(0);
            } else {
                System.out.println(INSTRUCTION_ENTER_COIN);
            }
        }
    }
}
