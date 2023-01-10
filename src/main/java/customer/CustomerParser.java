package customer;

import utils.DateParser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CustomerParser {

    public static CustomerEntity parseToCustomer(ResultSet resultSet) throws SQLException {
        int customer_id = resultSet.getInt("customer_id");
        String first_name = resultSet.getString("first_name");
        String last_name = resultSet.getString("last_name");
        String birth_date = resultSet.getString("birth_date");
        String phone = resultSet.getString("phone");
        String address = resultSet.getString("address");
        String city = resultSet.getString("city");
        String state = resultSet.getString("state");
        int points = resultSet.getInt("points");

        LocalDate birthDate = DateParser.parseToLocalDate(birth_date);

        return new CustomerEntity(customer_id, first_name, last_name, birthDate, phone, address, city, state, points);
    }

    public static CustomerDTO parseEntityToDto(CustomerEntity customerEntity) {
        return new CustomerDTO(String.valueOf(customerEntity.getId()),
                customerEntity.getFirstName(),
                customerEntity.getLastName(),
                customerEntity.getBirthDate().toString(),
                customerEntity.getPhone(),
                customerEntity.getAddress(),
                customerEntity.getCity(),
                customerEntity.getState(),
                String.valueOf(customerEntity.getPoints()));
    }
}
