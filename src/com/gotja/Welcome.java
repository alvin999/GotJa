package com.gotja;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

public class Welcome extends Activity {
	//宣告facebook會用到的物件
	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};
	LoginButton authButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 設定 Layout
		setContentView(R.layout.welcomeactivity);
		// 設定Logo動畫
		ImageView gotJaLogo = (ImageView)findViewById(R.id.gotjalogo);
		Animation gotJaLogoAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
		gotJaLogo.startAnimation(gotJaLogoAnimation); //Set animation to your ImageView
		//宣告facebook物件以及listener
		uiHelper = new UiLifecycleHelper(Welcome.this, callback);
		uiHelper.onCreate(savedInstanceState);
		authButton = (LoginButton) findViewById(R.id.login_button);
	}
	
	private void getInMainActivity(){
		//隱藏登入按鈕
		authButton.setVisibility(View.GONE);
		//設定倒數轉換Activity，數三秒
		new CountDownTimer(3000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				//mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
			}
			@Override
			public void onFinish() {
				Intent intentTabs = new Intent(Welcome.this, FragmentTabs.class);
				startActivity(intentTabs);
				finish();
				return;
			}
		}.start();
	}

	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {
			//Log.i(TAG, "Logged in...");
			getInMainActivity();
		} else if (state.isClosed()) {
			//Log.i(TAG, "Logged out...");
			authButton.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed())) {
	        onSessionStateChange(session, session.getState(), null);
	    }
	    uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
}