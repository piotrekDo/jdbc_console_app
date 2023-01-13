package app;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SortOptions {

    private final ConsolePrinter consolePrinter;
    private final InputCollector inputCollector;
    private final LinkedList<TableDetails> tableDetails;
    private final String tableName;


    public SortOptions(ConsolePrinter consolePrinter, InputCollector inputCollector, LinkedList<TableDetails> tableDetails, String tableName) {
        this.consolePrinter = consolePrinter;
        this.inputCollector = inputCollector;
        this.tableDetails = tableDetails;
        this.tableName = tableName;
    }

    Sort load(String sortyBy, boolean isDescending) {
        List<String> strings = tableDetails.stream().map(TableDetails::getTableName).collect(Collectors.toList());
        LinkedList<String> strings1 = new LinkedList<>();
        for (int i = 0; i < strings.size(); i++) {
            strings1.add(i + 1 + " " + strings.get(i));
        }
        strings1.add("");
        strings1.add("0 Powrót");

        consolePrinter.print(tableName + " wybierz sortowanie:", strings1);
        System.out.print("Wybierz: ");
        int userInput = inputCollector.getNumericInput(strings1.size() - 1);
        if (userInput == 0)
            return new Sort(sortyBy, isDescending);

        consolePrinter.print(tableName + " wybierz kierunek sortowania:", List.of("1 Rosnąco", "2 malejąco"));
        System.out.print("Wybierz: ");
        int userInput2 = inputCollector.getNumericInput(3);

        return new Sort(strings.get(userInput - 1), userInput2 == 2);
    }
}
