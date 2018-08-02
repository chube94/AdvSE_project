package hardwarestore;

public class Customer extends User {
    private String phoneNumber;
    private String address;

    /**
     * initializes a customer object with provided values
     * @param id
     * @param phoneNumber
     * @param address
     * @param firstName
     * @param lastName
     */
    public Customer(int id, String firstName, String lastName, String phoneNumber, String address) {
        super(id, firstName, lastName, false);
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    /**
     * returns the phone number.
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Set the phone number.
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * returns the address.
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the address.
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * Returns the attributes of the customes in a formatted text
     * @return Formatted Text.
     */
    @Override
    public String getFormattedText() {
        return String.format("| %-10s| %-9s| %-12s| %-12s| Ph#: %12s, Add: %20s |%n",
                "Customer", id, firstName, lastName, phoneNumber, address);
    }

}
