package readability_score;

import java.io.*;
import java.util.Scanner;

public class ReadabilityScore {

    //Import
    static String in (String argument) {
        StringBuilder sb = new StringBuilder();
        try {
            File myObj = new File(argument);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                sb.append(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return sb.toString();
    }

    // Process
    static void processingString (String inputString) {
        int numberOfCharacters = 0;
        for (int i = 0; i < inputString.length(); i++) {
            char ch = inputString.charAt(i);
            if (ch != '\u0020' & ch != '\u0009' & ch != '\n') {
                numberOfCharacters++;
            }
        }
        String[] sentences = inputString.split("[.?!]\\s");
        int numberOfSentences = sentences.length;
        int numberOfWords = 0;
        for (String sentence : sentences) {
            String[] wordsInSentence = sentence.split("\\s+");
            for (String str : wordsInSentence) {
                if (str.matches("\\W?\\w+[,.?!\n\\W]?\\d*")) {
                    numberOfWords ++;
                }
            }
        }
        float score = (float) (4.71 * 1.0 * numberOfCharacters / numberOfWords + 0.5 * numberOfWords / numberOfSentences - 21.43);

        System.out.println("Words: "+ numberOfWords);
        System.out.println("Sentences: " + numberOfSentences);
        System.out.println("Characters: " + numberOfCharacters);
        System.out.printf("The score is: %.2f\n", score);
        ReadabilityScore.readabilityIndex(score);
    }

    static void readabilityIndex (float score) {
        String ages = null;
        if (score > 0 & score <= 1) {
            ages = "5-6";
        } else if (score > 1  & score <= 2) {
            ages = "6-7";
        } else if (score > 2  & score <= 3) {
            ages = "7-9";
        } else if (score > 3 & score <= 4) {
            ages = "9-10";
        } else if (score > 4 & score <= 5) {
            ages = "10-11";
        } else if (score > 5 & score <= 6) {
            ages = "11-12";
        }else if (score > 6 & score <= 7) {
            ages = "12-13";
        } else if (score > 7 & score <= 8) {
            ages = "13-14";
        } else if (score > 8 & score <= 9) {
            ages = "14-15";
        } else if (score > 9 & score <= 10) {
            ages = "15-16";
        } else if (score > 10 & score <= 11) {
            ages = "16-17";
        } else if (score > 11 & score <= 12) {
            ages = "17-18";
        } else if (score > 12 & score <= 13) {
            ages = "18-24";
        } else if (score > 13) {
            ages = "24+";
        }
        System.out.println("This text should be understood by " + ages + " year olds.");
    }

    public static void main(String[] args) {
        String argument = "src/main/java/readability_score/resources/in.txt";
        ReadabilityScore.processingString(ReadabilityScore.in(argument));
    }
}