package converter;

import java.util.Scanner;

public class Converter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int radixOfNum = Integer.parseInt(sc.nextLine());
        String[] numInString = sc.nextLine().split("\\.");
        int radixTarget = Integer.parseInt(sc.nextLine());
        long num;
        StringBuilder numTargetInt = new StringBuilder();
        //Преобразование целой части
        if (!numInString[0].equals("0")) {
            if (radixOfNum == 1) {
                num = numInString[0].length();
            } else {

                num = Long.parseLong(numInString[0], radixOfNum);
            }
            if (radixTarget == 1) {
                for (int i = 0; i < num; i++) {
                    numTargetInt.append(1);
                }
            } else {
                numTargetInt.append(Long.toString(num, radixTarget));
            }
        } else {
            numTargetInt.append("0");

        }
        System.out.print(numTargetInt);


        //Преобразование дробной части
        StringBuilder numTargetFract = new StringBuilder();
        double tempDeciFract = 0;
        if (numInString.length > 1) {
            if (radixOfNum != 10) {
                for (int i = 0; i < numInString[1].length(); i++) {
                    if (!Character.isDigit(numInString[1].charAt(i))) {
                        int n = ((int) numInString[1].charAt(i)) - 87;
                        tempDeciFract = tempDeciFract + n / Math.pow(radixOfNum, i + 1);
                    } else {
                        tempDeciFract = tempDeciFract + Long.parseLong(String.valueOf(numInString[1].charAt(i))) /
                                Math.pow(radixTarget, i + 1);
                    }
                }
            } else {
                tempDeciFract = Double.parseDouble("0." + numInString[1]);
            }
            for (int i = 0; i < 5; i++) {
                double p = tempDeciFract * radixTarget;
                long p0 = Long.parseLong(String.valueOf(p).split("\\.")[0]);
                tempDeciFract = Double.parseDouble("0." + String.valueOf(p).split("\\.")[1]);

                if (p0 > 9) {
                    numTargetFract.append(Character.valueOf((char) ((int) p0 + 87)));
                } else {
                    numTargetFract.append(p0);
                }
            }
            String fract = numTargetFract + "";
            //Вывод
            if (radixOfNum != 1 & radixTarget != 1) {
                System.out.print("." + fract);
            }
        }
    }
}