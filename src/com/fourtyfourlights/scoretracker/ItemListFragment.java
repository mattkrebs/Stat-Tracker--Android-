package com.fourtyfourlights.scoretracker;

import java.util.List;
import java.util.Map.Entry;


import android.app.ListFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class ItemListFragment extends ListFragment {

	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
	
        ListView lv = getListView();
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setCacheColorHint(Color.TRANSPARENT);
        lv.setOnClickListener(new OnClickListener() {
         
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				
			}
        });
	
	
	}
	
	
	 public void populateTitles(List<Entry<String, String>> items) {
	     
	    	  setListAdapter(new ItemListArrayAdapter(getActivity(),
		                R.layout.custom_list_item, items));
	     
	    }
	
}
