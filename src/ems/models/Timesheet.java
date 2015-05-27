package ems.models;

import java.text.DateFormat;
import java.util.GregorianCalendar;


public class Timesheet{
	
	private int userID;
	private boolean approved;
	private String userNameString;
	private Project project;
	private GregorianCalendar startTimeDate;
	private GregorianCalendar endTimeDate;
	private float totalHours;
	
	public Timesheet(String userNameString, int userID, Project project, GregorianCalendar startTimeDate, GregorianCalendar endTimeDate) {
		if (userNameString == null){
			throw new IllegalArgumentException("username should never be null");
		}		
		this.userNameString = userNameString;		
		this.userID = userID;
		
		if(project == null){
			throw new IllegalArgumentException("project should never be null");
		}
		this.project = project;	
		
		
		
		if(startTimeDate == null){
			throw new IllegalArgumentException("start time/date object should never be null");
		}		
		if(endTimeDate == null){
			throw new IllegalArgumentException("end time/date object should never be null");
		}
		if(startTimeDate.after(endTimeDate)){
			throw new IllegalArgumentException("start time/date object must be after end time/date object");
		}
		this.startTimeDate = startTimeDate;
		this.endTimeDate = endTimeDate;
		
	}
	
	public int getUserID(){
		return userID;
	}
	
	public void setStartTime(GregorianCalendar start){		
		if(start !=null){
			startTimeDate = start;
		}else{
			throw new IllegalArgumentException("start time/date object should never be null");
		}
	}
	
	public void setEndTime(GregorianCalendar end){
		if(end !=null){
			endTimeDate = end;
		}else{
			throw new IllegalArgumentException("start time/date object should never be null");
		}
	}
	
	public void setHours(float hours){
		if(hours>0){
			totalHours = hours;
		}else {
			throw new IllegalArgumentException("hours should never be negative");
		}
	}
	
	public GregorianCalendar getStartTime(){
		return startTimeDate;
	}
	
	public GregorianCalendar getEndTime(){
		return endTimeDate;
	}
	
	public String getEndTimeString(){		
		return DateFormat.getTimeInstance(DateFormat.SHORT).format(endTimeDate.getTime());
	}
	
	public String getStartTimeString(){		
		return DateFormat.getTimeInstance(DateFormat.SHORT).format(startTimeDate.getTime());
	}
	
	public String getStartDateString(){
		return DateFormat.getDateInstance(DateFormat.SHORT).format(startTimeDate.getTime());
	}
	
	public String getEndDateString(){
		return DateFormat.getDateInstance(DateFormat.SHORT).format(endTimeDate.getTime());
	}
	
	public String getUserName(){
		return userNameString;
	}
	
	public void setProject(Project project){
		this.project = project;
	}
	
	public float getHours(){
		return totalHours;
	}
	
	public String getProjectName(){
		return project == null ? null : project.getName();
	}
	
	public boolean hasProjectName(String name){
		return project.hasName(name);
	}
	
	public boolean isApproved(){
		return approved;
	}
	
	public String toString(){
		return userID+" "+userNameString+" "+getProjectName()+" "+approved;
	}
}