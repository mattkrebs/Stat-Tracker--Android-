package com.fourtyfourlights.scoretracker.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.fourtyfourlights.scoretracker.domain.*;

public class StorageProvider {

	private String TAG = "StorageProvider";
	
	
	private static final String DATABASE_NAME = "stattrackr.db";
	
	private static final int DATABASE_VERSION = 10;
	private static final String ACTION_LOG_TABLE_NAME = "actionlog";
	private static final String ACTION_TYPE_TABLE_NAME = "actiontype";
	private static final String GAME_TABLE_NAME = "game";
	private static final String TEAM_TABLE_NAME = "team";
	private static final String PLAYER_TABLE_NAME = "player";
	//actionlog
		//<id, action_name,player_name,player_id, time>
		//game
		//<id, home_team_blob, away_team_blob>
	
	
	private Context context;
	private SQLiteDatabase db;
	private SQLHelper openHelper;
	
	private SQLiteStatement actionInsert;
	private SQLiteStatement gameInsert;
	private SQLiteStatement actionTypeInsert;
	private SQLiteStatement playerInsert;
	private SQLiteStatement teamInsert;
	
	private static final String ACTION_INSERT = "insert into " + ACTION_LOG_TABLE_NAME + " (action_id, player_name, player_id, time, game_id) values (?,?,?,?,?) ";
	private static final String ACTION_TYPE_INSERT = "insert into " + ACTION_TYPE_TABLE_NAME + " (id, action_name) values (?,?) ";
	private static final String GAME_INSERT = "insert into " + GAME_TABLE_NAME + " (home_team_id, away_team_id, game_time, periods) values (?,?,?,?) ";
	private static final String TEAM_INSERT = "insert into " + TEAM_TABLE_NAME+ " (TeamName, PhotoUrl) values (?,?) ";
	private static final String PLAYER_INSERT = "insert into " + PLAYER_TABLE_NAME + " (player_name, player_number, team_id) values (?,?,?) ";
	
	public StorageProvider(Context context){
		this.context = context;
		openHelper = new SQLHelper(this.context, DATABASE_NAME, null, DATABASE_VERSION);
		
		this.db = openHelper.getWritableDatabase();
		
		this.actionInsert = this.db.compileStatement(ACTION_INSERT);
		this.gameInsert = this.db.compileStatement(GAME_INSERT);
		this.actionTypeInsert = this.db.compileStatement(ACTION_TYPE_INSERT);
		this.playerInsert = this.db.compileStatement(PLAYER_INSERT);
		this.teamInsert = this.db.compileStatement(TEAM_INSERT);
	}
	
	public long playerInsert(String playerName, int playerNumber, int teamId){
	
		this.playerInsert.bindString(1, playerName);
		this.playerInsert.bindLong(2, playerNumber);
		this.playerInsert.bindLong(3, teamId);
		return this.playerInsert.executeInsert();
	}
	
	public long actionTypeInsert(int actionTypeId, String actionName){
		this.actionTypeInsert.bindLong(1, actionTypeId);
		this.actionTypeInsert.bindString(2, actionName);
		return this.actionTypeInsert.executeInsert();
	}
	
	public long actionInsert(int actionId, String playerName, int playerid, String time, int gameid){
		
		this.actionInsert.bindLong(1, actionId);
		this.actionInsert.bindString(2, playerName);
		this.actionInsert.bindLong(3, playerid);
		this.actionInsert.bindString(4, time);
		this.actionInsert.bindLong(5, gameid);
		
		return this.actionInsert.executeInsert();		
		
	}
	public long gameInsert(int homeTeamId, int awayTeamId, Integer gameTime, Integer periods){
		this.gameInsert.bindLong(1, homeTeamId);
		this.gameInsert.bindLong(2, awayTeamId);
		this.gameInsert.bindLong(3, gameTime);
		this.gameInsert.bindLong(4, periods);
		return this.gameInsert.executeInsert();
	}
	
	public long teamInsert(Team team){
		if (team.getTeamName() != null)
			this.teamInsert.bindString(1, team.getTeamName());
		if (team.getPhotoUrl() != null)
			this.teamInsert.bindString(2, team.getPhotoUrl());
		
		return this.teamInsert.executeInsert();
	}
	
