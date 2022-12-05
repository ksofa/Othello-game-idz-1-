import java.util.Objects;

public class Logic extends Table {
    public static void mode1_human_vs_bot(boolean levelflag) {
        System.out.println("Black");
        run_board(board);
        add_to_memory();
        boolean[][] flag_show;
        while (true) {
            if (isFinish(true, false)) {
                System.out.println("End");
                break;
            }
            flag_show = showAllAvailableSteps("B", true);
            boolean isReturnToLastMove = userMove(flag_show, true);
            if (isReturnToLastMove) {
                String[][] boardClone = new String[8][8];
                for (int i = 0; i < 8; ++i) {
                    System.arraycopy(board_memory.get(board_memory.size() - 1)[i], 0, boardClone[i], 0, 8);
                }
                board = boardClone;
                continue;
            }
            new_max_scores();
            if (isFinish(false, false)) {
                System.out.println("End!");
                break;
            }
            flag_show = showAllAvailableSteps("W", false);
            computerMove(flag_show, levelflag);
            new_max_scores();
            add_to_memory();
        }
    }

    public static void playMode2() {
        mode1_human_vs_bot(true);
    }

    public static void playMode3() {
        run_board(board);
        boolean[][] availableStepsTable;
        while (true) {
            if (isFinish(true, true)) {  // true, т.к. ходит пользователь
                System.out.println("End!");
                break;
            }
            System.out.println("Black");
            availableStepsTable = showAllAvailableSteps("B", true);
            userMove(availableStepsTable, false);
            new_max_scores();
            System.out.println("White");
            availableStepsTable = showAllAvailableSteps("W", true);
            userMove(availableStepsTable, false);
            new_max_scores();
            if (isFinish(false, true)) {
                System.out.println("End!");
                break;
            }
            add_to_memory();
        }
    }

