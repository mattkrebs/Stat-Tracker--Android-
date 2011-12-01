package com.fourtyfourlights.scoretracker.ui;


import java.util.ArrayList;
import java.util.List;

import com.fourtyfourlights.scoretracker.R;
import com.fourtyfourlights.scoretracker.domain.Team;
import com.fourtyfourlights.scoretracker.sync.DetachableResultReceiver;
import com.fourtyfourlights.scoretracker.sync.SyncService;
import com.fourtyfourlights.scoretracker.ui.ItemListFragment.ListType;
import com.fourtyfourlights.scoretracker.util.StorageProvider;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends Activity implements DetachableResultReceiver.Receiver, ActionBar.TabListener {

	/** State held between configuration changes. */
    private State mState;
    private DetachableResultReceiver mReceiver;
	private SharedPreferences sPrefs;
	
	 private View mActionBarView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teamdashboard);
        
        mState = (State) getLastNonConfigurationInstance();
        final boolean previousState = mState != null;

        sPrefs = this.getSharedPreferences("authenticate", MODE_PRIVATE);
      
        
        
        
        if (previousState) {
            // Start listening for SyncService updates again
            mState.mReceiver.setReceiver(this);
            //updateRefreshStatus();
            refreshTeams();

        } else {
            mState = new State();
            mState.mReceiver.setReceiver(this);
           // onRefreshClick(null);
        }
      //  onRefreshClick(null);
       // final Intent
        
        mReceiver = new DetachableResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        
        ActionBar bar = getActionBar();
        mActionBarView = getLayoutInflater().inflate(
                R.layout.action_bar_custom, null);

        bar.setCustomView(mActionBarView);
        bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_USE_LOGO);
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayShowHomeEnabled(true);
        
        bar.addTab(bar.newTab().setText("Teams").setTabListener(this));
        bar.addTab(bar.newTab().setText("Players").setTabListener(this));
        bar.addTab(bar.newTab().setText("Leagues").setTabListener(this));
     
        bar.selectTab(bar.getTabAt(0));
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
     /* Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menusearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setSubmitButtonEnabled(true);
       */ 
     
        return true;
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.btnSync:
        	final Intent intent = new Intent(Intent.ACTION_SYNC, null, this, SyncService.class);
	        intent.putExtra(SyncService.EXTRA_STATUS_RECEIVER, mState.mReceiver);
	        startService(intent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	
	
	public void refreshTeams(){
		   ItemListFragment itemFrag = (ItemListFragment) getFragmentManager()
	                .findFragmentById(R.id.frag_list);
		   StorageProvider db = new StorageProvider(this);
		   List<Team> teams = db.getTeams();
		   db.close();
		   List<ListItemObject> lTeams = new ArrayList<ListItemObject>();
		   for(Team t : teams){
			   lTeams.add(new ListItemObject(t.getTeamId(), t.getTeamName(), t.getPhotoUrl()));
		   }
		   itemFrag.populateTitles(lTeams, ListType.Team);
		  
	}
	
	
    private void updateRefreshStatus() {
        //findViewById(R.id.btn_title_refresh).setVisibility(
       //         mState.mSyncing ? View.GONE : View.VISIBLE);
      //  findViewById(R.id.title_refresh_progress).setVisibility(
       //         mState.mSyncing ? View.VISIBLE : View.GONE);
    	
    	refreshTeams();
    }


	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		 switch (resultCode) {
         case SyncService.STATUS_RUNNING: {
             mState.mSyncing = true;
            // updateRefreshStatus();
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
             Toast.makeText(MainActivity.this, errorText, Toast.LENGTH_LONG).show();
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

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		   ItemListFragment itemFrag = (ItemListFragment) getFragmentManager()
	                .findFragmentById(R.id.frag_list);
		   StorageProvider db = new StorageProvider(this);
		   List<ListItemObject> lTeams = new ArrayList<ListItemObject>();
		   
		   switch(tab.getPosition()){
		   case 0:
			   List<Team> teams = db.getTeams();
			   for(Team t : teams){
				   lTeams.add(new ListItemObject(t.getTeamId(), t.getTeamName(), t.getPhotoUrl()));
			   }
			   break;
		   case 1:
			   break;
		   case 2:
			   break;
		   }
		   
		   db.close();
		   
		   
		   itemFrag.populateTitles(lTeams, ListType.Team);
		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
}
