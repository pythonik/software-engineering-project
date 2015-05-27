package tests.business;

import java.util.Calendar;
import java.util.GregorianCalendar;
import ems.business.CalculateDate;
import ems.persistence.DataBaseInterface;
import ems.persistence.DatabaseAdapter;
import ems.persistence.DatabaseStub;
import junit.framework.TestCase;

public class CalculateDateTest extends TestCase {
	GregorianCalendar payPeriodStart;
	GregorianCalendar payPeriodEnd;
	
	protected void setUp() throws Exception {
		DataBaseInterface db = new DatabaseStub("TestDBAccess");
		db.init();
		DatabaseAdapter.setAdapterInterface(db);
		super.setUp();
	}
	
	public void testDateInBoundsFuture(){
		
		System.out.println("\nStarting CalculationTest: checking future date in bounds");
		
			Calendar rightNow = Calendar.getInstance();
			rightNow.add(Calendar.MINUTE, 14);
			rightNow.add(Calendar.SECOND, 50);
			assertTrue(CalculateDate.timesheetDateInBounds(rightNow));
		
		System.out.println("Finished CalculationTest: checking future date in bounds");	
	}
	
	public void testDateInBoundsPast(){
		
		System.out.println("\nStarting CalculationTest: checking past dates in bounds");
		
		for (int i = -1; i > -7; i--){
			Calendar rightNow = Calendar.getInstance();
			rightNow.add(Calendar.DAY_OF_WEEK, i);
			assertTrue(CalculateDate.timesheetDateInBounds(rightNow));
		}
		
		System.out.println("Finished CalculationTest: checking past dates in bounds");
	}
	
	public void testDateInBoundsNow(){
		
		System.out.println("\nStarting CalculationTest: checking current time in bounds");
		
		Calendar rightNow = Calendar.getInstance();
		assertTrue(CalculateDate.timesheetDateInBounds(rightNow));
		
		System.out.println("Finished CalculationTest: checking current time in bounds");
	}
	
	public void testDateOutOfBoundsFuture(){
		System.out.println("\nStarting CalculationTest: checking future date out of bounds");
		
		
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.MINUTE, 16);
		assertFalse(CalculateDate.timesheetDateInBounds(rightNow));
		
