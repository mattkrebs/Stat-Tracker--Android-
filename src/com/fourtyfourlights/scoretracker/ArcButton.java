package com.fourtyfourlights.scoretracker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

public class ArcButton extends Button {
	private Context mContext;
	
	private int height;
	private int width;
	private int left;
	private int top;
	
	private boolean isTwoPointer = false;
	/* (non-Javadoc)
	 * @see android.widget.TextView#onDraw(android.graphics.Canvas) 
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		
		
		//Paint p = getPaint();
		//p.setColor(0x88FF8844);
		//canvas.drawArc(null, 270, 180, false, p);
		super.onDraw(canvas);
	}


	public boolean getIsTwoPointer(){
		return isTwoPointer;
	}
	
	public ArcButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		
		mContext = context;
	}

	public ArcButton(Context context, AttributeSet attrs) {
		super(context, attrs); 
		// TODO Auto-generated constructor stub
		mContext = context;
		Log.w("Button Positon", "TOP: " + getTop() + "LEFT: " + getLeft() + "width: " + getWidth() + "HEIGHT: " + getHeight());
		
	}

	public ArcButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mContext = context;
		Log.w("Button Positon", "TOP: " +getTop() + "LEFT: " + getLeft() + "width: " + getWidth() + "HEIGHT: " + getHeight());
		
	}

	
	
	/* (non-Javadoc)
	 * @see android.widget.TextView#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float x = event.getX();
		float y = event.getY();
		// because margin is -200 must reset it to 0 by adding the half the width
		int left = getLeft() + (getWidth() / 2);
		
		float midx = x -(getWidth() / 2);
		float midy = (getHeight() / 2);
		if (event.getAction() == MotionEvent.ACTION_UP){
			if (inCircle(left, getTop(), (int)x, (int)y, (getWidth() / 2))) {				
				isTwoPointer = true;
			}else {
				
				isTwoPointer = false;
			}
		}
		
		
		return super.onTouchEvent(event);
	}
	
	
	public boolean inCircle(int circleX, int circleY, int clickX, int clickY, int radius){
		return java.lang.Math.pow((circleX+radius - clickX),2) + java.lang.Math.pow((circleY+radius -clickY),2) < java.lang.Math.pow(radius,2);
	}
	
}
