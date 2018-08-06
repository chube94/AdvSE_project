package hardwarestore;

public class SmallHardwareItems extends Item {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final String category;

    /**
     * initializes a customer object with the provided values
     * @param idNumber
     * @param name
     * @param quantity
     * @param price
     * @param category 
     */
    public SmallHardwareItems(String idNumber, String name, int quantity, float price, String category) {
        super(idNumber, name, quantity, price);
        this.category = category;
    }

    /**
     * returns the category.
     * @return category
     */
    public String getCategory() {
        return category;
    }


    /**
     * Returns the attributes of the small hardware item in formatted text
     * @return Formatted Text.
     */
    @Override
    public String getFormattedText() {
        return String.format("| %-8s| %-25s| %-10s| %-10s| %-20s| %-30s|%n",
                this.getIdNumber(),
                this.getName(),
                Integer.toString(this.getQuantity()),
                String.format("%.2f", this.getPrice()),
                "Small Hardware Items",
                this.getCategory()
        );
    }

}
