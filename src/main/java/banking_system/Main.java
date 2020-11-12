package banking_system;

public class Main {

    public static void main(String[] args) {
        String fileName = "jdbc:sqlite:bank.s3db";
        SQL_Manager sqlManager = new SQL_Manager(fileName);
        BankManager bankManager = new BankManager(sqlManager);
        Menu menu = new Menu();
        menu.showMenu(bankManager);
    }
}