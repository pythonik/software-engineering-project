package ems.persistence;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import ems.business.CalculateHours;
import ems.models.Employee;
import ems.models.Financial;
import ems.models.Manager;
import ems.models.Notification;
import ems.models.Project;
import ems.models.Timesheet;
import ems.models.Title;



public class DatabaseStub implements DataBaseInterface{
	
	private ArrayList<Employee> users;
	private ArrayList<Project> projects;
	private ArrayList<Timesheet> timesheets;
	private ArrayList<Title> titles;
	private ArrayList<Notification> notifications;
	private String dbname;
	
	public DatabaseStub(String dbname) {
		this.dbname = dbname;
		users = new ArrayList<Employee>();
		projects = new ArrayList<Project>();
		timesheets = new ArrayList<Timesheet>();
		titles = new ArrayList<Title>();
		notifications = new ArrayList<Notification>();
	}
	
	@Override
	public void init(){
		//mouth is zero based see calendar doc
		GregorianCalendar startTime = new GregorianCalendar();
		startTime.set(2013, 5, 03, 8, 0);
		GregorianCalendar endTime = new GregorianCalendar();
		endTime.set(2013, 5, 03, 16, 0);
		titles.add(new Title("Jr.Developer", 1, 19));
		titles.add(new Title("Sr.Developer", 2, 30));
		titles.add(new Title("Hardware Engineer", 3, 25));
		titles.add(new Title("Technical Writer", 4, 18));
		
		
		users.add(new Manager("Smith","A", titles.get(1)));
		users.add(new Employee("Smith","B",titles.get(0)));
		users.add(new Employee("Smith","C",titles.get(0)));
		users.add(new Employee("Smith","D",titles.get(3)));
		users.add(new Employee("Smith","E",titles.get(3)));
		users.add(new Manager("Smith","F", titles.get(1)));
		users.add(new Financial("Smith","Fin", titles.get(1)));
		
		projects.add(new Project("BusyBox"));
		projects.add(new Project("BackFire"));
		projects.add(new Project("redHat"));
		
		for(Employee el:users){ //add 8 hour timesheet for each employee on BackFire project 
			
			Timesheet newTimesheet = new Timesheet(el.getUsername(),el.getUserID(), projects.get(1), startTime, endTime);			
			el.setManager(users.get(0));
			newTimesheet.setHours(CalculateHours.getDifferenceInHours(startTime, endTime));
			timesheets.add(newTimesheet);
		}

		users.get(0).setManager(users.get(0));
		users.get(1).setManager(users.get(0));
		users.get(2).setManager(users.get(5));
		users.get(3).setManager(users.get(5));
		users.get(4).setManager(users.get(0));
		users.get(5).setManager(users.get(0));
		
		for(int i = 0; i < 4; i++){ //add four 8 hour timesheets for B Smith on BusyBox project
			Timesheet ts = new Timesheet(
					users.get(1).getUsername(),
					users.get(1).getUserID(),
					projects.get(0),
					startTime,
					endTime);
		    ts.setHours(CalculateHours.getDifferenceInHours(startTime, endTime));
			timesheets.add(ts);
		}
		

		GregorianCalendar newStartTime = new GregorianCalendar();
		GregorianCalendar newEndTime = new GregorianCalendar();
		Notification currNotif;
		newStartTime.set(2013, 06, 03, 10, 0);
		newEndTime.set(2013, 06, 03, 16, 0);
		currNotif = new Notification(timesheets.get(1));
		currNotif.setProject(projects.get(1));
		currNotif.setStartTime(newStartTime);
		currNotif.setEndTime(newEndTime);
		notifications.add(currNotif);
		((Manager)users.get(0)).addNotification(currNotif);
		newStartTime = new GregorianCalendar();
		newStartTime.set(2013, 06, 03, 8, 0);
		currNotif = new Notification(timesheets.get(2));
		currNotif.setProject(projects.get(2));
		currNotif.setStartTime(newStartTime);
		currNotif.setEndTime(newEndTime);
		currNotif.setCompleted(true);
		notifications.add(currNotif);
		((Manager)users.get(5)).addNotification(currNotif);
		newStartTime = new GregorianCalendar();
		newEndTime = new GregorianCalendar();
		newStartTime.set(2013, 06, 03, 12, 0);
		newEndTime.set(2013, 06, 03, 17, 0);
		currNotif = new Notification(timesheets.get(3));
		currNotif.setProject(projects.get(2));
		currNotif.setStartTime(newStartTime);
		currNotif.setEndTime(newEndTime);
		notifications.add(currNotif);
		((Manager)users.get(5)).addNotification(currNotif);
	}
	
	public void close(){
		
	}
	
	@Override
	public void insertUser(Employee newUser){
		if(newUser!=null && titleExist(newUser.getTitle())){
			users.add(newUser);
		}else{
			throw new IllegalArgumentException("Inserting null user to database");
		}
	}
	
	@Override
	public void insertTimesheet(Timesheet newTimesheet){
		if(newTimesheet!=null && userExist(newTimesheet.getUserID()) && projectExist(newTimesheet.getProjectName())){
			timesheets.add(newTimesheet);
		}else{
			throw new IllegalArgumentException("Inserting null Timesheet or referencing non-exist entity to database");
		}	
	}
	@Override
	public boolean projectExist(String projectName) {
		
		boolean found = false;
		for (int i = 0; i < projects.size()&&!found; i++) {
			if(projects.get(i).getName().equals(projectName)){
				found = true;
			}
		}
		return found;
	}
	@Override
	public boolean titleExist(String name){
		boolean found = false;
		for (int i = 0; i < titles.size()&&!found; i++) {
			if(titles.get(i).getTitle().equals(name)){
				found = true;
			}
		}
		return found;
		
	}
	@Override
	public boolean userExist(int uid) {
		boolean found = false;
		for (int i = 0; i < users.size()&&!found; i++) {
			if(users.get(i).getUserID() == uid){
				found = true;
			}
		}
		return found;
	}
	
	@Override
	public void insertProject(Project newProject){
		if(newProject!=null && newProject.getProjectNameLen()>0){
			projects.add(newProject);
		}else{
			throw new IllegalArgumentException("Inserting null or invalid Project to database");
		}	
	}
		
	
	@Override
	public ArrayList<Employee> getAllUser() {
		return users;
	}
	
	@Override
	public Employee getUser(int userID){
		for (Employee user:users){
			if(user.getUserID() == userID){
				return user;
			}
		}
		return null;
	}
	
	@Override
	public ArrayList<Timesheet> getAllTimesheet(){
		return timesheets;
	}

	@Override
	public ArrayList<Project> getAllProject() {
		return projects;
	}
	
	public String getDataBaseName(){
		return dbname;
	}

	@Override
	public void insertTitle(Title newTitle) {
		if(newTitle!=null && newTitle.getTitle()!=null && newTitle.getId()>0){
			titles.add(newTitle);
		}else{
			throw new IllegalArgumentException("Inserting null 	Title to database");
		}	
	}

	@Override
	public ArrayList<Title> getAllTitle() {
		return titles;
	}
}