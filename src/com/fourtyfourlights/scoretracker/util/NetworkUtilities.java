package com.fourtyfourlights.scoretracker.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fourtyfourlights.scoretracker.R;
import com.fourtyfourlights.scoretracker.domain.Player;
import com.fourtyfourlights.scoretracker.domain.Team;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class NetworkUtilities {

	 /** The tag used to log to adb console. */
    private static final String TAG = "NetworkUtilities";
    
   
    
    public static List<Team>SyncTeams(List<Team> dirtyTeams, String uri, String token){
    	 Gson gson = new Gson();
    	gson.toJson(dirtyTeams);

    	
    	  RestClient client = new RestClient(uri + "syncteams");
	       // client.addBasicAuthentication("joecool", "password");
		  
	        client.setJSONString("{\"token\": \""+ token +"\", \"teams\": \""+ gson.toJson(dirtyTeams) + "\"}");
	        try {
	            client.execute(RequestMethod.POST);
	            if (client.getResponseCode() != 200) {
	                //return server error
	                return null;
	            }
	            //return valid data
	            Type listOfTeamObject = new TypeToken<List<Team>>(){}.getType();
	            Log.w(TAG, client.getResponse());
	            return gson.fromJson(client.getResponse(),listOfTeamObject);
	            	
	           
	        } catch(Exception e) {
	        	Log.w(TAG, "Sync Team Exception: " + e.getMessage());
	            return null;
	        }
	        
    }
    
    
    public void seedData(Context context){
		List<Player> p = new ArrayList<Player>();
		
		StorageProvider provider = new StorageProvider(context);
		
		
		provider.teamInsert(new Team("Abe Froman", "Test Data"));
		provider.teamInsert(new Team("Saints +", "Test Data"));
		
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
		provider.actionTypeInsert(17, "Blocked Shot");
		
		
		
		provider.gameInsert(1, 2, 20, 2);
		provider.close();
		
	}


}
