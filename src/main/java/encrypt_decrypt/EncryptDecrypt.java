package encrypt_decrypt;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class EncryptDecrypt {
    public static void main(String[] args) {
        ArrayList<String> arguments = new ArrayList<>();
        Collections.addAll(arguments, args);
        String mode, data, filenameIn, filenameOut, algorithm;
        int key;

        // MODE
        if (arguments.contains("-mode")) {
            mode = arguments.get(arguments.indexOf("-mode") + 1);
        } else {
            mode = "enc";
        }

        // KEY
        if (arguments.contains("-key")) {
            key = Integer.parseInt(arguments.get(arguments.indexOf("-key") + 1));
        } else {
            key = 0;
        }

        // OUT
        if (arguments.contains("-out")) {
            filenameOut = arguments.get(arguments.indexOf("-out") + 1);
        } else {
            filenameOut = null;
        }

        // IN
        if (arguments.contains("-in") & !arguments.contains("-data")) {
            filenameIn = arguments.get(arguments.indexOf("-in") + 1);
            data = EncryptDecrypt.input(filenameIn);
        } else if (arguments.contains("-data") & !arguments.contains("-in")) {
            data = arguments.get(arguments.indexOf("-data") + 1);
        } else if (!arguments.contains("-in") & !arguments.contains("-data")) {
            data = "";
        } else {
            data = "";
        }
        if (arguments.contains("-in") & arguments.contains("-data")) {
            data = arguments.get(arguments.indexOf("-data") + 1);
        }

        //ALG
        if (arguments.contains("-alg")) {
            algorithm = arguments.get(arguments.indexOf("-alg") + 1);
        } else {
            algorithm = "shift";
        }

        switch (mode) {
            case "enc":
                if (filenameOut != null) {
                    EncryptDecrypt.output(EncryptDecrypt.Encrypt(data, key, algorithm), filenameOut);
                } else {
                    System.out.println(EncryptDecrypt.Encrypt(data, key, algorithm));
                }
                break;
            case "dec":
                if (filenameOut != null) {
                    EncryptDecrypt.output(EncryptDecrypt.Decrypt(data, key, algorithm), filenameOut);
                } else {
                    System.out.println(EncryptDecrypt.Decrypt(data, key, algorithm));
                }
                break;
        }
    }

    static String Encrypt(String data, int key, String algorithm) {
        StringBuilder sb = new StringBuilder();
        if (algorithm.equals("unicode")) {
            for (char s : data.toCharArray()) {
                int numbertoChar = (int) s + key;
                if (numbertoChar > 126) {
                    numbertoChar = numbertoChar - 126;
                }
                sb.append((char) numbertoChar);
            }
        } else {
            String alphabet = "abcdefghijklmnopqrstuvwxyz";
            String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            int index;
            for (int i = 0; i < data.length(); i++) {
                if (alphabet.contains(String.valueOf(data.charAt(i)))) {
                    var i1 = alphabet.indexOf(data.charAt(i)) + key;
                    if (i1 > alphabet.length()) {
                        index = i1 - alphabet.length();
                    } else {
                        index = i1;
                    }
                    sb.append(alphabet.charAt(index));
                } else if (ALPHABET.contains(String.valueOf(data.charAt(i)))) {
                    var i1 = ALPHABET.indexOf(data.charAt(i)) + key;
                    if (i1 > ALPHABET.length()) {
                        index = i1 - ALPHABET.length();
                    } else {
                        index = i1;
                    }
                    sb.append(ALPHABET.charAt(index));
                } else {
                    sb.append(data.charAt(i));
                }
            }
        }
        return "" + sb;
    }


    static String Decrypt(String data, int key, String algorithm) {
        StringBuilder sb = new StringBuilder();
        if (algorithm.equals("unicode")) {
            for (char s : data.toCharArray()) {
                int numbertoChar = (int) s - key;
                if (numbertoChar < 32) {
                    numbertoChar = numbertoChar + 126;
                }
                sb.append((char) numbertoChar);
            }
        } else {
            String alphabet = "abcdefghijklmnopqrstuvwxyz";
            String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            int index;
            for (int i = 0; i < data.length(); i++) {
                if (alphabet.contains(String.valueOf(data.charAt(i)))) {
                    var i1 = alphabet.indexOf(data.charAt(i)) - key;
                    if (i1 < 0) {
                        index = i1 + alphabet.length();
                    } else {
                        index = i1;
                    }
                    sb.append(alphabet.charAt(index));
                } else if (ALPHABET.contains(String.valueOf(data.charAt(i)))) {
                    var i1 = ALPHABET.indexOf(data.charAt(i)) - key;
                    if (i1 < 0) {
                        index = i1 + ALPHABET.length();
                    } else {
                        index = i1;
                    }
                    sb.append(ALPHABET.charAt(index));
                } else {
                    sb.append(data.charAt(i));
                }
            }
        }
        return "" + sb;
    }

    static void output(String encryptData, String filenameOut) {
        File file = new File(filenameOut);
        try {
            FileWriter fr = new FileWriter(file);
            fr.write(encryptData);
            fr.close();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    static String input(String filenameIn) {
        File file = new File(filenameIn);
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fr = new FileReader(file);
            char[] buffer = new char[8096];
            int chars = fr.read(buffer);
            while (chars != -1) {
                sb.append(String.valueOf(buffer, 0, chars));
                chars = fr.read(buffer);
            }
            fr.close();
        } catch (IOException e) {
            System.out.println("Error");
        }
        return sb + "";
    }
}
