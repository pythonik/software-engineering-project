package tests.models;
import ems.models.Project;
import junit.framework.TestCase;

public class ProjectTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testCreateProject(){
		
		System.out.println("\nStarting ProjectTest: creating project");
		
		Project proj = new Project("redhat");
		assertEquals(true, proj != null);
		System.out.println("Finished ProjectTest: creating project");
		
	}
	
	public void testCreateProjectNullName(){
		
		System.out.println("\nStarting CreateProject: null name");
		try{
			@SuppressWarnings("unused")
			Project proj = new Project(null);
			fail("null project name should fail");
		}catch(Exception e){
			
		}
		System.out.println("Finished CreateProject: null name");
		
	}
	
	public void testCreateProjectInvalidName(){
		
		System.out.println("\nStarting CreateProject: zero len name");
		try{
			@SuppressWarnings("unused")
			Project proj = new Project("");
			fail("null project name should fail");
		}catch(Exception e){
			
		}
		System.out.println("Finished CreateProject: zero len name");
		
	}
	
	public  void testGetProjectName() {
		
		System.out.println("\nStarting ProjectTest: getting project name");
		
		Project proj = new Project("DHCP");
		assertTrue(proj.getName().equals("DHCP"));
		
		System.out.println("Finished ProjectTest: getting project name");
	}

}
