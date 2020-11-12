package error_corrector;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;

class Procedure {

    final String sendFileName = "src/main/java/error_corrector/resources/send.txt";
    final String encodedFileName = "src/main/java/error_corrector/resources/encoded.txt";
    final String receivedFileName = "src/main/java/error_corrector/resources/received.txt";
    final String decodedFileName = "src/main/java/error_corrector/resources/decoded.txt";

    byte[] readFileToByteArray(String fileName) {
        byte[] readBytes = new byte[0];
        try {
            readBytes = Files.readAllBytes(Paths.get(fileName));
        } catch (IOException e) {
            System.out.println("Cannot read file: " + e.getMessage());
        }
        System.out.println("\n" + fileName + ":");
        return readBytes;
    }

    void printTextViewFromBytes(byte[] bytes) {
        String readMessage = new String(bytes);
        System.out.println("text view: " + readMessage);
    }

    void printHexViewFromBytes(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        String hexTemp;
        for (byte aByte : bytes) {
            hexTemp = String.format("%x", aByte);
            if (hexTemp.length() == 1) {
                sb.append("0");
            }
            sb.append(hexTemp).append(" ");
        }
        String hexString = sb.toString().trim();
        System.out.println("hex view: " + hexString.toUpperCase());
    }

    String printBinaryViewFromBytes(byte[] bytes) {
        StringBuilder sbBin = new StringBuilder();
        char[] chars = new String(bytes).toCharArray();
        for (char ch : chars) {
            String tempBinaryString = String.format("%08d", Integer.parseInt(Integer.toBinaryString(ch)));
            sbBin.append(tempBinaryString).append(" ");
        }
        String binView = (sbBin + "").trim();
        System.out.println("bin view: " + binView);
        return binView.replace(" ", "");
    }

    void printBinaryViewFromBinaryString(String binaryMessage) {
        for (int i = 0; i < binaryMessage.length(); i++) {
            if (i > 0 & i % 8 == 0) {
                System.out.print(" ");
            }
            System.out.print(binaryMessage.charAt(i));
        }
        System.out.println();
    }

    void printHexViewFromBinary(String message) {
        StringBuilder sb = new StringBuilder();
        String hexTemp;
        for (int i = 0; i < message.length(); i = i + 8) {
            hexTemp = Integer.toHexString(Integer.parseInt(message.substring(i, i + 8), 2));
            if (hexTemp.length() == 1) {
                sb.append("0");
            }
            sb.append(hexTemp).append(" ");
        }
        String hex = (sb + "").trim().toUpperCase();
        System.out.println("hex view: " + hex);
    }

