package com.fourtyfourlights.scoretracker.sync;

import java.util.List;

import com.fourtyfourlights.scoretracker.R;
import com.fourtyfourlights.scoretracker.domain.Team;
import com.fourtyfourlights.scoretracker.util.NetworkUtilities;
import com.fourtyfourlights.scoretracker.util.StorageProvider;

import android.app.IntentService;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.format.DateUtils;
import android.util.Log;

public class SyncService extends IntentService {

	  private static final String TAG = "SyncService";

	
    public static final String EXTRA_STATUS_RECEIVER = "com.fourtyfourlights.scoretracker.extra.STATUS_RECEIVER";
    public static final int STATUS_RUNNING = 0x1;
    public static final int STATUS_ERROR = 0x2;
    public static final int STATUS_FINISHED = 0x3;

    private static final int SECOND_IN_MILLIS = (int) DateUtils.SECOND_IN_MILLIS;


	public SyncService() {
		super("SyncService");
		
	}
 

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		
		final ResultReceiver receiver = intent.getParcelableExtra(EXTRA_STATUS_RECEIVER);
		if (receiver != null) receiver.send(STATUS_RUNNING, Bundle.EMPTY);

		
		 final Context context = this;
	        final SharedPreferences prefs = getSharedPreferences("authenticate", Context.MODE_PRIVATE);
		
		
		try{
			Log.w(TAG, "Trying to update teams URI:" + getResources().getString(R.string.api_base_uri));
			StorageProvider db = new StorageProvider(this);
			String uri =getResources().getString(R.string.api_base_uri);
			String token = prefs.getString("token", "");
			
			List<Team> dirtyTeams;
			List<Team> updatedTeams;
		        
			// Find the local 'dirty' teams that we need to tell the server about...
			dirtyTeams = db.getTeams();
	
			
			// Send the dirty teams to the server, and retrieve the server-side changes
			updatedTeams = NetworkUtilities.SyncTeams(dirtyTeams,uri, token);
		          
			db.updateTeams(updatedTeams);
		} catch (Exception e){
			final Bundle bundle = new Bundle();
            bundle.putString(Intent.EXTRA_TEXT, e.toString());
            receiver.send(STATUS_ERROR, bundle);
		}
        
		 // Announce success to any surface listener
        Log.d(TAG, "sync finished");
        if (receiver != null) receiver.send(STATUS_FINISHED, Bundle.EMPTY);
		
	}
}
