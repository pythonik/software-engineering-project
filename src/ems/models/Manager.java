package ems.models;

import java.util.ArrayList;

public class Manager extends Employee{
	
	private ArrayList<Notification> notifications;
	
	public Manager(String lastNameString, String firstNameString,Title title) {
		super(lastNameString, firstNameString,title);
		notifications = new ArrayList<Notification>();
	}
	
	public String getEmployeeType() {
		return "M";
	}
	
	public void setNotifications(ArrayList<Notification> notifications){
		this.notifications = notifications;
	}
	
	public void addNotification(Notification notification){
		this.notifications.add(notification);
	}
	
	public ArrayList<Notification> getNotifications(){
		ArrayList<Notification> pending = new ArrayList<Notification>();
		for(Notification notification:this.notifications){
			if(notification.isPending()){
				pending.add(notification);
			}
		}
		
		return pending;
	}
}