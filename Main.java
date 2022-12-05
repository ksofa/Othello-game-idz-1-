import java.util.Scanner;

public class Main extends Table {
    public static Scanner inp = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println( "Welcome to Othello(Reversi)!");
        int gameMode = Menu.menu(inp);
        if (gameMode == 1) {
            Logic.mode1_human_vs_bot(false);
        }
        if (gameMode == 2) {
            Logic.playMode2();
        }
        if (gameMode == 3) {
            Logic.playMode3();
        }
    }
}