package app;

import java.util.ArrayList;

public class DisplayAvalibleTables {

    private final ConsolePrinter consolePrinter;
    private final InputCollector inputCollector;
    private final Service service;

    /**
     * Object created when tables data was loaded. Responsible for displaying all tables
     * @param consolePrinter class responsible for displaying data in user terminal,
     * @param inputCollector class responsible for collecting data from user,
     * @param service utility class containing service method to work with data.
     */

    public DisplayAvalibleTables(ConsolePrinter consolePrinter, InputCollector inputCollector, Service service) {
        this.consolePrinter = consolePrinter;
        this.inputCollector = inputCollector;
        this.service = service;
    }

    /**
     * Main method responsible for displaying available tables and collect user input.
     */

    void loadOptions() {
        ArrayList<String> allTables = service.getAllTableNames();

        boolean running = true;
        do {
            consolePrinter.print("Dostępne tabele", allTables);
            System.out.print("Wybierz: ");
            int userInput = inputCollector.getNumericInput(allTables.size() -1);
            if (userInput == 0) running = false;
            else {
                String userChoice = allTables.get(userInput -1).substring(2);
                new TableOperations(consolePrinter, inputCollector, service, userChoice).loadOptions();
            }
        } while (running);
    }

}
