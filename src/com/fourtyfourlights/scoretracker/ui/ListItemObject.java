package com.fourtyfourlights.scoretracker.ui;


public class ListItemObject {
	private String Title;
	private String Description;
	private Integer Id;
	
	public ListItemObject(Integer id, String title, String description) {
		this.setId(id);
		this.setTitle(title);
		this.setDescription(description);
	}
	public String getTitle(){
		return Title;
	}
	public String getDescription(){
		return Description;
	}
	public Integer getId(){
		return Id;
	}
	
	public void setTitle(String value){
		this. Title = value;
	}
	public void setDescription(String value){
		this.Description = value;
	}
	public void setId(Integer value){
		this.Id = value;
	}
}
