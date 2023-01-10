package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParser {
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate parseToLocalDate(String date) {
        LocalDate parse = null;
        try {
            parse = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date pattern");
        }
        return parse;
    }
}
