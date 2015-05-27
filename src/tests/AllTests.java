package tests;
import tests.models.*;
import tests.stub.*;
import tests.business.*;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {
	
	public static TestSuite suite;

    public static Test suite() {
        suite = new TestSuite("All tests");
        testModels();
        testStub();
        testBusiness();
        return suite;
    }

    private static void testModels() {
        suite.addTestSuite(EmployeeTest.class);
        suite.addTestSuite(ProjectTest.class);
        suite.addTestSuite(TimesheetTest.class);
        suite.addTestSuite(TitleTest.class);
        suite.addTestSuite(NotificationTest.class);
    }

    private static void testStub() {
        suite.addTestSuite(DatabaseStubTest.class);
    }

    private static void testBusiness() {
        suite.addTestSuite(AccessProjectTest.class);
        suite.addTestSuite(AccessTimesheetTest.class);
        suite.addTestSuite(AccessUserTest.class);
        suite.addTestSuite(DataBaseAccessTest.class);
        suite.addTestSuite(CalculateHoursTest.class);
        suite.addTestSuite(CalculateDateTest.class);
        suite.addTestSuite(FinancialCalculatorTest.class);
    }
}