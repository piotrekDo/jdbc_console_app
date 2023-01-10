package customer;

public class CustomerDTOSize {
    private int id;
    private int firstName;
    private int lastName;
    private int birthDate;
    private int phone;
    private int address;
    private int city;
    private int state;
    private int points;

    public CustomerDTOSize(String id, String firstName, String lastName, String birthDate, String phone, String address, String city, String state, String points) {
        this.id = id.length();
        this.firstName = firstName.length();
        this.lastName = lastName.length();
        this.birthDate = birthDate.length();
        this.phone = phone.length();
        this.address = address.length();
        this.city = city.length();
        this.state = state.length();
        this.points = points.length();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFirstName() {
        return firstName;
    }

    public void setFirstName(int firstName) {
        this.firstName = firstName;
    }

    public int getLastName() {
        return lastName;
    }

    public void setLastName(int lastName) {
        this.lastName = lastName;
    }

    public int getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(int birthDate) {
        this.birthDate = birthDate;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
