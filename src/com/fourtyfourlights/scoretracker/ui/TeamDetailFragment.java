package com.fourtyfourlights.scoretracker.ui;

import com.fourtyfourlights.scoretracker.R;
import com.fourtyfourlights.scoretracker.domain.Team;
import com.fourtyfourlights.scoretracker.util.ImageManager;
import com.fourtyfourlights.scoretracker.util.StorageProvider;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TeamDetailFragment extends Fragment {
	private View mContentView;
	private ImageManager imageManager;
	private Team mTeam;
	private String TAG = "TeamDetailFragment";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (container == null)
			return null;
		
		imageManager = new ImageManager(getActivity().getApplicationContext());
		mContentView = inflater.inflate(R.layout.team_detail_fragment, null);
		
		ImageView teamPhoto = (ImageView)mContentView.findViewById(R.id.imgTeamPhoto);
		TextView teamName = (TextView)mContentView.findViewById(R.id.lblTeamName);
		
		StorageProvider db = new StorageProvider(getActivity().getApplicationContext());
		Team team = db.getTeamById(getTeamId());
		teamName.setText(team.getTeamName());
		teamPhoto.setTag(team.getPhotoUrl());
		imageManager.displayImage(team.getPhotoUrl(), getActivity(), teamPhoto);
		
		
		return mContentView;
		 
	}
	 public int getShownIndex() {
		
	        return getArguments().getInt("index", 0);
	    }
	 
	 public int getTeamId(){
		 return getArguments().getInt("teamid", 0);
	 }

	public static TeamDetailFragment newInstance(Integer teamId, Integer index){
		TeamDetailFragment f = new TeamDetailFragment();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("teamid", teamId);
        args.putInt("index", index);
        f.setArguments(args);
        
        return f;

	}
	public void populateFragment(){
		
		
		
	}
}
