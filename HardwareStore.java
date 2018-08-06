package hardwarestore;

import java.io.*;
import java.util.*;

/**
 *
 * @author Junye Wen modified by @author William Hubert
 */
public class HardwareStore {

    private static ArrayList<Item> itemList;
    private static ArrayList<User> userList;
    private static ArrayList<Transaction> transactionList;
    private static int userIdCounter;

    private static final String DATA_FILE_NAME = "database.ser";

    /**
     * @throws IOException
     */
    public HardwareStore() throws IOException {
        readDatabase();
    }

    /**
     * Method getAllItemsFormatted returns the current list of items in the Arraylist
     * 
     * @return a formatted String representation of all the items in itemList.
     */
    public String getAllItemsFormatted() {
        return getFormattedItemList(itemList);
    }

    /**
     * a method to return a given ArrayList of items in a formatted text
     *
     * @param items the item list to be displayed.
     * @return a formatted String representation of all the items in the list
     */
    public String getFormattedItemList(ArrayList<Item> items) {

        String text = " ------------------------------------------------------------------------------------------------------------------\n" +
                String.format("| %-8s| %-25s| %-10s| %-10s| %-20s| %-30s|%n", "Item ID", "Name", "Quantity", "Price", "Item Type", "Category / Brand and type") +
                      " ------------------------------------------------------------------------------------------------------------------\n";

        for (Item item : items) {
           text += item.getFormattedText();
           text += " ------------------------------------------------------------------------------------------------------------------\n";
        }
        //text += " ------------------------------------------------------------------------------------------------------------------\n";

        return text;
    }

    /**
     * returns the current list of users in the Arraylist
     *
     * @return a formatted String representation of all the users in userList
     */
    public String getAllUsersFormatted() {
        return getFormattedUserList(userList);
    }

    /**
     * used as a method to return a given ArrayList of users in a formatted text string
     *
     * @param users the user list to be displayed.
     * @return a formatted String representation of all the users in the list give as a parameter.
     */
    private String getFormattedUserList(ArrayList<User> users) {

        String text = " -------------------------------------------------------------------------------------------------\n" +
                String.format("| %-10s| %-9s| %-12s| %-12s| %-45s|%n", "User Type", "User ID", "First Name", "Last Name", "Special") +
                " -------------------------------------------------------------------------------------------------\n";

        for (User user : users) {
            text += user.getFormattedText();
            text += " -------------------------------------------------------------------------------------------------\n";
        }
        //text += " -------------------------------------------------------------------------------------------------\n";

        return text;
    }

    /**
     * returns the current list of transactions in the Arraylist
     *
     * @return a formatted String representation of all the transactions in transactionList.
     */
    public String getAllTransactionsFormatted() {
        return getFormattedTransactionList(transactionList);
    }

    /**
     * used as a method to return a given ArrayList of items in a formatted manner.
     *
     * @param transactions the transaction list to be displayed.
     * @return a formatted String representation of all the items in the list give as a parameter.
     */
    private String getFormattedTransactionList(ArrayList<Transaction> transactions) {

        String text = " -----------------------------------------------------------------------------------\n" +
                String.format("| %-10s| %-30s| %-10s| %-12s| %-12s|%n", "Item ID", "Date", "Quantity", "Customer ID", "Employee ID") +
                " -----------------------------------------------------------------------------------\n";

        for (Transaction transaction : transactions) {
            text += transaction.getFormattedText();
            text += " -----------------------------------------------------------------------------------\n";
        }
        //text += " -----------------------------------------------------------------------------------\n";

        return text;
    }



    /**
     * used to add a hardware item to the itemList 
     *
     * @param idNumber a String representing the ID number of item
     * @param name a String representing the name of item
     * @param quantity an int representing the quantity of item
     * @param price a float representing the price of item
     * @param category a String representing the category of item
     */
    public void addNewSmallHardwareItem(String idNumber, String name, int quantity, float price, String category) {
        itemList.add(new SmallHardwareItems(idNumber, name, quantity, price, category));
        System.out.println("New hardware item has been added.");
    }

    /**
     * used to add an appliance to the itemList ArrayList.
     *
     * @param idNumber a String representing the ID number of item
     * @param name a String representing the name of item
     * @param quantity an int representing the quantity of item
     * @param price a float representing the price of item
     * @param brand a String representing the brand of item
     * @param type a String representing the type of item
     */
    public void addNewAppliance(String idNumber, String name, int quantity, float price, String brand, String type) {
        //If passed all the checks, add the item to the list
        itemList.add(new Appliances(idNumber, name, quantity, price, brand, type));
        System.out.println("New appliance has been added.");
    }


    /**
     * @param firstName a String representing the first name of user
     * @param lastName a String representing the last name of user
     * @param phoneNumber a String representing the telephone number of user
     * @param address a String representing the address of user
     */
    public void addCustomer(String firstName, String lastName, String phoneNumber, String address) {
        userList.add(new Customer(userIdCounter++, firstName, lastName, phoneNumber, address));
        sortUserList();
        System.out.println("New customer has been added.");
    }

