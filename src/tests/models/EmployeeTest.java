package tests.models;
import ems.models.Employee;
import ems.models.Manager;
import ems.models.Financial;
import junit.framework.TestCase;

public class EmployeeTest extends TestCase {
	
	
	public void testCreateEmployees(){
		
		System.out.println("\nStarting EmployeeTest: creating employee");
		
		Employee bot = new Employee("SMITH","JOHN",null);
		Manager fake = new Manager("Boss","Boss",null);
		Financial fraud = new Financial("Roy", "Yor",null);
		assertTrue(bot != null);
		assertTrue(fake != null);
		assertTrue(fraud != null);
		
		System.out.println("Finished EmployeeTest: creating employee");
	}
	
	public void testGetUserName(){
		
		System.out.println("\nStarting EmployeeTest: getting username");
		
		Employee bot = new Employee("FAKE", "USER",null);
		assertTrue(bot.getUsername().equals("USER,FAKE"));
		
		System.out.println("Finished EmployeeTest: getting username");
	}
	
	public void testSetManager(){
		
		System.out.println("\nStarting testSetManager: setting null manager");
		Employee worker = new Employee("Fake", "Worker",null);
		try {
			worker.setManager(null);
			fail("null manager");
		} catch (Exception e) {
			//passed
		}
		
		
		
		System.out.println("Finished testSetManager: setting null manager");
	}
	
	public void testGetManagerID(){
		
		System.out.println("\nStarting EmployeeTest: getting manager's ID");
		
		Employee bot = new Employee("NO", "NAME",null);
		Manager manager = new Manager("LOL", "FSNAME",null);
		assertTrue(bot.getManagerID() == -1);
		bot.setManager(manager);
		assertTrue(bot.getManagerID() == manager.getUserID());
		
		System.out.println("Finished EmployeeTest: getting manager's ID");
	}
}
