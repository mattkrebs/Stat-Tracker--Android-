package com.fourtyfourlights.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.SharedPreferences;

import com.fourtyfourlights.domain.Team;
import com.fourtyfourlights.scoretracker.R;
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

	            return gson.fromJson(client.getResponse(),listOfTeamObject);
	            	
	           
	        } catch(Exception e) {
	            return null;
	        }
    }


}