    /**
     * @param firstName a String representing the first name of user
     * @param lastName a String representing the last name of user
     * @param ssn an int representing the ssn of user
     * @param monthlySalary a float representing the monthly salary of user
     */
    public void addEmployee(String firstName, String lastName, int ssn, float monthlySalary) {
        userList.add(new Employee(userIdCounter++, firstName, lastName, ssn, monthlySalary));
        sortUserList();
        System.out.println("New employee has been added.");
    }


    /**
     * Adds a certain quantity of the given item index.
     * @param itemIndex the index of the item in the itemList
     * @param quantity  the quantity to add
     */
    public void addQuantity(int itemIndex, int quantity) {
        Item temp = getItem(itemIndex);
        temp.setQuantity(temp.getQuantity() + quantity);
        System.out.println("Quantity updated.");
    }

    /**
     * Removes a certain quantity of the given item index. 
     * @param itemIndex the index of the item in the itemList
     * @param quantity  the quantity to remove
     */
    public void removeQuantity(int itemIndex, int quantity) {
        Item temp = getItem(itemIndex);
        temp.setQuantity(temp.getQuantity() - quantity);
        System.out.println("Quantity updated.\n");
    }

    /**
     * Returns all the items that match the given name.
     * @param name the name to match.
     * @return a string containing a table of the matching items.
     */
    public String getMatchingItemsByName(String name) {
        ArrayList<Item> temp = new ArrayList<Item>();
        for (Item tempItem : itemList) {
            if (tempItem.getName().toLowerCase().contains(name.toLowerCase())) {
                temp.add(tempItem);
            }
        }
        
        if (temp.size() == 0) {
            return null;
        } else {
            return getFormattedItemList(temp);
        }
    }

    /**
     * Returns all the items with current quantity lower than the given int
     * 
     * @param quantity the quantity threshold.
     * @return a string containing a table of the matching items.
     */
    public String getMatchingItemsByQuantity(int quantity) {
        ArrayList<Item> temp = new ArrayList<Item>();
        for (Item tempItem : itemList) {
            if (tempItem.getQuantity() <= quantity) {
                temp.add(tempItem);
            }
        }
        if (temp.isEmpty()) {
            return null;
        } else {
            return getFormattedItemList(temp);
        }
    }

