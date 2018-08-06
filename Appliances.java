package hardwarestore;

public class Appliances extends Item {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final String brand;
    protected final String type;

    /**
     * Constructor initializes a customer object with the provided values.
     * @param idNumber
     * @param name
     * @param quantity
     * @param price
     * @param brand
     * @param type 
     */
    public Appliances(String idNumber, String name, int quantity, float price, String brand, String type) {
        super(idNumber, name, quantity, price);
        this.brand = brand;
        this.type = type;
    }

    /**
     * returns the brand 
     * @return brand
     */
    public String getBrand() {
        return brand;
    }


    /**
     * returns the type 
     * @return type
     */
    public String getType() {
        return type;
    }


    /**
     * Returns a formated list of the appliance's attributes 
     * @return Formatted Text.
     */
    @Override
    public String getFormattedText() {
        return String.format("| %-8s| %-25s| %-10s| %-10s| %-20s| %-30s|%n",
                this.getIdNumber(),
                this.getName(),
                Integer.toString(this.getQuantity()),
                String.format("%.2f", this.getPrice()),
                "Appliances",
                this.getBrand()+" "+ this.getType()
        );
    }

}
