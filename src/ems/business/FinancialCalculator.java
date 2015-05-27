package ems.business;

import java.util.ArrayList;

import ems.models.Employee;
import ems.models.Timesheet;
import ems.models.Title;

public class FinancialCalculator{
	
	public static double calcPayInPeriod(Timesheet timesheet, double wage){
		double result = -1;
		if(timesheet != null && wage >= 0){
		 result =  wage*CalculateHours.getHoursInPayPeriod(timesheet);
		}
		return result;
	} 
	
	public static double getTotalExpenditures(){
		ArrayList<Title> titles = new AccessTitle().getAllTitle();
		double totalPay = 0;
		for(Title el:titles){
			totalPay+=getTotalExpenditureByTitle(el);
		}
		return totalPay;
	}
	
	public static double getTotalExpenditureByTitle(Title title){
		ArrayList<Timesheet> timesheets = new AccessTimesheet().getAllTimesheet();
		ArrayList<Employee> users = new AccessUser().getUserList();
		double total = -1;
		if (title!=null){
			total = 0;
			for(Employee user : users){
				if(user.isType("E") && user.hasTitle(title)){
					for(Timesheet ts : timesheets){
						if(ts.getUserID() == user.getUserID()){
							total += calcPayInPeriod(ts, user.getRate());
						}
					}
				}
			}
		}
		return total;
	}
	
	public static double calculatePercentage(double nominator, double denominator) {
		double result = -1;
		if(denominator > 0 && nominator >=0){
			int trim =(int)((nominator/denominator)*10000);
			result = trim/100.0;
		}
		return result;
	}
	
}