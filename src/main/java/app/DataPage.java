package app;

import java.util.LinkedHashMap;
import java.util.LinkedList;



public class DataPage {
    private final LinkedList<LinkedList<String>> data;
    private final LinkedHashMap<String, Integer> maxRowLength;

    /**
     * DataPage object represents a collections of table rows. Each row has a list of columns.
     * @param data list of rows with rows having a list of columns.
     * @param maxRowLength map with key indicating column name and value indicating length-
     *                     used in ConsolePrinter to determine column width.
     */

    public DataPage(LinkedList<LinkedList<String>> data, LinkedHashMap<String, Integer> maxRowLength) {
        this.data = data;
        this.maxRowLength = maxRowLength;
    }

    public LinkedList<LinkedList<String>> getData() {
        return data;
    }

    public LinkedHashMap<String, Integer> getMaxRowLength() {
        return maxRowLength;
    }
}
