package com.gotja;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
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

	Handler goMainMenuHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			getInMainActivity();
			super.handleMessage(msg);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//隱藏TitleBar NotificationBar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

		//檢查網路連線
		if(!isNetworkAvailable()){
			final AlertDialog alertDialog = getAlertDialog("No Available Network",
					"Please check your network connection...");
			alertDialog.show();
		}
	}

	private void getInMainActivity(){
		//隱藏登入按鈕
		authButton.setVisibility(View.GONE);
		//設定倒數轉換Activity，數0.5秒
		new CountDownTimer(500, 500) {
			@Override
			public void onTick(long millisUntilFinished) {
				//mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
			}
			@Override
			public void onFinish() {
				Intent intentTabs = new Intent(Welcome.this, MainMenu.class);
				startActivity(intentTabs);
				finish();
				return;
			}
		}.start();
	}

	private AlertDialog getAlertDialog(String title,String message){
		//產生一個Builder物件
		Builder builder = new AlertDialog.Builder(Welcome.this);
		//設定Dialog的標題
		builder.setTitle(title);
		//設定Dialog的內容
		builder.setMessage(message);
		//設定Positive按鈕資料
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//按下按鈕時顯示快顯
				finish();
			}
		});
		//利用Builder物件建立AlertDialog
		return builder.create();
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager 
		= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	private void registerAccount(final Session session) {
		// Make an API call to get user data and define a 
		// new callback to handle the response.
		Request request = Request.newMeRequest(session, 
				new Request.GraphUserCallback() {
			@Override
			public void onCompleted(final GraphUser user, Response response) {
				// If the response is successful
				if (session == Session.getActiveSession()) {
					if (user != null) {
						//userID寫入全域變數
						GlobalVariable globalVariable = (GlobalVariable)getBaseContext().getApplicationContext();
						globalVariable.userID = user.getId();
						//寫入login.php
						Thread thread = new Thread(){
							@Override
							public void run(){
								try {
									// Create a new HttpClient and Post Header
									final HttpClient httpclient = new DefaultHttpClient();
									final HttpPost httppost = new HttpPost("http://120.126.16.38/login.php");
									// Add your data
									List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
									nameValuePairs.add(new BasicNameValuePair("id", user.getId()));
									nameValuePairs.add(new BasicNameValuePair("name", user.getName()));
									httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

									// Execute HTTP Post Request
									HttpResponse response = httpclient.execute(httppost);
									if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
										// 取得返回的字串
										String strResult = EntityUtils.toString(response
												.getEntity());
										Log.v("response", strResult);
										Log.v("response", "Success in Register!!");
									} else {
										Log.v("response", String.valueOf(response.getStatusLine().getStatusCode()));

									}
								} catch (ClientProtocolException e) {
									// TODO Auto-generated catch block
								} catch (IOException e) {
									// TODO Auto-generated catch block
								} catch (Exception e) {
									Log.e("http request", e.toString());
								}
								Message msg = new Message();
								goMainMenuHandler.sendMessage(msg);
							}
						};
						thread.start();
					}
				}
				if (response.getError() != null) {
					// Handle errors, will do so later.
				}
			}
		});
		request.executeAsync();
	}

	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (state.isOpened() && isNetworkAvailable()) {
			//Log.i(TAG, "Logged in...");
			authButton.setVisibility(View.INVISIBLE);
			registerAccount(session);
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