package app;

import customer.CustomerPrinterData;


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
            CustomerPrinterData customerPrinterData = service.loadDataFromTable(tableName, offset, elements);
            consolePrinter.printCustomers(tableName, customerPrinterData);
            runnning = false;
        } while (runnning);
    }
}
