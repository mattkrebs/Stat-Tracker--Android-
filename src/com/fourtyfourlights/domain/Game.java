package com.fourtyfourlights.domain;

import java.util.ArrayList;

import com.fourtyfourlights.scoretracker.ThisApplication;
import com.fourtyfourlights.util.StorageProvider;

public class Game {

	private Integer gameId;
	private Team homeTeam;
	private Team awayTeam;
	private Integer clockTime;
	private Integer periods;
	
	
	public Integer getGameId(){
		return gameId;
	}
	public Team getHomeTeam(){
		return homeTeam;
	}
	public Team getAwayTeam(){
		return awayTeam;
	}
	public Integer getClockTime(){
		return clockTime;
	}
	public Integer getPeriods(){
		return periods;
	}
	
	public void setGameId(Integer value){
		this.gameId = value;
	}
	public void setHomeTeam(Team value){
		this.homeTeam = value;
	}
	public void setAwayTeam(Team value){
		this.awayTeam = value;
	}
	public void setClockTime(Integer value){
		this.clockTime = value;
	}
	public void setPeriods(Integer value){
		this.periods = value;
	}
	

	public Game(int clockTime, int periods) {
		this.setClockTime(clockTime);
		this.setPeriods(periods);
	}

	
	public Game(int id, int homeTeamId, int awayTeamId, int clockTime, int periods) {
		// TODO Auto-generated constructor stub
		this.setGameId(id);
		// These could be lazy loaded as well  Mesure performance gains... learn to spell measure
		this.setHomeTeam(new Team().getTeamById(homeTeamId));
		this.setAwayTeam(new Team().getTeamById(awayTeamId));
		this.setClockTime(clockTime);
		this.setPeriods(periods);
	}
	

	public void create(){
		StorageProvider provider = new StorageProvider(ThisApplication.getAppContext());
		provider.gameInsert(this.getHomeTeam().getTeamId(), this.getAwayTeam().getTeamId(),this.getClockTime(), this.getPeriods());
		
	}
	
	
	
	
	
}
