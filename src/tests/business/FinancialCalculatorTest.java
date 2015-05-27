package tests.business;
import java.util.GregorianCalendar;

import junit.framework.TestCase;
import ems.business.FinancialCalculator;
import ems.models.Project;
import ems.models.Timesheet;
import ems.models.Title;
import ems.persistence.DataBaseInterface;
import ems.persistence.DatabaseAdapter;
import ems.persistence.DatabaseStub;

public class FinancialCalculatorTest extends TestCase{
	
	protected void setUp() throws Exception {
		DataBaseInterface db = new DatabaseStub("TestDBAccess");
		db.init();
		DatabaseAdapter.setAdapterInterface(db);
		super.setUp();
	}

	public void testCalculatePercentage1(){
		System.out.println("\nStarting\ttestCalculatePercentage: normal data");
		assertEquals(((int)(1/2.0*10000))/100.0, FinancialCalculator.calculatePercentage(1, 2));
		assertEquals(((int)(3/5.0*10000))/100.0, FinancialCalculator.calculatePercentage(3, 5));
		assertEquals(((int)(3/7.0*10000))/100.0, FinancialCalculator.calculatePercentage(3, 7));
		assertEquals(((int)(5/7.0*10000))/100.0, FinancialCalculator.calculatePercentage(5, 7));
		System.out.println("Fininshing\ttestCalculatePercentage: normal data");
	}
	
	public void testCalculatePercentage2(){
		System.out.println("\nStarting\ttestCalculatePercentage: zero denominator");
		assertEquals(-1.0,FinancialCalculator.calculatePercentage(1, 0));
		System.out.println("Fininshing\ttestCalculatePercentage: zero denominator");
	}
	
	public void testCalculatePercentage3(){
		System.out.println("\nStarting\ttestCalculatePercentage: negative data");
		assertEquals(-1.0, FinancialCalculator.calculatePercentage(-1, 2));
		System.out.println("Fininshing\ttestCalculatePercentage: negative data");
	}
	
	public void testCalcPayInPeriod1(){	
		System.out.println("\nStarting\ttestCalcPayInPeriod: null timesheet");
		assertEquals(-1.0, FinancialCalculator.calcPayInPeriod(null, 2));
		System.out.println("Fininshing\ttestCalcPayInPeriod: null timesheet");
	}
	
	public void testCalcPayInPeriod2(){	
		System.out.println("\nStarting\ttestCalcPayInPeriod: Negative wage");
		GregorianCalendar startTime = new GregorianCalendar();
		startTime.set(2013, 06, 03, 8, 0);
		GregorianCalendar endTime = new GregorianCalendar();
		endTime.set(2013, 06, 03, 16, 0);
		Project project = new Project("openwrt");
		assertEquals(-1.0, FinancialCalculator.calcPayInPeriod(new Timesheet("test", 1,project,startTime,endTime),-1));
		System.out.println("Fininshing\ttestCalcPayInPeriod: Negative wage");
	}
	
	public void testGetTotalExpenditureByTitle1() {
		System.out.println("\nStarting\ttestGetTotalExpenditureByTitle: null title");
		assertEquals(-1.0, FinancialCalculator.getTotalExpenditureByTitle(null));
		System.out.println("Fininshing\ttestGetTotalExpenditureByTitle: null title");
	}
	
	public void testGetTotalExpenditureByTitle2() {
		System.out.println("\nStarting\ttestGetTotalExpenditureByTitle: non-exist title");
		Title title = new Title("Player", 9, 100);
		assertEquals(0.0, FinancialCalculator.getTotalExpenditureByTitle(title));
		System.out.println("Fininshing\ttestGetTotalExpenditureByTitle: non-exist title");
	}
	
}