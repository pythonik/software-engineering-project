package ems.application;
import ems.persistence.*;
import ems.presentation.*;


public class Main{
	
	public static void  main(String[] args) {
		//this will use default stub db 
		//DatabaseAdapter.setAdapterInterface();
		DatabaseAdapter.setAdapterInterface(new DatabaseReal("emsdb"));
		DatabaseAdapter.initDB();
		
		LogInWindow logInWindow = new LogInWindow();
		logInWindow.open();	
	}
	
	public static void shutDown(){
		DatabaseAdapter.closeDB();
	}
}