    /**
     * used to find a item in the Arraylist of items.
     *
     * @param id a String that represents the ID of
     * the item that to be searched for.
     * @return the Item in the Arraylist of
     * items, or null if the search failed.
     */
    public Item findItem(String id) {
        Item item = null;
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getIdNumber().equals(id)) {
                item = itemList.get(i);
                break;
            }

        }
        return item;
    }


    /**
     * used to find a item in the Arraylist of items.
     *
     * @param idNumber a String that represents the ID number of
     * the item that to be searched for.
     * @return the int index of the items in the Arraylist of
     * items, or -1 if the search failed.
     */
    public int findItemIndex(String idNumber) {
        int index = -1;
        for (int i = 0; i < itemList.size(); i++) {
            String temp = itemList.get(i).getIdNumber();

            if (idNumber.equalsIgnoreCase(temp)) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * used to find a user in the Arraylist of users.
     *
     * @param id an int that represents the ID of
     * the user that to be searched for.
     * @return the User in the Arraylist of
     * users, or null if the search failed.
     */
    public User findUser(int id) {
        User user = null;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == id) {
                user = userList.get(i);
                break;
            }
        }
        return user;
    }


    /**
     * used to find the index of user in the Arraylist of users.
     *
     * @param id a String that represents the ID of
     * the user that to be searched for.
     * @return the int index of the user in the Arraylist of
     * users, or -1 if the search failed.
     */
    public int findUserIndex(int id) {
        int index = -1;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == id) {
                index = i;
                break;
            }
        }
        return index;
    }
    /**
     * used to edit information of an customer.
     * @param idInput the int index of the user's ID
     * @param firstName a String representing the first name of user
     * @param lastName a String representing the last name of user
     * @param phoneNumber a String representing the telephone number of user
     * @param address a String representing the address of user
     */
    public void editCustomerInformation(int idInput, String firstName, String lastName, String phoneNumber, String address) {
        userList.remove(findUserIndex(idInput));
        userList.add(new Customer(idInput, firstName, lastName, phoneNumber, address));
        sortUserList();
        System.out.println("Customer information updated.");
    }


    /**
     * used to edit information of an employee.
     * It will first remove the old entry and add a new one with same user ID
     * @param idInput the int index of the user's ID
     * @param firstName a String representing the first name of user
     * @param lastName a String representing the last name of user
     * @param socialSecurityNumber an int representing the ssn of user
     * @param monthlySalary a float representing the monthly salary of user
     */
    public void editEmployeeInformation(int idInput, String firstName, String lastName, int socialSecurityNumber, float monthlySalary) {
        userList.remove(findUserIndex(idInput));
        userList.add(new Employee(idInput, firstName, lastName, socialSecurityNumber, monthlySalary));
        sortUserList();
        System.out.println("Employee information updated.");
    }

    /**
     * used to remove a item in the Arraylist of items.
     *
     * @param itemIndex an int that represents the Index of
     * the item in the list that to be removed.
     */
    public void removeItem(int itemIndex) {
        itemList.remove(itemIndex);
    }

    /**
     * used to retrieve the Item object from the
     * itemList at a given index.
     *
     * @param i the index of the desired Item object.
     * @return the Item object at the index or null if the index is
     * invalid.
     */
    public Item getItem(int i) {
        if (i < itemList.size() && i >= 0) {
            return itemList.get(i);
        } else {
            System.out.println("Invalid Index.");
            return null;
        }
    }
    
    /**
     * used to retrieve the User object from the userList at the given index 
     * 
     * @param i the index of the desired User object
     * @return the User object at the index or null if their index is invalid
     */
    public User getUser(int i) {
        if (i < userList.size() && i >= 0) {
            return userList.get(i);
        } else {
            System.out.println("Invalid Index.");
            return null;
        }
    }
    

    /**
     * adds a transaction to the list, and removes the quantity for the target item.
     * @param itemId a String representing the ID of item
     * @param saleQuantity an int of the quantity
     * @param customerId an int representing the ID of customer
     * @param employeeId an int representing the ID of employee
     * @param itemIndex an int representing the index of item in the list, used to reduce the quantity.
     */
    public void progressTransaction(String itemId, int saleQuantity, int customerId, int employeeId, int itemIndex) {
        transactionList.add(new Transaction(itemId, new Date(), saleQuantity, customerId, employeeId));
        addQuantity(itemIndex, -1*saleQuantity);
    }

    /**
     * opens the database file and overwrites it with a
     * serialized representation of all the items in the itemList,
     * all users in the userList, all transactions in the
     * transactionList, and the userIdCounter.
     * This should be the last method to be called before exiting the program.
     *
     * @throws IOException
     */
    public void writeDatabase() throws IOException {
        //serialize the database
        OutputStream file = null;
        OutputStream buffer = null;
        ObjectOutput output = null;
        try {
            file = new FileOutputStream(DATA_FILE_NAME);
            buffer = new BufferedOutputStream(file);
            output = new ObjectOutputStream(buffer);

            output.writeObject(itemList);
            output.writeObject(userList);
            output.writeObject(transactionList);
            output.writeInt(userIdCounter);

            output.close();
        } catch (IOException ex) {
            System.err.println(ex.toString());
        } finally {
            close(file);
        }
        System.out.println("Done.");
    }

    /**
     * opens the database file and initializes the itemList,
     * userList, transactionList and the userIdCounter
     * with their contents. If no such file exists, then one is created.
     * The contents of the file are "loaded" into the itemList ArrayList in no 
     * particular order. The file is then closed during the duration of the 
     * program until writeDatabase() is called.
     *
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	public void readDatabase() throws IOException {

        System.out.print("Reading database...");

        File dataFile = new File(DATA_FILE_NAME);

        InputStream file = null;
        InputStream buffer = null;
        ObjectInput input = null;
        try {
            if (!dataFile.exists()) {
                System.out.println("Data file does not exist. Creating a new database.");
                itemList = new ArrayList<Item>();
                userList = new ArrayList<User>();
                transactionList = new ArrayList<Transaction>();
                userIdCounter = 1;
                return;
            }
            file = new FileInputStream(dataFile);
            buffer = new BufferedInputStream(file);
            input = new ObjectInputStream(buffer);

            itemList = (ArrayList<Item>) input.readObject();
            userList = (ArrayList<User>) input.readObject();
            transactionList = (ArrayList<Transaction>) input.readObject();
            userIdCounter = input.readInt();

            input.close();
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.toString());
        } catch (FileNotFoundException ex) {
            System.err.println("Database file not found.");
        } catch (IOException ex) {
            System.err.println(ex.toString());
        } finally {
        	close(file);
        }
        System.out.println("Done.");
    }

    /**
     * used to close a file and handle exceptions that might occur
     *
     * @param c
     */
    public static void close(Closeable c) {
        if (c == null) {
            return;
        }
        try {
            c.close();
        } catch (IOException ex) {
            System.err.println(ex.toString());
        }
    }

    /**
     * sorts the item list, based on the item ID.
     */
    public static void sortItemList() {
        itemList.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                if (o1.getIdNumber().compareTo(o2.getIdNumber())>0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

    }
    /**
     * sorts the user list, based on the user ID.
     */
    public static void sortUserList() {
        userList.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if (o1.getId()>o2.getId()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

    }
}
