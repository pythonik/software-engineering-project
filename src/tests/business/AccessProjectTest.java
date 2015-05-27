package tests.business;
import ems.business.AccessProject;
import ems.models.Project;
import ems.persistence.DataBaseInterface;
import ems.persistence.DatabaseAdapter;
import ems.persistence.DatabaseStub;
import junit.framework.TestCase;

public class AccessProjectTest extends TestCase {

	protected void setUp() throws Exception {
		DataBaseInterface db = new DatabaseStub("TestDBAccess");
		db.init();
		DatabaseAdapter.setAdapterInterface(db);
		super.setUp();
	}
	
	public void testGetAllProject() {
		
		System.out.println("\nStarting AccessProjectTest: getting all projects");
		
		AccessProject projects = new AccessProject();
		assertNotNull(projects.getAllProjects());
		assertEquals(3, projects.getAllProjects().size());
		
		System.out.println("Finished AccessProjectTest: getting all projects");
	}
	
	public void testInsertProject() {
		
		System.out.println("\nStarting AccessProjectTest: inserting valid project");
		
		AccessProject projects = new AccessProject();
		assertTrue(projects.insertProject(new Project("TestProj")));
		
		System.out.println("Finished AccessProjectTest: inserting valid project");
	}
	
	public void testInsertProjectNullProject() {
		
		System.out.println("\nStarting AccessProjectTest: inserting null project");
		
		AccessProject projects = new AccessProject();
		assertFalse(projects.insertProject(null));
		
		System.out.println("Finished AccessProjectTest: inserting null project");
	}
}
