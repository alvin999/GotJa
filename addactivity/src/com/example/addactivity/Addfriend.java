package com.example.addactivity;

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
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

public class Addfriend extends Activity {
	String uacno;
	String userID;
	String invitedFriends="";
	String invitedFriendsName="";

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
		// 設定 Layout
		setContentView(R.layout.activity_addfriend);


		Bundle bundle =getIntent().getExtras();
		uacno=bundle.getString("uacno");
		userID = uacno.split("_")[0];
		Log.v("User ID", userID);
		Log.v("Uacno", uacno);
		//宣告facebook物件以及listener
		uiHelper = new UiLifecycleHelper(Addfriend.this, callback);
		uiHelper.onCreate(savedInstanceState);

		sendRequestDialog();        

	}

	private void sendRequestDialog() {
		Bundle params = new Bundle();
		params.putString("message", "Gotja Activity Invitation");
		params.putString("frictionless", "1"); //Turn on frictionless
		params.putString("data", uacno);
		WebDialog requestsDialog = (
				new WebDialog.RequestsDialogBuilder(Addfriend.this,
						Session.getActiveSession(),
						params))
						.setOnCompleteListener(new OnCompleteListener() {

							@Override
							public void onComplete(Bundle values,
									FacebookException error) {
								if (error != null) {
									if (error instanceof FacebookOperationCanceledException) {
										Toast.makeText(Addfriend.this.getApplicationContext(), 
												"Request cancelled", 
												Toast.LENGTH_SHORT).show();
										finish();
									} else {
										Toast.makeText(Addfriend.this.getApplicationContext(), 
												"Network Error", 
												Toast.LENGTH_SHORT).show();
										finish();
									}
								} else {
									final String requestId = values.getString("request");
									if (requestId != null) {
										Toast.makeText(Addfriend.this.getApplicationContext(), 
												"Request sent",  
												Toast.LENGTH_SHORT).show();
										//輸出全部被邀請的id
										for (int i = 0; values.containsKey("to[" + i + "]"); i++) {
											invitedFriends = invitedFriends + values.getString("to[" + i + "]") + " ";
											//invitedFriendsName
										}
										Log.v("invite result", invitedFriends);

										Thread thread = new Thread(){
											@Override
											public void run(){
												try {
													// Create a new HttpClient and Post Header
													final HttpClient httpclient = new DefaultHttpClient();
													final HttpPost httppost = new HttpPost("http://120.126.16.38/addfriend.php");
													// Add your data
													List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
													nameValuePairs.add(new BasicNameValuePair("uacno", uacno));
													nameValuePairs.add(new BasicNameValuePair("friendid", invitedFriends));
													httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

													// Execute HTTP Post Request
													HttpResponse response = httpclient.execute(httppost);
													if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
														// 取得返回的字串
														String strResult = EntityUtils.toString(response
																.getEntity());
														Log.v("response", strResult);
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
											}
										};
										thread.start();
										finish();
									} else {
										Toast.makeText(Addfriend.this.getApplicationContext(), 
												"Request cancelled", 
												Toast.LENGTH_SHORT).show();
										finish();
									}
								}   
							}

						})
						.build();
		requestsDialog.show();
	}


	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {
			//sendRequestButton.setVisibility(View.VISIBLE);
		} else if (state.isClosed()) {
			//sendRequestButton.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
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
