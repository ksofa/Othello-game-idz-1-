import java.util.ArrayList;

public class Table {
    public static int max_res_B = 0;
    public static int max_res_W = 0;
    public static String[][] board = new String[8][8];

    public static ArrayList<String[][]> board_memory = new ArrayList<>();

    public static void add_to_memory() {
        String[][] iboard = new String[8][8];
        for (int i = 0; i < 8; ++i) {
            System.arraycopy(board[i], 0, iboard[i], 0, 8);
        }
        board_memory.add(iboard);
    }
    public static void cancel_move() {
        int last = board_memory.size()-1;
        if (last <= 0) {
            System.out.println("Error. Please repeat");
            return;
        }
        System.out.println("Your last step deleted.Please repeat");
        board_memory.remove(last);
        board = board_memory.get(--last);
    }

    public static void run_board(String[][] board) {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j <8; ++j) {
                if (i == 3 && j == 3 || i == 4 && j == 4) {
                    board[i][j] = "W";
                } else if (i == 3 && j == 4 || i == 4 && j == 3) {
                    board[i][j] = "B";
                } else {
                    board[i][j] = "-";
                }
            }
        }
    }
    public static void printBoard(String[][] board) {
        System.out.println("+" + "  1  2  3  4  5  6  7  8 ");
        for (int i = 0; i < 8; ++i) {
            System.out.print((i+1)+ " ");
            for (int j = 0; j < 8; ++j) {
                if (board[i][j].equals("W")) {
                    System.out.print(" " + board[i][j] + " ");
                } else if (board[i][j].equals("B")) {
                    System.out.print(" " + board[i][j] + " ");
                } else {
                    System.out.print(" " + board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public static void printboardste(String[][] board, boolean[][] boardAvailable) {
        System.out.println("+" + "  1  2  3  4  5  6  7  8 " );
        for (int i = 0; i < 8; ++i) {
            System.out.print( (i+1) + " ");
            for (int j = 0; j < 8; ++j) {
                if (boardAvailable[i][j]) {
                    System.out.print(" " + "*" + " ");
                    continue;
                }
                if (board[i][j].equals("W")) {
                    System.out.print(" " + board[i][j] + " ");
                } else if (board[i][j].equals("B")) {
                    System.out.print(" " + board[i][j]  + " ");
                } else {
                    System.out.print(" " + board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}
