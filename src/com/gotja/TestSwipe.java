package com.gotja;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class TestSwipe extends Activity implements OnGestureListener{

	private GestureDetector gDetector;

	private View topLayout;
	private View spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_swipe);

		gDetector = new GestureDetector(this);

		topLayout = findViewById(R.id.top_layout);
		spinner = findViewById(R.id.spinner_test);

	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		topLayout.setVisibility(View.GONE);
		Log.v("scroll", "down");
		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		topLayout.setVisibility(View.VISIBLE);
		Log.v("scroll", "single tap");
		return false;
	}

	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev){
		super.dispatchTouchEvent(ev);    
		return gDetector.onTouchEvent(ev); 
	}

	@Override
	public boolean onTouchEvent(MotionEvent me) {
		return gDetector.onTouchEvent(me);
	}
}