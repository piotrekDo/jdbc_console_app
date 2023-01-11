package app;

import customer.*;

import java.util.ArrayList;
import java.util.LinkedList;

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

    public CustomerPrinterPage loadDataFromTable(String table, int offset, int elements) {

        switch (table) {
            case "customers" -> {
                LinkedList<CustomerDTO> customerDTOS = new LinkedList<>();
                LinkedList<CustomerEntity> customerEntities = repository.selectDataFromCustomersTable(offset, elements);
                customerDTOS.add(new CustomerDTO("customer_id", "first_name", "last_name", "birth_date", "phone", "address", "city", "state", "points"));
                CustomerDTOSize customerDTOSize = new CustomerDTOSize("customer_id", "first_name", "last_name", "birth_date", "phone", "address", "city", "state", "points");
                CustomerParser customerParser = new CustomerParser(customerDTOSize);
                customerEntities.forEach(customerEntity -> {
                    customerDTOS.add(customerParser.parseEntityToDto(customerEntity));
                });
                return new CustomerPrinterPage(customerDTOSize, customerDTOS, offset, elements);
            }

            default -> throw new IllegalStateException("Unexpected value: " + table);
        }

    }
}
