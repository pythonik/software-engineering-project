package tests.business;
import java.util.ArrayList;
import ems.business.AccessTimesheet;
import ems.models.Employee;
import ems.models.Project;
import ems.persistence.DataBaseInterface;
import ems.persistence.DatabaseAdapter;
import ems.persistence.DatabaseStub;
import junit.framework.TestCase;

public class AccessTimesheetTest extends TestCase {

	protected void setUp() throws Exception {
		DataBaseInterface db = new DatabaseStub("TestDBAccess");
		db.init();
		DatabaseAdapter.setAdapterInterface(db);
		
		super.setUp();
	}
	
	public void testAccessUserWithOutDataBase(){
		
		System.out.println("\nStarting AccessTimesheetTest: getting accessor without database");
		DatabaseAdapter.release();
		try{
			new AccessTimesheet(null);
			fail("IllegalState Exception should be raised");
		}catch(IllegalAccessError e){
			//test passed if exception caught
		}
		System.out.println("Finished AccessTimesheetTest: getting accessor without database");
	}
	
	public void testGetTimesheetByUser(){
		
		System.out.println("\nStarting GetTimesheetByUser: getting user's timesheet");
		Employee bot = DatabaseAdapter.getAllUser().get(1);
		AccessTimesheet records = new AccessTimesheet(bot);
		assertNotNull(records.getTimesheetByUser(bot));
		System.out.println("Finished GetTimesheetByUser: getting user's timesheet");

	}
	
	public void testGetTimesheetByNullUser(){
		
		System.out.println("\nStarting GetTimesheetByUser: getting Null User timesheet");
		AccessTimesheet records = new AccessTimesheet();
		assertEquals(0, records.getTimesheetByUser(null).size());
		System.out.println("Finished GetTimesheetByUser: getting Null User timesheet");

	}
	
	public void testAllTimesheetByCurrentUser(){
		
		System.out.println("\nStarting AccessTimesheetTest: getting all of user's timesheets");
		ArrayList<Employee> bots = DatabaseAdapter.getAllUser();
		for(Employee el:bots){
			AccessTimesheet records = new AccessTimesheet(el);
			if (el.getUserFirstName().equals("B")){
				assertEquals(5, records.getCurrentUserTimesheet().size());
			} else {
				assertEquals(1, records.getCurrentUserTimesheet().size());
			}
		}
		System.out.println("Finished AccessTimesheetTest: getting all of user's timesheets");
	}
	
	public void testGetTimesheetNullCurrentUser(){
		
		System.out.println("\nStarting testGetCurrentUserTimesheet: getting  null current user's timesheet");
		AccessTimesheet records = new AccessTimesheet(null);
		assertNull(records.getCurrentUserTimesheet());
		System.out.println("Finished testGetCurrentUserTimesheet: getting null current user's timesheet");
	}
	
	public void testGetgetCurrentInvalidUserTimesheet(){
		
		System.out.println("\nStarting GetgetCurrentInvalidUserTimesheet: getting timesheet from invalid user");
		Employee notExist = new Employee("Not", "Exsit",null);
		AccessTimesheet timesheet = new AccessTimesheet(notExist);
		assertEquals(0, timesheet.getCurrentUserTimesheet().size());
		System.out.println("Finished GetgetCurrentInvalidUserTimesheet: getting timesheet from invalid user");
	}
	
	public void testGetAllTimesheet(){
		
		System.out.println("\nStarting AccessTimesheetTest: getting all timesheets");
		AccessTimesheet timesheets = new AccessTimesheet();
		assertEquals(11, timesheets.getAllTimesheet().size());
		System.out.println("Finished AccessTimesheetTest: getting all timesheets");
	}
	
	public void testGetTimesheetByProject(){
		
		System.out.println("\nStarting AccessTimesheetTest: getting user's timesheet");
		AccessTimesheet timesheets = new AccessTimesheet();
		assertEquals(4, timesheets.getTimesheetByProject(new Project("BusyBox")).size());
		assertEquals(7, timesheets.getTimesheetByProject(new Project("BackFire")).size());
		System.out.println("Finished AccessTimesheetTest: getting user's timesheet");
	}
	
	public void testGetTimesheetFromNullProject(){
		System.out.println("\nStarting testGetTimesheetByProject: null project");
		AccessTimesheet timesheets = new AccessTimesheet();
		assertEquals(0, timesheets.getTimesheetByProject(null).size());
		System.out.println("Finished testGetTimesheetByProjectS: null project");
	}
	
	public void testIsUserSetNullUser(){		
		System.out.println("\nStarting testIsUserSet: setting null user");
		AccessTimesheet timesheets = new AccessTimesheet(null);
		assertFalse(timesheets.isUserSet());
		System.out.println("Finished testIsUserSet: setting null user");
	}
	
	public void testInsertTimesheetNullTimesheet(){
		System.out.println("\nStarting AccessTimesheetTest: inserting null timesheet");
		AccessTimesheet timesheets = new AccessTimesheet();
		assertFalse(timesheets.insertTimesheet(null));
		System.out.println("Finished AccessTimesheetTest: inserting null timesheet");
	}
	
}
