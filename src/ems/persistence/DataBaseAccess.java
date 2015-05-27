package ems.persistence;
public abstract class DataBaseAccess{
	
	public DataBaseAccess() {
		if(!DatabaseAdapter.databaseIsSet()){
			throw new IllegalAccessError("Database not set");
		}
	}	
}