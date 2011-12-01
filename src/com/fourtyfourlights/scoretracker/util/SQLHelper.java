package com.fourtyfourlights.scoretracker.util;

import org.json.JSONArray;

import com.fourtyfourlights.scoretracker.domain.Player;

import flexjson.JSONSerializer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelper extends SQLiteOpenHelper {

	//actionlog
	//<id, action_name,player_name,player_id, time>
	//game
	//<id, home_team_blob, away_team_blob>

	private Context context;
	
	private static final String ACTION_LOG_TABLE_NAME = "actionlog";
	private static final String GAME_TABLE_NAME = "game";
	private static final String ACTION_TYPE_TABLE_NAME = "actiontype";
	private static final String TEAM_TABLE_NAME = "team";
	private static final String PLAYER_TABLE_NAME = "player";
	
	public SQLHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + ACTION_LOG_TABLE_NAME + "(id INTEGER PRIMARY KEY autoincrement, action_id INT, player_name TEXT, player_id INT, time TEXT, game_id INT)");
		db.execSQL("CREATE TABLE " + GAME_TABLE_NAME + "(id INTEGER PRIMARY KEY autoincrement, home_team_id INT, away_team_id INT, game_time INT, periods INT)");
		db.execSQL("CREATE TABLE " + ACTION_TYPE_TABLE_NAME + "(id INTEGER PRIMARY KEY, action_name TEXT)");
		db.execSQL("CREATE TABLE " + TEAM_TABLE_NAME + "(id INTEGER PRIMARY KEY autoincrement, TeamName TEXT, PhotoUrl TEXT)");
		db.execSQL("CREATE TABLE " + PLAYER_TABLE_NAME + "(id INTEGER PRIMARY KEY autoincrement, player_name TEXT, player_number INT, team_id INT)");
		
	//	seedData();
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + ACTION_LOG_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + GAME_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + ACTION_TYPE_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TEAM_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + PLAYER_TABLE_NAME);
		onCreate(db);
	}
	

}