	public void close(){
		if (openHelper != null)
			openHelper.close();
		
		this.db.close();
	}
	
	public ArrayList<ScoringAction> getAllActionsByGameId(int gameId){
		ArrayList<ScoringAction> list = new ArrayList<ScoringAction>();
		
		String q = "select A.id, T.action_name, player_name, time from " + ACTION_LOG_TABLE_NAME + " A INNER JOIN " + ACTION_TYPE_TABLE_NAME + " T on A.action_id = T.id where game_id = " + gameId + " order by A.id desc";
		Cursor cursor = this.db.rawQuery(q, null);
		
		if (cursor.moveToFirst()){
			do{
				ScoringAction action = new ScoringAction(cursor.getInt(0), cursor.getString(2), cursor.getString(1), cursor.getString(3));
				list.add(action);
			}while(cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()){
			cursor.close();
		}
		close();
		return list;
	}
	
	
	public List<Player> getAllPlayersByTeamId(int teamId){
		List<Player> players = new ArrayList<Player>();
		
		String q = "select id, player_name, player_number, TeamID from " + PLAYER_TABLE_NAME + " where TeamID = " + teamId;
		Cursor cursor = this.db.rawQuery(q, null);
		
		if (cursor.moveToFirst()){
			do{
				Player p = new Player(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
				players.add(p);
			}while(cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()){
			cursor.close();
		}
		close();
		return players;
	}
	
	public Player getPlayer(int playerId){
		Player player = null;
		
		String q = "select id, player_name, player_number, TeamID from " + PLAYER_TABLE_NAME + " where id = " + playerId;
		Cursor cursor = this.db.rawQuery(q, null);
		
		if (cursor.moveToFirst()){
			do{
				player = new Player(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
				
			}while(cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()){
			cursor.close();
		}
		close();
		return player;
	}
	
	
	public List<Game> getGames(){
		List<Game> games = new ArrayList<Game>();
		String q = "select id, home_team_id, away_team_id, game_time, periods from " + GAME_TABLE_NAME;
		
		Cursor cursor = this.db.rawQuery(q, null);
		
		if (cursor.moveToFirst()){
			do{
				Game game = new Game(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2),cursor.getInt(3),cursor.getInt(4));
				games.add(game);
			}while(cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()){
			cursor.close();
		}
		close();
		return games;
	}
	
	public Game getGame(int gameId){
		Game game = null;
		String q = "select id, home_team_id, away_team_id, game_time, periods from " + GAME_TABLE_NAME + " where id = " + gameId;
		
		Cursor cursor = this.db.rawQuery(q, null);
		
		if (cursor.moveToFirst()){
			do{
				game = new Game(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3),cursor.getInt(4));
				
			}while(cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()){
			cursor.close();
		}
		close();
		return game;	
		
	}

	public List<Team> getTeams(){
		List<Team> teams = new ArrayList<Team>();
		
		String q = "select id, TeamName, PhotoUrl from " + TEAM_TABLE_NAME;
		
		Cursor cursor = this.db.rawQuery(q, null);
		
		if (cursor.moveToFirst()){
			do{
				Team team = new Team(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
				teams.add(team);
			}while(cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()){
			cursor.close();
		}
		
		return teams;
		
	}
	public Team getTeamById(int teamId) {
		Team team = null;
		String q = "select id, TeamName, PhotoUrl from " + TEAM_TABLE_NAME + " where id = " + teamId;
		
		Cursor cursor = this.db.rawQuery(q, null);
		
		if (cursor.moveToFirst()){
			do{
				team = new Team(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
				
			}while(cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()){
			cursor.close();
		}
		close();
		return team;	
	}
	
	
	
	public void updateTeams(List<Team> teams){
		Log.w(TAG, "Updating Teams from server");
		db.execSQL("DROP TABLE IF EXISTS " + TEAM_TABLE_NAME);
		db.execSQL("CREATE TABLE " + TEAM_TABLE_NAME + "(id INTEGER PRIMARY KEY autoincrement, TeamName TEXT, PhotoUrl TEXT)");
		
		try{
			for(Team t : teams){
					teamInsert(t);
				
			}
		}catch(Exception e){
			Log.w(TAG, e.getMessage());
		}finally{
			close();
		}
	}
	
	
}
