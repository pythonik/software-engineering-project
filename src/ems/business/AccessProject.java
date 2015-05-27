package ems.business;
import java.util.ArrayList;
import ems.models.Project;
import ems.persistence.DataBaseAccess;
import ems.persistence.DatabaseAdapter;

public class AccessProject extends DataBaseAccess{
	
	public ArrayList<Project> getAllProjects(){
		return DatabaseAdapter.getAllProject();
	}
	
	public boolean insertProject(Project proj){
		boolean result = false;
		if(proj!=null){
			result = true;
			DatabaseAdapter.insertProject(proj);
		}
		return result;
	}
}