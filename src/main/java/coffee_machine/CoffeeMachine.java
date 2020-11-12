package coffee_machine;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CoffeeMachine {

    public static void coffeeMETOD (Map<String, Integer> coffeeMachine, Scanner sc) {
        String str1;
        boolean flag = true;
        while (flag) {
            System.out.println("Write action (buy, fill, take, remaining, exit)");
            str1 = sc.nextLine();
            switch (str1) {
                case "buy":
                    CoffeeMachine.buy(coffeeMachine, sc);
                    break;
                case "fill":
                    CoffeeMachine.fill(sc, coffeeMachine);
                    break;
                case "take":
                    CoffeeMachine.take(coffeeMachine);
                    break;
                case "remaining":
                    CoffeeMachine.printState(coffeeMachine);
                    break;
                case "exit":
                    flag = false;
                    break;
                default:
            }
        }
    }

    public static void buy(Map<String, Integer> coffeeMachine, Scanner sc) {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
        switch (sc.nextLine()) {
            case "0":
                break;
            case "1":
                if (coffeeMachine.get("waterSupply")>=250&coffeeMachine.get("coffeeSupply")>=16) {
                    System.out.println("I have enough resources, making you a coffee!");
                    coffeeMachine.put("waterSupply", coffeeMachine.get("waterSupply") - 250);
                    coffeeMachine.put("coffeeSupply", coffeeMachine.get("coffeeSupply") - 16);
                    coffeeMachine.put("money", coffeeMachine.get("money") + 4);
                    coffeeMachine.put("disCupsSupply", coffeeMachine.get("disCupsSupply") - 1);
                } else if (coffeeMachine.get("waterSupply")<250){
                    System.out.println("Sorry, not enough water!");
                } else if (coffeeMachine.get("coffeeSupply")<16) {
                    System.out.println("Sorry, not enough coffee beans!");
                }
                break;
            case "2":
                if (coffeeMachine.get("waterSupply")>=350&
                        coffeeMachine.get("milkSupply")>=75&
                        coffeeMachine.get("coffeeSupply")>=20) {
                    System.out.println("I have enough resources, making you a coffee!");
                    coffeeMachine.put("waterSupply", coffeeMachine.get("waterSupply") - 350);
                    coffeeMachine.put("milkSupply", coffeeMachine.get("milkSupply") - 75);
                    coffeeMachine.put("coffeeSupply", coffeeMachine.get("coffeeSupply") - 20);
                    coffeeMachine.put("money", coffeeMachine.get("money") + 7);
                    coffeeMachine.put("disCupsSupply", coffeeMachine.get("disCupsSupply") - 1);
                } else if (coffeeMachine.get("waterSupply")<350){
                    System.out.println("Sorry, not enough water!");
                } else if (coffeeMachine.get("milkSupply")<75){
                    System.out.println("Sorry, not enough milk!");
                } else if (coffeeMachine.get("coffeeSupply")<20) {
                    System.out.println("Sorry, not enough coffee beans!");
                }
                break;
            case "3":
                if (coffeeMachine.get("waterSupply")>=200&
                        coffeeMachine.get("milkSupply")>=100&
                        coffeeMachine.get("coffeeSupply")>=12) {
                    System.out.println("I have enough resources, making you a coffee!");
                    coffeeMachine.put("waterSupply", coffeeMachine.get("waterSupply") - 200);
                    coffeeMachine.put("milkSupply", coffeeMachine.get("milkSupply") - 100);
                    coffeeMachine.put("coffeeSupply", coffeeMachine.get("coffeeSupply") - 12);
                    coffeeMachine.put("money", coffeeMachine.get("money") + 6);
                    coffeeMachine.put("disCupsSupply", coffeeMachine.get("disCupsSupply") - 1);
                } else if (coffeeMachine.get("waterSupply")<200){
                    System.out.println("Sorry, not enough water!");
                } else if (coffeeMachine.get("milkSupply")<100){
                    System.out.println("Sorry, not enough milk!");
                } else if (coffeeMachine.get("coffeeSupply")<12) {
                    System.out.println("Sorry, not enough coffee beans!");
                }
                break;
        }
    }

    public static void fill(Scanner sc, Map<String, Integer> coffeeMachine) {
        System.out.println("Write how many ml of water do you want to add:");
        coffeeMachine.put("waterSupply", coffeeMachine.get("waterSupply") + Integer.parseInt(sc.nextLine()));
        System.out.println("Write how many ml of milk do you want to add:");
        coffeeMachine.put("milkSupply", coffeeMachine.get("milkSupply") + Integer.parseInt(sc.nextLine()));
        System.out.println("Write how many grams of coffee beans do you want to add:");
        coffeeMachine.put("coffeeSupply", coffeeMachine.get("coffeeSupply") + Integer.parseInt(sc.nextLine()));
        System.out.println("Write how many disposable cups of coffee do you want to add:");
        coffeeMachine.put("disCupsSupply", coffeeMachine.get("disCupsSupply") + Integer.parseInt(sc.nextLine()));
    }

    public static void take(Map<String, Integer> coffeeMachine) {
        System.out.printf("I gave you $%d\n", coffeeMachine.get("money"));
        coffeeMachine.put("money", 0);
    }

    public static void printState(Map<String, Integer> coffeeMachine) {
        System.out.printf("The coffee machine has:\n" +
                        "%d of water\n" +
                        "%d of milk\n" +
                        "%d of coffee beans\n" +
                        "%d of disposable cups\n" +
                        "%d of money\n",
                coffeeMachine.get("waterSupply"),
                coffeeMachine.get("milkSupply"),
                coffeeMachine.get("coffeeSupply"),
                coffeeMachine.get("disCupsSupply"),
                coffeeMachine.get("money"));
    }

    public static void main(String[] args) {
        Map<String, Integer> coffeeMachine = new HashMap<>(5);
        coffeeMachine.put("waterSupply", 400);
        coffeeMachine.put("milkSupply", 540);
        coffeeMachine.put("coffeeSupply", 120);
        coffeeMachine.put("disCupsSupply", 9);
        coffeeMachine.put("money", 550);
        Scanner sc = new Scanner(System.in);
        CoffeeMachine.coffeeMETOD(coffeeMachine, sc);
    }
}