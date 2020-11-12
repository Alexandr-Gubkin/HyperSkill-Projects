package flash_cards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class FlashCards {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        Map<String, String> cards = new LinkedHashMap<>();
        Map<String, Integer> mistakes = new LinkedHashMap<>();
        List<String> logFile = new ArrayList<>(); // создаем список логов

        if ((args.length==2)&&args[0].equalsIgnoreCase("-import")) {
            cmdImportCards(args[1], cards, logFile, mistakes);
        } else if ((args.length==4)&&args[2].equalsIgnoreCase("-import")) {
            cmdImportCards(args[3], cards, logFile, mistakes);
        } else if ((args.length==4)&&args[0].equalsIgnoreCase("-import")) {
            cmdImportCards(args[1], cards, logFile, mistakes);
        }

        outputMsg("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):", logFile);
        String comm = inputMsg(scanner, logFile).toLowerCase();
        // switch for menu

        while (!comm.equalsIgnoreCase("exit")) {
            if (comm.equalsIgnoreCase("add")) {
                logFile.add("add");
                FlashCards.addCard(scanner, cards, logFile);
            } else if (comm.equalsIgnoreCase("remove")) {
                logFile.add("remove");
                FlashCards.removeCard(scanner, cards, mistakes, logFile);
            } else if (comm.equalsIgnoreCase("import")) {
                logFile.add("import");
                FlashCards.importCards(scanner, cards, logFile, mistakes);
            } else if (comm.equalsIgnoreCase("export")) {
                logFile.add("export");
                FlashCards.exportCards(scanner, cards, logFile, mistakes);
            } else if (comm.equalsIgnoreCase("ask")) {
                logFile.add("ask");
                FlashCards.askCard(scanner, cards, mistakes);
            } else if (comm.equalsIgnoreCase("log")) {
                logFile.add("log");
                FlashCards.log(scanner, logFile);
            } else if (comm.equalsIgnoreCase("hardest card")) {
                logFile.add("hardest card");
                FlashCards.hardestCard(mistakes, logFile);
            } else if (comm.equalsIgnoreCase("reset stats")) {
                logFile.add("reset stats");
                FlashCards.resetStats(mistakes, logFile);
            }
            outputMsg("", logFile);
            outputMsg("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):", logFile);
            comm = inputMsg(scanner, logFile).toLowerCase();
        }
        outputMsg("Bye bye!", logFile);

        if ((args.length==2)&&args[0].equalsIgnoreCase("-export")) {
            exitExportCards(args[1], cards, logFile, mistakes);
        } else if ((args.length==4)&&args[2].equalsIgnoreCase("-export")) {
            exitExportCards(args[3], cards, logFile, mistakes);
        }else if ((args.length==4)&&args[0].equalsIgnoreCase("-export")) {
            exitExportCards(args[1], cards, logFile, mistakes);
        }

    } // end of main

    //adding card

    static void addCard(Scanner scanner, Map<String, String> cards, List<String> logFile) {
        for (int i = 0; i < 1; i++) {                   // заполнение карточек циклом for
            System.out.println("The card:");
            logFile.add("The card:");
            String k = scanner.nextLine();
            logFile.add(k);
            if (cards.containsKey(k)) {
                System.out.println("The card \"" + k + "\" already exists");
                logFile.add("The card \"" + k + "\" already exists");
                break;
            }

            System.out.println("The definition of the card:");
            logFile.add("The definition of the card:");
            String v = scanner.nextLine();
            logFile.add(v);
            if (cards.containsValue(v)) {
                System.out.println("The definition \"" + v + "\" already exists");
                logFile.add("The definition \"" + v + "\" already exists");
                break;
            }

            cards.put(k, v);
            System.out.println("The pair (\"" + k + "\":\"" + v + "\") has been added.");
            logFile.add("The pair (\"" + k + "\":\"" + v + "\") has been added.");
        }
    }

    // removing card

    static void removeCard(Scanner scanner, Map<String, String> cards, Map<String, Integer> mistakes, List<String> logFile) {
        System.out.println("The card:");
        logFile.add("The card:");
        String removeKey = scanner.nextLine();
        logFile.add(removeKey);
        if (cards.containsKey(removeKey)) {
            cards.remove(removeKey);
            mistakes.remove(removeKey);
            System.out.println("The card has been removed.");
            logFile.add("The card has been removed.");
        } else {
            System.out.println("Can't remove \"" + removeKey + "\": there is no such card.");
            logFile.add("Can't remove \"" + removeKey + "\": there is no such card.");
        }
    }

    // import cards from file

    static void importCards(Scanner scanner, Map<String, String> cards, List<String> logFile, Map<String, Integer> mistakes) {
        System.out.println("File name:");
        logFile.add("File name:");
        File file = new File(scanner.nextLine());
        logFile.add(file.toString());
        int i = 0;
        try (Scanner scanFile = new Scanner(file)) {
            String[] temp;
            while (scanFile.hasNextLine()) {
                temp = scanFile.nextLine().split("--");
                cards.put(temp[0], temp[1]);
                if (Integer.parseInt(temp[2])>0) {
                    mistakes.put(temp[0], Integer.parseInt(temp[2]));
                }
                i++;
            }
            System.out.println(i + " cards have been loaded.");
            logFile.add(i + " cards have been loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            logFile.add("File not found.");
        }
    }

    // command-line import cards from file

    static void cmdImportCards(String fileName, Map<String, String> cards, List<String> logFile, Map<String, Integer> mistakes) {
        File file = new File(fileName);
        int i = 0;
        try (Scanner scanFile = new Scanner(file)) {
            String[] temp;
            while (scanFile.hasNextLine()) {
                temp = scanFile.nextLine().split("--");
                cards.put(temp[0], temp[1]);
                if (Integer.parseInt(temp[2])>0) {
                    mistakes.put(temp[0], Integer.parseInt(temp[2]));
                }
                i++;
            }
            outputMsg(i + " cards have been loaded.", logFile);
        } catch (FileNotFoundException e) {
            outputMsg("File not found.", logFile);
        }
    }

    // export cards to file

    static void exportCards(Scanner scanner, Map<String, String> cards, List<String> logFile, Map<String, Integer> mistakes) {
        System.out.println("File name:");
        logFile.add("File name:");
        File newFile = new File(scanner.nextLine());
        logFile.add(newFile.toString());
        int j = 0;

        try {
            PrintWriter writer = new PrintWriter(newFile); // overwrites the file
            for (String card : cards.keySet()) {
                writer.print(card);
                writer.print("--");
                writer.print(cards.get(card));
                writer.print("--");
                if (mistakes.containsKey(card)) {
                    writer.println(mistakes.get(card));
                } else {
                    writer.println(0);
                }
                j++;
            }
            writer.close();
            System.out.println(j + " cards have been saved");
            logFile.add(j + " cards have been saved");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // command-line exit with export

    static void exitExportCards(String fileName, Map<String, String> cards, List<String> logFile, Map<String, Integer> mistakes) {

            File newFile = new File(fileName);
            int j = 0;
            try {
                PrintWriter writer = new PrintWriter(newFile); // overwrites the file
                for (String card : cards.keySet()) {
                    writer.print(card);
                    writer.print("--");
                    writer.print(cards.get(card));
                    writer.print("--");
                    if (mistakes.containsKey(card)) {
                        writer.println(mistakes.get(card));
                    } else {
                        writer.println(0);
                    }
                    j++;
                }
                writer.close();
                System.out.println(j + " cards have been saved");
                logFile.add(j + " cards have been saved");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    // asking card

    static void askCard(Scanner scanner, Map<String, String> cards, Map<String, Integer> mistakes) {
        System.out.println("How many times to ask?");
        String inputNumber = scanner.nextLine();
        Random random = new Random();
        ArrayList<String> keys = new ArrayList<>(cards.keySet());
        int numberOfAsking = Integer.parseInt(inputNumber);
        String term;
        // ввод данных и сверка ответа
        for (int k = 0; k < numberOfAsking; k++) {
            term = keys.get(random.nextInt(keys.size()));
            System.out.println("Print the definition of \"" + term + "\":");
            String definition = scanner.nextLine();
            if (cards.get(term).equalsIgnoreCase(definition)) {
                System.out.println("Correct answer.");
            } else if (!cards.get(term).equalsIgnoreCase(definition) && cards.containsValue(definition)) {
                System.out.println("Wrong answer. The correct one is " + "\"" + cards.get(term) + "\"" + ", you've just " +
                        "written the definition of \"" + FlashCards.getTerm(cards, definition) + "\"");
                FlashCards.writeMistake(mistakes, term);
            } else {
                System.out.println("Wrong answer. The correct one is " + "\"" + cards.get(term) + "\"");
                FlashCards.writeMistake(mistakes, term);
            }
        }
    }

    // процедура для поиска ключа по значению
    private static String getTerm(Map<String, String> cards, String definition) {
        Set<Map.Entry<String, String>> entrySet = cards.entrySet();
        String desiredObject = "";   //что хотим найти

        for (Map.Entry<String, String> pair : entrySet) {
            if (definition.equalsIgnoreCase(pair.getValue())) {
                desiredObject = pair.getKey();             // нашли наше значение и возвращаем  ключ
            }
        }
        return desiredObject;
    }

    // Запись логов
    static void log(Scanner scanner, List<String> logFile) {
        outputMsg("File name:", logFile);
        File newFile = new File(scanner.nextLine());
        logFile.add(newFile.toString());
        try {
            PrintWriter writer = new PrintWriter(newFile); // overwrites the file
            for (String logs : logFile) {
                writer.println(logs);
            }
            writer.println("The log has been saved.");
            writer.close();
            System.out.println("The log has been saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Запись ошибок
    static void writeMistake(Map<String, Integer> mistakes, String wrongAnswer) {
        if (!mistakes.containsKey(wrongAnswer)) {
            mistakes.put(wrongAnswer, 1);
        } else {
            mistakes.put(wrongAnswer, mistakes.get(wrongAnswer)+1);
        }
    }

    static void hardestCard(Map<String, Integer> mistakes, List<String> logFile) {
        if (mistakes.isEmpty()) {
            outputMsg("There are no cards with errors.", logFile);
        } else {
            List<String> wrongCards = new ArrayList<>();
            Integer max = 0;
            for (Integer i : mistakes.values()) {
                if (i > max) {
                    max = i;
                }
            }
            for (var m : mistakes.entrySet()) {
                if (max.equals(m.getValue())) {
                    wrongCards.add(m.getKey());
                }
            }
            if (wrongCards.size() == 1) {
                outputMsg("The hardest card is " + "\"" + wrongCards.get(0) + "\"" +
                        ". You have " + max + " errors answering it.", logFile);
            } else {
                System.out.print("The hardest cards are ");
                logFile.add("The hardest cards are ");
                for (int i=0; i<wrongCards.size()-1; i++) {
                    System.out.print("\""+wrongCards.get(i)+"\"" +", ");
                    logFile.add("\""+wrongCards.get(i)+"\"" +", ");
                }
                outputMsg("\"" + wrongCards.get((wrongCards.size()-1)) + "\". You have " + max + " errors answering them.", logFile);
            }
        }
    }

    //Сброс ошибок
    static void resetStats (Map<String, Integer> mistakes, List<String> logFile) {

        mistakes.clear();
        outputMsg("Card statistics has been reset.", logFile);
    }

    static void outputMsg (String msg, List<String> logFile) {
        System.out.println(msg);
        logFile.add(msg);
    }

    static String inputMsg (Scanner scanner, List<String> logFile) {
        String s = scanner.nextLine();
        logFile.    add(s);
        return s;
    }
}