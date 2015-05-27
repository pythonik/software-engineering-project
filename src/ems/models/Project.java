package ems.models;

public class Project{
	
	private String name;
	
	public Project(String name){
		if(name!=null && name.length()>0){
			this.name = name;
		}else{
			throw new IllegalArgumentException("null proj name");
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String toString(){
		return name;
	}
	
	public int getProjectNameLen(){
		return name.length();
	}
	
	public boolean hasName(String name){
		return this.name.equals(name) ? true : false;
	}
}