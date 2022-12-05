import java.util.Scanner;
import static java.lang.Integer.parseInt;

public class Menu {
    public static int menu(Scanner inp) {
        while (true) {
            System.out.println("Choose mode");

            System.out.println( "1) Easy level ( human vs bot) + you can delete your step\n"+
                    "2) Hard level ( human vs hard_bot) + you can delete your step\n"+
                    "3) Human vs Human + no cancel\n"+
                    "To delete your step you can press 'q' ");

            int mode = parseInt(inp.next());

            if (mode == 1 || mode == 2 || mode == 3) {
                return mode;
            } else {
                System.out.println("Wrong mode. Repeat");
            }
        }
    }
}