    void writeToFile(String fileName, String message) {
        byte bytes;
        try (OutputStream outputStream = new FileOutputStream(fileName, false)) {
            for (int i = 0; i < message.length(); i = i + 8) {
                bytes = (byte) Integer.parseInt(message.substring(i, i + 8), 2);
                outputStream.write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeTextViewToFile(String fileName, String message) {
        try (BufferedWriter writter = new BufferedWriter(new FileWriter(fileName))) {
            writter.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String printBinaryViewFromEncodedBytes(byte[] bytes) {

        StringBuilder sb = new StringBuilder();
        String hexTemp;
        for (byte aByte : bytes) {
            hexTemp = String.format("%x", aByte);
            sb.append(hexTemp).append(" ");
        }
        String hexString = sb.toString().trim();
        StringBuilder sbBin = new StringBuilder();
        String[] hexs = hexString.split("\\s+");
        for (String ch : hexs) {
            String tempBinaryString = String.format("%8s", new BigInteger(ch, 16).toString(2)).replace(" ", "0");
            sbBin.append(tempBinaryString).append(" ");
        }
        String binView = (sbBin + "").trim();
        System.out.println("bin view: " + binView);
        return binView.replace(" ", "");
    }

    /*void encodedMessage() {
        byte[] messageInBytes = this.readFileToByteArray(sendFileName);
        this.printTextViewFromBytes(messageInBytes);
        this.printHexViewFromBytes(messageInBytes);
        String binaryStringAfterRead = this.printBinaryViewFromBytes(messageInBytes);

        System.out.println("\n" + encodedFileName + ":");
        StringBuilder encoded_1 = new StringBuilder();
        StringBuilder encoded_2 = new StringBuilder();

        int end = binaryStringAfterRead.length() - binaryStringAfterRead.length() % 3;
        for (int i = 0; i < end; i = i + 3) {
            char first = binaryStringAfterRead.charAt(i);
            char second = binaryStringAfterRead.charAt(i + 1);
            char third = binaryStringAfterRead.charAt(i + 2);

            encoded_1.append(first).append(first).append(second).append(second).append(third).append(third).append(".").append(".").append(" ");
            encoded_2.append(first).append(first).append(second).append(second).append(third).append(third);

            boolean firstBoolean = (first == '1');
            boolean secondBoolean = (second == '1');
            boolean thirdBoolean = (third == '1');

            if (firstBoolean ^ secondBoolean ^ thirdBoolean) {
                encoded_2.append("11 ");
            } else {
                encoded_2.append("00 ");
            }
        }

        for (int i = end; i < binaryStringAfterRead.length(); i++) {
            encoded_1.append(binaryStringAfterRead.charAt(i)).append(binaryStringAfterRead.charAt(i));
            encoded_2.append(binaryStringAfterRead.charAt(i)).append(binaryStringAfterRead.charAt(i));
        }

        if (binaryStringAfterRead.length() - end == 1) {
            if (binaryStringAfterRead.charAt(end) == '1') {
                encoded_2.append("000011");
            } else {
                encoded_2.append("000000");
            }
        } else if (binaryStringAfterRead.length() - end == 2) {
            if (binaryStringAfterRead.charAt(end) == '1' ^ binaryStringAfterRead.charAt(end + 1) == '1') {
                encoded_2.append("0011");
            } else {
                encoded_2.append("0000");
            }
        }

        int n = 8 - binaryStringAfterRead.length() % 3 * 2;
        if (n < 8) {
            while (n > 0) {
                encoded_1.append(".");
                n--;
            }
        }

        String expand = encoded_1 + "";
        System.out.println("expand: " + expand.trim());
        String parity = (encoded_2 + "").trim();
        System.out.println("parity: " + parity.trim());
        String parityBinaryStringAfterEncode = parity.replace(" ", "");

        this.printHexViewFromBinary(parityBinaryStringAfterEncode);

        this.writeToFile(encodedFileName, parityBinaryStringAfterEncode);
    }*/

    void encodedMessageByHamming() {
        byte[] messageInBytes = this.readFileToByteArray(sendFileName);
        this.printTextViewFromBytes(messageInBytes);
        this.printHexViewFromBytes(messageInBytes);
        String binaryStringAfterRead = this.printBinaryViewFromBytes(messageInBytes);

        System.out.println("\n" + encodedFileName + ":");
        StringBuilder encoded_1 = new StringBuilder();
        StringBuilder encoded_2 = new StringBuilder();

        for (int i = 0; i < binaryStringAfterRead.length(); i = i + 4) {
            char first = binaryStringAfterRead.charAt(i);
            char second = binaryStringAfterRead.charAt(i + 1);
            char third = binaryStringAfterRead.charAt(i + 2);
            char fourth = binaryStringAfterRead.charAt(i + 3);
            encoded_1.append("..").append(first).append(".").append(second).append(third).append(fourth).append(".").append(" ");

            boolean firstBoolean = (first == '1');
            boolean secondBoolean = (second == '1');
            boolean thirdBoolean = (third == '1');
            boolean fourthBoolean = (fourth == '1');

            //Set 1st parity bit
            if (firstBoolean ^ secondBoolean ^ fourthBoolean) {
                encoded_2.append("1");
            } else {
                encoded_2.append("0");
            }

            //Set 2nd parity bit
            if (firstBoolean ^ thirdBoolean ^ fourthBoolean) {
                encoded_2.append("1");
            } else {
                encoded_2.append("0");
            }

            //Set 3rd bit
            encoded_2.append(first);

            //Set 4th parity bit
            if (secondBoolean ^ thirdBoolean ^ fourthBoolean) {
                encoded_2.append("1");
            } else {
                encoded_2.append("0");
            }

            //Set 5-8 bits
            encoded_2.append(second).append(third).append(fourth).append("0 ");

        }

        String expand = encoded_1 + "";
        System.out.println("expand: " + expand.trim());
        String parity = (encoded_2 + "").trim();
        System.out.println("parity: " + parity.trim());
        String parityBinaryStringAfterEncode = parity.replace(" ", "");

        this.printHexViewFromBinary(parityBinaryStringAfterEncode);

        this.writeToFile(encodedFileName, parityBinaryStringAfterEncode);
    }

    void sendMessage() {
        byte[] messageInBytes = this.readFileToByteArray(encodedFileName);
        this.printHexViewFromBytes(messageInBytes);
        String binaryStringAfterRead = this.printBinaryViewFromEncodedBytes(messageInBytes);

        //Poor sending
        char[] charsOfMessage = binaryStringAfterRead.toCharArray();
        String receivedString = null;
        for (int i = 0; i < charsOfMessage.length; i = i + 8) {
            int n = (int) (Math.random() * (8)) + i;
            if (charsOfMessage[n] == '1') {
                charsOfMessage[n] = '0';
            } else {
                charsOfMessage[n] = '1';
            }
            receivedString = String.valueOf(charsOfMessage);

        }

        System.out.println("\n" + receivedFileName + ":");
        assert receivedString != null;

        System.out.print("bin view: ");
        this.printBinaryViewFromBinaryString(receivedString);

        this.printHexViewFromBinary(receivedString);

        this.writeToFile(receivedFileName, receivedString);
    }

    /*void decodeMessage() {

        byte[] messageInBytes = this.readFileToByteArray(receivedFileName);
        this.printHexViewFromBytes(messageInBytes);
        String binaryStringAfterRead = this.printBinaryViewFromEncodedBytes(messageInBytes);

        System.out.println("\n" + decodedFileName + ":");
        StringBuilder sb = new StringBuilder();
        char poorbit;
        for (int i = 0; i < binaryStringAfterRead.length(); i = i + 8) {
            char first_1 = binaryStringAfterRead.charAt(i);
            char first_2 = binaryStringAfterRead.charAt(i + 1);
            char second_1 = binaryStringAfterRead.charAt(i + 2);
            char second_2 = binaryStringAfterRead.charAt(i + 3);
            char third_1 = binaryStringAfterRead.charAt(i + 4);
            char third_2 = binaryStringAfterRead.charAt(i + 5);
            char parity_1 = binaryStringAfterRead.charAt(i + 6);
            boolean one = first_1 == '1';
            boolean two = second_1 == '1';
            boolean three = third_1 == '1';
            boolean parity = binaryStringAfterRead.charAt(i + 6) == '1';
            if (first_1 != first_2) {
                poorbit = (two ^ three ^ parity) ? '1' : '0';
                sb.append(poorbit).append(poorbit).append(second_1).append(second_1).append(third_1).append(third_1).append(parity_1).append(parity_1);
            } else if (second_1 != second_2) {
                poorbit = (one ^ three ^ parity) ? '1' : '0';
                sb.append(first_1).append(first_1).append(poorbit).append(poorbit).append(third_1).append(third_1).append(parity_1).append(parity_1);
            } else if (third_1 != third_2) {
                poorbit = (one ^ two ^ parity) ? '1' : '0';
                sb.append(first_1).append(first_1).append(second_1).append(second_1).append(poorbit).append(poorbit).append(parity_1).append(parity_1);
            } else {
                poorbit = (one ^ two ^ three) ? '1' : '0';
                sb.append(first_1).append(first_1).append(second_1).append(second_1).append(third_1).append(third_1).append(poorbit).append(poorbit);
            }
            sb.append(" ");

        }
        String correctParityBinaryMessage = (sb + "").trim();
        System.out.println("correct: " + correctParityBinaryMessage);
        correctParityBinaryMessage = correctParityBinaryMessage.replace(" ", "");

        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < correctParityBinaryMessage.length(); i = i + 8) {
            char first = correctParityBinaryMessage.charAt(i);
            char second = correctParityBinaryMessage.charAt(i + 2);
            char third = correctParityBinaryMessage.charAt(i + 4);

            sb2.append(first).append(second).append(third);
        }
        String correctBinaryMessage = sb2 + "";
        System.out.print("decode: ");
        this.printBinaryViewFromBinaryString(correctBinaryMessage);
        String remove = correctBinaryMessage.substring(0, correctBinaryMessage.length() - correctBinaryMessage.length() % 8);
        System.out.print("remove: ");
        this.printBinaryViewFromBinaryString(remove);
        this.printHexViewFromBinary(remove);
        StringBuilder finalText = new StringBuilder();
        for (int i = 0; i < remove.length(); i = i + 8) {
            finalText.append((char) Integer.parseInt(remove.substring(i, i + 8), 2));
        }
        System.out.print("text view: " + finalText);

        this.writeTextViewToFile(decodedFileName, finalText.toString());
    }*/

    void decodeMessageByHamming() {

        byte[] messageInBytes = this.readFileToByteArray(receivedFileName);
        this.printHexViewFromBytes(messageInBytes);
        String binaryStringAfterRead = this.printBinaryViewFromEncodedBytes(messageInBytes);

        System.out.println("\n" + decodedFileName + ":");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < binaryStringAfterRead.length(); i = i + 8) {
            char first = binaryStringAfterRead.charAt(i);
            char second = binaryStringAfterRead.charAt(i + 1);
            char third = binaryStringAfterRead.charAt(i + 2);
            char fourth = binaryStringAfterRead.charAt(i + 3);
            char fifth = binaryStringAfterRead.charAt(i + 4);
            char sixth = binaryStringAfterRead.charAt(i + 5);
            char seventh = binaryStringAfterRead.charAt(i + 6);

            boolean one = first == '1';
            boolean two = second == '1';
            boolean three = third == '1';
            boolean four = fourth == '1';
            boolean five = fifth == '1';
            boolean six = sixth == '1';
            boolean seven = seventh == '1';

            boolean checkFirstParity = three ^ five ^ seven;
            boolean checkSecondParity = three ^ six ^ seven;
            boolean checkThirdParity = five ^ six ^ seven;

            int positionOfMistake = i;
            if (checkFirstParity != one) {
                positionOfMistake += 1;
            }
            if (checkSecondParity != two) {
                positionOfMistake += 2;
            }
            if (checkThirdParity != four) {
                positionOfMistake += 4;
            }
            positionOfMistake -= 1;


            for (int k = i; k < i + 8; k++) {
                if (positionOfMistake > i - 1 & positionOfMistake == k) {
                    if (binaryStringAfterRead.charAt(positionOfMistake) == '1') {
                        sb.append("0");
                    } else {
                        sb.append("1");
                    }
                } else {
                    sb.append(binaryStringAfterRead.charAt(k));
                }
            }
        }

        String correctParityBinaryMessage = (sb + "").trim();
        System.out.println("correct: " + correctParityBinaryMessage);
        correctParityBinaryMessage = correctParityBinaryMessage.replace(" ", "");

        StringBuilder sb2 = new StringBuilder();
        for (int j = 0; j < correctParityBinaryMessage.length(); j = j + 8) {
            char firstCorrectBit = correctParityBinaryMessage.charAt(j + 2);
            char secondCorrectBit = correctParityBinaryMessage.charAt(j + 4);
            char thirdCorrectBit = correctParityBinaryMessage.charAt(j + 5);
            char fourthCorrectBit = correctParityBinaryMessage.charAt(j + 6);

            sb2.append(firstCorrectBit).append(secondCorrectBit).append(thirdCorrectBit).append(fourthCorrectBit);
        }
        String correctBinaryMessage = sb2 + "";
        System.out.print("decode: ");
        this.printBinaryViewFromBinaryString(correctBinaryMessage);
        String remove = correctBinaryMessage.substring(0, correctBinaryMessage.length() - correctBinaryMessage.length() % 8);
        System.out.print("remove: ");
        this.printBinaryViewFromBinaryString(remove);
        this.printHexViewFromBinary(remove);
        StringBuilder finalText = new StringBuilder();
        for (int m = 0; m < remove.length(); m = m + 8) {
            finalText.append((char) Integer.parseInt(remove.substring(m, m + 8), 2));
        }
        System.out.print("text view: " + finalText);

        this.writeTextViewToFile(decodedFileName, finalText.toString());
    }
}
