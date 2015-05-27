package ems.persistence;
import java.util.ArrayList;

import ems.models.Employee;
import ems.models.Project;
import ems.models.Timesheet;
import ems.models.Title;


public interface DataBaseInterface {
	
	public void init();
	public void close();
	public void insertUser(Employee newUser);
	public void insertTimesheet(Timesheet newTimesheet);
	public void insertProject(Project newProject);
	public void insertTitle(Title newTitle);
	public ArrayList<Employee> getAllUser();
	public Employee getUser(int userID);
	public ArrayList<Timesheet> getAllTimesheet();
	public ArrayList<Project> getAllProject();
	public ArrayList<Title> getAllTitle();
	public boolean titleExist(String name);
	public boolean projectExist(String projectName);
	public boolean userExist(int uid);


}
