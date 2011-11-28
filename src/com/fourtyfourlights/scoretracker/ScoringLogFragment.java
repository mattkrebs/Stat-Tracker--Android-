package com.fourtyfourlights.scoretracker;



import java.util.ArrayList;

import com.fourtyfourlights.domain.ScoringAction;
import com.fourtyfourlights.util.StorageProvider;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

public class ScoringLogFragment extends ListFragment{
	private View mContentView;
	private Integer mCurPosition = 0;
	private ArrayList<ScoringAction> actions;
	  @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);

	        //Current position should survive screen rotations.
	        if (savedInstanceState != null) {
	          
	            mCurPosition = savedInstanceState.getInt("listPosition");
	        }
            populateScoringList();
	        ListView lv = getListView();
	        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	        lv.setCacheColorHint(Color.TRANSPARENT);
	        setSelection(mCurPosition);
	        /*lv.setOnItemLongClickListener(new OnItemLongClickListener() {
	           public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
	                final String title = (String) ((TextView) v).getText();

	                // Set up clip data with the category||entry_id format.
	                final String textData = String.format("%d||%d", mCategory, pos);
	                ClipData data = ClipData.newPlainText(title, textData);
	                v.startDrag(data, new MyDragShadowBuilder(v), null, 0);
	                return true;
	            }
	        });*/

	        //selectPosition(mCurPosition);
	  }
	  
	  public void populateScoringList(){

	       actions = new StorageProvider(getActivity().getApplicationContext()).getAllActionsByGameId(1);
	      
		  String[] items = new String[actions.size()];
		        
		        for (int i = 0; i < actions.size(); i++)
		        	// 18:54 off-rebound 44:Krebs 
		        	items[i] = actions.get(i).getGameTime() + " " + actions.get(i).getActionName().toLowerCase() + " " + actions.get(i).getPlayer();
		        setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.action_list_item, items));
		        
		    
	  }
	  
	 
	  	@Override
	    public void onSaveInstanceState (Bundle outState) {
	        super.onSaveInstanceState(outState);
	        outState.putInt("listPosition", mCurPosition);
	       
	        
	    }

		public void refresh() {
			// TODO Auto-generated method stub
			populateScoringList();
		}
	  
}
