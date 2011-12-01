package com.fourtyfourlights.scoretracker.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.fourtyfourlights.scoretracker.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ItemListArrayAdapter extends ArrayAdapter<ListItemObject> {
	
	private List<ListItemObject> items;
	
	public ItemListArrayAdapter(Context context, int textViewResourceId, List<ListItemObject> objects) {
		super(context, textViewResourceId, objects);
		
		this.items = objects;
		
		
	}	
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.custom_list_item, null);
            }
            ListItemObject o = items.get(position);
            v.setTag(o.getId());
            if (o != null) {
                    TextView tt = (TextView) v.findViewById(R.id.txtItemTitle);
                    TextView bt = (TextView) v.findViewById(R.id.txtItemDescription);
                    if (tt != null) {
                          tt.setText(o.getTitle());                            }
                   
            }
            return v;
    }

}
