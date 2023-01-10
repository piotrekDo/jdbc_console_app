import java.util.List;

public class ConsolePrinter {

    private final int offset = 10;

    void print(String title, List<String> data) {
        System.out.println();
        System.out.println();
        int length = Math.max(getMaxLength(data), title.length());
        String bottomLine = getBottomLine(length);

        System.out.println(getTopLine(length, title));
        printEmptyLine(length);
        for (String string : data) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("+");
            for (int i = 0; i < offset; i++) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(string);
            int rightSpace = length + offset - string.length();
            for (int i = 0; i < rightSpace; i++) {
                stringBuilder.append(" ");
            }
            stringBuilder.append("+");
            System.out.println(stringBuilder);
        }
        printEmptyLine(length);
        System.out.println(bottomLine);
    }

    private void printEmptyLine(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("+");
        for (int i = 0; i < length + (offset * 2); i++) {
            stringBuilder.append(" ");
        }
        stringBuilder.append("+");
        System.out.println(stringBuilder);
    }

    private String getTopLine(int length, String title) {
        int leftOffset = (length + (offset * 2) - title.length()) / 2 - 3;
        int rightOffset = length + (offset * 2) - title.length() - leftOffset -2;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("+");
        for (int i = 0; i < leftOffset; i++) {
            stringBuilder.append("-");
        }
        stringBuilder.append(" ");
        stringBuilder.append(title);
        stringBuilder.append(" ");
        for (int i = 0; i < rightOffset; i++) {
            stringBuilder.append("-");
        }
        stringBuilder.append("+");
        return stringBuilder.toString();
    }

    private String getBottomLine(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("+");
        for (int i = 0; i < length + (offset * 2); i++) {
            stringBuilder.append("-");
        }
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
