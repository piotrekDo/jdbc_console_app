package app;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SortOptions {

    private final ConsolePrinter consolePrinter;
    private final InputCollector inputCollector;
    private final LinkedList<ColumnDetails> columnDetails;
    private final String tableName;

    /**
     * Object created when sorting options are changed from default.
     * @param consolePrinter class responsible for displaying data in user terminal,
     * @param inputCollector class responsible for collecting data from user,
     * @param columnsDetails collection of ColumnDetails object representing metadata of each column,
     * @param tableName      selected table name.
     */

    public SortOptions(ConsolePrinter consolePrinter, InputCollector inputCollector, LinkedList<ColumnDetails> columnsDetails, String tableName) {
        this.consolePrinter = consolePrinter;
        this.inputCollector = inputCollector;
        this.columnDetails = columnsDetails;
        this.tableName = tableName;
    }

    /**
     * Main method responsible for displaying possible data and returning Sort object to be use when collecting data.
     * @param sortBy determines column name to be sorted by
     * @param isDescending determines sorting direction.
     */

    Sort load(String sortBy, boolean isDescending) {
        List<String> strings = columnDetails.stream().map(ColumnDetails::getTableName).collect(Collectors.toList());
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
            return new Sort(sortBy, isDescending);

        consolePrinter.print(tableName + " wybierz kierunek sortowania:", List.of("1 Rosnąco", "2 malejąco"));
        System.out.print("Wybierz: ");
        int userInput2 = inputCollector.getNumericInput(3);

        return new Sort(strings.get(userInput - 1), userInput2 == 2);
    }
}
