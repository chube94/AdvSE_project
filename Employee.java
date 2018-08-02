package hardwarestore;

public class Employee extends User {
    private int socialSecurityNumber;
    private float monthlySalary;

    /**
     * initializes an employee object with the provided values.
     * @param id
     * @param firstName
     * @param lastName
     * @param socialSecurityNumber
     * @param monthlySalary
     */
    public Employee(int id, String firstName, String lastName, int socialSecurityNumber, float monthlySalary) {
        super(id, firstName, lastName, true);
        this.socialSecurityNumber = socialSecurityNumber;
        this.monthlySalary = monthlySalary;
    }

    /**
     * returns the SSN.
     * @return socialSecurityNumber
     */
    public int getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    /**
     * Set the SSN.
     * @param socialSecurityNumber
     */
    public void setSocialSecurityNumber(int socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    /**
     * returns the monthly salary.
     * @return monthlySalary
     */
    public float getMonthlySalary() {
        return monthlySalary;
    }

    /**
     * Set the monthly salary.
     * @param monthlySalary
     */
    public void setMonthlySalary(float monthlySalary) {
        this.monthlySalary = monthlySalary;
    }


    /**
     * Returns the attributes of the employee in formatted text
     * @return Formatted Text.
     */
    @Override
    public String getFormattedText() {
        return String.format("| %-10s| %-9s| %-12s| %-12s| SSN: %12d, Salary: %10s        |%n",
                "Employee", id, firstName, lastName, socialSecurityNumber, monthlySalary);
    }

}
