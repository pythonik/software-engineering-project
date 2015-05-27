package ems.models;

public class Title{
	private String title;
	private int id;
	private double rate;
	
	public Title(String title, int id, double rate){
		if(title!=null && title.length()>0){
			this.title = title;
			this.id = id;
			this.rate = rate;
		}else {
			throw new IllegalArgumentException("null title name or zero len title name");
		}
	}
	
	public boolean equals(Title comp) {
		return comp.getTitle().equals(title);
	}
	
	public String getTitle(){
		return title;
	}
	
	public double getRate(){
		return rate;
	}
	
	public int getId(){
		return id;
	}
	
	public String toString() {
		return title;
		
	}
}