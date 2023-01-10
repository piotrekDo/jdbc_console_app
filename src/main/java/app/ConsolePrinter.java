package app;

import customer.CustomerDTO;
import customer.CustomerDTOSize;
import customer.CustomerPrinterData;

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

    public void printCustomers(String tableName, CustomerPrinterData customerPrinterData) {
        CustomerDTOSize size = customerPrinterData.getSize();
        List<CustomerDTO> dto = customerPrinterData.getDto();
        int totalLength = size.getId() + size.getFirstName() + size.getLastName() + size.getBirthDate()
                + size.getPhone() + size.getAddress() + size.getCity() + size.getState() + size.getPoints() + (offset * 2);
        System.out.println(getTopLine(totalLength + 8, tableName));
        printEmptyLine(totalLength + 8);

        dto.forEach(customer -> {
            // Left offset
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("+");
           for (int i = 0; i < offset; i++) {
               stringBuilder.append(" ");
           }

           // customer_id
           stringBuilder.append("| ");
           int leftSpace = (size.getId() - customer.getId().length()) / 2;
           for (int i = 0; i < leftSpace; i++) {
               stringBuilder.append(" ");
           }
           stringBuilder.append(customer.getId());
           int rightSpace = size.getId() - leftSpace - customer.getId().length();
            for (int i = 0; i < rightSpace; i++) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(" |");

            //customer_first_name
            stringBuilder.append(" ");
            leftSpace = (size.getFirstName() - customer.getFirstName().length()) / 2;
            for (int i = 0; i < leftSpace; i++) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(customer.getFirstName());
            rightSpace = size.getFirstName() - leftSpace - customer.getFirstName().length();
            for (int i = 0; i < rightSpace; i++) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(" |");

            //customer_last_name
            stringBuilder.append(" ");
            leftSpace = (size.getLastName() - customer.getLastName().length()) / 2;
            for (int i = 0; i < leftSpace; i++) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(customer.getLastName());
            rightSpace = size.getLastName() - leftSpace - customer.getLastName().length();
            for (int i = 0; i < rightSpace; i++) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(" |");

            //customer_birth_name
            stringBuilder.append(" ");
            leftSpace = (size.getBirthDate() - customer.getBirthDate().length()) / 2;
            for (int i = 0; i < leftSpace; i++) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(customer.getBirthDate());
            rightSpace = size.getBirthDate() - leftSpace - customer.getBirthDate().length();
            for (int i = 0; i < rightSpace; i++) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(" |");

            //phone
            stringBuilder.append(" ");
            leftSpace = (size.getPhone() - (customer.getPhone() == null ? 4 : customer.getPhone().length())) / 2;
            for (int i = 0; i < leftSpace; i++) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(customer.getPhone());
            rightSpace = size.getPhone() - leftSpace - (customer.getPhone() == null ? 4 : customer.getPhone().length());
            for (int i = 0; i < rightSpace; i++) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(" |");

            //address
            stringBuilder.append(" ");
            leftSpace = (size.getAddress() - customer.getAddress().length()) / 2;
            for (int i = 0; i < leftSpace; i++) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(customer.getAddress());
            rightSpace = size.getAddress() - leftSpace - customer.getAddress().length();
            for (int i = 0; i < rightSpace; i++) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(" |");

            //city
            stringBuilder.append(" ");
            leftSpace = (size.getCity() - customer.getCity().length()) / 2;
            for (int i = 0; i < leftSpace; i++) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(customer.getCity());
            rightSpace = size.getCity() - leftSpace - customer.getCity().length();
            for (int i = 0; i < rightSpace; i++) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(" |");

            //state
            stringBuilder.append(" ");
            leftSpace = (size.getState() - customer.getState().length()) / 2;
            for (int i = 0; i < leftSpace; i++) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(customer.getState());
            rightSpace = size.getState() - leftSpace - customer.getState().length();
            for (int i = 0; i < rightSpace; i++) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(" |");

            //points
            stringBuilder.append(" ");
            leftSpace = (size.getPoints() - customer.getPoints().length()) / 2;
            for (int i = 0; i < leftSpace; i++) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(customer.getPoints());
            rightSpace = size.getPoints() - leftSpace - customer.getPoints().length();
            for (int i = 0; i < rightSpace; i++) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(" |");

            //right offset
            for (int i = 0; i < offset; i++) {
                stringBuilder.append(" ");
            }
            stringBuilder.append("+");

            System.out.println(stringBuilder);
       });

        printEmptyLine(totalLength + 8);
        System.out.println(getBottomLine(totalLength + 8));

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
        int rightOffset = length + (offset * 2) - title.length() - leftOffset - 2;
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
