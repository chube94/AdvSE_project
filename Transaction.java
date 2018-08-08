package hardwarestore;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;
	private final String itemID;
    private final Date saleDate;
    private final int saleQuantity;
    private final int customerId;
    private final int employeeId;

    /**
     * initializes a transaction object with the provided values
     * @param itemID
     * @param saleDate
     * @param saleQuantity
     * @param customerId
     * @param employeeId
     */
    public Transaction(String itemID, Date saleDate, int saleQuantity, int customerId, int employeeId) {
        this.itemID = itemID;
        this.saleDate = saleDate;
        this.saleQuantity = saleQuantity;
        this.customerId = customerId;
        this.employeeId = employeeId;
    }

    /**
     * returns the item ID of this transaction
     * @return itemID
     */
    public String getItemID() {
        return itemID;
    }

    /**
     * returns the sale date of this transaction
     * @return saleDate
     */
    public Date getsaleDate() {
        return saleDate;
    }

    /**
     * returns the quantity of this transaction
     * @return saleQuantity
     */
    public int getSaleQuantity() {
        return saleQuantity;
    }

    /**
     * returns the customer ID of this transaction
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * returns the employee ID of this transaction
     * @return
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * returns transaction attributes in formatted text 
     * @return
     */
    public String getFormattedText() {
        return String.format("| %-10s| %-30s| %-10s| %-12s| %-12s|%n",
                itemID, saleDate, saleQuantity, customerId, employeeId);
    }
    @Override
    public String toString() {
        return "Transaction{" + "itemID=" + itemID + ", saleDate=" + saleDate + ",  saleQuantity=" + saleQuantity
                + ", customerId=" + customerId + ", employeeId=" + employeeId + '}';
    }
}
