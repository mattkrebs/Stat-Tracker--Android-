package com.fourtyfourlights.scoretracker.ui;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fourtyfourlights.scoretracker.R;
import com.fourtyfourlights.scoretracker.domain.Game;
import com.fourtyfourlights.scoretracker.domain.Player;
import com.fourtyfourlights.scoretracker.domain.ScoringAction;
import com.fourtyfourlights.scoretracker.domain.ScoringAction.ActionType;
import com.fourtyfourlights.scoretracker.util.AdvancedCountdownTimer;
import com.fourtyfourlights.scoretracker.util.StorageProvider;

import flexjson.JSONSerializer;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.renderscript.Font;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Typeface;
public class ScoreTrackrActivity extends Activity {
    /** Called when the activity is first created. */
	
	private Player sPlayer;
	private int sAction;
	
	private LinearLayout homePlayers;
	private LinearLayout awayPlayers;
	
	private ScoringLogFragment actionFrag;
	
	private StorageProvider storage;
	
	private TextView homeScore;
	private TextView awayScore;
	private TextView gameClock;
	
	private TextView homeTeamName;
	private TextView awayTeamName;
	
	private final int HOMETEAM = 0;
	private final int AWAYTEAM = 1;
	
	private Boolean clockpaused = true;
	private Game game;
	
	//Fouls
	private TextView homeFouls;
	private TextView awayFouls;
	private TextView homeFoulText;
	private TextView awayFoulText;
	
	private Typeface scoreboardfont;
	private Typeface athleticfont;
	private Typeface gothicBfont;
	private Typeface gothicBIfont;
	private Typeface gothicIfont;
	private Typeface gothicfont;
	
	private CurrentTeam cTeam;
	
	private int sTeam;
	
	 GameClock counter;
	private int GAMEID = 1;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //SetTypeFace
        scoreboardfont = Typeface.createFromAsset(getAssets(),"fonts/Ozone.ttf");
        athleticfont = Typeface.createFromAsset(getAssets(),"fonts/Athletic.TTF");
        gothicIfont= Typeface.createFromAsset(getAssets(),"fonts/GOTHICI.TTF");
        gothicBIfont = Typeface.createFromAsset(getAssets(),"fonts/GOTHICBI.TTF");
        gothicBfont = Typeface.createFromAsset(getAssets(),"fonts/GOTHICB.TTF");
        gothicfont = Typeface.createFromAsset(getAssets(),"fonts/GOTHIC.TTF");
        
        homeFouls = (TextView)this.findViewById(R.id.homeFouls);
        homeFoulText = (TextView)this.findViewById(R.id.foulText);
        awayFouls = (TextView)this.findViewById(R.id.awayFouls);
        awayFoulText = (TextView)this.findViewById(R.id.awayFoulText);
        
        homeFouls.setTypeface(scoreboardfont);
        homeFoulText.setTypeface(gothicfont);
        awayFouls.setTypeface(scoreboardfont);
        awayFoulText.setTypeface(gothicfont);
        
        
        
        
        homePlayers = (LinearLayout)this.findViewById(R.id.homePlayerList);
        awayPlayers = (LinearLayout)this.findViewById(R.id.awayPlayerList);
        
        homeScore = (TextView)this.findViewById(R.id.homeScore);
        awayScore = (TextView)this.findViewById(R.id.awayScore);
        
