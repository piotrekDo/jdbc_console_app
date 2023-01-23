package app;

import java.util.LinkedList;
import java.util.List;

public class TableOperations {

    private final ConsolePrinter consolePrinter;
    private final InputCollector inputCollector;
    private final Service service;
    private final String tableName;

    /**
     * Object created when table was selected. Responsible for displaying table operations such as selecting data,
     * editing, ect.
     *
     * @param consolePrinter class responsible for displaying data in user terminal,
     * @param inputCollector class responsible for collecting data from user,
     * @param service utility class containing service method to work with data,
     * @param tableName selected table name
     */

    public TableOperations(ConsolePrinter consolePrinter, InputCollector inputCollector, Service service, String tableName) {
        this.consolePrinter = consolePrinter;
        this.inputCollector = inputCollector;
        this.service = service;
        this.tableName = tableName;
    }

    /**
     * Main method responsible for displaying all possible actions. Based on TableOperationsMenu enum.
     */

    public void loadOptions() {
        boolean running = true;
        do {
            consolePrinter.print(tableName, getOptions());
            LinkedList<ColumnDetails> columnsDetails = service.fetchTableDetails(tableName);
            System.out.print("Wybierz: ");
            int userInput = inputCollector.getNumericInput(getOptions().size() - 1);
            TableOperationsMenu option = TableOperationsMenu.values()[userInput];
            switch (option) {

                case EXIT -> {
                    running = false;
                }
                case LOAD -> {
                    new LoadDataFromTable(consolePrinter, inputCollector, service, tableName, columnsDetails).load();
                }
            }
        } while (running);
    }

    /**
     * Auxiliary method used to retrieve description field from TableOperationsMenu enum.
     * Description data is needed in ConsolePrinter in order to display further options.
     */

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
