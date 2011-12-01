package com.fourtyfourlights.scoretracker.ui;

import java.util.List;


import com.fourtyfourlights.scoretracker.R;
import com.fourtyfourlights.scoretracker.domain.Team;
import com.fourtyfourlights.scoretracker.util.StorageProvider;


import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ItemListFragment extends ListFragment {
	boolean mDualPane;
	int mCurCheckPosition = 0;

	private ListType mListType;
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
	
     // Check to see if we have a frame in which to embed the details
        // fragment directly in the containing UI.
        View detailsFrame = getActivity().findViewById(R.id.details);
      

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

      
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
           // showDetails(mCurCheckPosition);
        

	
	
	}
	
	
	 public void populateTitles(List<ListItemObject> items, ListType type) {
	     
	    	  setListAdapter(new ItemListArrayAdapter(getActivity(),
		                R.layout.custom_list_item, items));
	    	  mListType = type;
	     
	    }
	 
	 @Override
	 public void onListItemClick(ListView l, View v, int position, long id) {
		 switch (mListType){
			 case Team:
				 showTeamDetails(position, (Integer)v.getTag());
				 break;
			 case League:
				 break;
			 case Player:
				 break;
		 
		 }
	 }
	 
	 /**
	     * Helper function to show the details of a selected item, either by
	     * displaying a fragment in-place in the current UI, or starting a
	     * whole new activity in which it is displayed.
	     */
	    void showTeamDetails(int index, int id) {
	        mCurCheckPosition = index;

	      
	            getListView().setItemChecked(index, true);

	            // Check what fragment is shown, replace if needed.
	            TeamDetailFragment details = (TeamDetailFragment)
	                    getFragmentManager().findFragmentById(R.id.details);
	           
	            if (details == null || details.getShownIndex() != index) {
	                // Make new fragment to show this selection.
	                details = TeamDetailFragment.newInstance(id, index);

	                // Execute a transaction, replacing any existing
	                // fragment with this one inside the frame.
	                FragmentTransaction ft
	                        = getFragmentManager().beginTransaction();
	                ft.replace(R.id.details, details);
	                ft.setTransition(
	                        FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	                ft.commit();
	            }

	        
	    }

	 
	 public enum ListType{
		 Team,
		 League,
		 Player
	 
	 }
	 
	
}
