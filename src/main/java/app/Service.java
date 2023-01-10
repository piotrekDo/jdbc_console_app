package app;

import customer.CustomerDTO;
import customer.CustomerEntity;
import customer.CustomerParser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    public List<String> loadDataFromTable(String table, int offset, int elements) {
        LinkedList<String> customerDTOS = new LinkedList<>();

        switch (table) {
            case "customers" -> {
                LinkedList<CustomerEntity> customerEntities = repository.selectDataFromCustomersTable(offset, elements);
                customerDTOS.add(new CustomerDTO("customer_id", "first_name", "last_name", "birth_date", "phone", "address", "city", "state", "points").toString());
                customerEntities.forEach(customerEntity -> customerDTOS.add(CustomerParser.parseEntityToDto(customerEntity).toString()));
                return customerDTOS;
            }

            default -> throw new IllegalStateException("Unexpected value: " + table);
        }

    }
}
