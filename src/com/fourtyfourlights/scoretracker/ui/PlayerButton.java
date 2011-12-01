package com.fourtyfourlights.scoretracker.ui;

import com.fourtyfourlights.scoretracker.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class PlayerButton extends LinearLayout {

	private String name;
	private Integer number;
	private String color;
	
	private Typeface athleticfont;
	private Typeface gothicfont;
	
	private String direction = "left";



	public void render(){
		
		LinearLayout layout = new LinearLayout(getContext());
		
		TextView playerName = new TextView(getContext());
		
		playerName.setText(this.name);
		playerName.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,53, 2));
		playerName.setGravity(Gravity.CENTER_VERTICAL);
		
		playerName.setTextSize(22);
		playerName.setPadding(0, 0, 14, 0);
		playerName.setTypeface(gothicfont);
		
		TextView playerNumber = new TextView(getContext());
		playerNumber.setText(this.number.toString());
		playerNumber.setLayoutParams(new LayoutParams(40,LayoutParams.MATCH_PARENT));
		playerNumber.setGravity(Gravity.CENTER);
		playerNumber.setTextSize(26);
		
			playerName.setPadding(0, 0, 14, 0);
			playerNumber.setPadding(0, 0, 10, 0);
		if (direction.equals("right")){
			playerName.setPadding(14, 0, 0, 0);
			playerNumber.setPadding(10, 0, 0, 0);
		}
		playerNumber.setTypeface(athleticfont);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lp.setMargins(0, 0, 0, 10);
		
		LayoutParams lp2 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	
		
		this.setBackgroundColor(Color.BLUE);

		this.setLayoutParams(lp);
		layout.setLayoutParams(lp2);
		if (direction.equals("right")){
			layout.setBackgroundResource(R.drawable.playerrightbutton);
			playerName.setGravity(Gravity.LEFT);
			layout.addView(playerNumber);
			layout.addView(playerName);
		}else{
			layout.setBackgroundResource(R.drawable.playerleftbutton);
			playerName.setGravity(Gravity.RIGHT);
			layout.addView(playerName);
			layout.addView(playerNumber);
			
		}
		
		this.addView(layout);
	
	}
	
	
	
	public void setDirection(String direction){
		this.direction = direction;
	}
	public void setText(String name, Integer number, String color){
		this.name = name;
		this.number = number;
		this.color = color;
		render();
	}
	public PlayerButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		   athleticfont = Typeface.createFromAsset(context.getAssets(),"fonts/Athletic.TTF");
	}
	public PlayerButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		   athleticfont = Typeface.createFromAsset(context.getAssets(),"fonts/Athletic.TTF");
		   gothicfont = Typeface.createFromAsset(context.getAssets(),"fonts/GOTHIC.TTF");
	}

	public PlayerButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		//this.setBackgroundResource(R.drawable.actionbutton);
		   athleticfont = Typeface.createFromAsset(context.getAssets(),"fonts/Athletic.TTF");
	
	}

	/* (non-Javadoc)
	 * @see android.view.View#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
	}

	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		
	
		
		
		super.onDraw(canvas);
	}

	
}
