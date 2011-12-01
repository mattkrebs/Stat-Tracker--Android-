package com.fourtyfourlights.scoretracker.domain;

import java.util.ArrayList;

public class ScoringAction {

	private int actionId;
	private String player;
	private String actionName;
	private String time;
		
	
	public void setID(int value){
		this.actionId = value;
	}
	
	public void setPlayer(String value){
		this.player = value;
	}
	public void setActionName(String value){
		this.actionName = value;
	}
	public void setGameTime(String value){
		this.time = value;
	}
	public int getId(){
		return this.actionId;
	}
	public String getPlayer(){
		return this.player;
	}
	
	public String getActionName(){
		return this.actionName;
	}
	
	public String getGameTime(){
		return this.time;
	}
	
	
	public ScoringAction(int Id, String player, String action, String time){
		setID(Id);
		setPlayer(player);
		setActionName(action);
		setGameTime(time);
	}
	
	
	
	
	public enum ActionType {
		OFFREBOUND, DEFREBOUND,TWOPOINTFG,THREEPOINTFG,ASSIST,TURNOVER,
		FREETHROWMADE,FREETHROWMISSED,STEAL,PERSONALFOUL,TECHNICALFOUL,
		SUBSTITUTION,SHORTTIMEOUT,LONGTIMEOUT
	}
}
