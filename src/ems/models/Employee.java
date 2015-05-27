package ems.models;

public class Employee {
	
	private String lastNameString;
	private String firstNameString;
	private int userID;
	private static int serial = 0;
	private Manager manager;
	private Title title;
	
	public Employee(String lastNameString, String firstNameString, Title title){
		userID = Employee.assignID();
		this.lastNameString = lastNameString;
		this.firstNameString = firstNameString;
		this.title = title;
	}
	
	public int getUserID(){
		return userID;
	}
	
	public String getUserFirstName(){
		return firstNameString;
	}
	
	public String getUserLastName(){
		return lastNameString;
	}
	
	public double getRate() {
		return title.getRate();
	}
	
	public String getTitle() {
		return title.getTitle();
	}
	public Title getTitleObj() {
		return title;
	}
	
	public String getUsername(){
		return firstNameString+","+lastNameString;
	}
	
	private static int assignID(){
		int id = serial;
		serial++;
		return id;
	}
	
	public void setUserID(int id){
		userID = id;
	}
	
	public static void resetCounter(){
		serial = 0;
	}

	public void setManager(Employee manager){
		if (manager.getEmployeeType().equals("M") && manager!=null) {
			this.manager = (Manager)manager;
		}else{
			new IllegalArgumentException("null manager passed in or not a manager");
		}
	}
	
	public int getManagerID() {
		int result = -1;
		if(manager!=null){
			result = manager.getUserID();
		}
		return result;
	}
	
	public String toString(){
		return "User:"+lastNameString+","+firstNameString;
	}
	
	public String getEmployeeType() {
		return "E";
	}
	
	public boolean isType(String type) {
		return type.equals(getEmployeeType()) ? true : false;
	}
	
	public boolean hasTitle(Title title) {
		return title.equals(getTitleObj()) ? true : false;
	}
	
	public boolean hasID(int id) {
		return id == getUserID() ? true : false;
	}
	
}