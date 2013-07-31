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
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class ChatRoom extends Activity {

	private TextView ttt;
	private Button sendButton;
	private EditText comments;
	String message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatroom);
		
		ttt = (TextView)findViewById(R.id.chatContent);
		ttt.setMovementMethod(new ScrollingMovementMethod());

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
					nameValuePairs.add(new BasicNameValuePair("activityid","100000217523324_1"));
					nameValuePairs.add(new BasicNameValuePair("userid","100001255224261"));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

					// Execute HTTP Post Request
					HttpResponse response = httpclient.execute(httppost);
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						// 取得返回的字串
						strResult = EntityUtils.toString(response
								.getEntity());

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
				String[] userid = new String[256];
				String[] comments = new String[256];
				//將抓到的json轉成 
				JSONArray jArray = null;
				try {
					jArray = new JSONArray(result);
				} catch (JSONException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				for (int i = 0; i < jArray.length(); i++) { 
					JSONObject jsonObject = null;
					try {
						jsonObject = jArray.getJSONObject(i);
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
					try {
						
						userid[i]=jsonObject.getString("uname")+": ";
						comments[i]=jsonObject.getString("msg")+'\n';
						ttt.append(userid[i]);
						ttt.append(comments[i]);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.execute();
		
		
		sendButton = (Button)findViewById(R.id.sendCommentButton);       
	    sendButton.setOnClickListener(new Button.OnClickListener(){
	    	
	    	 @Override
	    	 public void onClick(View v) {
	    		 comments = (EditText)findViewById(R.id.comments);
	    		 
	    		 // TODO Auto-generated method stub		 
	    		 message  = comments.getText().toString();	
	    		 ttt.append("陳新驊: "+message+'\n');
	    		 
	    		 //--------------------------
	    		 
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
	    						nameValuePairs.add(new BasicNameValuePair("activityid","100000217523324_1"));
	    						nameValuePairs.add(new BasicNameValuePair("userid","100001255224261"));
	    						httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

	    						// Execute HTTP Post Request
	    						HttpResponse response = httpclient.execute(httppost);

	    						if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	    							// 取得返回的字串
	    							strResult = EntityUtils.toString(response
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
	    						Log.e("Exception", e.toString());
	    					}
	    					return strResult;
	    				}
	    				
	    				@Override
	    				protected void onPostExecute(String result) {
	    					
	    				}
	    			}.execute();
	    		
	    		 //-------------------------
	                          
	    	 }         

	    });
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}
