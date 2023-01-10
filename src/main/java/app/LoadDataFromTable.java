package app;

import customer.CustomerDTO;

import java.util.List;

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
            List<String> customerDTOS = service.loadDataFromTable(tableName, offset, elements);
            consolePrinter.print(tableName, customerDTOS);
            runnning = false;
        } while (runnning);
    }
}
