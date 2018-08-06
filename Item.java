package hardwarestore;

import java.io.Serializable;

public abstract class Item implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final String idNumber;
    protected final String name;
    protected int quantity;
    protected final float price;

    /**
     * This constructor initializes the item object. The constructor provides no
     * user input validation. That should be handled by the class that creates a
     * item object.
     *
     * @param idNumber 
     * @param name 
     * @param price
     */
    public Item(String idNumber, String name, int quantity, float price) {
        this.idNumber = idNumber;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * returns the items tracking number
     *
     * @return a String that is the ID number of the item.
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * returns the items name.
     *
     * @return a String that is the item's name.
     */
    public String getName() {
        return name;
    }

    /**
     * returns the item's quantity.
     *
     * @return an int that is the item's weight
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * set the item's quantity.
     *
     *  @param quantity an int that represents the quantity
     */
    public void setQuantity(int quantity) {
        this.quantity= quantity;
    }

    /**
     * returns the item's price.
     *
     * @return a float that is the item's price
     */
    public float getPrice() {
        return price;
    }

    /**
     * provides a way to compare two item objects.
     *
     * @param c a Item object that is used to compare to
     * this item. Two orders are equal if their ID is the
     * same.
     * @return the boolean value of the comparison.
     */
    public boolean equals(Item c) {
        return c.getIdNumber().equals(this.idNumber);
    }

    /**
     * Abstract print method, implemented by subclasses of class User.
     * @return
     */
    public abstract String getFormattedText();
}
