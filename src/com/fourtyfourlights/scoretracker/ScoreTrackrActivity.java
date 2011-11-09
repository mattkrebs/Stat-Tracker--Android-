package com.fourtyfourlights.scoretracker;


import java.util.ArrayList;
import java.util.List;

import com.fourtyfourlights.domain.Game;
import com.fourtyfourlights.domain.Player;
import com.fourtyfourlights.domain.ScoringAction;
import com.fourtyfourlights.domain.ScoringAction.ActionType;
import com.fourtyfourlights.util.StorageProvider;

import flexjson.JSONSerializer;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

	private TextView homeTeamName;
	private TextView awayTeamName;
	
	private final int HOMETEAM = 0;
	private final int AWAYTEAM = 1;
	
	private Game game;
	
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        homePlayers = (LinearLayout)this.findViewById(R.id.homePlayerList);
        awayPlayers = (LinearLayout)this.findViewById(R.id.awayPlayerList);
        
        homeScore = (TextView)this.findViewById(R.id.homeScore);
        awayScore = (TextView)this.findViewById(R.id.awayScore);
        
        homeTeamName = (TextView)this.findViewById(R.id.homeTeamName);
        awayTeamName = (TextView)this.findViewById(R.id.homeTeamName);
        
        
   	 	actionFrag = (ScoringLogFragment) getFragmentManager().findFragmentById(R.id.frag_action);
   	 	//actionFrag.populateScoringList();

   	 	//actionFrag.selectPosition(0);
        
   		Button twopointer = (Button)this.findViewById(R.id.ltwopoint);
    	ShapeDrawable d = new ShapeDrawable(new ArcShape(270, 180));
		d.getPaint().setColor(0x88FF8844);
	
		twopointer.setBackgroundDrawable(d);
		homeScore.setText("0");
		storage = new StorageProvider(getApplicationContext());
	
		//	seedData();
		
		game = storage.getGame(1);
		
        for (Player player : game.getHomeTeam().getPlayers()){
        	PlayerButton b = new PlayerButton(this);
        	b.setText(player.getName(), player.getNumber(), "");
        	b.setTag(player.getID());
        	
        	b.setOnClickListener(new OnClickListener(){
        		public void onClick(View v){
        			PlayerButton btn = (PlayerButton)v;
        			sPlayer = findPlayer(Integer.parseInt(btn.getTag().toString()), game.getHomeTeam().getPlayers());
        		}
        	});
        	homePlayers.addView(b);
        }
        
        for (Player player : game.getAwayTeam().getPlayers()){
        	PlayerButton b = new PlayerButton(this);
        	b.setText(player.getName(), player.getNumber(), "");
        	b.setTag(player.getID());
        	b.setOnClickListener(new OnClickListener(){
        		public void onClick(View v){
        			PlayerButton btn = (PlayerButton)v;
        			sPlayer = findPlayer(Integer.parseInt(btn.getTag().toString()), game.getAwayTeam().getPlayers());
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
	
	public void seedData(){
		List<Player> p = new ArrayList<Player>();
		
		StorageProvider provider = new StorageProvider(getApplicationContext());
		
		
		
		provider.teamInsert("Abe Froman", "Purple");
		provider.teamInsert("Saints +", "Gold");
		
		provider.playerInsert("Matt Krebs", 2, 1);
		provider.playerInsert("Bryan Munsell", 4, 1);
		provider.playerInsert("Hans Hennen", 4, 1);
		provider.playerInsert("Sean Ball", 1, 1);
		provider.playerInsert("Ryan Janson", 7, 1);
		
		provider.playerInsert("Pat Magnuson", 22, 2);
		provider.playerInsert("Brian Sames", 44, 2);
		provider.playerInsert("Mike Byrne", 14, 2);
		provider.playerInsert("Mike Wolfe", 1, 2);
		provider.playerInsert("Joe Neuenfeldt", 7, 2);
		


		//insert ActionTypes
	
		provider.actionTypeInsert(1, "Def Rebound");
		provider.actionTypeInsert(2, "Off Rebound");
		provider.actionTypeInsert(3, "2pt FG Made");
		provider.actionTypeInsert(4, "3pt FG Made");
		provider.actionTypeInsert(5, "Free Throw Made");
		provider.actionTypeInsert(6, "Free Throw Missed");
		provider.actionTypeInsert(7, "Steal");
		provider.actionTypeInsert(8, "Turnover");
		provider.actionTypeInsert(9, "Assist");
		provider.actionTypeInsert(10, "Personal Foul");
		provider.actionTypeInsert(11, "Technical Foul");
		provider.actionTypeInsert(12, "Substitution");
		provider.actionTypeInsert(13, "Full Timeout");
		provider.actionTypeInsert(14, "Partial Timeout");
		provider.actionTypeInsert(15, "2pt FG Missed");
		provider.actionTypeInsert(16, "3pt FG Missed");
		
		
		
		provider.gameInsert(1, 2, 20, 2);
		provider.close();
		
	}
 	public void recordPoints(View button){
    	
    	if (button.getId() == R.id.ltwopoint){
    		ArcButton btn = (ArcButton)button;
        	
        	if (btn.getIsTwoPointer()){
        		Log.w("SCORE", "ITS A TWO POINTER");
        		int i = Integer.parseInt(homeScore.getText().toString()) + 2;
        		homeScore.setText(Integer.toString(i));
        		
        	}else{
        		int i = Integer.parseInt(homeScore.getText().toString()) + 3;
        		homeScore.setText(Integer.toString(i));
        	}
    	} else {
    		int i = Integer.parseInt(homeScore.getText().toString()) + 3;
    		homeScore.setText(Integer.toString(i));
    	}
    	
    	
    }
    
	public void playerClick(View button) {
		// TODO Auto-generated method stub
		Button btn = (Button)button;
		
		Log.w("Player Click", btn.getText().toString());
		
	}
	
	public void actionClick(View button){
		// TODO Auto-generated method stub
		
		storage = new StorageProvider(getApplicationContext());
		
		Button btn = (Button)button;
		
		Log.w("Action Click", btn.getText().toString());
		sAction = Integer.parseInt(btn.getTag().toString());
		if (sPlayer != null){
			Toast.makeText(getApplicationContext(), sPlayer.getName() + " : " + sAction, Toast.LENGTH_SHORT).show();
			//TODO:FIX INSERT
			setAction(sAction, HOMETEAM, sPlayer);
			
			
			sAction = 0;
			sPlayer = null;
		} else{
			Toast.makeText(getApplicationContext(), "Please Click on a Player First", Toast.LENGTH_SHORT).show();
		}
		
	
	   
	}
	
	
	public void setAction(int action, int team, Player player){
		storage.actionInsert(sAction, player.getName(), player.getID(), "10:50", 1);
		actionFrag.refresh();
	}
	
	
	

}