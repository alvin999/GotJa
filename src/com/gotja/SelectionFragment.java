package com.gotja;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

public class SelectionFragment extends Fragment implements OnClickListener{
	private ProfilePictureView profilePictureView;
	private TextView userNameView;

	private TextView countdownText;
	private TextView activityText;
	private TextView notificationText;
	private static final int REAUTH_ACTIVITY_CODE = 100;

	private PopupWindow pwindo;
	private ListView notificationListView;

	private String userID;
	private String AnnouncementResult[];

	Handler setNotificationHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){	        
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
					R.layout.notification_listview, R.id.textItem, AnnouncementResult);

			notificationListView.setAdapter(adapter);
			notificationListView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> a, View v, int position, long id) {
					//TODO 設定intent
				}
			});
			super.handleMessage(msg);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.notificationText:
			initiatePopupWindow();
			Log.v("notificationText", "onClick");
			break;
		default :
			pwindo.dismiss();
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.selection, container, false);

		// Find the user's profile picture custom view
		profilePictureView = (ProfilePictureView) view.findViewById(R.id.selection_profile_pic);

		// Find the user's name view
		userNameView = (TextView) view.findViewById(R.id.selection_user_name);

		//View
		activityText = (TextView) view.findViewById(R.id.activityText);
		notificationText = (TextView) view.findViewById(R.id.notificationText);
		notificationText.setOnClickListener(this);
		countdownText = (TextView) view.findViewById(R.id.countdownText);

		// Check for an open session
		Session session = Session.getActiveSession();
		if (session != null && session.isOpened()) {
			// Get the user's data
			makeMeRequest(session);
		}

		//設定View的AsyncTask
		HTTPTask httpTask = new HTTPTask();
		httpTask.execute();

		return view;
	}

	private void getPopupWindowContent() {
		Thread thread = new Thread(){
			@Override
			public void run(){
				try {
					// Create a new HttpClient and Post Header
					final HttpClient httpclient = new DefaultHttpClient();
					final HttpPost httppost = new HttpPost("http://120.126.16.38/announcement.php");
					// Add your data
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
					nameValuePairs.add(new BasicNameValuePair("id", userID));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

					// Execute HTTP Post Request
					HttpResponse response = httpclient.execute(httppost);
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						// 取得返回的字串
						String strResult = EntityUtils.toString(response
								.getEntity(), HTTP.UTF_8);
						AnnouncementResult = strResult.split(",");
						Log.v("Announce Response", strResult);
					} else {
						Log.v("Announce Response", String.valueOf(response.getStatusLine().getStatusCode()));
					}
					Message msg = new Message();
					setNotificationHandler.sendMessage(msg);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
				} catch (IOException e) {
					// TODO Auto-generated catch block
				} catch (Exception e) {
					// TODO Auto-generated catch block
				} 
			}
		};
		thread.start();
	}

	private void initiatePopupWindow() { 
		try { 
			// We need to get the instance of the LayoutInflater 
			LayoutInflater inflater = (LayoutInflater) getActivity() 
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
			View layout = inflater.inflate(R.layout.popup_notification, null); 
			pwindo = new PopupWindow(layout, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, true); 
			//pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
			pwindo.setBackgroundDrawable(new BitmapDrawable());
			pwindo.setOutsideTouchable(true);
			pwindo.setAnimationStyle(R.style.PopupWindowAnimation);
			pwindo.showAsDropDown(notificationText, 50, 0);

			notificationListView = (ListView) layout.findViewById(R.id.notificationListView);
			notificationListView.setEmptyView(layout.findViewById(R.id.empty_list_item));
			getPopupWindowContent();


		} catch (Exception e) { 
			e.printStackTrace(); 
		} 
	}

	private void makeMeRequest(final Session session) {
		// Make an API call to get user data and define a 
		// new callback to handle the response.
		Request request = Request.newMeRequest(session, 
				new Request.GraphUserCallback() {
			@Override
			public void onCompleted(GraphUser user, Response response) {
				// If the response is successful
				if (session == Session.getActiveSession()) {
					if (user != null) {
						userID = String.valueOf(user.getId());
						Log.v("FB_ID",  userID);
						// Set the id for the ProfilePictureView
						// view that in turn displays the profile picture.
						profilePictureView.setProfileId(user.getId());
						// Set the Textview's text to the user's name.
						userNameView.setText(user.getName());
					}
				}
				if (response.getError() != null) {
					// Handle errors, will do so later.
					userNameView.setText(response.getError().toString());
				}
			}
		});
		request.executeAsync();
	}

	private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
		if (session != null && session.isOpened()) {
			// Get the user's data.
			makeMeRequest(session);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REAUTH_ACTIVITY_CODE) {
			uiHelper.onActivityResult(requestCode, resultCode, data);
		}
	}

	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(final Session session, final SessionState state, final Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		uiHelper.onSaveInstanceState(bundle);
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

	private class HTTPTask extends AsyncTask<String, Integer, String> {
		ProgressDialog myDialog;
		@Override
		protected void onPreExecute() {
			// Do stuff
			// For example showing a Dialog to give some feedback to the user.
			myDialog = ProgressDialog.show
					(
							getActivity(),
							"Loading...",
							"Please Wait",
							true
							);
		}

		@Override
		protected String doInBackground(String... args) {
			String httpResult = "";
			try {
				// Create a new HttpClient and Post Header
				final HttpClient httpclient = new DefaultHttpClient();
				final HttpPost httppost = new HttpPost("http://120.126.16.38/count_time.php");
				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				GlobalVariable globalVariable = (GlobalVariable)getActivity().getApplicationContext();
				final String globalUserID = globalVariable.userID;
				nameValuePairs.add(new BasicNameValuePair("id", globalUserID));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httppost);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					// 取得返回的字串
					String strResult = EntityUtils.toString(response
							.getEntity(), HTTP.UTF_8);
					Log.v("Count Time Response", strResult);
					httpResult = strResult;
				} else {
					Log.v("response", String.valueOf(response.getStatusLine().getStatusCode()));
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
				// TODO Auto-generated catch block
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
			return httpResult;
		}

		@Override
		protected void onPostExecute(String result) {
			// If you have created a Dialog, here is the place to dismiss it.
			// The `xml` that you returned will be passed to this method
			super.onPostExecute(result);
			myDialog.dismiss();
			String splitedHttpResult[] = result.split("\\n");
			Log.v("String Result",splitedHttpResult[0]);


			if(splitedHttpResult.length == 3){
				activityText.setText(splitedHttpResult[1]);
				notificationText.setText(splitedHttpResult[2]);

				//Countdown
				final SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date a = null, b = null;
				try {
					a = sdf.parse(splitedHttpResult[0]);
					//a = sdf.parse("2013-08-08 10:10:10");
					b = new Date();
				} catch (ParseException e) {
					e.printStackTrace();
				}

				//      .getTime() does the conversion: Date --> long
				/*final CountDownTimer cdt =*/ new CountDownTimer(a.getTime() - b.getTime(), 1000) {

					@Override
					public void onTick(long millisUntilFinished) {
						// TODO Auto-generated method stub
						long secUntilFinished = millisUntilFinished / 1000;
						int h = (int) (secUntilFinished / 3600);
						int m = (int) (secUntilFinished % 3600 / 60);
						int s = (int) (secUntilFinished % 60);
						countdownText.setText(String.valueOf(h) + ":" 
								+ new DecimalFormat("00").format(m) + ":"
								+ new DecimalFormat("00").format(s));
					}

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
					}
				}.start();
			}
			else if(splitedHttpResult.length == 3){
				countdownText.setVisibility(View.INVISIBLE);
				activityText.setText(splitedHttpResult[0]);
				notificationText.setText(splitedHttpResult[1]);
			}
		}
	}
}