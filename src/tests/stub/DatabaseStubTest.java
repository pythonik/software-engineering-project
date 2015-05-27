package tests.stub;
import java.util.GregorianCalendar;

import ems.models.Employee;
import ems.models.Project;
import ems.models.Timesheet;
import ems.models.Title;
import ems.persistence.DatabaseStub;
import junit.framework.TestCase;

public class DatabaseStubTest extends TestCase {

	public void testInsertNullUser() {
		System.out.println("\nStarting DatabaseStubTest: inserting null user");
		DatabaseStub db = setUpDB();
		try {
			db.insertUser(null);
			fail("null user should not insert to database");
		} catch (Exception e) {
			//test passed
		}
		System.out.println("Finished DatabaseStubTest: inserting null user");
	}
	
	public void testInsertUserWithNonExistTitle() {
		System.out.println("\nStarting DatabaseStubTest: inserting user with NonExistTitle");
		DatabaseStub db = setUpDB();
		try {
			Title title = new Title("Cooker", 10, 40);
			Employee employee = new Employee("lol", "lol", title);
			db.insertUser(employee);
			fail("invalid user should not insert to database");
		} catch (Exception e) {
			//test passed
		}
		System.out.println("Finished DatabaseStubTest: inserting user with NonExistTitle");
	}
	
	public void testInsertUserWithNullTitle() {
		System.out.println("\nStarting DatabaseStubTest: inserting user with NullTitle");
		DatabaseStub db = setUpDB();
		try {
			Employee employee = new Employee("lol", "lol", null);
			db.insertUser(employee);
			fail("invalid user should not insert to database");
		} catch (Exception e) {
			//test passed
		}
		System.out.println("Finished DatabaseStubTest: inserting user with NullTitle");
	}
	
	public void testInsertNullProject(){
		
		System.out.println("\nStarting DatabaseStubTest: inserting null project");
		DatabaseStub db = setUpDB();
		try {
			db.insertProject(null);
			fail("null project should not insert to database");
		} catch (Exception e) {
			//test passed
		}
		System.out.println("Finished DatabaseStubTest: inserting null project");
	}
	
	public void testInsertInvalidProject(){
		
		System.out.println("\nStarting DatabaseStubTest: inserting invalid project");
		DatabaseStub db = setUpDB();
		try {
			Project proj =  new Project("");
			db.insertProject(proj);
			fail("invalid project should not insert to database");
		} catch (Exception e) {
			//test passed
		}
		System.out.println("Finished DatabaseStubTest: inserting invalid project");
	}
	
	
	public void testInsertTimesheetWithNonExistUser(){
		DatabaseStub db = setUpDB();
		System.out.println("\nStarting DatabaseStubTest: inserting timesheet with NonExistUser");
		GregorianCalendar startTime = new GregorianCalendar();
		startTime.set(2013, 06, 03, 8, 0);
		GregorianCalendar endTime = new GregorianCalendar();
		endTime.set(2013, 06, 03, 16, 0);
		Project project = new Project("BusyBox");
		Timesheet newTimesheet = new Timesheet("tigerdon", 8, project, startTime, endTime);
		try{
			db.insertTimesheet(newTimesheet);
			fail("A expection should thrown");
		}catch(Exception e){
			
		}
		System.out.println("Finished DatabaseStubTest: inserting timesheet with NonExistUser");
	}
	
	public void testInsertTimesheetWithInvalidProject(){
		DatabaseStub db = setUpDB();
		db.init();
		System.out.println("\nStarting DatabaseStubTest: inserting timesheet with NonExistProject");
		GregorianCalendar startTime = new GregorianCalendar();
		startTime.set(2013, 06, 03, 8, 0);
		GregorianCalendar endTime = new GregorianCalendar();
		endTime.set(2013, 06, 03, 16, 0);
		Project project = new Project("ArchBang");
		Employee emps = db.getUser(1);
		Timesheet newTimesheet = new Timesheet(emps.getUsername(), emps.getUserID(), project, startTime, endTime);
		try{
			db.insertTimesheet(newTimesheet);
			fail("A expection should thrown");
		}catch(Exception e){
			
		}
		System.out.println("Finished DatabaseStubTest: inserting timesheet with NonExistProject");
	}
	
	public void testInsertNullTitle() {
		System.out.println("\nStarting DatabaseStubTest: inserting null title");
		DatabaseStub db = setUpDB();
		try {
			db.insertTitle(null);
			fail("null project should not insert to database");
		} catch (Exception e) {
			//test passed
		}
		System.out.println("Finished DatabaseStubTest: inserting null title");
		
		
	}
	private DatabaseStub setUpDB(){
		
		DatabaseStub db = new DatabaseStub("testdb");
		Employee.resetCounter();
		return db;
		
	}
}
