package customer;

import utils.DateParser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CustomerParser {

    private final CustomerDTOSize size;

    public CustomerParser(CustomerDTOSize size) {
        this.size = size;
    }

    public CustomerEntity parseToCustomer(ResultSet resultSet) throws SQLException {
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

    public CustomerDTO parseEntityToDto(CustomerEntity customerEntity) {
        CustomerDTO customerDTO = new CustomerDTO(String.valueOf(customerEntity.getId()),
                customerEntity.getFirstName(),
                customerEntity.getLastName(),
                customerEntity.getBirthDate().toString(),
                customerEntity.getPhone(),
                customerEntity.getAddress(),
                customerEntity.getCity(),
                customerEntity.getState(),
                String.valueOf(customerEntity.getPoints()));
        if (customerDTO.getId().length() > size.getId()) size.setId(customerDTO.getId().length());
        if (customerDTO.getFirstName().length() > size.getFirstName()) size.setFirstName(customerDTO.getFirstName().length());
        if (customerDTO.getLastName().length() > size.getLastName()) size.setLastName(customerDTO.getLastName().length());
        if (customerDTO.getBirthDate().length() > size.getBirthDate()) size.setBirthDate(customerDTO.getBirthDate().length());
        if (customerDTO.getPhone() != null && customerDTO.getPhone().length() > size.getPhone()) size.setPhone(customerDTO.getPhone().length());
        if (customerDTO.getAddress().length() > size.getAddress()) size.setAddress(customerDTO.getAddress().length());
        if (customerDTO.getCity().length() > size.getCity()) size.setCity(customerDTO.getCity().length());
        if (customerDTO.getState().length() > size.getState()) size.setState(customerDTO.getState().length());
        if (customerDTO.getPoints().length() > size.getPoints()) size.setPoints(customerDTO.getPoints().length());

        return customerDTO;
    }
}
