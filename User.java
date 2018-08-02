package hardwarestore;

import java.io.Serializable;

public abstract class User implements Serializable {

    protected final int id;
    protected String firstName;
    protected String lastName;
    public final boolean isEmployee;

    /**
     * initializes a user object with the provided values
     * @param id User ID
     * @param firstName First Name
     * @param lastName Last Name
     */
    public User(int id, String firstName, String lastName, boolean isEmployee) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isEmployee = isEmployee;
    }

    /**
     * returns the last name
     * @return lastName Last Name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name
     * @param lastName Last Name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * returns the user ID
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * returns the first name
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the first name
     * @param firstName First Name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Abstract print method implemented by subclasses user
     * @return 
     */
    public abstract String getFormattedText();
}

