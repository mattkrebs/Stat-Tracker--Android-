package com.fourtyfourlights.scoretracker;

import com.fourtyfourlights.scoretracker.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlayersFragment extends Fragment {
	private View mContentView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		mContentView = inflater.inflate(R.layout.player_fragment, null);
		return super.onCreateView(inflater, container, savedInstanceState);
		 
	}

	

}
