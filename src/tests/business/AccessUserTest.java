package tests.business;
import ems.business.AccessUser;
import ems.models.Employee;
import ems.models.Title;
import ems.persistence.DataBaseInterface;
import ems.persistence.DatabaseAdapter;
import ems.persistence.DatabaseStub;
import junit.framework.TestCase;

public class AccessUserTest extends TestCase {

	protected void setUp() throws Exception {
		DataBaseInterface db = new DatabaseStub("TestDBAccess");
		db.init();
		DatabaseAdapter.setAdapterInterface(db);
		super.setUp();
	}
	
	private AccessUser getUserAccessor(){
		AccessUser user = new AccessUser();
		return user;
	}
	
	public void testAccessUserWithOutDataBase(){
		
		System.out.println("\nStarting AccessUserTest: accessing user without database");
		
		DatabaseAdapter.release();
		try{
			getUserAccessor();
			fail("IllegalState Exception should be rasied");
		}catch(IllegalAccessError e){
			//test passed if exception caught
		}
		
		System.out.println("Finished AccessUserTest: accessing user without database");
	}
	
	public void testGetAllUser() {
		System.out.println("\nStarting AccessUserTest: accessing all users");
		
		assertNotNull(getUserAccessor().getUserList());
		assertEquals(7, getUserAccessor().getUserList().size());
		
		System.out.println("Finished AccessUserTest: accessing all users");
	}

	public void testIsLoggedIn(){
		
		System.out.println("\nStarting AccessUserTest: logging valid user in");
		
		AccessUser user = getUserAccessor();
		assertFalse(user.isLoggedIn());
		Employee bot = new Employee("Surfer", "Dude",null);
		user.selectUser(bot);
		assertTrue(user.isLoggedIn());
		
		System.out.println("Finished AccessUserTest: logging valid user in");
	}

	public void testIsLoggedInNullUser(){
		
		System.out.println("\nStarting AccessUserTest: logging invalid user in");
		
		AccessUser user = getUserAccessor();
		user.selectUser(null);
		assertFalse(user.isLoggedIn());
		
		System.out.println("Finished AccessUserTest: logging invalid user in");
	}
	
	public void testUserExist(){
		
		System.out.println("\nStarting testUserExist: exsiting user");
		
		AccessUser users = getUserAccessor();
		assertTrue(users.userExist(users.getUserList().get(0)));
		
		System.out.println("Finished testUserExist: exsiting user");
	}

	public void testUserExistNullUser(){
		
		System.out.println("\nStarting testUserExist: null user");
		
		AccessUser users = getUserAccessor();
		assertFalse(users.userExist(null));
		
		System.out.println("Finished testUserExist: null user");
	}
	
	public void testGetUserTypeNoLogin(){
		
		System.out.println("\nStarting AccessUserTest: getting user type without login");
		
		AccessUser users = getUserAccessor();
		assertEquals("No user logged in", users.getUserType());
		
		System.out.println("Finished AccessUserTest: getting user type without login");
	}
	
	public void  testGetUserType(){
		
		System.out.println("\nStarting AccessUserTest: getting user type");
		
		AccessUser users = getUserAccessor();
		users.selectUser(users.getUserList().get(0));
		assertEquals("Manager logged in","M", users.getUserType());
		
		users = getUserAccessor();
		users.selectUser(users.getUserList().get(1));
		assertEquals("Employee logged in","E", users.getUserType());
		
		System.out.println("Finished AccessUserTest: getting user type");
	}
	
	
	public void testGetManagerMembers(){
		
		System.out.println("\nStarting AccessUserTest: getting manager's member");
		
		AccessUser users = getUserAccessor();
		Employee bot = users.getUserList().get(0);
		users.selectUser(bot);
		assertEquals("should have 4 team members including manager himself", 4, users.getTeamMember().size());
	
		System.out.println("Finished AccessUserTest: getting manager's member");
	}
	
	public void testGetEmployeeMembers(){
		
		System.out.println("\nStarting AccessUserTest: getting employee's member");
		
		AccessUser users = getUserAccessor();
		Employee bot = users.getUserList().get(1);
		users.selectUser(bot);
		assertEquals("should have 0 team members", 0, users.getTeamMember().size());
		
		System.out.println("Finished AccessUserTest: getting employee's member");
	}
	
	
	public void testGetNullMember(){
		
		System.out.println("\nStarting AccessUserTest: getting null member");
		
		AccessUser users = getUserAccessor();
		users.selectUser(null);
		assertNull(users.getTeamMember());
		
		System.out.println("Finished AccessUserTest: getting null member");
	}
	
	public void testGetCurrentUserName(){
		
		System.out.println("\nStarting AccessUserTest: getting current user name");
		
		Employee bot = new Employee("Smith", "Joe",null);
		AccessUser users = getUserAccessor();
		users.selectUser(bot);
		assertEquals("Joe,Smith",users.getCurrentUserName());
		
		System.out.println("Finished AccessUserTest: getting current user name");
	}
	
	public void testGetCurrentUserNameNullUser(){
		
		System.out.println("\nStarting AccessUserTest: getting current username from null user");
		AccessUser user = getUserAccessor();
		user.selectUser(null);
		assertEquals("",user.getCurrentUserName());
		
		System.out.println("Finished AccessUserTest: getting current username from null user");
	}
	
	public void  testGetCurrentUserID() {
		
		System.out.println("\nStarting AccessUserTest: getting current user id");
		
		AccessUser user = getUserAccessor();
		Employee bot = new Employee("Smith", "Joe",null);
		user.selectUser(bot);
		assertEquals(user.getCurrentUserID(), bot.getUserID());
		
		System.out.println("Finished AccessUserTest: getting current user id");
	}
	
	public void  testGetCurrentUserIDWithOutLogin() {
		
		System.out.println("\nStarting AccessUserTest: getting userID without login");
		
		AccessUser user = getUserAccessor();
		assertEquals(-1, user.getCurrentUserID());
		
		System.out.println("Finished AccessUserTest: getting userID without login");
	}
	
	public void testUserLogOut(){
		
		System.out.println("\nStarting AccessUserTest: logging out user");
		
		AccessUser user = getUserAccessor();
		Employee bot = new Employee("Jadi", "lol",null);
		user.selectUser(bot);
		user.logOutUser();
		assertEquals(-1, user.getCurrentUserID());
		assertNull(user.getTeamMember());
		
		System.out.println("Finished AccessUserTest: logging out user");
	}
	
	public void testInsertUser(){
		
		System.out.println("\nStarting AccessUserTest: inserting new user");
		
		AccessUser user = getUserAccessor();
		assertEquals(7, user.getUserList().size());
		Title title = DatabaseAdapter.getAllTitle().get(1);
		Employee bot = new Employee("Doe","John",title);
		assertTrue(user.insertUser(bot));
		assertEquals(8, user.getUserList().size());
		
		System.out.println("Finished AccessUserTest: inserting new user");
	}
	
	public void testInsertUserNullUser(){
		
		System.out.println("\nStarting AccessUserTest: inserting null user");
		
		AccessUser user = getUserAccessor();
		assertFalse(user.insertUser(null));
		
		System.out.println("Finished AccessUserTest: inserting null user");
	}
}