		System.out.println("Finished CalculationTest: checking future date out of bounds");
		
	}
	
	public void testDateOutOfBoundsPast(){
		
		System.out.println("\nStarting CalculationTest: checking past date out of bounds");
		
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.DAY_OF_WEEK, -8);
		assertFalse(CalculateDate.timesheetDateInBounds(rightNow));
		
		System.out.println("Finished CalculationTest: checking past date out of bounds");
	}
	
	public void testDateOutOfBoundsNull(){
		
		System.out.println("\nStarting CalculationTest: checking null date in bounds");
		assertFalse(CalculateDate.timesheetDateInBounds(null));
		System.out.println("Finished CalculationTest: checking null date in bounds");
	}
	
	public void testNullDateToMidnight(){
		System.out.println("\nStarting CalculationTest: setting null date to midnight");
		
		GregorianCalendar rightNow = null;
		CalculateDate.setToMidnight(rightNow);
		assertEquals(null, rightNow);
		
		System.out.println("Finished CalculationTest: setting null date to midnight");
	}
	
	public void testValidDateToMidnight(){
		System.out.println("\nStarting CalculationTest: setting valid date to midnight");
		
		GregorianCalendar now = new GregorianCalendar();
		GregorianCalendar midnight = (GregorianCalendar)now.clone();
		
		CalculateDate.setToMidnight(midnight);
		
		assertEquals(now.get(Calendar.DAY_OF_YEAR), midnight.get(Calendar.DAY_OF_YEAR));
		assertEquals(now.get(Calendar.YEAR), midnight.get(Calendar.YEAR));
		assertEquals(11, midnight.get(Calendar.HOUR));
		assertEquals(59, midnight.get(Calendar.MINUTE));
		assertEquals(59, midnight.get(Calendar.SECOND));
		
		System.out.println("Finished CalculationTest: setting valid date to midnight");
	}
	
	public void testReferenceDateIsUnchanged(){
		System.out.println("\nStarting CalculationTest: checking if reference date is unchanged");
		GregorianCalendar date = new GregorianCalendar();
		GregorianCalendar origDate; 

		origDate = (GregorianCalendar)date.clone();
		
		CalculateDate.getPayPeriodStart(date);
		CalculateDate.getPayPeriodEnd(date);
		
		assertTrue(date.equals(origDate));
		
		System.out.println("Finished CalculationTest: checking if reference date is unchanged");
	}
	
	public void testReferenceDateStartOfMonth(){
		System.out.println("\nStarting CalculationTest: testing reference date at start of month");
		
		GregorianCalendar date = new GregorianCalendar();
		GregorianCalendar origDate; 
		
		date.set(Calendar.DAY_OF_MONTH, 2);
		date.set(Calendar.MONTH, 6);
		
		origDate = (GregorianCalendar)date.clone();
		
		payPeriodStart = CalculateDate.getPayPeriodStart(date);
		assertTrue(date.equals(origDate));
		
		assertEquals(15, payPeriodStart.get(Calendar.DAY_OF_MONTH));
		assertEquals(5, payPeriodStart.get(Calendar.MONTH));
		
		payPeriodEnd = CalculateDate.getPayPeriodEnd(date);
		assertTrue(date.equals(origDate));
		
		assertEquals(payPeriodStart.getActualMaximum(Calendar.DAY_OF_MONTH), payPeriodEnd.get(Calendar.DAY_OF_MONTH));
		assertEquals(5, payPeriodEnd.get(Calendar.MONTH));

		System.out.println("Finished CalculationTest: testing reference date at start of month");
	}
	
	public void testReferenceDateEndOfMonth(){
		System.out.println("\nStarting CalculationTest: testing reference date at end of month");
		
		GregorianCalendar date = new GregorianCalendar();
		
		date.set(Calendar.DAY_OF_MONTH, 22);
		date.set(Calendar.MONTH, 6);
		
		payPeriodStart = CalculateDate.getPayPeriodStart(date);
		
		assertEquals(1, payPeriodStart.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, payPeriodStart.get(Calendar.MONTH));
		
		payPeriodEnd = CalculateDate.getPayPeriodEnd(date);
		
		assertEquals(15, payPeriodEnd.get(Calendar.DAY_OF_MONTH));
		assertEquals(6, payPeriodEnd.get(Calendar.MONTH));

		System.out.println("Finished CalculationTest: testing reference date at end of month");
	}
	
	public void testReferenceDateNextYear(){
		System.out.println("\nStarting CalculationTest: testing reference date at start of next year");

		GregorianCalendar date = new GregorianCalendar();
		
		date.set(Calendar.DAY_OF_MONTH, 2);
		date.set(Calendar.MONTH, 0);
		date.set(Calendar.YEAR, 2014);
		
		payPeriodStart = CalculateDate.getPayPeriodStart(date);
		
		assertEquals(15, payPeriodStart.get(Calendar.DAY_OF_MONTH));
		assertEquals(11, payPeriodStart.get(Calendar.MONTH));
		assertEquals(2013, payPeriodStart.get(Calendar.YEAR));
		
		payPeriodEnd = CalculateDate.getPayPeriodEnd(date);
		
		assertEquals(payPeriodStart.getActualMaximum(Calendar.DAY_OF_MONTH), payPeriodEnd.get(Calendar.DAY_OF_MONTH));
		assertEquals(11, payPeriodEnd.get(Calendar.MONTH));
		assertEquals(2013, payPeriodStart.get(Calendar.YEAR));

		System.out.println("Finished CalculationTest: testing reference date at start of next year");
	}

}