package app;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class DataPage {
    private final LinkedList<LinkedList<String>> data;
    private final LinkedHashMap<String, Integer> maxRowLength;

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
