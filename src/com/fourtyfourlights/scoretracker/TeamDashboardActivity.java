package com.fourtyfourlights.scoretracker;


import com.fourtyfourlights.sync.SyncService;
import com.fourtyfourlights.sync.DetachableResultReceiver;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

public class TeamDashboardActivity extends Activity implements DetachableResultReceiver.Receiver {

	/** State held between configuration changes. */
    private State mState;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teamdashboard);
	
        
       // final Intent
        
        
        
        ActionBar bar = getActionBar();
        
        
        ItemListFragment itemFrag = (ItemListFragment) getFragmentManager()
                .findFragmentById(R.id.frag_list);
    
	}
	
	public void onRefreshClick(View v){
		   // trigger off background sync
        final Intent intent = new Intent(Intent.ACTION_SYNC, null, this, SyncService.class);
        intent.putExtra(SyncService.EXTRA_STATUS_RECEIVER, mState.mReceiver);
        startService(intent);

	}
	
    private void updateRefreshStatus() {
        //findViewById(R.id.btn_title_refresh).setVisibility(
       //         mState.mSyncing ? View.GONE : View.VISIBLE);
      //  findViewById(R.id.title_refresh_progress).setVisibility(
       //         mState.mSyncing ? View.VISIBLE : View.GONE);
    }


	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		 switch (resultCode) {
         case SyncService.STATUS_RUNNING: {
             mState.mSyncing = true;
             updateRefreshStatus();
             break;
         }
         case SyncService.STATUS_FINISHED: {
             mState.mSyncing = false;
             updateRefreshStatus();
            // reloadNowPlaying(true);
             break;
         }
         case SyncService.STATUS_ERROR: {
             // Error happened down in SyncService, show as toast.
             mState.mSyncing = false;
             updateRefreshStatus();
             final String errorText = getString(R.string.toast_sync_error) + resultData.getString(Intent.EXTRA_TEXT);
             Toast.makeText(TeamDashboardActivity.this, errorText, Toast.LENGTH_LONG).show();
             break;
         }
     }
		
	}
	
	 private static class State {
	        public DetachableResultReceiver mReceiver;
	        public Uri mNowPlayingUri = null;
	        public boolean mNowPlayingGotoWifi = false;
	        public boolean mSyncing = false;
	        public boolean mNoResults = false;

	        private State() {
	            mReceiver = new DetachableResultReceiver(new Handler());
	        }
	    }
	
	
}
