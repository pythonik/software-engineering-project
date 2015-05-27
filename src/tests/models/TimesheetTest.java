package tests.models;
import java.util.GregorianCalendar;

import ems.models.Project;
import ems.models.Timesheet;
import junit.framework.TestCase;

public class TimesheetTest extends TestCase {

	public void testCreateTimesheet(){
		System.out.println("\nStarting TimesheetTest: creating timesheet null parameters");
		try {
			@SuppressWarnings("unused")
			Timesheet oneTimesheet = new Timesheet(null, 1, null, null, null);
			fail("should throw exceptions when null parameter passed in");
		} catch (Exception e) {
			//passed
		}
		System.out.println("Finished TimesheetTest: creating timesheet null parameters");
	}
	
	public void testSetStartTime(){
		System.out.println("\nStarting testSetStartTime: null start time");
		try {
			GregorianCalendar startTime = new GregorianCalendar();
			startTime.set(2013, 06, 03, 8, 0);
			GregorianCalendar endTime = new GregorianCalendar();
			endTime.set(2013, 06, 03, 16, 0);
			Timesheet oneTimesheet = new Timesheet("loluser", 1, new Project("tiger"),startTime, endTime);
			oneTimesheet.setStartTime(null);
			fail("should throw exceptions when null start passed in");
		} catch (Exception e) {
			//passed
		}
		System.out.println("Finished testSetStartTime: null start time");
	}
	
	public void testSetEndTime(){
		System.out.println("\nStarting testSetStartTime: null start time");
		try {
			GregorianCalendar startTime = new GregorianCalendar();
			startTime.set(2013, 06, 03, 8, 0);
			GregorianCalendar endTime = new GregorianCalendar();
			endTime.set(2013, 06, 03, 16, 0);
			Timesheet oneTimesheet = new Timesheet("loluser", 1, new Project("tiger"),startTime, endTime);
			oneTimesheet.setEndTime(null);
			fail("should throw exceptions when null endtime in");
		} catch (Exception e) {
			//passed
		}
		System.out.println("Finished testSetStartTime: null start time");
	}
	
	
	public void testSetHours(){
		System.out.println("\nStarting testSetHours: nagetive hours");
		try {
			GregorianCalendar startTime = new GregorianCalendar();
			startTime.set(2013, 06, 03, 8, 0);
			GregorianCalendar endTime = new GregorianCalendar();
			endTime.set(2013, 06, 03, 16, 0);
			Timesheet oneTimesheet = new Timesheet("loluser", 1, new Project("tiger"),startTime, endTime);
			oneTimesheet.setHours(-1);
			fail("should throw exceptions when nagetive hours passed in");
		} catch (Exception e) {
			//passed
		}
		System.out.println("Finished testSetHours: nagetive hours");
	}
}
