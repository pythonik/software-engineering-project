package ems.business;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import ems.models.Employee;
import ems.models.Project;
import ems.models.Timesheet;
import ems.models.Title;

public class CalculateHours{
	
	
	/*
	 * Get total hours spent on one project in entire system 
	 */
	public static float getHoursByProject(Project proj){
		ArrayList<Timesheet> records = new AccessTimesheet().getTimesheetByProject(proj);
		float hours = 0;
		for(Timesheet el:records){
			hours+=el.getHours();
		}
		return hours;
	}
	
	public static float getEmployeeHoursByProject(int userID, Project proj){
		ArrayList<Timesheet> records = new AccessTimesheet().getTimesheetByProject(proj);
		float hours = 0;
		if(userID>=0 && proj!=null){
			for(Timesheet el:records){
				if (el.getUserID()==userID) {
					hours+=el.getHours();
				}
			}
		}
		return hours;
	}
	
	public static double getTotalHoursByTitle(Title title) {
		AccessTimesheet timesheets = new AccessTimesheet();
		AccessUser users = new AccessUser();
		double sum = 0;
		for(Timesheet ts:timesheets.getAllTimesheet()){
			for(Employee el:users.getUserByTitle(title)){
				if(ts.getUserID() ==el.getUserID()){
					sum+=getHoursInPayPeriod(ts);
				}
			}
		}
		return sum;
	}
	
	public static int getAllHours(){
		AccessTimesheet timesheets = new AccessTimesheet();
		ArrayList<Timesheet> alltimesheets = timesheets.getAllTimesheet();
		float sum = 0;
		for (Timesheet el:alltimesheets){
			sum+=el.getHours();
		}
		return (int) sum;
		
	}
	
	public static int getEmployeeHoursInPeriod(Employee employee) {
		float sum = 0;
		AccessTimesheet timesheets = new AccessTimesheet();
		ArrayList<Timesheet> employeeTimesheets = timesheets.getTimesheetByUser(employee);
		
		for(Timesheet el:employeeTimesheets){
			sum += CalculateHours.getHoursInPayPeriod(el);
		}
		
		return (int)sum;
	}
	
	public static int getTotalHoursByEmployee(Employee employee) {
		AccessTimesheet timesheets = new AccessTimesheet();
		ArrayList<Timesheet> employeeTimesheets = timesheets.getTimesheetByUser(employee);
		float sum = 0;
		for(Timesheet el:employeeTimesheets){
			sum+=el.getHours();
		}
		return (int) sum;
	}
	
	public static float getHoursInPayPeriod(Timesheet ts){
		GregorianCalendar tsStart, tsEnd;
		GregorianCalendar payStart, payEnd;
		float hours;
		
		payStart = CalculateDate.getPayPeriodStart(new GregorianCalendar());
		payEnd = CalculateDate.getPayPeriodEnd(new GregorianCalendar());
		
		tsStart = ts.getStartTime();
		tsEnd = ts.getEndTime();
		
		if((tsStart.before(payStart) && tsEnd.before(payEnd)) || (tsStart.after(payEnd) && tsEnd.after(payEnd))){
			hours = 0.0f;
		}
		else{
			if(tsStart.before(payStart)){
				tsStart = payStart;
			}
			if(tsEnd.after(payEnd)){
				tsEnd = payEnd;
			}
			hours = getDifferenceInHours(tsStart, tsEnd);
		}
		
		return hours;
	}
	
	public static float getDifferenceInHours(GregorianCalendar start, GregorianCalendar end){
		double timeDifference = 0;

		timeDifference = end.getTimeInMillis() - start.getTimeInMillis();
		timeDifference = Math.round(timeDifference / (1000 * 60)); //Time worked in minutes.
		float hours = (float) Math.floor(timeDifference / 60) + ((float) Math.round(timeDifference/15)%4)/4; //Hours worked, rounded to the nearest 15 minutes.
		
		return hours;
	}
}