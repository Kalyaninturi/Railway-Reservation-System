package railwayreservation;

import java.util.Scanner;

public class InputHelper {

    private static final Scanner SCANNER = new Scanner(System.in);

    public int readInt() {

        while (true) {
            try {
                return Integer.parseInt(SCANNER.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print(
                        "Invalid input. Enter a whole number: ");
            }
        }
    }

    public String readText() {
        return SCANNER.nextLine().trim();
    }
}