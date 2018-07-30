
package hardwarestore;

import java.util.logging.*;
import java.util.ArrayList;
import hardwarestore.items.Item;
import hardwarestore.users.User;
import java.awt.*;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.geom.*;
import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;

/**
 * This is the main class of the Hardware Store database manager. It provides a
 * GUI for the user to input data
 *
 * @author Junye Wen modified by @author William Hubert
 *
 */
public class MainApp extends JFrame{

  // This object is used to log all User interactions with the GUI and for possible errors and exceptions
  private static final Logger logger = Logger.getLogger(MainApp.class.getName());
  //This object is used to write all logging messages to a file
  private static FileHandler fh = null;
    // This object will allow us to interact with the methods of the class HardwareStore
    private HardwareStore hardwareStore;
    private static final Scanner CONSOLE_INPUT = new Scanner(System.in); // Used to read from System's standard input

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
*This method is used to creat the GUI main frame that holds all of the GUI components
*used in this program.
*/
     public void createFrame() throws Exception{
        JFrame frame = new JFrame("Hardware Store");
        logger.info("Main Frame is created");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {

/**
*This method is used to add a text box asking if the user really wants to exit the Program
*when the exit button is clicked. It then saves the database if the user checks that
*they really want to exit the program.
*@param java.awt.event is used as the action when the user clicks to exit the program.
*@exception e is the event when the database cannot be saved
*/
          @Override
          public void windowClosing(java.awt.event.WindowEvent windowEvent) {
          if (JOptionPane.showConfirmDialog(frame,
            "Are you sure to close this window?", "Really Closing?",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
              try
              {
                saveDatabase();
                JFrame frame2 = new JFrame("Exiting Program");
                JLabel label2 = new JLabel("Saving database before exit...");
                frame2.add(label2);
                frame2.setVisible(true);
                frame2.pack();
                logger.info("Database is saved");
              }
              catch (IOException e)
              {
                logger.log(Level.SEVERE, e.getMessage(), e);
                JFrame frame2 = new JFrame("Exiting Program");
                JLabel label2 = new JLabel("Exception caught, database not saved before exit...");
                frame2.add(label2);
                frame2.setVisible(true);
                frame2.pack();
                System.exit(0);
              }
            System.exit(0);
        }
    }
});

       frame.setSize(600, 200);
       JPanel panel = new JPanel();
       frame.add(panel);
       JButton button1 = new JButton("Show All Existing Items");
       panel.add(button1);
       button1.addActionListener(new ActionListener(){

/**
*This method is used to show all of the Items in the list when the user clicks button 1
*@param e is the action of the user clicking button1
*/
           public void actionPerformed(ActionEvent e){
           HardwareStore.sortItemList();
           String str = hardwareStore.getAllItemsFormatted();
           JTextArea textArea = new JTextArea(str);
           JFrame frame2 = new JFrame("Displaying List of Items");
           frame2.setSize(500, 100);
           JApplet applet = new JApplet();
           applet.getContentPane().add(textArea);
           frame2.add(applet);
           frame2.pack();
           frame2.setLocationRelativeTo(null);
           frame2.setVisible(true);
           logger.info("User displays list of all Existing Items");
    }
  });

       JButton button2 = new JButton("Add New Item");
       panel.add(button2);
       button2.addActionListener(new ActionListener(){

/**
*This method is used to ask the User if they would like to add an Appliance or a Hardware Item
*when they click button2
@param e is when button2 is clicked
*/
            public void actionPerformed(ActionEvent e){
              JFrame frame2 = new JFrame("Add New Item");
              frame2.setSize(400, 400);
              JButton button1 = new JButton("Hardware Item");
              JButton button2 = new JButton("Appliance Item");
              JPanel panel = new JPanel();
              panel.add(button1);
              logger.info("User chooses if they want to add an Appliance or Hardware Item");
              button1.addActionListener(new ActionListener(){

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
                  JPanel myPanel = new JPanel();
                  myPanel.add(new JLabel("ID: "));
                  myPanel.add(idField);
                  myPanel.add(new JLabel("Name: "));
                  myPanel.add(nameField);
                  myPanel.add(new JLabel("Quantity: "));
                  myPanel.add(quantityField);
                  myPanel.add(new JLabel("Price: "));
                  myPanel.add(priceField);
                  myPanel.add(option1);
                  myPanel.add(option2);
                  myPanel.add(option3);
                  myPanel.add(option4);
                  myPanel.add(option5);
                  myPanel.setLayout(new GridLayout(4,4));
                  String category = "";
                  String id = "";
                  String name = "";
                  Integer quantity = 0;
                  Float price = Float.parseFloat("0");
                  int result = JOptionPane.showConfirmDialog(null, myPanel,
                  "Please Enter ID and Name of Item", JOptionPane.OK_CANCEL_OPTION);
                  myPanel.setLayout(new FlowLayout());
                  try
                  {
                    category = group.getSelection().getActionCommand();
                    name = nameField.getText().trim();
                    id = idField.getText().trim();
                    quantity = Integer.parseInt(quantityField.getText().trim());
                    price = Float.parseFloat(priceField.getText().trim());
                    myPanel.setLayout(new FlowLayout());
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
          panel.add(button2);
          button2.addActionListener(new ActionListener(){

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
              JPanel myPanel = new JPanel();
              myPanel.add(new JLabel("ID: "));
              myPanel.add(idField);
              myPanel.add(new JLabel("Name: "));
              myPanel.add(nameField);
              myPanel.add(new JLabel("Quantity: "));
              myPanel.add(quantityField);
              myPanel.add(new JLabel("Price: "));
              myPanel.add(priceField);
              myPanel.add(new JLabel("Brand: "));
              myPanel.add(brandField);
              myPanel.add(option1);
              myPanel.add(option2);
              myPanel.add(option3);
              myPanel.add(option4);
              myPanel.setLayout(new GridLayout(4, 4));
              String brand = "";
              String type = "";
              String id = "";
              String name = "";
              Integer quantity = 0;
              Float price = Float.parseFloat("0");
              int result = JOptionPane.showConfirmDialog(null, myPanel,
              "Please Enter ID and Name of Item", JOptionPane.OK_CANCEL_OPTION);
              try
              {
                brand = brandField.getText().trim();
                type = group.getSelection().getActionCommand();
                name = nameField.getText().trim();
                id = idField.getText().trim();
                quantity = Integer.parseInt(quantityField.getText().trim());
                price = Float.parseFloat(priceField.getText().trim());
                myPanel.setLayout(new FlowLayout());
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
          frame2.add(panel);
          frame2.pack();
          frame2.setLocationRelativeTo(null);
          frame2.setVisible(true);
        }
      });

       JButton button3 = new JButton("Delete an Item");
       panel.add(button3);
       button3.addActionListener(new ActionListener(){

/**
*This method is used to delete and Item. It first asks the user to input the ID of the item they wish
*to delete. If the ID entered does not exist, then they keep getting requested to enter an ID of an Item
*that does exist. If they delete all of the quanity of the Item, then the entire Item is deleted from the list.
*@param e is when the user clicks button3
*@exception NumberFormatException is when the user does not input an int for the amount of quantity
*to delete.
*/
           public void actionPerformed(ActionEvent e){
             JPanel panel = new JPanel();
             JTextField idTextField = new JTextField(5);
             JTextField numToDelete = new JTextField(5);
             String id = "";
             Integer quantity = 0;
             int indexToRemove = 0;
             id = JOptionPane.showInputDialog("ID of Item you wish to delete: ");
             indexToRemove = hardwareStore.findItemIndex(id);
             while (indexToRemove == -1){
               id = JOptionPane.showInputDialog("No Item found with given ID, try a new one: ");
               indexToRemove = hardwareStore.findItemIndex(id);
             }
             panel.add(new JLabel("Quantity you wish to delete: "));
             panel.add(numToDelete);
             int result = JOptionPane.showConfirmDialog(null, panel,
             "Please Enter ID and Quantity to delete", JOptionPane.OK_CANCEL_OPTION);
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
             if( hardwareStore.findItem(id).getQuantity() == 0)
              hardwareStore.removeItem(indexToRemove);
              logger.info("User deletes Item from list");
           }});

       JButton button4 = new JButton("Search for an Item");
       panel.add(button4);
       button4.addActionListener(new ActionListener(){

/**
*This method is used to Search for an item containg a given name, and display a list of all the Items
*that contain the given name.
*@param e is when the user clicks button4
*/
           public void actionPerformed(ActionEvent e){
             JPanel panel = new JPanel();
             String name = "";
             name = JOptionPane.showInputDialog("Name of Item to search for : ");
             String str = hardwareStore.getMatchingItemsByName(name);
             JTextArea textArea = new JTextArea(str);
             JFrame frame2 = new JFrame("Displaying List of Items with Given Name");
             frame2.setSize(500, 100);
             JApplet applet = new JApplet();
             applet.getContentPane().add(textArea);
             frame2.add(applet);
             frame2.pack();
             frame2.setLocationRelativeTo(null);
             frame2.setVisible(true);
             logger.info("User displays list of Item's containing the given name");
        }});

       JButton button5 = new JButton("Show list of Users");
       panel.add(button5);
       button5.addActionListener(new ActionListener(){

/**
*This method is used to show a list of all of the existing users.
*@param e is when the user clicks button5
*/
           public void actionPerformed(ActionEvent e){
           String str = hardwareStore.getAllUsersFormatted();
           JTextArea textArea = new JTextArea(str);
           JFrame frame2 = new JFrame("Displaying List of Users");
           frame2.setSize(500, 100);
           JApplet applet = new JApplet();
           applet.getContentPane().add(textArea);
           frame2.add(applet);
           frame2.pack();
           frame2.setLocationRelativeTo(null);
           frame2.setVisible(true);
           logger.info("User displays list of Users ");
    }
  });

       JButton button6 = new JButton("Add new User");
       panel.add(button6);
       button6.addActionListener(new ActionListener(){

/**
*This method is used to ask the user if they would like to add a Customer or Employee when they
*click the button to add a new user
*@param e is when button 6 is clicked
*/
       public void actionPerformed(ActionEvent e){
         JFrame frame2 = new JFrame("Add New User");
         frame2.setSize(400, 400);
         JButton button1 = new JButton("Employee");
         JButton button2 = new JButton("Customer");
         JPanel panel = new JPanel();
         logger.info("User decides if they want to add a Customer or an Employee");
         panel.add(button1);
         button1.addActionListener(new ActionListener(){

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
             JPanel myPanel = new JPanel();
             myPanel.add(new JLabel("First Name: "));
             myPanel.add(firstNameField);
             myPanel.add(new JLabel("Last Name: "));
             myPanel.add(lastNameField);
             myPanel.add(new JLabel("Social Security Number: "));
             myPanel.add(ssnField);
             myPanel.add(new JLabel("Monthly Salary: "));
             myPanel.add(salaryField);
             myPanel.setLayout(new GridLayout(4,4));
             String firstName = "";
             String lastName = "";
             Integer ssn = 0;
             int result = JOptionPane.showConfirmDialog(null, myPanel,
             "Please Enter Employee Fields", JOptionPane.OK_CANCEL_OPTION);
             myPanel.setLayout(new FlowLayout());
             try
             {
               firstName = firstNameField.getText().trim();
               lastName = lastNameField.getText().trim();
               ssn = Integer.parseInt(ssnField.getText().trim());
               Float salary = Float.parseFloat(salaryField.getText().trim());
               myPanel.setLayout(new FlowLayout());
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

         panel.add(button2);
         button2.addActionListener(new ActionListener(){

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
             JPanel myPanel = new JPanel();
             myPanel.add(new JLabel("First Name: "));
             myPanel.add(firstNameField);
             myPanel.add(new JLabel("Last Name: "));
             myPanel.add(lastNameField);
             myPanel.add(new JLabel("Phone Number: "));
             myPanel.add(phoneNumberField);
             myPanel.add(new JLabel("Address: "));
             myPanel.add(addressField);
             myPanel.setLayout(new GridLayout(4,4));
             String firstName = "";
             String lastName = "";
             String phoneNumber = "";
             String address = "";
             int result = JOptionPane.showConfirmDialog(null, myPanel,
             "Please Enter Customer Fields", JOptionPane.OK_CANCEL_OPTION);
             myPanel.setLayout(new FlowLayout());
             try
             {
               firstName = firstNameField.getText().trim();
               lastName = lastNameField.getText().trim();
               phoneNumber = phoneNumberField.getText().trim();
               address = addressField.getText().trim();
               myPanel.setLayout(new FlowLayout());
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
         frame2.add(panel);
         frame2.pack();
         frame2.setLocationRelativeTo(null);
         frame2.setVisible(true);

       }
     });

       JButton button7 = new JButton("Update User Info");
       panel.add(button7);
       button7.addActionListener(new ActionListener(){

/**
*This method is used to ask if the user would like to update customer or employee info when they
*click the button to Update User Info
*@param e is when the user clikcs button7
*/
       public void actionPerformed(ActionEvent e){
         JFrame frame2 = new JFrame("Update User Info");
         frame2.setSize(400, 400);
         JButton button1 = new JButton("Employee");
         JButton button2 = new JButton("Customer");
         JPanel panel = new JPanel();
         panel.add(button1);
         logger.info("User decides if they want to edit Customer or Employee info");
         button1.addActionListener(new ActionListener(){

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
             JPanel myPanel = new JPanel();
             myPanel.add(new JLabel("First Name: "));
             myPanel.add(firstNameField);
             myPanel.add(new JLabel("Last Name: "));
             myPanel.add(lastNameField);
             myPanel.add(new JLabel("Social Security Number: "));
             myPanel.add(ssnField);
             myPanel.add(new JLabel("Monthly Salary: "));
             myPanel.add(salaryField);
             myPanel.setLayout(new GridLayout(4,4));
             String firstName = "";
             String lastName = "";
             Integer ssn = 0;
             int result = JOptionPane.showConfirmDialog(null, myPanel,
             "Edit Employee Info", JOptionPane.OK_CANCEL_OPTION);
             myPanel.setLayout(new FlowLayout());
             try
             {
               firstName = firstNameField.getText().trim();
               lastName = lastNameField.getText().trim();
               ssn = Integer.parseInt(ssnField.getText().trim());
               Float salary = Float.parseFloat(salaryField.getText().trim());
               myPanel.setLayout(new FlowLayout());
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

         panel.add(button2);
         button2.addActionListener(new ActionListener(){

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
             JPanel myPanel = new JPanel();
             myPanel.add(new JLabel("First Name: "));
             myPanel.add(firstNameField);
             myPanel.add(new JLabel("Last Name: "));
             myPanel.add(lastNameField);
             myPanel.add(new JLabel("Phone Number: "));
             myPanel.add(phoneNumberField);
             myPanel.add(new JLabel("Address: "));
             myPanel.add(addressField);
             myPanel.setLayout(new GridLayout(4,4));
             String firstName = "";
             String lastName = "";
             String phoneNumber = "";
             String address = "";
             int result = JOptionPane.showConfirmDialog(null, myPanel,
             "Edit Customer Info", JOptionPane.OK_CANCEL_OPTION);
             myPanel.setLayout(new FlowLayout());
             try
             {
               firstName = firstNameField.getText().trim();
               lastName = lastNameField.getText().trim();
               phoneNumber = phoneNumberField.getText().trim();
               address = addressField.getText().trim();
               myPanel.setLayout(new FlowLayout());
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
         frame2.add(panel);
         frame2.pack();
         frame2.setLocationRelativeTo(null);
         frame2.setVisible(true);
       }
     });

       JButton button8 = new JButton("Complete Sale Transaction");
       panel.add(button8);
       button8.addActionListener(new ActionListener(){

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
         }
         int customerID = Integer.parseInt(JOptionPane.showInputDialog("ID of Customer : "));
         int customerIndex = hardwareStore.findUserIndex(customerID);
         while(customerIndex == -1){
           customerID = Integer.parseInt(JOptionPane.showInputDialog("ID not found, enter a new ID : "));
           customerIndex = hardwareStore.findUserIndex(customerID);
         }
         int employeeID = Integer.parseInt(JOptionPane.showInputDialog("ID of Employee : "));
         int employeeIndex = hardwareStore.findUserIndex(employeeID);
         while(employeeIndex == -1){
           employeeID = Integer.parseInt(JOptionPane.showInputDialog("ID not found, enter a new ID : "));
           employeeIndex = hardwareStore.findUserIndex(employeeID);
         }
         int quantitySold = Integer.parseInt(JOptionPane.showInputDialog("Quantity of Items Sold : "));
         while(quantitySold < 0){
           quantitySold = Integer.parseInt(JOptionPane.showInputDialog("Quantity Sold Must be a Positive Integer : "));
         }
         hardwareStore.progressTransaction(itemID, quantitySold, customerID, employeeID, itemIndex);
         logger.info("User Completes a Transaction");
       }});

       JButton button9 = new JButton("Show List of Transactions");
       panel.add(button9);
      button9.addActionListener(new ActionListener(){

/**
*This method is used to show a list of all of the completed Transactions.
*@param e is when the user clicks button9
*/
          public void actionPerformed(ActionEvent e){
          String str = hardwareStore.getAllTransactionsFormatted();
          JTextArea textArea = new JTextArea(str);
          JFrame frame2 = new JFrame("Displaying List of Transactions");
          frame2.setSize(500, 100);
          JApplet applet = new JApplet();
          applet.getContentPane().add(textArea);
          frame2.add(applet);
          frame2.pack();
          frame2.setLocationRelativeTo(null);
          frame2.setVisible(true);
          logger.info("User shows list of all Completed Transactions");
   }
 });
 frame.setLocationRelativeTo(null);
 frame.setVisible(true);
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
