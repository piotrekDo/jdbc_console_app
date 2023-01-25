package app.io;

import java.util.Scanner;

public class InputCollector {
    Scanner input;

    /**
     * Method will collect numeric user input and return and integer.
     *
     * @param range argument representing available options subtracted by 1.
     */

    public int getNumericInput(int range) {
        input = new Scanner(System.in);
        int userInput = -1;
        do {
            try {
                userInput = input.nextInt();
            } catch (Exception ignored) {
            } finally {
                input.nextLine();
            }
            if (userInput >= 0 && userInput < range) return userInput;
            else {
                System.out.println("Niepoprawna wartość");
                System.out.print("Wybierz: ");
            }
        } while (userInput != 0);
        input.close();
        return userInput;
    }
}
