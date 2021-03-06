package com.example.addactivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class InviteContacts extends Activity{
	private ListView emailList;

	private String uacno;
	private String uname;
	private String activityName;
	private String time;
	private String des;

	Handler handler = new Handler()
	{
		@Override  
		public void handleMessage(Message msg) 
		{ 
			Toast.makeText(InviteContacts.this,  msg.getData().getString("MESSAGE"), Toast.LENGTH_SHORT).show();  
		}
	};


	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);

		setContentView(R.layout.invitecontacts);


		Bundle bundle = this.getIntent().getExtras();

		uacno = bundle.getString("uacno");
		uname = bundle.getString("uname");
		activityName = bundle.getString("name");
		time = bundle.getString("time");
		des = bundle.getString("des");

		final ArrayList<EmailAddressWrapper> email_data = getNameEmailDetails();
		final EmailAddressAdapter adapter = new EmailAddressAdapter(this, 
				R.layout.email_listview, email_data);
		emailList = (ListView)findViewById(R.id.emailList);
		emailList.setAdapter(adapter);

		Button sendRequest = (Button) findViewById(R.id.sendInvite);
		sendRequest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String result = "";
				String nameResult = "";
				for(int i = 0; i < adapter.itemChecked.size(); i++){
					if(adapter.itemChecked.get(i)){
						Log.v("Selected", email_data.get(i).email);
						//result = result + "'" + email_data.get(i).email + "',";
						result = result + email_data.get(i).email + ',';
						nameResult = nameResult + email_data.get(i).name + ',';
					}
				}
				Toast.makeText(getApplicationContext(), "Please Wait", Toast.LENGTH_SHORT).show();
				sendServerRequest(result, nameResult);
				finish();
			}
		});
	}

	private void sendServerRequest(final String result, final String nameResult){
		Thread thread = new Thread(){
			@Override
			public void run(){
				Looper.prepare();  
				try {
					// Create a new HttpClient and Post Header
					final HttpClient httpclient = new DefaultHttpClient();
					final HttpPost httppost = new HttpPost("https://www.gotja.idv.tw/cgi-bin/sendmail.rb");
					// Add your data
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
					nameValuePairs.add(new BasicNameValuePair("usermail", result));
					nameValuePairs.add(new BasicNameValuePair("uacno", uacno));
					nameValuePairs.add(new BasicNameValuePair("uname", uname));
					nameValuePairs.add(new BasicNameValuePair("activityName", activityName));
					nameValuePairs.add(new BasicNameValuePair("time", time));
					nameValuePairs.add(new BasicNameValuePair("des", des));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

					// Execute HTTP Post Request
					HttpResponse response = httpclient.execute(httppost);
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						String strResult = EntityUtils.toString(response
								.getEntity());
						Log.v("response", strResult);
						Message status = handler.obtainMessage();
						Bundle data = new Bundle();
						data.putString("MESSAGE", strResult); 
						status.setData(data);     
						handler.sendMessage(status);    
						//Toast.makeText(getApplicationContext(), strResult, Toast.LENGTH_SHORT).show();
					} else {
						String strResult = EntityUtils.toString(response
								.getEntity());
						Log.v("response", String.valueOf(response.getStatusLine().getStatusCode()));
						//Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
						Message status = handler.obtainMessage();
						Bundle data = new Bundle();
						data.putString("MESSAGE", "Please Retry"); 
						Log.v("response", strResult);
						status.setData(data);     
						handler.sendMessage(status); 
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
		
		Thread thread2 = new Thread(){
			@Override
			public void run(){
				try {
					// Create a new HttpClient and Post Header
					final HttpClient httpclient = new DefaultHttpClient();
					final HttpPost httppost = new HttpPost("http://120.126.16.38/addemail.php");
					// Add your data
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
					nameValuePairs.add(new BasicNameValuePair("uacno", uacno));
					nameValuePairs.add(new BasicNameValuePair("friendid", result));
					nameValuePairs.add(new BasicNameValuePair("friendname", nameResult));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

					// Execute HTTP Post Request
					HttpResponse response = httpclient.execute(httppost);
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						String strResult = EntityUtils.toString(response
								.getEntity());
						Log.v("response2", strResult);
					} else {
						Log.v("response2", String.valueOf(response.getStatusLine().getStatusCode()));
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
		thread2.start();
	}
	public ArrayList<EmailAddressWrapper> getNameEmailDetails() {
		ArrayList<EmailAddressWrapper> emlRecs = new ArrayList<EmailAddressWrapper>();
		HashSet<String> emlRecsHS = new HashSet<String>();
		Context context = InviteContacts.this;
		ContentResolver cr = context.getContentResolver();
		String[] PROJECTION = new String[] { 
				BaseColumns._ID, 
				ContactsContract.Contacts.DISPLAY_NAME,
				ContactsContract.Contacts.PHOTO_ID,
				ContactsContract.CommonDataKinds.Email.DATA, 
				ContactsContract.CommonDataKinds.Photo.CONTACT_ID };
		String order = "CASE WHEN " 
				+ ContactsContract.Contacts.DISPLAY_NAME 
				+ " NOT LIKE '%@%' THEN 1 ELSE 2 END, " 
				+ ContactsContract.Contacts.DISPLAY_NAME 
				+ ", " 
				+ ContactsContract.CommonDataKinds.Email.DATA
				+ " COLLATE NOCASE";
		String filter = ContactsContract.CommonDataKinds.Email.DATA + " NOT LIKE ''";
		//table, column, condition
		Cursor cur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION, filter, null, order);
		//cur.moveToFirst() will return false if the cursor is empty.
		if (cur.moveToFirst()) {
			do {
				// names comes in hand sometimes
				String name = cur.getString(1);
				String emlAddr = cur.getString(3);

				// keep unique only
				if (emlRecsHS.add(emlAddr.toLowerCase())) {
					emlRecs.add(new EmailAddressWrapper(name, emlAddr));
				}
			} while (cur.moveToNext());
		}
		cur.close();
		return emlRecs;
	}
}
