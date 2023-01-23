package app;

import java.util.Arrays;
import java.util.LinkedList;

public class LoadDataFromTable {


    private final ConsolePrinter consolePrinter;
    private final InputCollector inputCollector;
    private final Service service;
    private final String tableName;
    private final LinkedList<ColumnDetails> columnsDetails;

    /**
     * Object created when table was selected, and data is loaded. Responsible for load data and managing
     * pagination and sorting options
     *
     * @param consolePrinter class responsible for displaying data in user terminal,
     * @param inputCollector class responsible for collecting data from user,
     * @param service        utility class containing service method to work with data,
     * @param tableName      selected table name,
     * @param columnsDetails collection of ColumnDetails object representing metadata of each column.
     */

    public LoadDataFromTable(ConsolePrinter consolePrinter, InputCollector inputCollector, Service service, String tableName, LinkedList<ColumnDetails> columnsDetails) {
        this.consolePrinter = consolePrinter;
        this.inputCollector = inputCollector;
        this.service = service;
        this.tableName = tableName;
        this.columnsDetails = columnsDetails;
    }

    /**
     * Main method responsible for loading data and presenting further options.
     */

    void load() {
        boolean isRunning = true;
        int offset = 0;
        int elements = 50;
        String sortyBy = columnsDetails.get(0).getColumnName();
        boolean isDescending = false;
        do {
            DataPage dataPage = service.loadDataFromTable(tableName, columnsDetails, offset, elements, sortyBy, isDescending);
            consolePrinter.printTable(dataPage, tableName);
            consolePrinter.print(tableName, Arrays.stream(getOptions()).toList());
            System.out.print("Wybierz: ");
            int userInput = inputCollector.getNumericInput(getOptions().length);
            Options option = Options.values()[userInput];

            switch (option) {

                case EXIT -> {
                    isRunning = false;
                }
                case NEXT -> {
                    offset += elements;
                }
                case PREV -> {
                    offset = Math.max(offset - elements, 0);
                }
                case SORT -> {
                    Sort sort = new SortOptions(consolePrinter, inputCollector, columnsDetails, tableName).load(sortyBy, isDescending);
                    sortyBy = sort.getSortBy();
                    isDescending = sort.isDescending();
                }
            }
        } while (isRunning);
    }

    /**
     * Auxiliary method responsible for extracting description from Options enum.
     * Returned array is needed in ConsolePrinter in order to display further options.
     */

    private String[] getOptions() {
        String[] options = new String[Options.values().length];
        for (int i = 1; i < Options.values().length; i++) {
            Options option = Options.values()[i];
            options[i - 1] = option.ordinal() + " " + option.desc;
        }

        options[options.length - 1] = Options.values()[0].ordinal() + " " + Options.values()[0].desc;
        return options;
    }

    private enum Options {
        EXIT("Powrót"),
        NEXT("Kolejna strona"),
        PREV("Poprzednia strona"),
        SORT("Zmień sortowanie");

        private final String desc;

        Options(String desc) {
            this.desc = desc;
        }
    }
}