        gameClock = (TextView)this.findViewById(R.id.gameclock);
        gameClock.setTextColor(Color.RED);
        gameClock.setTypeface(scoreboardfont);
        // (minutes * 60000) 60000 = milliseconds in a minute
        counter = new GameClock(20 * 60000, 1000);
        gameClock.setOnLongClickListener(new OnLongClickListener(){

     			@Override
     			public boolean onLongClick(View arg0) {
     				// TODO Auto-generated method stub
     				if (clockpaused){
     					 counter.reset();
     					
     				}
     				return false;
     			}
             	
             });
        gameClock.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (clockpaused){
					 counter.start();
					 clockpaused = false;
					 gameClock.setTextColor(Color.GREEN);
				}else
				{
					counter.pause();
					clockpaused = true;
					gameClock.setTextColor(Color.RED);
				}
				
			}
        	
        });
        
     
       
       
        
        
        homeScore.setTypeface(scoreboardfont);
        awayScore.setTypeface(scoreboardfont);
        
        homeTeamName = (TextView)this.findViewById(R.id.homeTeamName);
        awayTeamName = (TextView)this.findViewById(R.id.awayTeamName);
        homeTeamName.setTypeface(scoreboardfont);
        awayTeamName.setTypeface(scoreboardfont);
        
      
   	 	actionFrag = (ScoringLogFragment) getFragmentManager().findFragmentById(R.id.frag_action);
   	 	//actionFrag.populateScoringList();

   	 	//actionFrag.selectPosition(0);
        
   		Button twopointer = (Button)this.findViewById(R.id.ltwopoint);
    	ShapeDrawable d = new ShapeDrawable(new ArcShape(270, 180));
		d.getPaint().setColor(0x88FF8844);
		
 		Button awaytwopointer = (Button)this.findViewById(R.id.rtwopoint);
    	ShapeDrawable da = new ShapeDrawable(new ArcShape(90, 180));
		da.getPaint().setColor(0x88FF8844);
		awaytwopointer.setBackgroundDrawable(da);
		twopointer.setBackgroundDrawable(d);
		homeScore.setText("0");
		awayScore.setText("0");
		storage = new StorageProvider(getApplicationContext());
	
		//	seedData();
		
		game = storage.getGame(GAMEID);
		
		homeTeamName.setText(game.getHomeTeam().getTeamName().toString().toUpperCase());
		awayTeamName.setText(game.getAwayTeam().getTeamName().toString().toUpperCase());
		
        for (Player player : game.getHomeTeam().getPlayers()){
        	PlayerButton b = new PlayerButton(this);
        	b.setText(player.getName(), player.getNumber(), "");
        	b.setTag(player.getID());
        	
        	
        	b.setOnClickListener(new OnClickListener(){
        		public void onClick(View v){
        			PlayerButton btn = (PlayerButton)v;
        			sPlayer = findPlayer(Integer.parseInt(btn.getTag().toString()), game.getHomeTeam().getPlayers());
        			cTeam = CurrentTeam.HOMETEAM;
        		}
        	});
        	homePlayers.addView(b);
        }
        
        for (Player player : game.getAwayTeam().getPlayers()){
        	PlayerButton b = new PlayerButton(this);
        	b.setDirection("right");
        	b.setText(player.getName(), player.getNumber(), "");
        	b.setTag(player.getID());
        	
        	b.setOnClickListener(new OnClickListener(){
        		public void onClick(View v){
        			PlayerButton btn = (PlayerButton)v;
        			sPlayer = findPlayer(Integer.parseInt(btn.getTag().toString()), game.getAwayTeam().getPlayers());
        			cTeam = CurrentTeam.AWAYTEAM;
        		}
        	});
        	awayPlayers.addView(b);
        }
        
        
    }

	public Player findPlayer(int id, List<Player> players){
		for (Player p :players){
			if (p.getID().equals(id))
				return p;
		}
		return null;
	}
	
	
	
	
	
	
 	public void recordPoints(View button){
 		int i;
    	if(sPlayer != null){
    		if (button.getId() == R.id.ltwopoint){
        		ArcButton btn = (ArcButton)button;
            	
            	if (btn.getIsTwoPointer()){
            		Log.w("SCORE", "ITS A TWO POINTER");
            		i = 2;
            		setAction(3, sPlayer);
            	}else{
            		i = 3;
            		setAction(4, sPlayer);
            	}
        	} else {
        		i = 3;
        		setAction(4, sPlayer);
        	}
    		
    		if (cTeam.equals(CurrentTeam.HOMETEAM)){
    			sPlayer = null;
    			i = Integer.parseInt(homeScore.getText().toString()) + i;
    			homeScore.setText(Integer.toString(i));
    		}else{
    			sPlayer = null;
    			i = Integer.parseInt(awayScore.getText().toString()) + i;
    			awayScore.setText(Integer.toString(i));
    		}
    			
    	}else{
			Toast.makeText(getApplicationContext(), "Please Click on a Player First", Toast.LENGTH_SHORT).show();
    	}
    	
    	
    	
    }
    
	public void playerClick(View button) {
		// TODO Auto-generated method stub
		Button btn = (Button)button;
		
		Log.w("Player Click", btn.getText().toString());
		
	}
	
	public void actionClick(View button){
		Button btn = (Button)button;
		
		Log.w("Action Click", btn.getText().toString());
		sAction = Integer.parseInt(btn.getTag().toString());
		if (sPlayer != null){
			setAction(sAction, sPlayer);
			sAction = 0;
			sPlayer = null;
		} else{
			Toast.makeText(getApplicationContext(), "Please Click on a Player First", Toast.LENGTH_SHORT).show();
		}
		
	
	   
	}
	
	
	public void setAction(int action, Player player){
		storage = new StorageProvider(getApplicationContext());
		storage.actionInsert(action, player.getName(), player.getID(), gameClock.getText().toString(), GAMEID);
		storage.close();
		actionFrag.refresh();
		
	}
	
	
	public class GameClock extends AdvancedCountdownTimer{

		public GameClock(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void onFinish() {
			gameClock.setText("done");
		}
		

		
		@Override
		public void onTick(long millisUntilFinished, int percent) {
			// TODO Auto-generated method stub
			SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
			String dateString = formatter.format(new Date(millisUntilFinished));
			gameClock.setText(dateString);
		}
		
	}
	
	public enum CurrentTeam{
		HOMETEAM,AWAYTEAM
	}

}