package tests.business;
import ems.persistence.DataBaseInterface;
import ems.persistence.DatabaseAdapter;
import ems.persistence.DatabaseStub;
import junit.framework.TestCase;

public class DataBaseAccessTest extends TestCase {

	protected void setUp() throws Exception {
		DataBaseInterface db = new DatabaseStub("TestDBAccess");
		db.init();
		DatabaseAdapter.setAdapterInterface(db);
		
		super.setUp();
	}
	
	public void testConnection() {
		
		System.out.println("\nStarting DataBaseAccessTest: setting up database");
		
		assertEquals(true, DatabaseAdapter.databaseIsSet());
		
		System.out.println("Finished DataBaseAccessTest: setting up database");
		
		
	}
}