    public static boolean isFinish(boolean userStep, boolean hvsh) {
        int k = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (board[i][j].equals("B") || board[i][j].equals("W")) {
                    ++k;
                }
            }
        }
        if (k == 8 * 8) {
            Table.printBoard(Table.board);
            Print.count(hvsh);
            Print.printMaxCounts();
            return true;
        }

        int counterBlack = 0, counterWhite = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (Objects.equals(board[i][j], "B")) {
                    ++counterBlack;
                } else if (Objects.equals(board[i][j], "W")) {
                    ++counterWhite;
                }
            }
        }
        if (counterBlack == 0 || counterWhite == 0) {
            Print.count(hvsh);
            Print.printMaxCounts();
            return true;
        }
        boolean canUserMakeMove = true, canComputerMakeMove = true;
        boolean[][] availableSteps = showAllAvailableSteps("B", false);
        if (!haveAnyAvailableSteps(availableSteps)) {
            System.out.println("Can not move. Next bot");
            canUserMakeMove = false;
        }
        availableSteps = showAllAvailableSteps("W", false);
        if (!haveAnyAvailableSteps(availableSteps)) {
            System.out.println("Can not move. Next human");
        }
        return false;
    }

    public static boolean haveAnyAvailableSteps(boolean[][] arr) {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (arr[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private static boolean userMove(boolean[][] availableSteps, boolean canselflag) {
        int x, y;
        while (true) {
            System.out.println("\nWrite coordinates: x y ");
            String input = Main.inp.next();
            if (input.equals("q") && canselflag) {
                cancel_move();
                return true;
            }
            while (!isNumeric(input)) {
                System.out.println("Write number");
                input = Main.inp.next();
            }
            x = Integer.parseInt(input);
            input = Main.inp.next();
            while (!isNumeric(input)) {
                System.out.println("Write number");
                input = Main.inp.next();
            }
            y = Integer.parseInt(input);
            if (x < 1 || x > 8 || y < 1 || y > 8) {
                System.out.println("Error. Try again");
                continue;
            }
            if (availableSteps[x-1][y-1]) {
                board[x-1][y-1] = "B";
                R(x-1, y-1, true, "B");
                break;
            } else {
                System.out.println("Error. Try again");
            }
        }
        return false;
    }

    private static void computerMove(boolean[][] arr, boolean flag_adv) {
        if (!haveAnyAvailableSteps(arr)) {
            System.out.println("Your step");
            return;
        }
        int x = -1, y = -1;
        double maxR = -999;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                double tempR;
                tempR = R(i, j, false, "W");
                if (flag_adv) {
                    tempR = advancedR(tempR, i, j);
                }
                if (arr[i][j] && tempR > maxR) {
                    x = i;
                    y = j;
                    maxR = tempR;
                }
            }
        }
        board[x][y] = "W";
        R(x, y, true, "W");
        System.out.println("Enemy step - x: " + (x+1) + " y: " + (y+1));
        System.out.println("Your step!");
    }
    public static boolean isCellFree(int i, int j) {
        return (i < 8 && i >= 0 && j < 8 && j >= 0 && board[i][j].equals("-"));
    }
    public static int cellWeight(int i, int j) {
        if (i == 7 || j == 7) {
            return 2;
        }
        return 1;
    }
    public static double cellWeightSS(int i, int j) {
        if (i == 8 - 1 && j == 8 - 1) {
            return 0.8;
        } else if (i == 8 - 1 || j == 8 - 1) {
            return 0.4;
        }
        return 0;
    }
    private static double R (int x, int y, boolean flag, String color) {
        double Si = 0;
        String color_2;
        if (color.equals("B")) {
            color_2 = "W";
        } else {
            color_2 = "B";
        }
        boolean b1 = false;
        {
            for (int i = 0; i < x; ++i) {
                if (!b1 && board[i][y].equals(color)) {
                    b1 = true;
                } else if (b1 && board[i][y].equals(color_2)) {
                    if (flag) {
                        board[i][y] = color;
                    }
                    Si += cellWeight(i, y);
                }

            }
            b1 = false;
        }

        {
            for (int i = 8 - 1; i > x; --i) {
                if (!b1 && board[i][y].equals(color)) {
                    b1 = true;
                } else if (b1 && board[i][y].equals(color_2)) {
                    if (flag) {
                        board[i][y] = color;
                    }
                    Si += cellWeight(i, y);
                }
            }
            b1 = false;
        }
        {
            for (int j = 0; j < y; ++j) {
                if (!b1 && board[x][j].equals(color)) {
                    b1 = true;
                } else if (b1 && board[x][j].equals(color_2)) {
                    // ЗАМЫКАНИЕ
                    if (flag) {
                        board[x][j] = color;
                    }
                    Si += cellWeight(x, j);
                }

            }
            b1 = false;
        }
        {
            for (int j = 8 - 1; j > y; --j) {
                if (!b1 && board[x][j].equals(color)) {
                    b1 = true;
                } else if (b1 && board[x][j].equals(color_2)) {
                    if (flag) {
                        board[x][j] = color;
                    }
                    Si += cellWeight(x, j);
                }

            }
            b1 = false;
        }
        {
            int ofs = 0;
            while ((x-ofs) < 8 && (x-ofs) >= 0 && (y-ofs) < 8 && (y-ofs) >= 0) {
                ++ofs;
            }
            --ofs;
            int xBorder = x - ofs, yBorder = y + ofs;

            for (int i = xBorder, j = yBorder; i < x && j > y && j < 8; ++i, --j) {
                if (!b1 && board[i][j].equals(color)) {
                    b1 = true;
                } else if (b1 && board[i][j].equals(color_2)) {
                    if (flag) {
                        board[i][j] = color;
                    }
                    Si += cellWeight(i, j);
                }
            }
        }

        {
            int oset = 0;
            while ((x-oset) < 8 && (x-oset) >= 0 && (y-oset) < 8 && (y-oset) >= 0) {
                ++oset;
            }
            --oset;
            int xBorder = x - oset, yBorder = y - oset;
            for (int i = xBorder, j = yBorder; i < x && j < y; ++i, ++j) {
                if (!b1 && board[i][j].equals(color)) {
                    b1 = true;
                } else if (b1 && board[i][j].equals(color_2)) {
                    if (flag) {
                        board[i][j] = color;
                    }
                    Si += cellWeight(i, j);
                }
            }
        }

        {
            int b = 0;
            while ((x + b) < 8 && (x + b) >= 0 && (y-b) < 8 && (y-b) >= 0) {
                ++b;
            }
            --b;
            int xB = x + b, yB = y - b;
            for (int i = xB, j = yB; i > x && j < y; --i, ++j) {
                if (!b1 && board[i][j].equals(color)) {
                    b1 = true;
                } else if (b1 && board[i][j].equals(color_2)) {
                    if (flag) {
                        board[i][j] = color;
                    }
                    Si += cellWeight(i, j);
                }
            }
        }

        {
            int offset = 0;
            while ((x + offset) < 8 && (x + offset) >= 0 && (y+offset) < 8 && (y+offset) >= 0) {
                ++offset;
            }
            --offset;
            int xBorder = x + offset, yBorder = y + offset;
            for (int i = xBorder, j = yBorder; i > x && j > y; --i, --j) {
                if (!b1 && board[i][j].equals(color)) {
                    b1 = true;
                } else if (b1 && board[i][j].equals(color_2)) {
                    if (flag) {
                        board[i][j] = color;
                    }
                    Si += cellWeight(i, j);
                }
            }
        }

        Si += cellWeightSS(x, y);
        return Si;
    }
    private static double advancedR(double resultR, int x, int y) {
        double maxR = -1000;
        String tempChange = board[x][y];
        board[x][y] = "W";
        boolean[][] availableStepsTable = showAllAvailableSteps("B", false);
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (availableStepsTable[i][j]) {
                    double tempR = R(i, j, false, "B");
                    if (tempR > maxR) {
                        maxR = tempR;
                    }
                }
            }
        }
        board[x][y] = tempChange;
        return resultR - maxR;
    }
    public static boolean[][] showAllAvailableSteps(String color, boolean flagprint) {
        String col;
        if (color.equals("B")) {
            col = "W";
        } else {
            col = "B";
        }
        boolean[][] boardAvailable = new boolean[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                boardAvailable[i][j] = false;
            }
        }

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (board[i][j].equals(col)) {

                    if (isCellFree(i - 1, j - 1) && !boardAvailable[i - 1][j - 1]) {
                        boardAvailable[i-1][j-1] = true;
                    }
                    if (isCellFree(i, j - 1) && !boardAvailable[i][j - 1]) {
                        boardAvailable[i][j-1] = true;
                    }
                    if (isCellFree(i + 1, j - 1) && !boardAvailable[i + 1][j - 1]) {
                        boardAvailable[i+1][j-1] = true;
                    }
                    if (isCellFree(i - 1, j) && !boardAvailable[i - 1][j]) {
                        boardAvailable[i-1][j] = true;
                    }
                    if (isCellFree(i + 1, j) && !boardAvailable[i + 1][j]) {
                        boardAvailable[i+1][j] = true;
                    }

                    if (isCellFree(i - 1, j+1) && !boardAvailable[i - 1][j+1]) {
                        boardAvailable[i-1][j+1] = true;
                    }
                    if (isCellFree(i, j+1) && !boardAvailable[i][j+1]) {
                        boardAvailable[i][j+1] = true;
                    }
                    if (isCellFree(i+1, j+1) && !boardAvailable[i+1][j+1]) {
                        boardAvailable[i+1][j+1] = true;
                    }

                }
            }
        }

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                int myInt = boardAvailable[i][j] ? 1 : 0;
                if (R(i, j, false, color) >= 1 && myInt == 1) {
                    myInt = 1;
                } else {
                    myInt = 0;
                }
                boardAvailable[i][j] = myInt == 1;
            }
        }

        if (flagprint) {
            printboardste(board, boardAvailable);
            Print.printAllAvailableNextSteps(boardAvailable);
        }
        return boardAvailable;
    }

    private static void new_max_scores() {
        int w_cur = 0;
        int b_cur = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (board[i][j].equals("W")) {
                    ++w_cur;
                } else if (board[i][j].equals("B")) {
                    ++b_cur;
                }
            }
        }
        if (max_res_W < w_cur) {
            max_res_W = w_cur;
        }
        if (max_res_B < b_cur) {
            max_res_B = b_cur;
        }
    }
}

