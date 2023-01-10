import java.util.Scanner;

public class InputCollector {
    Scanner input = new Scanner(System.in);

    int getInput(int range) {
        int userInput = -1;

        do {
            try {
                userInput = input.nextInt();
            } catch (Exception e) {
            } finally {
                input.nextLine();
            }
            if (userInput > 0 && userInput < range) return userInput;
            else if (userInput == 0);
            else {
                System.out.println("Niepoprawna wartość");
                System.out.print("Wybierz: ");
            }
        } while (userInput != 0);

        return 0;
    }
}
