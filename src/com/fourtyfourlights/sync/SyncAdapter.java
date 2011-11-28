package com.fourtyfourlights.sync;

import java.util.List;

import com.fourtyfourlights.domain.Team;
import com.fourtyfourlights.util.NetworkUtilities;
import com.fourtyfourlights.util.StorageProvider;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public class SyncAdapter {
	 private static final String TAG = "SyncAdapter";
	    
	   
	    private final Context mContext;

	public SyncAdapter(Context context){
		mContext = context;
	}

	public void performSync(Bundle extras) {
		Log.v(TAG, "Starting Sync");
		StorageProvider db = new StorageProvider(mContext);
		String uri = extras.getString("uri");
		String token = extras.getString("token");
		
		List<Team> dirtyTeams;
        List<Team> updatedTeams;
          
          // Find the local 'dirty' teams that we need to tell the server about...
        dirtyTeams = db.getTeams();

          // Send the dirty teams to the server, and retrieve the server-side changes
        updatedTeams = NetworkUtilities.SyncTeams(dirtyTeams,uri, token);
          
        db.updateTeams(updatedTeams);

	}
	
	
	

}
