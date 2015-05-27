package ems.business;

import java.util.ArrayList;

import ems.models.Title;
import ems.persistence.DatabaseAdapter;

public class AccessTitle{
	
	public ArrayList<Title> getAllTitle(){
		return DatabaseAdapter.getAllTitle();
	}
	
	
}