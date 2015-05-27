package tests.models;


import ems.models.Title;
import junit.framework.TestCase;

public class TitleTest extends TestCase {
	
	
	public void testCreateTitle(){
		
		System.out.println("\nStarting TitleTest: creating Title Object");
		Title title = new Title("Monkey", 10, 1);
		assertEquals("Monkey", title.getTitle());
		System.out.println("Finished TitleTest: creating Title Object");
	}
	
	public void testCreateTitleNullName(){
		
		System.out.println("\nStarting CreateTitle: null name");
		try{
			@SuppressWarnings("unused")
			Title title = new Title(null, 10, 1);
			fail("should fail");
		}catch(Exception e){
			
		}
		System.out.println("Finished TCreateTitle: null name");
	}
	
	public void testCreateTitleZeroLenName(){
		
		System.out.println("\nStarting CreateTitle: invalid name");
		try{
			@SuppressWarnings("unused")
			Title title = new Title("", 10, 1);
			fail("should fail");
		}catch(Exception e){
			
		}
		System.out.println("Finished CreateTitle: invalid name");
	}

}