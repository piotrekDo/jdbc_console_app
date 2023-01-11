package app;

import java.util.*;
import java.util.stream.IntStream;

public class Service {

    private final Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    ArrayList<String> getAllTableNames() {
        LinkedList<String> allTableNames = repository.getAllTableNames();
        ArrayList<String> tables = new ArrayList<>();
        for (int i = 0; i < allTableNames.size(); i++) {
            tables.add((i + 1) + " " + allTableNames.get(i));
        }
        tables.add("");
        tables.add(0 + " Powrót");
        return tables;
    }

    public DataPage loadDataFromTable(String table, Map<String, String> tableDetails, int offset, int elements) {
        LinkedList<LinkedList<String>> results = repository.selectDataFromTable(table, tableDetails, offset, elements);
        LinkedHashMap<String, Integer> maxLengths = new LinkedHashMap<>();
        results.get(0).forEach(header -> maxLengths.put(header, 0));

        List<String> keys = maxLengths.keySet().stream().toList();
        IntStream.range(0, maxLengths.size())
                .forEach(i -> results.stream()
                        .map(x -> x.get(i) == null ? 4 : x.get(i).length())
                        .max(Integer::compare)
                        .ifPresent(x -> maxLengths.put(keys.get(i), Math.max(x, maxLengths.get(keys.get(i))))));

        return new DataPage(results, maxLengths);
    }

    public LinkedHashMap<String, String> fetchTableDetails(String tableName) {
        return repository.fetchTableColumnsData(tableName);
    }
}
