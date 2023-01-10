package app;

import java.util.LinkedList;
import java.util.List;

public class TableOperations {

    private final ConsolePrinter consolePrinter;
    private final InputCollector inputCollector;
    private final Service service;
    private final String tableName;

    public TableOperations(ConsolePrinter consolePrinter, InputCollector inputCollector, Service service, String tableName) {
        this.consolePrinter = consolePrinter;
        this.inputCollector = inputCollector;
        this.service = service;
        this.tableName = tableName;
    }

    public void loadOptions() {
        boolean running = true;
        do {
            consolePrinter.print(tableName, getOptions());
            System.out.print("Wybierz: ");
            int userInput = inputCollector.getNumericInput(getOptions().size() - 1);
            TableOperationsMenu option = TableOperationsMenu.values()[userInput];
            switch (option) {

                case EXIT -> {
                    running = false;
                }
                case LOAD -> {
                    new LoadDataFromTable(consolePrinter, inputCollector, service, tableName).load();
                }
            }
        } while (running);
    }

    private List<String> getOptions() {
        LinkedList<String> options = new LinkedList<>();
        for (int i = 1; i < TableOperationsMenu.values().length; i++) {
            TableOperationsMenu option = TableOperationsMenu.values()[i];
            options.add(option.ordinal() + " " + option.desc);
        }
        options.add("");
        options.add(TableOperationsMenu.values()[0].ordinal() + " " + TableOperationsMenu.values()[0].desc);
        return options;
    }

    private enum TableOperationsMenu {
        EXIT("Powrót"),
        LOAD("Pobierz dane");

        private final String desc;

        public String getDesc() {
            return desc;
        }

        TableOperationsMenu(String desc) {
            this.desc = desc;
        }
    }
}
