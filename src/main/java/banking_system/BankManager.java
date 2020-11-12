package banking_system;

import java.util.Scanner;
import java.util.stream.Stream;

public class BankManager {

    SQL_Manager sqlManager;

    public BankManager(SQL_Manager sqlManager) {
        this.sqlManager = sqlManager;
    }

    public void createAccount() {
        Account acc = new Account();
        System.out.println("Your card has been created\n" +
                "Your card number:\n" +
                acc.getCardNumber() + "\n" +
                "Your card PIN:\n" +
                acc.getPIN());
        int id = Integer.parseInt(acc.getAccountIdentifier());
        String number = String.valueOf(acc.getCardNumber());
        String pin = acc.getPIN();
        this.sqlManager.insert(id, number, pin);
    }

    public void logging(Scanner scanner) {
        System.out.println("Enter your card number:");
        String userCardNumber = scanner.nextLine();
        System.out.println("Enter your PIN:");
        String userPIN = scanner.nextLine();
        String search = this.sqlManager.select(userCardNumber, userPIN);
        if (search != null) {
            String[] resultOfSearchClient = search.split("\\s+");
            System.out.println("You have successfully logged in!");
            Menu.showMenuLogging(this, resultOfSearchClient);
        } else {
            System.out.println("Wrong card number or PIN!");
        }
    }

    public void showBalance(String[] resultOfSearchClient) {
        String search = sqlManager.select(resultOfSearchClient[1], resultOfSearchClient[2]);
        String[] results = search.split("\\s+");
        System.out.println("Balance " + results[3]);
    }

    public void addIncome(String[] resultOfSearchClient, Scanner scanner) {
        System.out.println("Enter income:");
        int income = Integer.parseInt(scanner.nextLine());
        String number = resultOfSearchClient[1];
        int resultOfQuery = sqlManager.updateBalance(number, income, true);
        System.out.println(resultOfQuery == 1 ? "Income was added!" : "Something wrong");
    }

    public void doTransfer(String[] resultOfSearchClient, Scanner scanner) {
        String search = sqlManager.select(resultOfSearchClient[1], resultOfSearchClient[2]);
        String[] results = search.split("\\s+");
        System.out.println("Transfer\n" +
                "Enter card number:");
        String recipientsCard = scanner.nextLine();
        //Поиск карты реципиента в базе
        String recipientSearch = sqlManager.selectRecipient(recipientsCard);
        //Выделяем две составляющие карты реципиента для проверка алгоритмом Луна
        String IINOfRecipientsCard = recipientsCard.substring(0, 7);
        String accountIdentifierOfRecipientsCard = recipientsCard.substring(7, recipientsCard.length() - 1);

        if (!recipientsCard.equals(String.valueOf(Account.LuhnsChecking(IINOfRecipientsCard, accountIdentifierOfRecipientsCard)))) {
            System.out.println("Probably you made mistake in the card number. Please try again!");
        } else if (results[1].equals(recipientsCard)) {
            System.out.println("You can't transfer money to the same account!");
        } else if (recipientSearch == null) {
            System.out.println("Such a card does not exist.");
        } else {
            System.out.println("Enter how much money you want to transfer:");
            int transferMoney = Integer.parseInt(scanner.nextLine());
            if (transferMoney > Integer.parseInt(results[3])) {
                System.out.println("Not enough money!");
            } else {
                int resultOfQuery = sqlManager.updateBalance(recipientsCard, transferMoney, true);
                if (resultOfQuery == 1) {
                    System.out.println("Success!");
                    sqlManager.updateBalance(results[1], transferMoney, false);
                } else {
                    System.out.println("Something wrong");
                }
            }
        }
    }

    public void closeAccount(String[] resultOfSearchClient) {
        int resultOfQuery = sqlManager.delete(resultOfSearchClient[1]);
        System.out.println(resultOfQuery == 1 ? "The account has been closed!" : "Something wrong");
    }

    public static Stream<Integer> generateStreamWithPowersOfTwo(int n) {
        return Stream.iterate(0, x -> x <= n, x -> {
            x = x + 1;
            return (int) Math.pow(2, x);
        });
    }
}




