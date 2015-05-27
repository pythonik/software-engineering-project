package ems.models;

import java.text.DateFormat;
import java.util.GregorianCalendar;

import ems.business.AccessUser;
import ems.business.CalculateHours;

public class Notification{
	private Timesheet timesheet;
	private Project newProject;
	private GregorianCalendar newStartTimeDate;
	private GregorianCalendar newEndTimeDate;
	private boolean completed;
	
	public Notification(Timesheet timesheet){
		if(timesheet!=null){
			this.timesheet = timesheet;
			this.newProject = new Project(timesheet.getProjectName());
			this.newStartTimeDate = timesheet.getStartTime();
			this.newEndTimeDate = timesheet.getEndTime();	
			this.completed = false;
		} else {
			throw new IllegalArgumentException("Timesheet cannot be null when creating a notification");
		}
	}
	
	public void setProject(Project project){
		this.newProject = project;
	}
	
	public String getProjectName(){
		return newProject.getName();
	}
	
	public void setStartTime(GregorianCalendar startTime){
		this.newStartTimeDate = startTime;
	}
	
	public String getStartTimeString(){
		return DateFormat.getTimeInstance(DateFormat.SHORT).format(newStartTimeDate.getTime());
	}
	
	public void setEndTime(GregorianCalendar endTime){
		this.newEndTimeDate = endTime;
	}
	
	public String getEndTimeString(){
		return DateFormat.getTimeInstance(DateFormat.SHORT).format(newEndTimeDate.getTime());
	}
	
	public void setCompleted(boolean completed){
		this.completed = completed;
	}
	
	
	public String toString(){
		return String.format("%-19s %-25s %-26s %-26s\n"+
							 "%-19s %-25s %-26s %-26s",
				"[timesheet change]","old project:"+timesheet.getProjectName(),"old start time:"+timesheet.getStartTimeString(),"old end time:"+timesheet.getEndTimeString(),
											  "","new project:"+newProject,				 "new start time:"+DateFormat.getTimeInstance(DateFormat.SHORT).format(newStartTimeDate.getTime()),
																							 "new end time:"+DateFormat.getTimeInstance(DateFormat.SHORT).format(newEndTimeDate.getTime()));
	}
	
	public int getManagerID(){
		AccessUser access = new AccessUser();
		return access.getEmployeesManagerID(timesheet.getUserID());
	}
	public void completeChange(){
		if(newStartTimeDate!=null && newEndTimeDate!=null && newProject!=null){
			timesheet.setStartTime(newStartTimeDate);
			timesheet.setEndTime(newEndTimeDate);
			timesheet.setHours(CalculateHours.getDifferenceInHours(newStartTimeDate, newEndTimeDate));
			timesheet.setProject(newProject);
			this.completed = true;
		} else {
			throw new IllegalArgumentException("Timesheet cannot be null when creating a notification");
		}
	}

	public boolean isPending() {
		return !completed;
	}
}