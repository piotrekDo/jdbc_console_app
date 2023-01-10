package customer;

public class CustomerDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String points;

    public CustomerDTO(String id, String firstName, String lastName, String birthDate, String phone, String address, String city, String state, String points) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.points = points;
    }

    @Override
    public String toString() {
        return id +
                " " +
                firstName +
                " " +
                lastName +
                " " +
                birthDate +
                " " +
                phone +
                " " +
                address +
                " " +
                city +
                " " +
                state +
                " " +
                points;
    }
}
