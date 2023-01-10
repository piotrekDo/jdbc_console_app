package app;

import java.util.ArrayList;

public class LoadOptions {

    private final ConsolePrinter consolePrinter;
    private final InputCollector inputCollector;
    private final Service service;

    public LoadOptions(ConsolePrinter consolePrinter, InputCollector inputCollector, Service service) {
        this.consolePrinter = consolePrinter;
        this.inputCollector = inputCollector;
        this.service = service;
    }

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
