package sorting;

import java.io.*;
import java.util.*;

public class SortingTool {
    public static void main(String[] args) {
        Map<String, String> argumentsMap = SortingTool.processingOfArgs(args);
        String sortType = argumentsMap.get("-sortingType");
        String dataType = argumentsMap.get("-dataType");
        String outputFile = argumentsMap.get("-outputFile");
        String inputFile  = argumentsMap.get("-inputFile");
        Scanner sc = null;
        if (inputFile != null) {
            File file = new File (inputFile);
            try {
                sc = new Scanner(file);
            } catch (FileNotFoundException e) {
                System.out.println("No file found: " + file);
            }
        } else {
            sc = new Scanner (System.in);
        }

        if (sortType == null) {
            System.out.println("No sorting type defined!");
        } else if (dataType == null) {
            System.out.println("No data type defined!");
        } else {
            if (argumentsMap.containsKey("Other")) {
                String[] otherArguments = argumentsMap.get("Other").split("\\s+");
                for (String otherArg : otherArguments) {
                    System.out.println("\"" + otherArg + "\"  isn't a valid parameter. It's skipped.");
                }
            }
            switch (dataType) {
                case "word":
                    if (outputFile != null) {
                        typeWord(sc, sortType, outputFile);
                    } else {
                        typeWordToConsole(sc, sortType);
                    }
                    break;
                case "long":
                    if (outputFile != null) {
                        typeLong(sc, sortType, outputFile);
                    } else {
                        typeLongToConsole(sc, sortType);
                    }
                    break;
                case "line":
                    if (outputFile != null) {
                        typeLine(sc, sortType, outputFile);
                    } else {
                        typeLineToConsole(sc, sortType);
                    }
                    break;
            }
            if (sc!=null) {
                sc.close();
            }
        }
    }

    //Processing the command-line argument for sorting
    static Map<String, String> processingOfArgs(String[] args) {
        Map<String, String> argsMap = new HashMap<>();
        ArrayList<String> arguments = new ArrayList<>();
        Collections.addAll(arguments, args);
        // SortingType
        String sortingType;
        if (arguments.contains("-sortingType")) {
            try {
                sortingType = arguments.get(arguments.indexOf("-sortingType") + 1);
                arguments.remove(arguments.indexOf("-sortingType") + 1);
            } catch (IndexOutOfBoundsException e) {
                sortingType = null;
            }
        } else {
            sortingType = "natural";
        }
        argsMap.put("-sortingType", sortingType);
        arguments.remove("-sortingType");
        // DataType
        String dataType;
        if (arguments.contains("-dataType")) {
            try {
                dataType = arguments.get(arguments.indexOf("-dataType") + 1);
                arguments.remove(arguments.indexOf("-dataType") + 1);
            } catch (IndexOutOfBoundsException e) {
                dataType = null;
            }
        } else {
            dataType = "word";
        }
        argsMap.put("-dataType", dataType);
        arguments.remove("-dataType");
        // InputFile
        String inputFile = null;
        if (arguments.contains("-inputFile")) {
            try {
                inputFile = arguments.get(arguments.indexOf("-inputFile") + 1);
                arguments.remove(arguments.indexOf("-inputFile") + 1);
            } catch (IndexOutOfBoundsException e) {
                inputFile = null;
            }
        }
        argsMap.put("-inputFile", inputFile);
        arguments.remove("-inputFile");
        // OutputFile
        String outputFile = null;
        if (arguments.contains("-outputFile")) {
            try {
                outputFile = arguments.get(arguments.indexOf("-outputFile") + 1);
                arguments.remove(arguments.indexOf("-outputFile") + 1);
            } catch (IndexOutOfBoundsException e) {
                outputFile = null;
            }
        }
        argsMap.put("-outputFile", outputFile);
        arguments.remove("-outputFile");

        int n = 0;
        StringBuilder sb = new StringBuilder();
        while (n < arguments.size()) {
            sb.append(arguments.get(n)).append(" ");
            n++;
        }
        String other = new String(sb);
        if (!other.equals("")) {
            argsMap.put("Other", other.trim());
        }
        return argsMap;
    }

