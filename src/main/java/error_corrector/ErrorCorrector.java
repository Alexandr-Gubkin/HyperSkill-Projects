package error_corrector;

import java.util.Scanner;

public class ErrorCorrector {

    public static void main(String[] args) {
        Procedure procedure = new Procedure();
        Scanner sc = new Scanner(System.in);
        System.out.print("Write a mode: ");
        String mode = sc.next();
        switch (mode) {
            case "encode":
                procedure.encodedMessageByHamming();
                break;
            case "send" :
                procedure.sendMessage();
                break;
            case "decode" :
                procedure.decodeMessageByHamming();
                break;
        }
    }
}