package com.fourtyfourlights.scoretracker.domain;
import java.util.List;

import com.fourtyfourlights.scoretracker.ui.ThisApplication;
import com.fourtyfourlights.scoretracker.util.StorageProvider;

public class Team {
	private Integer TeamID;
	private String TeamName;
	private String PhotoUrl;
	
	public void setTeamId(Integer value){
		this.TeamID = value;
		
	}
	public void setTeamName(String value){
		this.TeamName = value;
	}
	public void setPhotoUrl(String value){
		this.PhotoUrl = value;
	}
	
	public Integer getTeamId(){
		return this.TeamID;
	}
	public String getTeamName(){
		return this.TeamName;
	}
	public String getPhotoUrl(){
		return this.PhotoUrl;
	}
	
	public Team(){
		
	}
	
	public Team(int id, String teamName, String teamPhotoUrl){
		this.setTeamId(id);
		this.setTeamName(teamName);
		this.setPhotoUrl(teamPhotoUrl);
	}
	
	public Team(String teamName, String teamPhotoUrl){
		this.setTeamName(teamName);
		this.setPhotoUrl(teamPhotoUrl);
	}
	public static Team getTeamById(int teamId){
		StorageProvider provider = new StorageProvider(ThisApplication.getAppContext());
		return provider.getTeamById(teamId);
	}
	//Lazy loading players on request
	public List<Player> getPlayers(){
		if (this.getTeamId() != null){
			StorageProvider provider = new StorageProvider(ThisApplication.getAppContext());
			return provider.getAllPlayersByTeamId(this.getTeamId());
		}
		return null;
		
	}
	
}
