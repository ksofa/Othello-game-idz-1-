import java.util.Objects;

public class Print extends Table {

    public static void count(boolean isMultiPlayer) {
        int b = 0;
        int w = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (Objects.equals(board[i][j], "W")) {
                    ++w;
                } else if (Objects.equals(board[i][j], "B")) {
                    ++b;
                }
            }
        }
        System.out.println("Black: " + b + "| White: " + w);
        if (b > w) {
            System.out.println(isMultiPlayer ? "Black wins!" : "You win!");
        } else if (b == w) {
            System.out.println("Draw!");
        } else {
            System.out.println(isMultiPlayer ? "White wins!" : "You lost");
        }
    }
    public static void printAllAvailableNextSteps(boolean[][] boardAvailable) {
        System.out.print( "Your possible steps: ");
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (boardAvailable[i][j]) System.out.print("(" + (i + 1) + ", " + (j + 1) + "), ");
            }
        }
    }

    public static void printMaxCounts() {
        System.out.println("Maximum Black: " + max_res_B + " | Maximum White: " + max_res_W);
    }
}
