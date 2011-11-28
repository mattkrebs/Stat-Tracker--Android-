package com.fourtyfourlights.scoretracker;

import org.json.JSONObject;

import com.fourtyfourlights.util.RequestMethod;
import com.fourtyfourlights.util.RestClient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Login extends Activity{
	private String TAG = "Stat Trackr Login";
	
	private SharedPreferences sPrefs;
	public TextView txtUsername;
	public TextView txtPassword;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        sPrefs = this.getSharedPreferences("authenticate", MODE_PRIVATE);
        
        
        //Use async
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Button login = (Button)this.findViewById(R.id.btnLogin);
        txtUsername = (TextView)this.findViewById(R.id.username);
        txtPassword = (TextView)this.findViewById(R.id.password);
        
        login.setOnClickListener(new OnClickListener(){

        	
        	
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if (!txtUsername.getText().equals("") && !txtPassword.getText().equals("")){

					SharedPreferences.Editor e = sPrefs.edit();
					e.putString("username", txtUsername.getText().toString());
					e.putString("password", txtPassword.getText().toString());

					e.commit();
					if (Logmein().equals("success")){
						Log.w(TAG, "Successful Login");	
						//TODO:Send to Dashboard but for now just go to teamsactivity
						SendToActivity(TeamDashboardActivity.class);
					}
				}else
				{
					//display error message
				}
					
			
			}
        	
        });
        
      
        
	}
	
	public void SendToActivity(Class cls) {
		Intent i = new Intent(getApplicationContext(), cls);
		startActivity(i);
	}

	
	public String Logmein(){
			
		  RestClient client = new RestClient(getResources().getString(R.string.api_base_uri) + "authenticate");
	       // client.addBasicAuthentication("joecool", "password");
		  
	        client.setJSONString("{\"username\": \""+ sPrefs.getString("username", "") +"\", \"password\": \""+ sPrefs.getString("password", "") +"\", \"apikey\": \"" + getResources().getString(R.string.api_key) + "\"}");
	        try {
	            client.execute(RequestMethod.POST);
	            if (client.getResponseCode() != 200) {
	                //return server error
	                return client.getErrorMessage();
	            }
	            //return valid data
	            
	            JSONObject jObj = new JSONObject(client.getResponse());
	            if (jObj.getString("token").length() > 32){
	            	SharedPreferences.Editor e = sPrefs.edit();
					e.putString("token", jObj.getString("token"));
					e.commit();
	            }
	            	
	            return "success";
	        } catch(Exception e) {
	            return e.toString();
	        }
	}
}
