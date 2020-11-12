package banking_system;

import java.util.Scanner;

public class Menu {
    private static boolean exitFlag;
    private static final Scanner scanner = new Scanner(System.in);
    private static String clientChoice;

    public void showMenu(BankManager bankManager) {
        exitFlag = true;
        while (exitFlag) {
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");
            clientChoice = scanner.nextLine();
            switch (clientChoice) {
                case "1" -> bankManager.createAccount();
                case "2" -> bankManager.logging(scanner);
                case "0" -> {
                    System.out.println("Bye!");
                    exitFlag = false;
                }
            }
        }
    }

    public static void showMenuLogging(BankManager bankManager, String[] resultOfSearchClient) {
        boolean exitFlag_2 = true;
        while (exitFlag_2) {
            System.out.println("1. Balance\n"
                    + "2. Add income\n"
                    + "3. Do transfer\n"
                    + "4. Close account\n"
                    + "5. Log out\n"
                    + "0. Exit");
            clientChoice = scanner.nextLine();
            switch (clientChoice) {
                case "1" -> bankManager.showBalance(resultOfSearchClient);
                case "2" -> bankManager.addIncome(resultOfSearchClient, scanner);
                case "3" -> bankManager.doTransfer(resultOfSearchClient, scanner);
                case "4" -> {
                    bankManager.closeAccount(resultOfSearchClient);
                    exitFlag_2 = false;
                }
                case "5" -> {
                    System.out.println("You have successfully logged out!");
                    exitFlag_2 = false;
                }
                case "0" -> {
                    System.out.println("Bye!");
                    exitFlag_2 = false;
                    exitFlag = false;
                }
            }
        }
    }
}