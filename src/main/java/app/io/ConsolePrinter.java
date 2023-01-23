package app.io;

import app.model.DataPage;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class ConsolePrinter {

    private final int offset = 10;

    /**
     * Method used to print data in table form.
     *
     * @param title header to be printed in top line
     * @param data  list of strings to be printed
     */

    public void print(String title, List<String> data) {
        System.out.println();
        System.out.println();
        int length = Math.max(getMaxLength(data), title.length());
        String bottomLine = getBottomLine(length);

        System.out.println(getTopLine(length, title));
        printEmptyLine(length);
        for (String string : data) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("+");
            stringBuilder.append(" ".repeat(offset));
            stringBuilder.append(string);
            int rightSpace = length + offset - string.length();
            stringBuilder.append(" ".repeat(Math.max(0, rightSpace)));
            stringBuilder.append("+");
            System.out.println(stringBuilder);
        }
        printEmptyLine(length);
        System.out.println(bottomLine);
    }

    /**
     * Method used to print output from select statements.
     *
     * @param dataPage  object representing a collections of table rows. Each row has a list of columns.
     * @param tableName table name to be used as header in first line.
     */

    public void printTable(DataPage dataPage, String tableName) {
        LinkedList<LinkedList<String>> data = dataPage.getData();
        LinkedHashMap<String, Integer> maxRowLength = dataPage.getMaxRowLength();
        List<Integer> maxLengths = maxRowLength.values().stream().toList();
        int totalLength = maxRowLength.values().stream().reduce(0, Integer::sum) + (data.get(0).size() * 3) + 1;

        System.out.println(getTopLine(totalLength, tableName));
        printEmptyLine(totalLength);

        for (LinkedList<String> line : data) {
            StringBuilder sb = new StringBuilder();
            sb.append("+");
            sb.append(" ".repeat(offset));
            for (int j = 0; j < line.size(); j++) {
                String text = line.get(j);
                Integer max = maxLengths.get(j);
                int textLength = text == null ? 4 : text.length();

                int leftSpace = (max - textLength) / 2;
                int rightSpace = max - textLength - leftSpace;

                sb.append("| ");
                sb.append(" ".repeat(leftSpace));
                sb.append(text);
                sb.append(" ".repeat(rightSpace + 1));

            }
            sb.append("|");
            sb.append(" ".repeat(offset));
            sb.append("+");
            System.out.println(sb);
        }

        printEmptyLine(totalLength);
        System.out.println(getBottomLine(totalLength));
    }

    private void printSummary(int length, int pageOffset, int elements) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("+");
        stringBuilder.append(" ".repeat(offset));

        String message = String.format("Wyswietlam %d stronę, %d elementów", pageOffset / elements + 1, elements);
        stringBuilder.append(message);

        stringBuilder.append(" ".repeat(Math.max(0, length - offset - message.length())));

        stringBuilder.append("+");
        System.out.println(stringBuilder);
    }

    private void printEmptyLine(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("+");
        stringBuilder.append(" ".repeat(Math.max(0, length + (offset * 2))));
        stringBuilder.append("+");
        System.out.println(stringBuilder);
    }

    private String getTopLine(int length, String title) {
        int leftOffset = (length + (offset * 2) - title.length()) / 2 - 3;
        int rightOffset = length + (offset * 2) - title.length() - leftOffset - 2;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("+");
        stringBuilder.append("-".repeat(Math.max(0, leftOffset)));
        stringBuilder.append(" ");
        stringBuilder.append(title);
        stringBuilder.append(" ");
        stringBuilder.append("-".repeat(Math.max(0, rightOffset)));
        stringBuilder.append("+");
        return stringBuilder.toString();
    }

    private String getBottomLine(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("+");
        stringBuilder.append("-".repeat(Math.max(0, length + (offset * 2))));
        stringBuilder.append("+");
        return stringBuilder.toString();
    }

    private int getMaxLength(List<String> data) {
        int counter = 0;
        for (String string : data) {
            if (string.length() > counter) counter = string.length();
        }
        return counter;
    }
}
