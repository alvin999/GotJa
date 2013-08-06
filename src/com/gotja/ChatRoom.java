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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class ChatRoom extends Activity {

	private TextView ttt;
	private Button sendButton;
	private EditText comments;
	String message;
	String nowUserName;
	String userid;
	String activityid = "100000217523324_1";//-->��Jactivityid
	String view;
	private ScrollView mChatBox;

	private class GotjaBroadcastReceiver extends BroadcastReceiver
	{
		public void onReceive(Context context, Intent intent)
		{
			loadChatView();
			Log.v("BroadcastReceive", "Receive!");
		}
	}
	GotjaBroadcastReceiver broadcastReceiver = new GotjaBroadcastReceiver();

	//delay must be expressed in milliseconds. For 3 seconds, delay = 3000
	private void scrollToBottom(int delay) {
		// If we don't call fullScroll inside a Runnable, it doesn't scroll to
		// the bottom but to the (bottom - 1)
		mChatBox.postDelayed(new Runnable() {
			public void run() {
				mChatBox.fullScroll(ScrollView.FOCUS_DOWN);
			}
		}, delay);
	}

	private void loadChatView(){

		//----��ܯd��----
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String strResult = "";
				view = "";
				try {
					// Create a new HttpClient and Post Header
					final HttpClient httpclient = new DefaultHttpClient();
					final HttpPost httppost = new HttpPost("http://120.126.16.38/androidChatroom.php");
					// Add your data
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
					nameValuePairs.add(new BasicNameValuePair("activityid",activityid));//-->��J����id
					nameValuePairs.add(new BasicNameValuePair("userid",userid));//-->��Juserid
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

					// Execute HTTP Post Request
					HttpResponse response = httpclient.execute(httppost);
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						// ���o��^���r��
						strResult = EntityUtils.toString(response.getEntity());
					} else {
						Log.v("response", String.valueOf(response.getStatusLine().getStatusCode()));
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
				} catch (IOException e) {
					// TODO Auto-generated catch block
				} catch (Exception e) {
					Log.e("Exception", e.toString());
				}
				return strResult;
			}

			@Override
			protected void onPostExecute(String result) {
				Log.v("result",result);
				if(result.indexOf("1") == 0){

					ttt.append("�ثe�L�d��");
				}
				else{
					String[] userid = new String[256];
					String[] comments = new String[256];

					//�N��쪺json�ন string
					JSONArray jArray = null;
					try {
						jArray = new JSONArray(result);
						for (int i = 0; i < jArray.length() - 1; i++) { 
							JSONObject jsonObject = null;
							jsonObject = jArray.getJSONObject(i);
							
							userid[i]=jsonObject.getString("uname");
							Log.v("get ID", userid[i]);
							comments[i]=jsonObject.getString("msg");

							view += userid[i] + ": " + comments[i] + "\n";
						}
						ttt.setText(view);
						scrollToBottom(1);
					}catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//+++++����{�buser��name+++
				JSONArray jsonArray = null;
				try {
					jsonArray = new JSONArray(result);

					JSONObject jsonObject = null;
					jsonObject = jsonArray.getJSONObject(jsonArray.length()-1);

					nowUserName=jsonObject.getString("nowUserName");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//++++++++
			}
		}.execute();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatroom);

		GlobalVariable globalVariable = (GlobalVariable)getApplicationContext();
		userid = globalVariable.userID;
		mChatBox = (ScrollView)findViewById(R.id.scrollView);
		

		ttt = (TextView)findViewById(R.id.chatContent);
		ttt.setMovementMethod(new ScrollingMovementMethod());

		loadChatView();

		//----�d��-----
		sendButton = (Button)findViewById(R.id.sendCommentButton);       
		sendButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				comments = (EditText)findViewById(R.id.comments);

				mChatBox = (ScrollView)findViewById(R.id.scrollView);
				scrollToBottom(1000);

				// TODO Auto-generated method stub		 
				message  = comments.getText().toString();
				if(message.equals("")){}
				else{
					ttt.append(nowUserName+": "+message+'\n');
				}

				//-----http post-------

				new AsyncTask<Void, Void, String>() {

					@Override
					protected String doInBackground(Void... params) {
						String strResult = "";

						try {

							// Create a new HttpClient and Post Header
							final HttpClient httpclient = new DefaultHttpClient();
							final HttpPost httppost = new HttpPost("http://120.126.16.38/androidChatroom.php");
							// Add your data
							List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
							nameValuePairs.add(new BasicNameValuePair("sendButtonClicked","1"));
							nameValuePairs.add(new BasicNameValuePair("message",message));
							nameValuePairs.add(new BasicNameValuePair("activityid",activityid));//-->��Jactivityid
							nameValuePairs.add(new BasicNameValuePair("userid",userid));//-->��Juserid
							httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

							// Execute HTTP Post Request
							HttpResponse response = httpclient.execute(httppost);

							if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
								// ���o��^���r��
								strResult = EntityUtils.toString(response.getEntity());

								Log.v("response", "Success in Register!!");

							} else {
								Log.v("response", String.valueOf(response.getStatusLine().getStatusCode()));
							}
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
						} catch (IOException e) {
							// TODO Auto-generated catch block
						} catch (Exception e) {
							Log.e("Exception", e.toString());
						}
						return strResult;
					}

					@Override
					protected void onPostExecute(String result) {

					}
				}.execute();

				comments.setText("");

				//-------------------------
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();

		String GOTJA_BROATCAST_STRING = "REFRESH_PAGE";
		IntentFilter intentFilter = new IntentFilter(GOTJA_BROATCAST_STRING);
		registerReceiver(broadcastReceiver, intentFilter); //���U��ť
	}

	@Override
	protected void onPause() {
		super.onPause();

		unregisterReceiver(broadcastReceiver); //������ť
	}
}