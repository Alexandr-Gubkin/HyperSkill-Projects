package TicTacToe;

import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] str = "         ".split("");
        String[][] m = new String[3][3];
        System.out.println("---------");
        int k = 0;
        for (int y = 0; y < 3; y++) {
            System.out.print("| ");
            for (int x = 0; x < 3; x++) {
                System.out.print(str[k] + " ");
                m[y][x] = str[k];
                k++;
            }
            System.out.println("|");
        }
        System.out.println("---------");
        String[] str2;
        String firstMove;
        String secondMove;
        String X_O = "X";
        int count = 0;
        String result;
        while (true) {
            System.out.print("Enter the coordinates: ");
            str2 = sc.nextLine().split(" ");
            firstMove = str2[0];
            if (!firstMove.matches("[\\d]")) {
                System.out.println("You should enter numbers!");
                continue;
            }
            secondMove = str2[1];
            if (!secondMove.matches("[\\d]")) {
                System.out.println("You should enter numbers!");
                continue;
            }
            if (!firstMove.matches("[123]") | !secondMove.matches("[123]")) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
            int first = Integer.parseInt(firstMove);
            int second = Integer.parseInt(secondMove);
            if (m[3 - second][first - 1].matches("[XO]")) {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            } else {
                switch (X_O) {
                    case "X" -> {
                        m[3 - second][first - 1] = "X";
                        X_O = "O";
                    }
                    case "O" -> {
                        m[3 - second][first - 1] = "O";
                        X_O = "X";
                    }
                }
                count++;
                System.out.println("---------");
                for (int y = 0; y < 3; y++) {
                    System.out.print("| ");
                    for (int x = 0; x < 3; x++) {
                        System.out.print(m[y][x] + " ");
                    }
                    System.out.println("|");
                }
                System.out.println("---------");
            }
            if (x_Wins(m) & !o_Wins(m)) {
                result = "X wins";
                System.out.println(result);
                break;
            } else if (o_Wins(m) & !x_Wins(m)) {
                result = "O wins";
                System.out.println(result);
                break;
            } else if ((count == 9) & (!o_Wins(m)) & (!x_Wins(m))) {
                result = "Draw";
                System.out.println(result);
                break;
            }
        }
    }

    // X Wins
    public static boolean x_Wins(String[][] m) {
        boolean flag = false;
        if (m[1][1].equals("X")) {
            if (m[0][0].equals("X") & m[2][2].equals("X") |
                    m[0][1].equals("X") & m[2][1].equals("X") |
                    m[0][2].equals("X") & m[2][0].equals("X") |
                    m[1][0].equals("X") & m[1][2].equals("X")) {
                flag = true;
            }
        } else if (m[0][0].equals("X")) {
            if (m[0][1].equals("X") & m[0][2].equals("X") |
                    (m[1][0].equals("X") & m[2][0].equals("X"))) {
                flag = true;
            }
        } else if (m[2][2].equals("X")) {
            if (m[0][2].equals("X") & m[1][2].equals("X") |
                    m[2][0].equals("X") & m[2][1].equals("X")) {
                flag = true;
            }
        }
        return flag;
    }

    // O Wins
    public static boolean o_Wins(String[][] m) {
        boolean flag = false;
        if (m[1][1].equals("O")) {
            if (m[0][0].equals("O") & m[2][2].equals("O") |
                    m[0][1].equals("O") & m[2][1].equals("O") |
                    m[0][2].equals("O") & m[2][0].equals("O") |
                    m[1][0].equals("O") & m[1][2].equals("O")) {
                flag = true;
            }
        } else if (m[0][0].equals("O")) {
            if (m[0][1].equals("O") & m[0][2].equals("O") |
                    (m[1][0].equals("O") & m[2][0].equals("O"))) {
                flag = true;
            }
        } else if (m[2][2].equals("O")) {
            if (m[0][2].equals("O") & m[1][2].equals("O") |
                    m[2][0].equals("O") & m[2][1].equals("O")) {
                flag = true;
            }
        }
        return flag;
    }

    //Игра не закончена
    public static boolean gameNotFinished(String[][] m) {
        boolean flag = false;
        for (String[] s : m) {
            for (String s2 : s) {
                if (s2.equals("_") | s2.equals(" ")) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    //Impossible
    public static boolean impossible(String[][] m) {
        boolean flag = false;
        int x_count = 0;
        int o_count = 0;
        for (String[] s : m) {
            for (String s2 : s) {
                if (s2.equals("X")) {
                    x_count++;
                } else if (s2.equals("O")) {
                    o_count++;
                }
            }
            flag = Math.abs(x_count - o_count) >= 2;
        }
        return flag;
    }
}
