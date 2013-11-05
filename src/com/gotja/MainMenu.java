package com.gotja;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.google.android.gcm.GCMRegistrar;

public class MainMenu extends FragmentActivity{
	Fragment f = null;
	FragmentManager fm;
	
	private String userid;

	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actnavbar);

		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);

		GlobalVariable globalVariable = (GlobalVariable)getApplicationContext();
		userid = globalVariable.userID;
		
		fm = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fm.beginTransaction();

		//add a fragment
		SelectionFragment f = new SelectionFragment();
		//fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		fragmentTransaction.replace(R.id.tabcontent, f);
		fragmentTransaction.commit();

		RadioButton radioButton;
		radioButton = (RadioButton) findViewById(R.id.btnHome);
		radioButton.setOnCheckedChangeListener(btnNavBarOnCheckedChangeListener);
		radioButton = (RadioButton) findViewById(R.id.btnActivity);
		radioButton.setOnCheckedChangeListener(btnNavBarOnCheckedChangeListener);
		radioButton = (RadioButton) findViewById(R.id.btnAdvice);
		radioButton.setOnCheckedChangeListener(btnNavBarOnCheckedChangeListener);
		radioButton = (RadioButton) findViewById(R.id.btnSetting);
		radioButton.setOnCheckedChangeListener(btnNavBarOnCheckedChangeListener);

		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		Log.i("Register", "Registering device");
		// Retrive the sender ID from GCMIntentService.java
		// Sender ID will be registered into GCMRegistrar
		GCMRegistrar.register(MainMenu.this,
				GCMIntentService.SENDER_ID);
	}

	private CompoundButton.OnCheckedChangeListener btnNavBarOnCheckedChangeListener 
	= new CompoundButton.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked) {
				int fragmentId = buttonView.getId();
				if(fragmentId == R.id.btnHome){
					f = new SelectionFragment();
				}
				else if(fragmentId == R.id.btnActivity){
					Bundle bundle=new Bundle();
					bundle.putString("userid", userid);
					f = new com.example.addactivity.Myactivity();
					  //set Fragmentclass Arguments
					f.setArguments(bundle);
				}
				else if(fragmentId == R.id.btnAdvice){
					Bundle bundle=new Bundle();
					bundle.putString("userid", userid);
					f = new Fragment4();
					  //set Fragmentclass Arguments
					f.setArguments(bundle);
				}
				else if(fragmentId == R.id.btnSetting){
					f = new SplashFragment();
				}
				FragmentTransaction transaction = fm.beginTransaction();
				transaction.replace(R.id.tabcontent, f);
				//transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				transaction.commit();
			}
		}
	};

	@SuppressWarnings("unused")
	private boolean isResumed = false;

	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {
			//Log.i(TAG, "Logged in...");
		} else if (state.isClosed()) {
			//Log.i(TAG, "Logged out...");
			Intent intentTabs = new Intent(MainMenu.this, Welcome.class);
			startActivity(intentTabs);
			finish();
		}
	}


	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
		isResumed = true;
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
		isResumed = false;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
}