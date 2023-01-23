package app.loops;

import app.model.ColumnDetails;
import app.model.DataPage;
import app.repository.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Service {

    private final Repository repository;

    /**
     * Service class used in loop classes
     */

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

    DataPage loadDataFromTable(String table, LinkedList<ColumnDetails> columnDetails, int offset, int elements, String sortBy, boolean isDescending) {
        LinkedList<LinkedList<String>> results = repository.selectDataFromTable(table, columnDetails, offset, elements, sortBy, isDescending);
        LinkedHashMap<String, Integer> maxLengths = new LinkedHashMap<>();


        IntStream.range(0, results.get(0).size())
                .forEach(idx -> maxLengths.put(idx + results.get(0).get(idx), 0));


        List<String> keys = maxLengths.keySet().stream().toList();
        IntStream.range(0, maxLengths.size())
                .forEach(i -> results.stream()
                        .map(x -> x.get(i) == null ? 4 : x.get(i).length())
                        .max(Integer::compare)
                        .ifPresent(x -> maxLengths.put(keys.get(i), Math.max(x, maxLengths.get(keys.get(i))))));

        return new DataPage(results, maxLengths);
    }

    LinkedList<ColumnDetails> fetchTableDetails(String tableName) {
        return repository.fetchTableColumnsData(tableName);
    }
}
