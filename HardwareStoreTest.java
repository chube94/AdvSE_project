package hardwarestore;

import java.io.IOException;
import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import hardwarestore.Item;
import hardwarestore.SmallHardwareItems;

import java.lang.Exception;

public class HardwareStoreTest {
	
	public ArrayList<Item> itemList;
	public HardwareStore hardwareStore;
	public ArrayList<Item> expectedItemList;
	
	@Before
	public void setUpEnvironmnet() throws IOException {
		System.out.println("START setting up environment");
		itemList = new ArrayList<>();
		expectedItemList = new ArrayList<>();
		hardwareStore = new HardwareStore();
		System.out.println("END setting up environment");
	}

	@After
	public void clearEnvironment() {
		itemList = null;
		expectedItemList = null;
		hardwareStore = null;
		System.out.println("CLEARED environemnt");
	}
	
	

	@Test(expected = Error.class)
	public void addNewApplianceExpectedError() {
		hardwareStore.addNewAppliance(-1, -1, -1, -1, -1, -1);
		//expected to cause an error, as the paramters are not compatible with the method
		((Object) hardwareStore).throwError();
	}
	
/*	
	@Test
	public void shouldAddNewHardwareItem() throws IOException {
		hardwareStore.addNewSmallHardwareItem("wch45", "Doorknob 3in", 44, (float)19.95, "Other");
		Item itemAdded = hardwareStore.getItem(1);
		Item expectedItem = new SmallHardwareItems("wch45", "Doorknob 3in", 44, (float)19.95, "Other");

		assertEquals("addNewSmallHardwareItem test has failed...", itemAdded.toString(), expectedItem.toString());

		System.out.println("***CHECK HERE***");
		System.out.println(itemAdded.toString());
		System.out.println(expectedItem.toString());
	}
*/
	
	@Test
	public void shouldFindItem() {
		int itemToBeFound = hardwareStore.findItemIndex("wch46");
		assertEquals("findItem test has failed...", itemToBeFound, 0);
	}
	
	@Test
	public void shouldNotFindItem() {
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

}
