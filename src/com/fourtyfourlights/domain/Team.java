package com.fourtyfourlights.domain;
import java.util.List;

import com.fourtyfourlights.scoretracker.ThisApplication;
import com.fourtyfourlights.util.StorageProvider;

public class Team {
	private Integer teamId;
	private String teamName;
	private String teamColor;
	
	public void setTeamId(Integer value){
		this.teamId = value;
		
	}
	public void setTeamName(String value){
		this.teamName = value;
	}
	public void setTeamColor(String value){
		this.teamColor = value;
	}
	
	public Integer getTeamId(){
		return this.teamId;
	}
	public String getTeamName(){
		return this.teamName;
	}
	public String getTeamColor(){
		return this.teamColor;
	}
	
	public Team(){
		
	}
	
	public Team(int id, String teamName, String teamColor){
		this.setTeamId(id);
		this.setTeamName(teamName);
		this.setTeamColor(teamColor);
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
