package hardwarestore;

import java.io.IOException;
import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;

import java.lang.Exception;

public class HardwareStoreTest {
	
	public ArrayList<Item> itemList;
	public HardwareStore hardwareStore;
	public ArrayList<Item> expectedItemList;
	public ArrayList<User> userList;
	
	@Before
	public void setUpEnvironmnet() throws IOException {
		System.out.println("START setting up environment");
		itemList = new ArrayList<>();
		expectedItemList = new ArrayList<>();
		hardwareStore = new HardwareStore();
		userList = new ArrayList<>();
		System.out.println("END setting up environment");
	}

	@After
	public void clearEnvironment() {
		itemList = null;
		expectedItemList = null;
		hardwareStore = null;
		userList = null;
		System.out.println("CLEARED environemnt");
	}
	
	

	@Test(expected = Error.class)
	public void addNewApplianceExpectedError() {
		hardwareStore.addNewAppliance(-1, -1, -1, -1, -1, -1);
		//expected to cause an error, as the paramters are not compatible with the method
		((Object) hardwareStore).throwError();
	}
	
	@Test
	public void shouldAddNewApplianceItem() throws IOException {
		hardwareStore.addNewAppliance("aaa11", "Fridge2000", 100, (float)499.99, "LG", "Refrigerators");
		Item itemAdded = hardwareStore.getItem(0);
		Item expectedItem = hardwareStore.findItem("aaa11");
		assertEquals("addNewSmallHardwareItem test has failed...", expectedItem, itemAdded);
	}
	
	@Test
	public void shouldAddNewCustomer(){
		hardwareStore.addCustomer("William", "Hubert", "512-618-2740", "1001 Ell Rd.");
		User userAdded = hardwareStore.findUser(1);
		//first user ID should be 1
		User expectedUser = hardwareStore.getUser(0);
		assertEquals("addNewCustomer test has failed...", userAdded, expectedUser);
	}
	
	@Test
	public void shouldAddNewEmployee(){
		hardwareStore.addEmployee("Warren", "Hubert", 627534008, (float)1199.99);
		User userAdded = hardwareStore.findUser(2);
		//first user ID should be 1
		User expectedUser = hardwareStore.getUser(1);
		assertEquals("addNewCustomer test has failed...", userAdded, expectedUser);
	}
	
	@Test
	public void shouldAddNewHardwareItem() throws IOException {
		hardwareStore.addNewSmallHardwareItem("wch45", "Doorknob 3in", 44, (float)19.95, "Other");
		Item itemAdded = hardwareStore.getItem(1);
		Item expectedItem = hardwareStore.findItem("wch45");
		assertEquals("addNewSmallHardwareItem test has failed...", expectedItem, itemAdded);
	}

	
	@Test
	public void shouldFindItemIndex() {
		hardwareStore.addNewSmallHardwareItem("wch45", "Doorknob 3in", 44, (float)19.95, "Other");
		int itemToBeFound = hardwareStore.findItemIndex("wch45");
		assertEquals("findItem test has failed...", itemToBeFound, 1);
	}
	
	@Test
	public void shouldNotFindItemIndex() {
		int itemToBeFound = hardwareStore.findItemIndex("abc123");
		assertTrue(itemToBeFound == -1);
	}
	
	@Test
	public void getItemThatDoesNotExist() {
		Item resultItem = hardwareStore.getItem(44);
		//the index 44 does not exist in the itemList, therefore it should return null
		assertNull(resultItem);
	}
	
	@Test
	public void shouldAddGivenQuantity() {
		int givenQuantity = 5;
		hardwareStore.addNewSmallHardwareItem("wch45", "Doorknob 3in", 44, (float)19.95, "Other");
		hardwareStore.addQuantity(1, givenQuantity);
		Item item = hardwareStore.getItem(1);
		int newQuantity = item.getQuantity();
		assertEquals("addQuantity test has failed...", newQuantity, 49);
		hardwareStore.addQuantity(1, givenQuantity);
		newQuantity = item.getQuantity();
		assertEquals("addQuantity test has failed...", newQuantity, 54);		
	}
	
	@Test
	public void shouldRemoveGivenQuantity() {
		int givenQuantity = 5;
		hardwareStore.addNewSmallHardwareItem("wch45", "Doorknob 3in", 44, (float)19.95, "Other");
		hardwareStore.removeQuantity(1, givenQuantity);
		Item item = hardwareStore.getItem(1);
		int newQuantity = item.getQuantity();
		assertEquals("addQuantity test has failed...", newQuantity, 39);
		hardwareStore.removeQuantity(1, givenQuantity);
		newQuantity = item.getQuantity();
		assertEquals("addQuantity test has failed...", newQuantity, 34);		
	}
	
	@Test
	public void shouldGetMatchingItemsByQuantity(){
		hardwareStore.addNewAppliance("aaa11", "Fridge2000", 100, (float)499.99, "LG", "Refrigerators");
		hardwareStore.addNewAppliance("bbb22", "x150Fridge", 50, (float)649.95, "Samsung", "Refrigerators");
		expectedItemList.add(new Appliances("aaa11", "Fridge2000", 100, (float)499.99, "LG", "Refrigerators"));
		expectedItemList.add(new Appliances("bbb22", "x150Fridge", 50, (float)649.95, "Samsung", "Refrigerators"));

		String itemsBelowGivenQuantity = hardwareStore.getMatchingItemsByQuantity(101);
		String expectedItems = hardwareStore.getFormattedItemList(expectedItemList);
		assertEquals("getMatchingItemsByQuantity test has failed...", itemsBelowGivenQuantity, expectedItems);
		
		itemsBelowGivenQuantity = hardwareStore.getMatchingItemsByQuantity(49);
		//no items have below 49 quantity, therefore the result should be null
		assertNull("getMatchingItemsByQuantity test has failed...", itemsBelowGivenQuantity);
	}
	
	@Test
	public void shouldGetMatchingItemsByNam(){
		hardwareStore.addNewAppliance("aaa11", "Fridge2000", 100, (float)499.99, "LG", "Refrigerators");
		hardwareStore.addNewAppliance("bbb22", "x150Fridge", 50, (float)649.95, "Samsung", "Refrigerators");
		expectedItemList.add(new Appliances("aaa11", "Fridge2000", 100, (float)499.99, "LG", "Refrigerators"));
		
		String itemsWithMatchingName = hardwareStore.getMatchingItemsByName("Fridge2");
		String expectedResult = hardwareStore.getFormattedItemList(expectedItemList);
		assertEquals("getMatchingItemsByName test has failed...", itemsWithMatchingName, expectedResult);
		
		itemsWithMatchingName = hardwareStore.getMatchingItemsByName("zlsoda");
		//no item with given name exists, therefore it should be null
		assertNull("getMatchingItemsByName test has failed...", itemsWithMatchingName);
	}
	
	@Test
	public void shouldFindItem(){
		hardwareStore.addNewAppliance("aaa11", "Fridge2000", 100, (float)499.99, "LG", "Refrigerators");
		hardwareStore.addNewAppliance("bbb22", "x150Fridge", 50, (float)649.95, "Samsung", "Refrigerators");
		Item itemFound = hardwareStore.findItem("aaa11");
		Item expectedItemFound = hardwareStore.getItem(0);
		assertEquals("findItem test has failed...", itemFound.toString(), expectedItemFound.toString());
		
		itemFound = hardwareStore.findItem("abc123");
		//no item with given ID exists, therefore it should return null
		assertNull("findItem test has failed...", itemFound);
	}
	
	

}
