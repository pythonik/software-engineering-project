package tests.business;


import java.util.ArrayList;
import ems.business.AccessTimesheet;
import ems.business.AccessUser;
import ems.business.CalculateHours;
import ems.models.Employee;
import ems.models.Project;
import ems.models.Timesheet;
import ems.persistence.DataBaseInterface;
import ems.persistence.DatabaseAdapter;
import ems.persistence.DatabaseStub;
import junit.framework.TestCase;

public class CalculateHoursTest extends TestCase {

	protected void setUp() throws Exception {
		DataBaseInterface db = new DatabaseStub("TestDBAccess");
		db.init();
		DatabaseAdapter.setAdapterInterface(db);
		super.setUp();
	}
	
	
	
	public void testGetAllHours(){
		
		System.out.println("\nStarting CalculationTest: checking total hours within system");
		AccessTimesheet timesheets = new AccessTimesheet();
		ArrayList<Timesheet> records = timesheets.getAllTimesheet();
		int sum = 0;
		for(Timesheet el:records){
			sum += el.getHours();
		}
		assertEquals(sum, CalculateHours.getAllHours());
		System.out.println("Finished CalculationTest: checking total hours within system");
		
	}
	
	public void testGetTotalHoursByEmployee1() {
		
		System.out.println("\nStarting GetTotalHoursByEmployeeTest: normal employee");
		ArrayList<Employee> list = new AccessUser().getUserList();
		assertEquals(40, CalculateHours.getTotalHoursByEmployee(list.get(1)));
		System.out.println("Finished GetTotalHoursByEmployeeTest: normal employee");
	}
	
	public void testGetTotalHoursByEmployee2() {
		
		System.out.println("\nStarting GetTotalHoursByEmployeeTest: null user");
		assertEquals(0, CalculateHours.getTotalHoursByEmployee(null));
		System.out.println("Finished GetTotalHoursByEmployeeTest: null user");
	}
	
	public void testGetTotalHoursByEmployee3() {
		
		System.out.println("\nStarting GetTotalHoursByEmployeeTest: non-exist user");
		Employee employee = new Employee("notexist", "null",null);
		assertEquals(0, CalculateHours.getTotalHoursByEmployee(employee));
		System.out.println("Finished GetTotalHoursByEmployeeTest: non-exist user");
	}
	
	public void testGetEmployeeHoursByProject1(){
		System.out.println("\nStarting  GetEmployeeHoursByProjectTest: null project");
		Employee bot = DatabaseAdapter.getAllUser().get(1);
		assertEquals((float)0, CalculateHours.getEmployeeHoursByProject(bot.getUserID(), null));
		System.out.println("Finished  GetEmployeeHoursByProjectTest: null project");
	}
	
	public void testGetEmployeeHoursByProject3(){
		System.out.println("\nStarting  GetEmployeeHoursByProjectTest: non-exist project");
		Employee bot = DatabaseAdapter.getAllUser().get(1);
		Project project = new Project("sysclt");
		assertEquals((float)0, CalculateHours.getEmployeeHoursByProject(bot.getUserID(), project));
		System.out.println("Finished  GetEmployeeHoursByProjectTest: non-exist project");
	}
	
	public void testGetEmployeeHoursByProject4(){
		System.out.println("\nStarting GetEmployeeHoursByProjectTest: non-exist user id");
		Project project = new Project("sysclt");
		assertEquals((float)0, CalculateHours.getEmployeeHoursByProject(51, project));
		System.out.println("Finished  GetEmployeeHoursByProjectTestTest: non-exist user id");
	}
	
	public void testGetEmployeeHoursByProject2(){
		
		System.out.println("\nStarting  GetEmployeeHoursByProjectTest: checking pre-stored employee's project hours");
		Employee bot = DatabaseAdapter.getAllUser().get(1);
		assertEquals((float)32, CalculateHours.getEmployeeHoursByProject(bot.getUserID(), new Project("BusyBox")));
		assertEquals((float)8, CalculateHours.getEmployeeHoursByProject(bot.getUserID(), new Project("BackFire")));
		
		bot = DatabaseAdapter.getAllUser().get(2);
		assertEquals((float)0, CalculateHours.getEmployeeHoursByProject(bot.getUserID(), new Project("BusyBox")));
		assertEquals((float)8, CalculateHours.getEmployeeHoursByProject(bot.getUserID(), new Project("BackFire")));
		System.out.println("Finished  GetEmployeeHoursByProjectTest: checking pre-stored employee's project hours");
	}
	
	
	public void testGetHoursByProject1(){
		
		System.out.println("\nStarting GetHoursByProjectTest: checking pre-stored hours by project");
		
		//0 entries were initialized with Slowloris
		assertEquals((float)0, CalculateHours.getHoursByProject(new Project("Slowloris")));
		//4 entries were initialized with BusyBox, each is 8 hours
		assertEquals((float)32, CalculateHours.getHoursByProject(new Project("BusyBox")));
		//5 entries were initialized with BackFire, each is 8 hours
		assertEquals((float)56, CalculateHours.getHoursByProject(new Project("BackFire")));
		
		System.out.println("Finished GetHoursByProjectTest: checking pre-stored hours by project");
	}
	
	public void testGetHoursByProject2(){
		System.out.println("\nStarting GetHoursByProjectTest: null project");
		assertEquals((float)0, CalculateHours.getHoursByProject(null));
		System.out.println("Finished GetHoursByProjectTest: checking pre-stored hours by project");
	}
	
	public void testGetHoursByProject3(){
		System.out.println("\nStarting GetHoursByProjectTest: non-exist project");
		Project project = new Project("NetBSD");
		assertEquals((float)0, CalculateHours.getHoursByProject(project));
		System.out.println("Finished GetHoursByProjectTest: non-exist");
	}
	
	public void testGetEmployeeHoursInPeriod2() {
		
		System.out.println("\nStarting GetEmployeeHoursInPeriodTest: null user");
		assertEquals(0, CalculateHours.getTotalHoursByEmployee(null));
		System.out.println("Finished GetEmployeeHoursInPeriodTest: null user");
	}
	
	public void testGetEmployeeHoursInPeriod3() {
		
		System.out.println("\nStarting GetEmployeeHoursInPeriodTest: non-exist user");
		Employee emp = new Employee("ben", "ben", null);
		assertEquals(0, CalculateHours.getTotalHoursByEmployee(emp));
		System.out.println("Finished GetEmployeeHoursInPeriodTest: non-exist user");
	}
	
	public void testGetEmployeeHoursInPeriod1() {
		
		System.out.println("\nStarting GetEmployeeHoursInPeriodTest: checking pre-stored hours of employee in pay period");
		
		ArrayList<Employee> list = new AccessUser().getUserList();
		assertEquals(40, CalculateHours.getTotalHoursByEmployee(list.get(1)));
		
		System.out.println("Finished GetEmployeeHoursInPeriodTest: checking pre-stored hours of employee in period");
	}
}