package banking_system;

import java.util.Random;

public class Account {
    private static final Random random = new Random();
    private static final String IIN = "400000";

    private final String accountIdentifier;
    private final long cardNumber;
    private final int pin;
    private long balance;

    public Account() {
        accountIdentifier = this.generateAccountID();
        balance = 0;
        pin = generatePin();
        cardNumber = LuhnsChecking(IIN, accountIdentifier);
    }

    private String generateAccountID () {
        StringBuilder account = new StringBuilder(9);
        for (int i = 0; i < 9; i++) {
            account.append(random.nextInt(10));
        }
        return account.toString();
    }

    private static int generatePin() {
        return random.nextInt(10000);
    }

    public static long LuhnsChecking (String IIN, String accountIdentifier) {
        String stringOfCardNumber = IIN + accountIdentifier;
        int sum = 0;
        int count = 0;
        for (int i = 0; i < stringOfCardNumber.length(); i++) {
            int number = Integer.parseInt(stringOfCardNumber.substring(i, i+1));
            if (count % 2 == 0) {
                number*=2;
                if (number > 9) {
                    number -= 9;
                }
            }
            count++;
            sum += number;
        }
        int checkSum = (sum % 10 == 0 ? 0 : ((sum / 10) * 10 + 10 - sum));
        return Long.parseLong(stringOfCardNumber) * 10 + checkSum;
    }

    public String getPIN() {
        return String.format("%04d", pin);
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public long getBalance() {
        return balance;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    @Override
    public String toString() {
        return "Card Number = " + this.getCardNumber() + " PIN = " + this.getPIN() + " Balance = " + this.getBalance();
    }
}