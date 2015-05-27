package tests.models;


import java.util.ArrayList;
import java.util.GregorianCalendar;

import ems.business.AccessTimesheet;
import ems.models.Notification;
import ems.models.Project;
import ems.models.Timesheet;
import ems.persistence.DataBaseInterface;
import ems.persistence.DatabaseAdapter;
import ems.persistence.DatabaseStub;
import junit.framework.TestCase;

public class NotificationTest extends TestCase {
	
	protected void setUp() throws Exception {
		DataBaseInterface db = new DatabaseStub("TestDBAccess");
		db.init();
		DatabaseAdapter.setAdapterInterface(db);
		super.setUp();
	}
	
	public void testCreateNotification(){
		System.out.println("\nStarting NotificationTest: creating Notification object from existing timesheet");
		AccessTimesheet timesheets = new AccessTimesheet();
		ArrayList<Timesheet> records = timesheets.getAllTimesheet();
		Notification currNotif = new Notification(records.get(0));
		assertEquals("BackFire",currNotif.getProjectName());
		assertEquals("8:00 AM",currNotif.getStartTimeString());
		assertEquals("4:00 PM",currNotif.getEndTimeString());
		assertTrue(currNotif.isPending());
		System.out.println("Finished NotificationTest: creating Notification object from existing timesheet");
	}
	
	public void testCreateNotificationNullTimesheet(){
		System.out.println("\nStarting NotificationTest: creating Notification object from null timesheet");
		try{
			new Notification(null);
		} catch (IllegalArgumentException e){
			//test passed if exception caught when instantiating with null timesheet
			assertEquals(e.getMessage(),"Timesheet cannot be null when creating a notification");
		}
		System.out.println("Finished NotificationTest: creating Notification object from null timesheet");
	}
	
	public void testSetNotificationProject(){
		System.out.println("\nStarting NotificationTest: setting Notification new project");
		AccessTimesheet timesheets = new AccessTimesheet();
		ArrayList<Timesheet> records = timesheets.getAllTimesheet();
		Notification currNotif = new Notification(records.get(0));
		currNotif.setProject(new Project("testProj"));
		assertEquals("testProj",currNotif.getProjectName());
		System.out.println("Finished NotificationTest: setting Notification new project");
	}
	
	public void testSetNotificationTimes(){
		System.out.println("\nStarting NotificationTest: setting Notification new start & end times");
		AccessTimesheet timesheets = new AccessTimesheet();
		ArrayList<Timesheet> records = timesheets.getAllTimesheet();
		Notification currNotif = new Notification(records.get(0));
		GregorianCalendar currTime = new GregorianCalendar();
		currTime.set(2013, 06, 03, 10, 0);
		currNotif.setStartTime(currTime);
		assertEquals("10:00 AM",currNotif.getStartTimeString());
		currTime = new GregorianCalendar();
		currTime.set(2013, 06, 03, 18, 0);
		currNotif.setEndTime(currTime);
		assertEquals("6:00 PM",currNotif.getEndTimeString());
		System.out.println("Finished NotificationTest: setting Notification new start & end times");
	}
	

	
	public void testNotificationCompleteChange(){
		System.out.println("\nStarting NotificationTest: complete timesheet change");
		GregorianCalendar startTime = new GregorianCalendar();
		startTime.set(2013, 06, 03, 10, 0);
		GregorianCalendar endTime = new GregorianCalendar();
		endTime.set(2013, 06, 03, 18, 0);
		Timesheet currTimesheet = new Timesheet("User,Test",541,new Project("testProj"),startTime,endTime);
		Notification currNotif = new Notification(currTimesheet);
		startTime = new GregorianCalendar();
		startTime.set(2013, 06, 03, 11, 0);
		endTime = new GregorianCalendar();
		endTime.set(2013, 06, 03, 19, 0);
		currNotif.setStartTime(startTime);
		currNotif.setEndTime(endTime);
		currNotif.setProject(new Project("testProj2"));
		currNotif.completeChange();
		assertEquals("11:00 AM",currTimesheet.getStartTimeString());
		assertEquals("7:00 PM",currTimesheet.getEndTimeString());
		assertEquals("testProj2",currTimesheet.getProjectName());
		System.out.println("Finished NotificationTest: complete timesheet change");
	}
	
	public void testNotificationCompleteChangeInvalidData(){
		System.out.println("\nStarting NotificationTest: complete timesheet change with invalid data");
		GregorianCalendar startTime = new GregorianCalendar();
		startTime.set(2013, 06, 03, 10, 0);
		GregorianCalendar endTime = new GregorianCalendar();
		endTime.set(2013, 06, 03, 18, 0);
		Timesheet currTimesheet = new Timesheet("User,Test",541,new Project("testProj"),startTime,endTime);
		Notification currNotif = new Notification(currTimesheet);
		currNotif.setStartTime(null);
		currNotif.setEndTime(null);
		currNotif.setProject(null);
		try{
			currNotif.completeChange();
		} catch (IllegalArgumentException e){
			//test passed if exception caught when instantiating with null timesheet
			assertEquals(e.getMessage(),"Timesheet cannot be null when creating a notification");
		}
		System.out.println("Finished NotificationTest: complete timesheet change with invalid data");
	}

}