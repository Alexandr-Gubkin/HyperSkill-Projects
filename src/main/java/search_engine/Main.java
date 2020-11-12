package search_engine;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> arguments = new ArrayList<>();
        Collections.addAll(arguments, args);
        Scanner sc = new Scanner(System.in);
        var data = Main.input(arguments);
        var hashMap = Main.invertedIndex(data);
        boolean flag = true;
        while (flag) {
            System.out.println("=== Menu ===" + "\n" +
                    "1. Find a person" + "\n" +
                    "2. Print all people" + "\n" +
                    "0. Exit");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Select a matching strategy: ALL, ANY, NONE");
                    String case1 = sc.nextLine();
                    WordsFinder wf;
                    switch (case1) {
                        case "ANY":
                            wf = new WordsFinder(new Find_Any_Strategy());
                            wf.find(sc, hashMap, data);
                            break;
                        case "ALL":
                            wf = new WordsFinder(new Find_All_Strategy());
                            wf.find(sc, hashMap, data);
                            break;
                        case "NONE":
                            wf = new WordsFinder(new Find_NONE_Strategy());
                            wf.find(sc, hashMap, data);
                            break;
                    }
                    break;
                case 2:
                    Main.printer(data);
                    break;
                case 0:
                    flag = false;
                    break;
                default:
                    System.out.println("Incorrect option! Try again.");
                    break;
            }
        }
    }

    //Printer
    static void printer(String[] data) {
        for (String str : data) {
            System.out.println(str);
        }
    }

    // Import
    static String[] input(List<String> arguments) {
        String filenameIn;
        String[] loadFile = new String[0];
        if (arguments.contains("--data")) {
            filenameIn = arguments.get(arguments.indexOf("--data") + 1);
            ArrayList<String> dataAL = new ArrayList<>();
            File file = new File(filenameIn);
            try {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    dataAL.add(line);
                }
                fr.close();
                br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            loadFile = dataAL.toArray(new String[0]);
            return loadFile;
        } else {
            System.out.println("Error");
        }
        return loadFile;
    }

    // InvertedIndex
    static Map<String, ArrayList<Integer>> invertedIndex(String[] loadfile) {
        Map<String, ArrayList<Integer>> hashMap = new HashMap<>();
        for (int i = 0; i < loadfile.length; i++) {
            String[] temp = loadfile[i].split("\\s+");
            for (String s : temp) {
                if (!hashMap.containsKey(s)) {
                    hashMap.put(s, new ArrayList<>());
                }
                hashMap.get(s).add(i);
            }
        }
        return hashMap;
    }
}

