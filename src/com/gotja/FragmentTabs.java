package com.gotja;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;

public class FragmentTabs extends FragmentActivity {
	
	private TabHost menuTabHost;
	private TabManager menuTabManager;
	
	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tabs);
        setMenuTab();
        
        uiHelper = new UiLifecycleHelper(this, callback);
	    uiHelper.onCreate(savedInstanceState);
    }
    
    private void setMenuTab(){
    	menuTabHost = (TabHost)findViewById(android.R.id.tabhost);
        menuTabHost.setup();
        menuTabManager = new TabManager(this, menuTabHost, R.id.realtabcontent);
        
        menuTabHost.setCurrentTab(0);//設定一開始就跳到第一個分頁
        
        //TabSpec是傳給menuTabManager的tag，Indicator是顯示的Text
        menuTabManager.addTab(
            menuTabHost.newTabSpec("Home").setIndicator("Home"),
            SelectionFragment.class, null);
            
        menuTabManager.addTab(
            menuTabHost.newTabSpec("MyActivity").setIndicator("MyActivity"),
            MyActivity.class, null);
        
        menuTabManager.addTab(
            menuTabHost.newTabSpec("Login").setIndicator("Login"),
            SplashFragment.class, null);
        
        menuTabManager.addTab(
            menuTabHost.newTabSpec("Test").setIndicator("Test"),
            TestFragment.class, null);
        
        DisplayMetrics dm = new DisplayMetrics();   
        getWindowManager().getDefaultDisplay().getMetrics(dm); //先取得螢幕解析度  
        int screenWidth = dm.widthPixels;   //取得螢幕的寬
           
        TabWidget tabWidget = menuTabHost.getTabWidget();   //取得tab的物件
        int count = tabWidget.getChildCount();   //取得tab的分頁有幾個
        for (int i = 0; i < count; i++) {   
            tabWidget.getChildTabViewAt(i)
                  .setMinimumWidth(screenWidth/(count - 1));//設定每一個分頁最小的寬度   
        }   
    }
    

	private boolean isResumed = false;


	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {
			//Log.i(TAG, "Logged in...");
		} else if (state.isClosed()) {
			//Log.i(TAG, "Logged out...");
			Intent intentTabs = new Intent(FragmentTabs.this, Welcome.class);
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