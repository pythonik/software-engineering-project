package ems.persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
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



public class DatabaseReal implements DataBaseInterface{
	
	String dbName;
	String dbType;
	
	Connection con;
	Statement st1;
	ResultSet rs2;
	
	public DatabaseReal(String dbName){
		this.dbName = dbName;
	}
	
	@Override
	public void init()
	{
		String url;
		try
		{
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			url = "jdbc:hsqldb:database/" + dbName; // stored on disk mode
			con = DriverManager.getConnection(url, "SA", "");
			st1 = con.createStatement();
			getAllTitle();
		}
		catch (Exception e)
		{
			System.out.println("Error opening db");
		}
	}
	
	@Override
	public ArrayList<Project> getAllProject(){
		String name;
		Project currProject;
		ArrayList<Project> projects = new ArrayList<Project>();
		
		try{
			ResultSet rs = con.prepareStatement("select * from projects;").executeQuery();
			
			while(rs.next()){
				name = rs.getString("Name");
				currProject = new Project(name);

				projects.add(currProject);
			}
		}
		catch(Exception e){
			System.out.println("Error getting all projects");
		}
		
		return projects;
	}
	
	@Override
	public ArrayList<Timesheet> getAllTimesheet(){
		String userName, projectName;
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		int userID;
		Timesheet currTimesheet;
		Project currProject;
		ArrayList<Timesheet> timesheets = new ArrayList<Timesheet>();
		
		try{
			ResultSet rs = con.prepareStatement("select * from timesheets;").executeQuery();
			ResultSet rs2;
			
			while(rs.next()){
				projectName = rs.getString("Project");
				start = new GregorianCalendar();
				end = new GregorianCalendar();

				start.setTimeInMillis(rs.getTimestamp("StartTime").getTime());
				end.setTimeInMillis(rs.getTimestamp("EndTime").getTime());

				userID = rs.getInt("UserID");
				
				rs2 = con.prepareStatement("select * from employees where UserID = " + userID).executeQuery();
				
				rs2.next();
				userName = rs2.getString("FirstName") + " " + rs2.getString("LastName");
				
				currProject = new Project(projectName);
				currTimesheet = new Timesheet(userName, 
						userID, 
						currProject, 
						start, 
						end);
				
				currTimesheet.setHours(CalculateHours.getDifferenceInHours(start, end));
				timesheets.add(currTimesheet);
			}
		}
		catch(Exception e){
			System.out.println("Error getting all timesheets");
		}
		
		return timesheets;
	}
	
	@Override
	public ArrayList<Employee> getAllUser(){
		ArrayList<Employee> employees = new ArrayList<Employee>();
		try{
			String firstName, lastName, type;
			int id, manId;
			Employee currEmp;
			Manager man;
			int title;
			ArrayList<Title> titles;
			ResultSet rs = con.prepareStatement("select * from employees;").executeQuery();
			ResultSet rs2 = con.prepareStatement("select * from notifications").executeQuery();
			ArrayList<Timesheet> timesheets = getAllTimesheet(); 
			ArrayList<Notification> notifications = new ArrayList<Notification>();
	  		while(rs2.next()){
				Timesheet currTimesheet = timesheets.get(rs2.getInt("TIMESHEET"));
				Project newProject = new Project(rs2.getString("NEWPROJECT"));
				GregorianCalendar newStartTime = new GregorianCalendar();
				newStartTime.setTime(rs2.getTimestamp("NEWSTARTTIME"));
				GregorianCalendar newEndTime = new GregorianCalendar();
				newEndTime.setTime(rs2.getTimestamp("NEWENDTIME"));
				Notification currNotif = new Notification(currTimesheet);
				currNotif.setProject(newProject);
				currNotif.setStartTime(newStartTime);
				currNotif.setEndTime(newEndTime);
				currNotif.setCompleted(rs2.getBoolean("COMPLETED"));
				notifications.add(currNotif);
  			}
			ResultSet rs3;
			titles = getAllTitle();
          while(rs.next()){
        	  id = rs.getInt("UserID");
        	  firstName = rs.getString("FirstName");
        	  lastName = rs.getString("LastName");
        	  type = rs.getString("Type");
        	  title = rs.getInt("Title");
        	  Title foundtTitle = null;
        	  manId = rs.getInt("ManID");
        	  for(Title el:titles){
        		  if(el.getId() == title){
        			  foundtTitle = el;
        			  break;
        		  }
        	  }
        	  if(type.equals("M")){
        		  currEmp = new Manager(firstName, lastName,foundtTitle);
        		  for(Notification notification:notifications){
        			  if(notification.getManagerID() == id){
        				  ((Manager)currEmp).addNotification(notification);
        			  }
        		  }
        		  
        	  }
        	  else if(type.equals("E")){
        		  currEmp = new Employee(firstName, lastName,foundtTitle);
        	  }
        	  else if(type.equals("F")){
        		  currEmp = new Financial(firstName, lastName,foundtTitle);
        	  }
        	  else{
        		  System.out.println("Error: Unidentified user type: " + type);
        		  currEmp = null;
        	  }
        	  
				rs3 = con.prepareStatement("select * from employees where UserID = " + manId).executeQuery();
				
				if(rs3.next()){
		        	  man = new Manager(rs3.getString("FirstName"), rs3.getString("LastName"),null);
		        	  man.setUserID(manId);
		        	  currEmp.setManager(man);
				}
        	  currEmp.setUserID(id);
        	  employees.add(currEmp);
          }

		}
		catch(Exception e){
			System.out.println(e);
			System.out.println("Error getting all employees");
		}
		
		return employees;
	}
	