    static void typeLong(Scanner sc, String sortType, String outputFile) {
        long total = 0;
        SortedMap<Long, Integer> values = new TreeMap<>();
        String word;
        while (sc.hasNext()) {
            word = sc.next();
            if (!word.matches("(-?[0-9]+)")) {
                System.out.println("\"" + word + "\"  isn't a long. It's skipped.");
            } else {
                long number = Long.parseLong(word);
                values.put(number, values.getOrDefault(number, 0) + 1);
                total += 1;
            }
        }
        File file;
        PrintStream ps;
        PrintWriter pw = null;
        if (outputFile != null) {
            file = new File(outputFile);
            try {
                pw = new PrintWriter(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            ps = new PrintStream(System.out);
            pw = new PrintWriter(ps);
        }
        assert pw != null;
        pw.printf("Total numbers: %d.%n", total);
        final long eftotal = total;

        if ("natural".equals(sortType)) {
            pw.print("Sorted data: ");
            for (var entry : values.entrySet()) {
                for (int i = 0; i < entry.getValue(); i++) {
                    pw.print(entry.getKey() + " ");
                }
            }
        } else {
            PrintWriter finalPw = pw;
            values.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(entry -> finalPw.printf("%s: %d time(s), %d%%%n", entry.getKey(), entry.getValue(), Math.round(entry.getValue() * 100.0 / eftotal)));
        }
        pw.close();
        sc.close();
    }

    static void typeLongToConsole(Scanner sc2, String sortType) {
        long total = 0;
        SortedMap<Long, Integer> values = new TreeMap<>();
        String word;
        while (sc2.hasNext()) {
            word = sc2.next();
            if (!word.matches("(-?[0-9]+)")) {
                System.out.println("\"" + word + "\"  isn't a long. It's skipped.");
            } else {
                long number = Long.parseLong(word);
                values.put(number, values.getOrDefault(number, 0) + 1);
                total += 1;
            }
        }
        System.out.printf("Total numbers: %d.%n", total);
        final long eftotal = total;

        if ("natural".equals(sortType)) {
            System.out.print("Sorted data: ");
            for (var entry : values.entrySet()) {
                for (int i = 0; i < entry.getValue(); i++) {
                    System.out.print(entry.getKey() + " ");
                }
            }
        } else {
            values.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(entry -> System.out.printf("%s: %d time(s), %d%%%n", entry.getKey(), entry.getValue(), Math.round(entry.getValue() * 100.0 / eftotal)));
        }
        sc2.close();
    }

    static void typeLine(Scanner sc, String sortType, String outputFile) {
        long total = 0;
        SortedMap<String, Integer> values = new TreeMap<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            values.put(line, values.getOrDefault(line, 0) + 1);
            total += 1;
        }

        File XXX;
        PrintStream ps;
        PrintWriter pw = null;
        if (outputFile != null) {
            XXX = new File(outputFile);
            try {
                pw = new PrintWriter(XXX);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            ps = new PrintStream(System.out);
            pw = new PrintWriter(ps);
        }

        pw.printf("Total lines: %s.%n", total);
        final long eftotal = total;

        if ("natural".equals(sortType)) {
            pw.println("Sorted data: ");
            for (var entry : values.entrySet()) {
                for (int i = 0; i < entry.getValue(); i++) {
                    pw.println(entry.getKey());
                }
            }
        } else {
            PrintWriter finalPw = pw;
            values.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(entry -> finalPw.printf("%s: %d time(s), %d%%%n", entry.getKey(), entry.getValue(), Math.round(entry.getValue() * 100.0 / eftotal)));
        }
        sc.close();
        pw.close();
    }

    static void typeLineToConsole(Scanner sc2, String sortType) {
        long total = 0;
        SortedMap<String, Integer> values = new TreeMap<>();

        while (sc2.hasNextLine()) {
            String line = sc2.nextLine();
            values.put(line, values.getOrDefault(line, 0) + 1);
            total += 1;
        }

        System.out.printf("Total lines: %s.%n", total);
        final long eftotal = total;

        if ("natural".equals(sortType)) {
            System.out.println("Sorted data: ");
            for (var entry : values.entrySet()) {
                for (int i = 0; i < entry.getValue(); i++) {
                    System.out.println(entry.getKey());
                }
            }
        } else {
            values.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(entry -> System.out.printf("%s: %d time(s), %d%%%n", entry.getKey(), entry.getValue(), Math.round(entry.getValue() * 100.0 / eftotal)));
        }
        sc2.close();
    }

    static void typeWord(Scanner sc, String sortType, String outputFile) {
        long total = 0;
        SortedMap<String, Integer> values = new TreeMap<>();

        while (sc.hasNext()) {
            String line = sc.next();
            values.put(line, values.getOrDefault(line, 0) + 1);
            total += 1;
        }

        File XXX;
        PrintStream ps;
        PrintWriter pw = null;
        if (outputFile != null) {
            XXX = new File(outputFile);
            try {
                pw = new PrintWriter(XXX);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            ps = new PrintStream(System.out);
            pw = new PrintWriter(ps);
        }

        pw.printf("Total words: %s.%n", total);
        final long eftotal = total;

        if ("natural".equals(sortType)) {
            pw.print("Sorted data: ");
            for (var entry : values.entrySet()) {
                for (int i = 0; i < entry.getValue(); i++) {
                    pw.print(entry.getKey() + " ");
                }
            }
        } else {
            PrintWriter finalPw = pw;
            values.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(entry -> finalPw.printf("%s: %d time(s), %d%%%n", entry.getKey(), entry.getValue(), Math.round(entry.getValue() * 100.0 / eftotal)));
        }
        sc.close();
        pw.close();
    }

    static void typeWordToConsole(Scanner sc2, String sortType) {
        long total = 0;
        SortedMap<String, Integer> values = new TreeMap<>();

        while (sc2.hasNext()) {
            String line = sc2.next();
            values.put(line, values.getOrDefault(line, 0) + 1);
            total += 1;
        }

        System.out.printf("Total words: %s.%n", total);
        final long eftotal = total;

        if ("natural".equals(sortType)) {
            System.out.print("Sorted data: ");
            for (var entry : values.entrySet()) {
                for (int i = 0; i < entry.getValue(); i++) {
                    System.out.print(entry.getKey() + " ");
                }
            }
        } else {
            values.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(entry -> System.out.printf("%s: %d time(s), %d%%%n", entry.getKey(), entry.getValue(), Math.round(entry.getValue() * 100.0 / eftotal)));
        }
        sc2.close();
    }
}