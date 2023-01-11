package app;

import customer.CustomerPrinterPage;

import java.util.Arrays;


public class LoadDataFromTable {

    private final ConsolePrinter consolePrinter;
    private final InputCollector inputCollector;
    private final Service service;
    private final String tableName;

    public LoadDataFromTable(ConsolePrinter consolePrinter, InputCollector inputCollector, Service service, String tableName) {
        this.consolePrinter = consolePrinter;
        this.inputCollector = inputCollector;
        this.service = service;
        this.tableName = tableName;
    }

    void load() {
        boolean runnning = true;
        int offset = 0;
        int elements = 20;
        do {
            CustomerPrinterPage customerPrinterData = service.loadDataFromTable(tableName, offset, elements);
            consolePrinter.printCustomers(tableName, customerPrinterData);
            consolePrinter.print(tableName, Arrays.stream(getOptions()).toList());
            System.out.print("Wybierz: ");
            int userInput = inputCollector.getNumericInput(getOptions().length);
            Options option = Options.values()[userInput];
            switch (option) {

                case EXIT -> {
                    runnning = false;
                }
                case NEXT -> {
                    offset += elements;
                }
                case PREV -> {
                    offset = Math.max(offset - elements, 0);
                }
            }


        } while (runnning);
    }

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
        PREV("Poprzednia strona");

        private final String desc;

        Options(String desc) {
            this.desc = desc;
        }
    }
}
