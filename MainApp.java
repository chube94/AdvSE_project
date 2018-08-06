package hardwarestore;

import java.util.logging.*;
import java.awt.*;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.io.IOException;

/**
 * This is the main class of the Hardware Store database manager. It provides a
 * GUI for the user to input data
 *
 * @author William Hubert
 * @version 7-30-18
 */
public class MainApp extends JFrame implements ItemListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// This object is used to log all User interactions with the GUI and for possible errors and exceptions
	private static final Logger logger = Logger.getLogger(MainApp.class.getName());
	//This object is used to write all logging messages to a file
	private static FileHandler fh = null;
    // This object will allow us to interact with the methods of the class HardwareStore
    private HardwareStore hardwareStore;
    
    JPanel cards;
    
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }
    
    /**
     * Default constructor. Initializes a new object of type HardwareStore and
     *adds the FileHandler fh to the logging object
     * @throws IOException
     */
    public MainApp() throws IOException {
        hardwareStore = new HardwareStore();
        try
        {
          fh = new FileHandler("LoggingMessages.log", false);
        }
        catch (SecurityException | IOException e)
        {
          e.printStackTrace();
          logger.log(Level.SEVERE, e.getMessage(), e);
        }
        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
    }

    /**
     * This method is called to save the database before exit the system.
     * @throws IOException
     */
    public void saveDatabase() throws IOException {
        hardwareStore.writeDatabase();
    }


