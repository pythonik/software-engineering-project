package ems.business;
import java.util.ArrayList;
import ems.models.Employee;
import ems.models.Title;
import ems.persistence.DataBaseAccess;
import ems.persistence.DatabaseAdapter;

public class AccessUser extends DataBaseAccess{
	private ArrayList<Employee> allMembers;
	private Employee currentUser;
	
	public AccessUser(){
		allMembers = null;
		currentUser = null;
	}
	
	public ArrayList<Employee> getUserList(){
		return DatabaseAdapter.getAllUser();
	}
	
	public ArrayList<Employee> getUserByType(String type){
		ArrayList<Employee> list = new ArrayList<Employee>();
		
		for(Employee el: getUserList()){
			if(el.isType(type)){
				list.add(el);
			}
		}
		return list;
	}
	
	public boolean isLoggedIn() {
		return currentUser != null;
	}
	
	public boolean selectUser(Employee selected){
		boolean result = false;
		if(!isLoggedIn() && selected != null){
			currentUser = selected;
			result = true;
		}
		return result;
	}
	
	public ArrayList<Employee> getUserByTitle(Title title){
		ArrayList<Employee> list = new ArrayList<Employee>();
		for(Employee el: getUserList()){
			if(el.hasTitle(title)){
				list.add(el);
			}
		}
		return list;
	}
	
	public boolean userExist(Employee user){
		boolean result = false;
		if (user!=null) {
			result = DatabaseAdapter.userExist(user.getUserID());
		}
		return result;
	}
	public String getUserType(){
		return (isLoggedIn())? currentUser.getEmployeeType() : "No user logged in";
	}
	
	public void logOutUser() {
		currentUser = null;
		allMembers = null;
	}
	
	public int getCurrentUserID(){
		return (isLoggedIn())? currentUser.getUserID() : -1;
	}
	
	public String getCurrentUserName(){
		return (isLoggedIn())? currentUser.getUsername() : "";
	}
	
	public Employee getCurrentUser(){
		return currentUser;
	}
	
	public ArrayList<Employee> getTeamMember(){
		if(isLoggedIn()){
			if (allMembers == null){
				allMembers = new ArrayList<Employee>();
				for(Employee cur: DatabaseAdapter.getAllUser()){
					if (cur.getManagerID() == currentUser.getUserID() && !cur.hasID(currentUser.getUserID())) {
						allMembers.add(cur);
					}
				}
			}
		}
		return allMembers;
	}
	
	public ArrayList<Employee> getUsersForSummary(){
		ArrayList<Employee> list = new ArrayList<Employee>();
		for(Employee el:getUserList()){
			if(!el.isType("F")){
				list.add(el);
			}
		}
		return list;
	}
	
	public boolean insertUser(Employee user){
		boolean result = false;
		if(user != null){
			DatabaseAdapter.insertUser(user);
			result = true;
		}
		return result;
	}
	
	
	public int getEmployeesManagerID(int userID){
		Employee user = DatabaseAdapter.getUser(userID);
		return user.getManagerID();
	}
}
