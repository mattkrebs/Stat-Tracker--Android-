package com.fourtyfourlights.scoretracker;

import java.util.List;
import java.util.Map.Entry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ItemListArrayAdapter extends ArrayAdapter<Entry<String, String>> {
	
	private List<Entry<String,String>> items;
	
	
	public ItemListArrayAdapter(Context context, int textViewResourceId,
			List<Entry<String, String>> objects) {
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
            Entry<String,String> o = items.get(position);
            if (o != null) {
                    TextView tt = (TextView) v.findViewById(R.id.txtItemTitle);
                    TextView bt = (TextView) v.findViewById(R.id.txtItemDescription);
                    if (tt != null) {
                          tt.setText("Name: "+o.getKey());                            }
                    if(bt != null){
                          bt.setText("Record: "+ o.getValue());
                    }
            }
            return v;
    }

}
