package ems.persistence;

import java.util.ArrayList;

import ems.models.Employee;
import ems.models.Project;
import ems.models.Timesheet;
import ems.models.Title;



public class DatabaseAdapter{
	
	private static DataBaseInterface currentDatabase;
	

	public static void setAdapterInterface(){
		currentDatabase = new DatabaseReal("emsdb");
	}
	
	public static void setAdapterInterface(DataBaseInterface database){
		currentDatabase = database;
	}
	
	public static void insertUser(Employee newUser){
		currentDatabase.insertUser(newUser);
	}
	
	public static void insertTimesheet(Timesheet newTimesheet){
		currentDatabase.insertTimesheet(newTimesheet);
	}
	
	public static void insertProject(Project newProject){
		currentDatabase.insertProject(newProject);
	}
	
	public static ArrayList<Employee> getAllUser(){
		return currentDatabase.getAllUser();
	}
	
	public static Employee getUser(int userID){
		return currentDatabase.getUser(userID);
	}
	
	public static ArrayList<Timesheet> getAllTimesheet(){
		return currentDatabase.getAllTimesheet();
	}
	
	public static boolean databaseIsSet() {
		return currentDatabase!=null;
	}
	
	public static ArrayList<Project> getAllProject() {
		
		return currentDatabase.getAllProject();
	}
	
	public static ArrayList<Title> getAllTitle(){
		return currentDatabase.getAllTitle();
	}
	
	public static void release(){
		currentDatabase = null;
	}
	
	public static void initDB() {
		currentDatabase.init();
	}
	
	public static boolean userExist(int emp){
		return currentDatabase.userExist(emp);
	}
	
	public static void closeDB() {
		currentDatabase.close();
	}
}