/**
*This method is used to create the GUI main frame that holds all of the GUI components
*used in this program.
*/
     public void createFrame() throws Exception{
        JFrame mainFrame = new JFrame("Hardware Store");
        logger.info("Main Frame is created");
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {

/**
*This method is used to add a text box asking if the user really wants to exit the Program
*when the exit button is clicked. It then saves the database if the user checks that
*they really want to exit the program.
*@param java.awt.event is used as the action when the user clicks to exit the program.
*@exception e is the event when the database cannot be saved
*/
          @Override
          public void windowClosing(java.awt.event.WindowEvent windowEvent) {
          if (JOptionPane.showConfirmDialog(mainFrame,
            "Are you sure to close this window?", "Really Closing?",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
              try
              {
                saveDatabase();
                JFrame exitFrame = new JFrame("Exiting Program");
                JLabel exitLabel = new JLabel("Saving database before exit...");
                exitFrame.add(exitLabel);
                exitFrame.setVisible(true);
                exitFrame.pack();
                logger.info("Database is saved");
              }
              catch (IOException e)
              {
                logger.log(Level.SEVERE, e.getMessage(), e);
                JFrame exitFrame = new JFrame("Exiting Program");
                JLabel exitLabel = new JLabel("Exception caught, database not saved before exit...");
                exitFrame.add(exitLabel);
                exitFrame.setVisible(true);
                exitFrame.pack();
                System.exit(0);
              }
            System.exit(0);
        }
    }
});

       mainFrame.setSize(500, 200);
       
       JPanel card1 = new JPanel();
       card1.setLayout(new GridLayout(3, 3));
       JPanel card2 = new JPanel();
       JPanel card3 = new JPanel();

       
       JPanel mainPanel = new JPanel();
       mainFrame.add(mainPanel);
       JButton showItemsButton = new JButton("Show All Existing Items");
       showItemsButton.addActionListener(new ActionListener(){

/**
*This method is used to show all of the Items in the list when the user clicks button 1
*@param e is the action of the user clicking button1
*/
           public void actionPerformed(ActionEvent e){
           HardwareStore.sortItemList();
           String str = hardwareStore.getAllItemsFormatted();
           JTextArea showAllItemsTextArea = new JTextArea(str);
           JFrame displayAllItemsFrame = new JFrame("Displaying List of Items");
           displayAllItemsFrame.setSize(500, 100);
           JApplet displayAllItemsApplet = new JApplet();
           displayAllItemsApplet.getContentPane().add(showAllItemsTextArea);
           displayAllItemsFrame.add(displayAllItemsApplet);
           displayAllItemsFrame.pack();
           displayAllItemsFrame.setLocationRelativeTo(null);
           displayAllItemsFrame.setVisible(true);
           logger.info("User displays list of all Existing Items");
    }
  });

       JButton addQuantityToExistingItem = new JButton("Add quantity to existing Item");
       addQuantityToExistingItem.addActionListener(new ActionListener(){
    	   
           public void actionPerformed(ActionEvent e){
        	   HardwareStore.sortItemList();
               JPanel addQuantityToItemPanel = new JPanel();
               JTextField numToAdd = new JTextField(5);
               String id = "";
               Integer quantity = 0;
               int indexToAddTo = 0;
               id = JOptionPane.showInputDialog("ID of Item you wish to add quantity to: ");
               indexToAddTo = hardwareStore.findItemIndex(id);
               if (indexToAddTo == -1){
            	   addQuantityToItemPanel.setVisible(false);
               }
               addQuantityToItemPanel.add(new JLabel("Quantity you wish to add: "));
               addQuantityToItemPanel.add(numToAdd);
               JOptionPane.showConfirmDialog(null, addQuantityToItemPanel,
               "Given ID not found, please try again", JOptionPane.OK_CANCEL_OPTION);
               try
               {
                 quantity = Integer.parseInt(numToAdd.getText().trim());
               }
               catch (NumberFormatException ex)
               {
                 logger.log(Level.SEVERE, ex.getMessage(), ex);
                 System.out.println("Could not delete Item");
               }
               hardwareStore.addQuantity(indexToAddTo, quantity);
               logger.info("User deletes specified quantity from given Item ");

                logger.info("User deletes Item from list");
           }
       });
       
       JButton searchForItemsBelowGivenQuantitiyButton = new JButton("Search for Items belows given quantity");
       searchForItemsBelowGivenQuantitiyButton.addActionListener(new ActionListener() {

    			public void actionPerformed(ActionEvent e){
    		             String num = JOptionPane.showInputDialog("Name of Item to search for : ");
    		             try {
    		            	 int number = Integer.parseInt(num);
    		            	 String str = hardwareStore.getMatchingItemsByQuantity(number);
    		            	 JTextArea matchingItemsTextArea = new JTextArea(str);
    		            	 JFrame displayItemsBelowGivenQuantityFrame = new JFrame("Displaying List of Items Below Given Quantity");
    		            	 displayItemsBelowGivenQuantityFrame.setSize(500, 100);
    		            	 JApplet displayItemsBelowGivenQuantityApplet = new JApplet();
    		            	 displayItemsBelowGivenQuantityApplet.getContentPane().add(matchingItemsTextArea);
    		            	 displayItemsBelowGivenQuantityFrame.add(displayItemsBelowGivenQuantityApplet);
    		            	 displayItemsBelowGivenQuantityFrame.pack();
    		            	 displayItemsBelowGivenQuantityFrame.setLocationRelativeTo(null);
    		            	 displayItemsBelowGivenQuantityFrame.setVisible(true);
    		            	 logger.info("User displays list of Item's below the given quantity");
    		             }
    		             catch(Exception ex) {
    		            	 System.out.println(ex);
    		             }    		   
    		   
    	   }
       });
       
       
       JButton addNewItemButton = new JButton("Add New Item");
       addNewItemButton.addActionListener(new ActionListener(){

/**
*This method is used to ask the User if they would like to add an Appliance or a Hardware Item
*when they click button2
@param e is when button2 is clicked
*/
            public void actionPerformed(ActionEvent e){
              JFrame addNewItemFrame = new JFrame("Add New Item");
              addNewItemFrame.setSize(400, 400);
              JButton hardwareItemButton = new JButton("Hardware Item");
              JButton applianceItemButton = new JButton("Appliance Item");
              JPanel addNewItemPanel = new JPanel();
              addNewItemPanel.add(hardwareItemButton);
              logger.info("User chooses if they want to add an Appliance or Hardware Item");
              hardwareItemButton.addActionListener(new ActionListener(){

/**
*This method is used to add a Hardware Item to the list.
*@param e is when the user clicks the button to add a Hardware Item
*@exception NumberFormatException is when the quantity or price fields cannot be parsed as
*an int or a float
*/
                public void actionPerformed(ActionEvent e) throws NumberFormatException {
                  JTextField idField = new JTextField(5);
                  JTextField nameField = new JTextField(12);
                  JTextField quantityField = new JTextField(4);
                  JTextField priceField = new JTextField(6);
                  JRadioButton option1 = new JRadioButton("Door&Window");
                  option1.setActionCommand("Door&Window");
                  JRadioButton option2 = new JRadioButton("Cabinet&Furniture");
                  option2.setActionCommand("Cabinet&Furniture");
                  JRadioButton option3 = new JRadioButton("Fasteners");
                  option3.setActionCommand("Fasteners");
                  JRadioButton option4 = new JRadioButton("Structural");
                  option4.setActionCommand("Structural");
                  JRadioButton option5 = new JRadioButton("Other");
                  option5.setActionCommand("Other");
                  ButtonGroup group = new ButtonGroup();
                  group.add(option1);
                  group.add(option2);
                  group.add(option3);
                  group.add(option4);
                  group.add(option5);
                  JPanel addNewHardwareItemPanel = new JPanel();
                  addNewHardwareItemPanel.add(new JLabel("ID: "));
                  addNewHardwareItemPanel.add(idField);
                  addNewHardwareItemPanel.add(new JLabel("Name: "));
                  addNewHardwareItemPanel.add(nameField);
                  addNewHardwareItemPanel.add(new JLabel("Quantity: "));
                  addNewHardwareItemPanel.add(quantityField);
                  addNewHardwareItemPanel.add(new JLabel("Price: "));
                  addNewHardwareItemPanel.add(priceField);
                  addNewHardwareItemPanel.add(option1);
                  addNewHardwareItemPanel.add(option2);
                  addNewHardwareItemPanel.add(option3);
                  addNewHardwareItemPanel.add(option4);
                  addNewHardwareItemPanel.add(option5);
                  addNewHardwareItemPanel.setLayout(new GridLayout(4,4));
                  String category = "";
                  String id = "";
                  String name = "";
                  Integer quantity = 0;
                  Float price = Float.parseFloat("0");
                  JOptionPane.showConfirmDialog(null, addNewHardwareItemPanel,
                  "Please Enter ID and Name of Item", JOptionPane.OK_CANCEL_OPTION);
                  addNewHardwareItemPanel.setLayout(new FlowLayout());
                  try
                  {
                    category = group.getSelection().getActionCommand();
                    name = nameField.getText().trim();
                    id = idField.getText().trim();
                    quantity = Integer.parseInt(quantityField.getText().trim());
                    price = Float.parseFloat(priceField.getText().trim());
                    addNewHardwareItemPanel.setLayout(new FlowLayout());
                    hardwareStore.addNewSmallHardwareItem(id,  name,  quantity,  price,  category);
                    logger.info("User adds new Hardware Item");
                  }
                  catch (NumberFormatException ex)
                  {
                    logger.log(Level.SEVERE, ex.getMessage(), ex);
                    System.out.println("Could not add new Item");
                  }
                }
              });
              addNewItemPanel.add(applianceItemButton);
          applianceItemButton.addActionListener(new ActionListener(){

            /**
            *This method is used to add an Appliance Item to the list.
            *@param e is when the user clicks the button to add an Appliance Item
            *@exception NumberFormatException is when the price field cannot be parsed as
            *a float
            */
            public void actionPerformed(ActionEvent e) throws NumberFormatException {
              JTextField idField = new JTextField(5);
              JTextField nameField = new JTextField(12);
              JTextField quantityField = new JTextField(4);
              JTextField priceField = new JTextField(6);
              JTextField brandField = new JTextField(10);
              JRadioButton option1 = new JRadioButton("Refrigerators");
              option1.setActionCommand("Refrigerators");
              JRadioButton option2 = new JRadioButton("Washers&Dryers");
              option2.setActionCommand("Washers&Dryers");
              JRadioButton option3 = new JRadioButton("Ranges&Ovens");
              option3.setActionCommand("Ranges&Ovens");
              JRadioButton option4 = new JRadioButton("Small Appliences");
              option4.setActionCommand("Small Appliences");
              ButtonGroup group = new ButtonGroup();
              group.add(option1);
              group.add(option2);
              group.add(option3);
              group.add(option4);
              JPanel addNewApplianceItemPanel = new JPanel();
              addNewApplianceItemPanel.add(new JLabel("ID: "));
              addNewApplianceItemPanel.add(idField);
              addNewApplianceItemPanel.add(new JLabel("Name: "));
              addNewApplianceItemPanel.add(nameField);
              addNewApplianceItemPanel.add(new JLabel("Quantity: "));
              addNewApplianceItemPanel.add(quantityField);
              addNewApplianceItemPanel.add(new JLabel("Price: "));
              addNewApplianceItemPanel.add(priceField);
              addNewApplianceItemPanel.add(new JLabel("Brand: "));
              addNewApplianceItemPanel.add(brandField);
              addNewApplianceItemPanel.add(option1);
              addNewApplianceItemPanel.add(option2);
              addNewApplianceItemPanel.add(option3);
              addNewApplianceItemPanel.add(option4);
              addNewApplianceItemPanel.setLayout(new GridLayout(4, 4));
              String brand = "";
              String type = "";
              String id = "";
              String name = "";
              Integer quantity = 0;
              Float price = Float.parseFloat("0");
              JOptionPane.showConfirmDialog(null, addNewApplianceItemPanel,
              "Please Enter ID and Name of Item", JOptionPane.OK_CANCEL_OPTION);
              try
              {
                brand = brandField.getText().trim();
                type = group.getSelection().getActionCommand();
                name = nameField.getText().trim();
                id = idField.getText().trim();
                quantity = Integer.parseInt(quantityField.getText().trim());
                price = Float.parseFloat(priceField.getText().trim());
                addNewApplianceItemPanel.setLayout(new FlowLayout());
                hardwareStore.addNewAppliance(id,  name,  quantity,  price, brand,  type);
                logger.info("User adds new Appliance Item");
              }
              catch (NumberFormatException ex)
              {
                System.out.println("Could not add new Item");
                logger.log(Level.SEVERE, ex.getMessage(), ex);
              }
            }
          });
          addNewItemFrame.add(addNewItemPanel);
          addNewItemFrame.pack();
          addNewItemFrame.setLocationRelativeTo(null);
          addNewItemFrame.setVisible(true);
        }
      });

       JButton deleteItemButton = new JButton("Delete an Item");
       deleteItemButton.addActionListener(new ActionListener(){

/**
*This method is used to delete and Item. It first asks the user to input the ID of the item they wish
*to delete. If the ID entered does not exist, then they keep getting requested to enter an ID of an Item
*that does exist. If they delete all of the quanity of the Item, then the entire Item is deleted from the list.
*@param e is when the user clicks button3
*@exception NumberFormatException is when the user does not input an int for the amount of quantity
*to delete.
*/
           public void actionPerformed(ActionEvent e){
             JPanel deleteItemPanel = new JPanel();
             JTextField numToDelete = new JTextField(5);
             String id = "";
             Integer quantity = 0;
             int indexToRemove = 0;
             id = JOptionPane.showInputDialog("ID of Item you wish to delete: ");
             indexToRemove = hardwareStore.findItemIndex(id);
             if (indexToRemove == -1){
            	 deleteItemPanel.setVisible(false);
             }
             deleteItemPanel.add(new JLabel("Quantity you wish to delete: "));
             deleteItemPanel.add(numToDelete);
             JOptionPane.showConfirmDialog(null, deleteItemPanel,
             "Given ID not found, please try again", JOptionPane.OK_CANCEL_OPTION);
             try
             {
               quantity = Integer.parseInt(numToDelete.getText().trim());
             }
             catch (NumberFormatException ex)
             {
               logger.log(Level.SEVERE, ex.getMessage(), ex);
               System.out.println("Could not delete Item");
             }
             hardwareStore.removeQuantity(indexToRemove, quantity);
             logger.info("User deletes specified quantity from given Item ");
             if( hardwareStore.findItem(id).getQuantity() < 0)
              hardwareStore.removeItem(indexToRemove);
              logger.info("User deletes Item from list");
           }});

       JButton searchItemButton = new JButton("Search for an Item");
       searchItemButton.addActionListener(new ActionListener(){

/**
*This method is used to Search for an item containg a given name, and display a list of all the Items
*that contain the given name.
*@param e is when the user clicks button4
*/
           public void actionPerformed(ActionEvent e){
             String name = "";
             name = JOptionPane.showInputDialog("Name of Item to search for : ");
             String str = hardwareStore.getMatchingItemsByName(name);
             JTextArea matchingItemsTextArea = new JTextArea(str);
             JFrame displayItemsWithGivenNameFrame = new JFrame("Displaying List of Items with Given Name");
             displayItemsWithGivenNameFrame.setSize(500, 100);
             JApplet displayItemsWithGivenNameApplet = new JApplet();
             displayItemsWithGivenNameApplet.getContentPane().add(matchingItemsTextArea);
             displayItemsWithGivenNameFrame.add(displayItemsWithGivenNameApplet);
             displayItemsWithGivenNameFrame.pack();
             displayItemsWithGivenNameFrame.setLocationRelativeTo(null);
             displayItemsWithGivenNameFrame.setVisible(true);
             logger.info("User displays list of Item's containing the given name");
        }});

       JButton showUsersButton = new JButton("Show list of Users");
       showUsersButton.addActionListener(new ActionListener(){

/**
*This method is used to show a list of all of the existing users.
*@param e is when the user clicks button5
*/
           public void actionPerformed(ActionEvent e){
           String str = hardwareStore.getAllUsersFormatted();
           JTextArea showUsersTextArea = new JTextArea(str);
           JFrame showUsersFrame = new JFrame("Displaying List of Users");
           showUsersFrame.setSize(500, 100);
           JApplet showUsersApplet = new JApplet();
           showUsersApplet.getContentPane().add(showUsersTextArea);
           showUsersFrame.add(showUsersApplet);
           showUsersFrame.pack();
           showUsersFrame.setLocationRelativeTo(null);
           showUsersFrame.setVisible(true);
           logger.info("User displays list of Users ");
    }
  });

       JButton addNewUserButton = new JButton("Add new User");
       addNewUserButton.addActionListener(new ActionListener(){

/**
*This method is used to ask the user if they would like to add a Customer or Employee when they
*click the button to add a new user
*@param e is when button 6 is clicked
*/
       public void actionPerformed(ActionEvent e){
         JFrame addNewUserFrame = new JFrame("Add New User");
         addNewUserFrame.setSize(400, 400);
         JButton employeeButton = new JButton("Employee");
         JButton customerButton = new JButton("Customer");
         JPanel addNewUserPanel = new JPanel();
         logger.info("User decides if they want to add a Customer or an Employee");
         addNewUserPanel.add(employeeButton);
         employeeButton.addActionListener(new ActionListener(){

/**
*This method is used to add a new Employee to the list.
*@param e is when the user clicks to add a new employee
*@exception NumberFormatException is when the input ssn and salary cannot be parsed as an int
*and a float
*/
           public void actionPerformed(ActionEvent e) throws NumberFormatException {
             JTextField firstNameField = new JTextField(10);
             JTextField lastNameField = new JTextField(10);
             JTextField ssnField = new JTextField(9);
             JTextField salaryField = new JTextField(5);
             JPanel addEmployeePanel = new JPanel();
             addEmployeePanel.add(new JLabel("First Name: "));
             addEmployeePanel.add(firstNameField);
             addEmployeePanel.add(new JLabel("Last Name: "));
             addEmployeePanel.add(lastNameField);
             addEmployeePanel.add(new JLabel("Social Security Number: "));
             addEmployeePanel.add(ssnField);
             addEmployeePanel.add(new JLabel("Monthly Salary: "));
             addEmployeePanel.add(salaryField);
             addEmployeePanel.setLayout(new GridLayout(4,4));
             String firstName = "";
             String lastName = "";
             Integer ssn = 0;
             JOptionPane.showConfirmDialog(null, addEmployeePanel,
             "Please Enter Employee Fields", JOptionPane.OK_CANCEL_OPTION);
             addEmployeePanel.setLayout(new FlowLayout());
             try
             {
               firstName = firstNameField.getText().trim();
               lastName = lastNameField.getText().trim();
               ssn = Integer.parseInt(ssnField.getText().trim());
               Float salary = Float.parseFloat(salaryField.getText().trim());
               addEmployeePanel.setLayout(new FlowLayout());
               hardwareStore.addEmployee(firstName,  lastName,  ssn,  salary);
               logger.info("User adds new Employee");
             }
             catch (NumberFormatException ex)
             {
               logger.log(Level.SEVERE, ex.getMessage(), ex);
               System.out.println("Could not add new Employee");
             }
           }
         });

         addNewUserPanel.add(customerButton);
         customerButton.addActionListener(new ActionListener(){

           /**
           *This method is used to add a new Customer to the list.
           *@param e is when the user clicks to add a new Customer
           *@exception NumberFormatException is when the input phone number cannot be parsed as an int
           */
           public void actionPerformed(ActionEvent e) throws NumberFormatException {
             JTextField firstNameField = new JTextField(10);
             JTextField lastNameField = new JTextField(10);
             JTextField phoneNumberField = new JTextField(10);
             JTextField addressField = new JTextField(12);
             JPanel addCustomerPanel = new JPanel();
             addCustomerPanel.add(new JLabel("First Name: "));
             addCustomerPanel.add(firstNameField);
             addCustomerPanel.add(new JLabel("Last Name: "));
             addCustomerPanel.add(lastNameField);
             addCustomerPanel.add(new JLabel("Phone Number: "));
             addCustomerPanel.add(phoneNumberField);
             addCustomerPanel.add(new JLabel("Address: "));
             addCustomerPanel.add(addressField);
             addCustomerPanel.setLayout(new GridLayout(4,4));
             String firstName = "";
             String lastName = "";
             String phoneNumber = "";
             String address = "";
             JOptionPane.showConfirmDialog(null, addCustomerPanel,
             "Please Enter Customer Fields", JOptionPane.OK_CANCEL_OPTION);
             addCustomerPanel.setLayout(new FlowLayout());
             try
             {
               firstName = firstNameField.getText().trim();
               lastName = lastNameField.getText().trim();
               phoneNumber = phoneNumberField.getText().trim();
               address = addressField.getText().trim();
               addCustomerPanel.setLayout(new FlowLayout());
               hardwareStore.addCustomer(firstName,  lastName,  phoneNumber,  address);
               logger.info("User adds new Customer");
             }
             catch (NumberFormatException ex)
             {
               System.out.println("Could not add new Customer");
               logger.log(Level.SEVERE, ex.getMessage(), ex);
             }
           }
         });
         addNewUserFrame.add(addNewUserPanel);
         addNewUserFrame.pack();
         addNewUserFrame.setLocationRelativeTo(null);
         addNewUserFrame.setVisible(true);

       }
     });

       JButton updateUserInfoButton = new JButton("Update User Info");
       updateUserInfoButton.addActionListener(new ActionListener(){

/**
*This method is used to ask if the user would like to update customer or employee info when they
*click the button to Update User Info
*@param e is when the user clikcs button7
*/
       public void actionPerformed(ActionEvent e){
         JFrame updateUserInfoFrame = new JFrame("Update User Info");
         updateUserInfoFrame.setSize(400, 400);
         JButton employeeButton = new JButton("Employee");
         JButton customerButton = new JButton("Customer");
         JPanel updateUserInfoPanel = new JPanel();
         updateUserInfoPanel.add(employeeButton);
         logger.info("User decides if they want to edit Customer or Employee info");
         employeeButton.addActionListener(new ActionListener(){

/**
*This method is used to update an Employee's Info. It first checks that the input ID exists, and then
*updates the Employee's info with the user input fields.
*@param e is when the user clicks to update Employee info
*@exception NumberFormatException is thrown when the ssn and salary cannot be parsed as a
*int and a float
*/
           public void actionPerformed(ActionEvent e) throws NumberFormatException {
             int id = Integer.parseInt(JOptionPane.showInputDialog("ID of Employee : "));
             int index = hardwareStore.findUserIndex(id);
             while (index == -1){
               id = Integer.parseInt(JOptionPane.showInputDialog("Employee not found, please enter new Employee ID: "));
               index = hardwareStore.findUserIndex(id);
             }

             JTextField firstNameField = new JTextField(10);
             JTextField lastNameField = new JTextField(10);
             JTextField ssnField = new JTextField(9);
             JTextField salaryField = new JTextField(5);
             JPanel updateEmployeePanel = new JPanel();
             updateEmployeePanel.add(new JLabel("First Name: "));
             updateEmployeePanel.add(firstNameField);
             updateEmployeePanel.add(new JLabel("Last Name: "));
             updateEmployeePanel.add(lastNameField);
             updateEmployeePanel.add(new JLabel("Social Security Number: "));
             updateEmployeePanel.add(ssnField);
             updateEmployeePanel.add(new JLabel("Monthly Salary: "));
             updateEmployeePanel.add(salaryField);
             updateEmployeePanel.setLayout(new GridLayout(4,4));
             String firstName = "";
             String lastName = "";
             Integer ssn = 0;
             JOptionPane.showConfirmDialog(null, updateEmployeePanel,
             "Edit Employee Info", JOptionPane.OK_CANCEL_OPTION);
             updateEmployeePanel.setLayout(new FlowLayout());
             try
             {
               firstName = firstNameField.getText().trim();
               lastName = lastNameField.getText().trim();
               ssn = Integer.parseInt(ssnField.getText().trim());
               Float salary = Float.parseFloat(salaryField.getText().trim());
               updateEmployeePanel.setLayout(new FlowLayout());
               hardwareStore.editEmployeeInformation(id, firstName,  lastName,  ssn,  salary);
               logger.info("User Updates Employee Info");
             }
             catch (NumberFormatException ex)
             {
               logger.log(Level.SEVERE, ex.getMessage(), ex);
               System.out.println("Could not edit Employee info");
             }
           }
         });

         updateUserInfoPanel.add(customerButton);
         customerButton.addActionListener(new ActionListener(){

 /**
*This method is used to update an Customer's Info. It first checks that the input ID exists, and then
*updates the Customer's info with the user input fields.
*@param e is when the user clicks to update Customer info
*@exception NumberFormatException is thrown when the inut phone number cannot be parsed as an
*int
*/
           public void actionPerformed(ActionEvent e) throws NumberFormatException {
             int id = Integer.parseInt(JOptionPane.showInputDialog("ID of Customer : "));
             int index = hardwareStore.findUserIndex(id);
             while (index == -1){
               id = Integer.parseInt(JOptionPane.showInputDialog("Customer not found, please enter new Customer ID: "));
               index = hardwareStore.findUserIndex(id);
             }
             JTextField firstNameField = new JTextField(10);
             JTextField lastNameField = new JTextField(10);
             JTextField phoneNumberField = new JTextField(10);
             JTextField addressField = new JTextField(12);
             JPanel updateCustomerPanel = new JPanel();
             updateCustomerPanel.add(new JLabel("First Name: "));
             updateCustomerPanel.add(firstNameField);
             updateCustomerPanel.add(new JLabel("Last Name: "));
             updateCustomerPanel.add(lastNameField);
             updateCustomerPanel.add(new JLabel("Phone Number: "));
             updateCustomerPanel.add(phoneNumberField);
             updateCustomerPanel.add(new JLabel("Address: "));
             updateCustomerPanel.add(addressField);
             updateCustomerPanel.setLayout(new GridLayout(4,4));
             String firstName = "";
             String lastName = "";
             String phoneNumber = "";
             String address = "";
             JOptionPane.showConfirmDialog(null, updateCustomerPanel,
             "Edit Customer Info", JOptionPane.OK_CANCEL_OPTION);
             updateCustomerPanel.setLayout(new FlowLayout());
             try
             {
               firstName = firstNameField.getText().trim();
               lastName = lastNameField.getText().trim();
               phoneNumber = phoneNumberField.getText().trim();
               address = addressField.getText().trim();
               updateCustomerPanel.setLayout(new FlowLayout());
               hardwareStore.editCustomerInformation(id, firstName,  lastName,  phoneNumber,  address);
               logger.info("User Updates Customer Info");
             }
             catch (NumberFormatException ex)
             {
               logger.log(Level.SEVERE, ex.getMessage(), ex);
               System.out.println("Could not add new Customer");
             }
           }
         });
         updateUserInfoFrame.add(updateUserInfoPanel);
         updateUserInfoFrame.pack();
         updateUserInfoFrame.setLocationRelativeTo(null);
         updateUserInfoFrame.setVisible(true);
       }
     });

       JButton completeTransactionButton = new JButton("Complete Sale Transaction");
       completeTransactionButton.addActionListener(new ActionListener(){

/**
*This method is used to complete a sale Transaction.
*@param e is when the user clicks button8
*/
       public void actionPerformed(ActionEvent e){
         String itemID = JOptionPane.showInputDialog("ID of Item : ");
         int itemIndex = hardwareStore.findItemIndex(itemID);
         while(itemIndex == -1){
           itemID = JOptionPane.showInputDialog("ID not found, enter a new ID : ");
           itemIndex = hardwareStore.findItemIndex(itemID);
           break;
         }
         int customerID = Integer.parseInt(JOptionPane.showInputDialog("ID of Customer : "));
         int customerIndex = hardwareStore.findUserIndex(customerID);
         while(customerIndex == -1){
           customerID = Integer.parseInt(JOptionPane.showInputDialog("ID not found, enter a new ID : "));
           customerIndex = hardwareStore.findUserIndex(customerID);
           break;
         }
         int employeeID = Integer.parseInt(JOptionPane.showInputDialog("ID of Employee : "));
         int employeeIndex = hardwareStore.findUserIndex(employeeID);
         while(employeeIndex == -1){
           employeeID = Integer.parseInt(JOptionPane.showInputDialog("ID not found, enter a new ID : "));
           employeeIndex = hardwareStore.findUserIndex(employeeID);
           break;
         }
         int quantitySold = Integer.parseInt(JOptionPane.showInputDialog("Quantity of Items Sold : "));
         while(quantitySold < 0){
           quantitySold = Integer.parseInt(JOptionPane.showInputDialog("Quantity Sold Must be a Positive Integer : "));
           break;
         }
         hardwareStore.progressTransaction(itemID, quantitySold, customerID, employeeID, itemIndex);
         if( hardwareStore.findItem(itemID).getQuantity() < 1) {
        	 int indexToRemove = hardwareStore.findItemIndex(itemID);
             hardwareStore.removeItem(indexToRemove);
         }
         logger.info("User Completes a Transaction");
       }});

       JButton showTransactionsButton = new JButton("Show List of Transactions");
       showTransactionsButton.addActionListener(new ActionListener(){

/**
*This method is used to show a list of all of the completed Transactions.
*@param e is when the user clicks button9
*/
          public void actionPerformed(ActionEvent e){
          String str = hardwareStore.getAllTransactionsFormatted();
          JTextArea showTransactionsTextArea = new JTextArea(str);
          JFrame showTransactionsFrame = new JFrame("Displaying List of Transactions");
          showTransactionsFrame.setSize(500, 100);
          JApplet applet = new JApplet();
          applet.getContentPane().add(showTransactionsTextArea);
          showTransactionsFrame.add(applet);
          showTransactionsFrame.pack();
          showTransactionsFrame.setLocationRelativeTo(null);
          showTransactionsFrame.setVisible(true);
          logger.info("User shows list of all Completed Transactions");
   }
 });
       
       card1.add(showItemsButton);
       card1.add(addNewItemButton);
       card1.add(addQuantityToExistingItem);
       card1.add(deleteItemButton);
       card1.add(searchItemButton);
       card1.add(searchForItemsBelowGivenQuantitiyButton);
       
       card2.add(showUsersButton);
       card2.add(addNewUserButton);
       card2.add(updateUserInfoButton);
       
       card3.add(showTransactionsButton);
       card3.add(completeTransactionButton);
       
       cards = new JPanel(new CardLayout());
       
       cards.add(card1, "Items Panel");
       cards.add(card2, "Users Panel");
       cards.add(card3, "Transactions Panel");
       
       JPanel comboBoxPane = new JPanel();
       String comboBoxItems[] = {"Items Panel", "Users Panel", "Transactions Panel"};
       @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cb = new JComboBox(comboBoxItems);
       cb.setEditable(false);
       cb.addItemListener(this);
       comboBoxPane.add(cb);
       
       mainPanel.add(comboBoxPane, BorderLayout.PAGE_START);
       mainPanel.add(cards, BorderLayout.CENTER);
       
       
       
       mainFrame.setLocationRelativeTo(null);
       mainFrame.setVisible(true);
}

/**
 * This method will begin the user GUI. It will exit when the user clicks the exit button on the mainframe.
 *
 * @param args this program expects no command line arguments
 * @throws Exception
 */

    public static void main(String[] args) throws Exception {

      MainApp app = new MainApp();
      logger.info("Logging begins...");
      app.createFrame();

    }
}
