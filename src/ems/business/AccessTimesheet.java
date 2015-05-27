package ems.business;
import java.util.ArrayList;
import ems.models.Employee;
import ems.models.Project;
import ems.models.Timesheet;
import ems.persistence.DataBaseAccess;
import ems.persistence.DatabaseAdapter;

public class AccessTimesheet extends DataBaseAccess{
	
	private Employee currentUser;
	private ArrayList<Timesheet> currentUserTimesheets;
	
	public AccessTimesheet(Employee currentUser) {
		this.currentUser = currentUser;
		currentUserTimesheets = null;
	}
	
	public AccessTimesheet(){
		currentUser = null;
		currentUserTimesheets = null;
	}
	
	public boolean isUserSet(){
		return currentUser!=null;
	}
	
	public ArrayList<Timesheet> getTimesheetByUser(Employee user){
		ArrayList<Timesheet> records = new ArrayList<Timesheet>();
		AccessUser access = new AccessUser();
		if (access.userExist(user)) {
			for(Timesheet cur:DatabaseAdapter.getAllTimesheet()){
				if(user.hasID(cur.getUserID())) {
					records.add(cur);
				}
			}
		}
		return records;
	}
	
	public ArrayList<Timesheet> getCurrentUserTimesheet(){
		if (isUserSet()){
			currentUserTimesheets = getTimesheetByUser(currentUser);
		}
		return currentUserTimesheets;
	}
	
	public boolean insertTimesheet(Timesheet newEntry){
		boolean result = false;
		if(newEntry !=null){
			DatabaseAdapter.insertTimesheet(newEntry);
			result = true;
		}
		return result;
	}
	
	public ArrayList<Timesheet> getAllTimesheet(){
		return DatabaseAdapter.getAllTimesheet();
	}
	
	public ArrayList<Timesheet> getTimesheetByProject(Project proj){
		ArrayList<Timesheet> groupByProject = new ArrayList<Timesheet>();
		if (proj !=null && proj.getProjectNameLen()>0){ 
			for (Timesheet ts:getAllTimesheet()){
				if(ts.getProjectName().equals(proj.getName())){
					groupByProject.add(ts);
				}
			}
		}
		return groupByProject;
	}
}