	@Override
	public Employee getUser(int userID){
		Employee currEmp;
		try{
			String firstName, lastName, type;
			@SuppressWarnings("unused")
			String manName;
			int id, manId;
			Manager man;
			int title;
			ArrayList<Title> titles;
			ResultSet rs = con.prepareStatement("select * from employees where UserID = "+userID+";").executeQuery();
			ResultSet rs2;
			titles = getAllTitle();
			
			rs.next();
        	id = rs.getInt("UserID");
        	firstName = rs.getString("FirstName");
        	lastName = rs.getString("LastName");
			type = rs.getString("Type");
			title = rs.getInt("Title");
			Title foundtTitle = null;
			manId = rs.getInt("ManID");
			for(Title el:titles){
				if(el.getId() == title){
					foundtTitle = el;
					break;
				}
			}
        	if(type.equals("M")){
        		currEmp = new Manager(firstName, lastName,foundtTitle);
        	}
        	else if(type.equals("E")){
        		currEmp = new Employee(firstName, lastName,foundtTitle);
        	}
        	else{
        		System.out.println("Error: Unidentified user type: " + type);
        		currEmp = null;
        	}
        	  
			rs2 = con.prepareStatement("select * from employees where UserID = " + manId).executeQuery();
				
			if(rs2.next()){
				man = new Manager(rs2.getString("FirstName"), rs2.getString("LastName"),null);
				man.setUserID(manId);
		        	  
				currEmp.setManager(man);
			}


			currEmp.setUserID(id);

    		return currEmp;
		}
		catch(Exception e){
			System.out.println("Error getting user #"+userID);
		}
		return null;
	}
	
	@Override
	public ArrayList<Title> getAllTitle(){
		
		ArrayList<Title> titles = new ArrayList<Title>();
		try{
			String title;
			int id;
			double rate;
			ResultSet rs = con.prepareStatement("select * from title;").executeQuery();
			while(rs.next()){
				id = rs.getInt("id");
				rate = rs.getDouble("rate");
				title = rs.getString("title");
				titles.add(new Title(title, id, rate));
			}
		}catch(Exception e){
				System.out.println("Error getting all titles");
		}
		return titles;
	}
	
	@Override
	public void insertTitle(Title title){
		
		try{
			String cmd;

			String values = title.getId() + ", '" +
							title.getTitle() + "', '" +
							title.getRate();
			cmd = "insert into Employees values(" + values + ")";
			st1.executeUpdate(cmd);
		}
		catch(Exception e){
			System.out.println("Error: Cannot insert new user");
		}
	}
	@Override
	public void insertUser(Employee newUser){
		
		try{
			String cmd;

			String values = newUser.getUserID() + ", '" +
							newUser.getUserFirstName() + "', '" +
							newUser.getUserLastName() + "', '" +
							newUser.getEmployeeType() + "'";
			cmd = "insert into Employees values(" + values + ")";
			st1.executeUpdate(cmd);
			
		}
		catch(Exception e){
			System.out.println("Error: Cannot insert new user");
		}
		
		
	}
	@Override
	public void insertTimesheet(Timesheet newTimesheet){
		Timestamp startTime, endTime;
		try{
			String cmd;
			
			startTime = new Timestamp(newTimesheet.getStartTime().getTimeInMillis());
			endTime = new Timestamp(newTimesheet.getEndTime().getTimeInMillis());
			
			String values = newTimesheet.getHours()+","+newTimesheet.isApproved() + ", " +
							newTimesheet.getUserID() + ", '" +
							newTimesheet.getProjectName() + "', '" +
							startTime.toString() + "', '" +
							endTime.toString() + "'";
			cmd = "insert into Timesheets values(" + values + ")";
			st1.executeUpdate(cmd);
			
			
		}
		catch(Exception e){
			System.out.println("Error: Cannot insert new timesheet");
		}
		
		
	}
	@Override
	public void insertProject(Project newProject){
		
		try{
			String cmd;
			String values = newProject.getName();
			
			cmd = "insert into Projects values('" + values +"')";
			st1.executeUpdate(cmd);
		}
		catch(Exception e){
			System.out.println("Error: Cannot insert new Project");
		}
		
	}

	@Override
	public void close()
	{
		String cmdString;
		try
		{	// commit all changes to the database
			cmdString = "shutdown compact";
			rs2 = st1.executeQuery(cmdString);
			con.close();
		}
		catch (Exception e)
		{
			System.out.println("Error Closing db");
		}
	}

	@Override
	public boolean titleExist(String name) {
		boolean result = false;
		ArrayList<Title> titles = getAllTitle();
		for(Title el:titles){
			if(el.getTitle().equals(name)){
				result= true;
			}
		}
		return result;
	}

	@Override
	public boolean projectExist(String projectName) {
		boolean result = false;
		ArrayList<Project> projs = getAllProject();
		for(Project el:projs){
			if(el.getName().equals(projectName)){
				result= true;
			}
		}
		return result;
	}

	@Override
	public boolean userExist(int uid) {
		boolean result = false;
		ArrayList<Employee> users = getAllUser();
		for(Employee el:users){
			if(el.getUserID()==uid){
				result= true;
			}
		}
		return result;
	}
}
