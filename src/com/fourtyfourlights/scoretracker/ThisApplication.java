package com.fourtyfourlights.scoretracker;

import android.app.Application;
import android.content.Context;

public class ThisApplication extends Application {

	private static Context context;
	
	public void onCreate(){
		ThisApplication.context = getApplicationContext();
	}
	
	public static Context getAppContext()
	{
		return ThisApplication.context;
	}
	
	

}
