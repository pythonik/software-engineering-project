package ems.models;

public class Financial extends Employee{
	
	public Financial(String lastNameString, String firstNameString, Title title) {
		super(lastNameString, firstNameString, title);
	}
	
	public String getEmployeeType() {
		return "F";
	}
}
