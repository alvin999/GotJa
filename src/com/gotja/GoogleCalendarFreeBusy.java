package com.gotja;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class GoogleCalendarFreeBusy extends Activity {

	private static final int AUTHORIZATION_CODE = 1993;
	private static final int ACCOUNT_CODE = 1601;

	private AuthPreferences authPreferences;
	private AccountManager accountManager;
	
	private String timeMin = "2013-08-15T00:00:00+08:00";
	private String timeMax = "2013-08-20T00:00:00+08:00";

	private TextView freeBusyText;

	/**
	 * change this depending on the scope needed for the things you do in
	 * doCoolAuthenticatedStuff()
	 */
	private final String SCOPE = "https://www.googleapis.com/auth/calendar";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.google_calendar_free_busy);

		freeBusyText = (TextView) findViewById(R.id.free_busy_text);

		accountManager = AccountManager.get(this);

		authPreferences = new AuthPreferences(this);
		if (authPreferences.getUser() != null
				&& authPreferences.getToken() != null) {
			doCoolAuthenticatedStuff();
		} else {
			chooseAccount();
		}
	}

	private void doCoolAuthenticatedStuff() {
		new AsyncTask<Void, Void, JSONObject>() {
			@Override
			protected JSONObject doInBackground(Void... params) {
				JSONObject json = null;
				HttpClient httpclient = new DefaultHttpClient();
				// Prepare a request object
				HttpPost request = new HttpPost("https://www.googleapis.com/calendar/v3/freeBusy"
						+ "?access_token=" + authPreferences.getToken());
				request.setHeader("Accept", "application/json");
				request.setHeader("Content-type", "application/json");
				
				JSONArray calendarIdArray = new JSONArray();
				JSONObject calendarId = new JSONObject();
				try {
					calendarId.put("id", authPreferences.getUser());
				} catch (JSONException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				calendarIdArray.put(calendarId);
				
				JSONObject param = new JSONObject();
				try {
					param.put("timeMin", timeMin);
					param.put("timeMax", timeMax);
					param.put("items", calendarIdArray);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				
				try {
					request.setEntity(new StringEntity(param.toString(), HTTP.UTF_8));
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				
				// Execute the request
				HttpResponse response;
				try {
					response = httpclient.execute(request);
					// Get the response entity
					HttpEntity entity = response.getEntity();
					// If response entity is not null
					if (entity != null) {
						String result = EntityUtils.toString(entity);
						//Log.v("result", result);
						// construct a JSON object with result
						json=new JSONObject(result);
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Return the json
				return json;
			}

			@Override
			protected void onPostExecute(JSONObject result) {
					freeBusyText.setText(result.toString());
					Log.v("result", result.toString());
			}
		}.execute();
		/*
		new AsyncTask<Void, Void, JSONObject>() {
			@Override
			protected JSONObject doInBackground(Void... params) {
				JSONObject json = null;
			    HttpClient httpclient = new DefaultHttpClient();
			    // Prepare a request object
			    HttpGet httpget = new HttpGet("https://www.googleapis.com/calendar/v3/calendars/"
			    		+ authPreferences.getUser() 
			    		+ "?access_token=" + authPreferences.getToken());
			    // Accept JSON
			    httpget.addHeader("accept", "application/json");
			    // Execute the request
			    HttpResponse response;
			    try {
			        response = httpclient.execute(httpget);
			        // Get the response entity
			        HttpEntity entity = response.getEntity();
			        // If response entity is not null
			        if (entity != null) {
			            String result = EntityUtils.toString(entity);
			            // construct a JSON object with result
			            json=new JSONObject(result);
			        }
			    } catch (ClientProtocolException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    } catch (IOException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    } catch (JSONException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    }
			    // Return the json
			    return json;
			}

			@Override
			protected void onPostExecute(JSONObject result) {
				try {
					freeBusyText.setText(result.getString("summary"));
					Log.v("user id", result.getString("summary"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}.execute();
		 */
	}

	private void chooseAccount() {
		// use https://github.com/frakbot/Android-AccountChooser for
		// compatibility with older devices
		Intent intent = AccountManager.newChooseAccountIntent(null, null,
				new String[] { "com.google" }, false, null, null, null, null);
		startActivityForResult(intent, ACCOUNT_CODE);
	}

	private void requestToken() {
		Account userAccount = null;
		String user = authPreferences.getUser();
		for (Account account : accountManager.getAccounts()) {
			if (account.name.equals(user)) {
				userAccount = account;

				break;
			}
		}

		accountManager.getAuthToken(userAccount, "oauth2:" + SCOPE, null, this,
				new OnTokenAcquired(), null);
	}

	/**
	 * call this method if your token expired, or you want to request a new
	 * token for whatever reason. call requestToken() again afterwards in order
	 * to get a new token.
	 */
	private void invalidateToken() {
		AccountManager accountManager = AccountManager.get(this);
		accountManager.invalidateAuthToken("com.google",
				authPreferences.getToken());

		authPreferences.setToken(null);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			if (requestCode == AUTHORIZATION_CODE) {
				requestToken();
			} else if (requestCode == ACCOUNT_CODE) {
				String accountName = data
						.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
				authPreferences.setUser(accountName);

				// invalidate old tokens which might be cached. we want a fresh
				// one, which is guaranteed to work
				invalidateToken();

				requestToken();
			}
		}
	}

	private class OnTokenAcquired implements AccountManagerCallback<Bundle> {

		@Override
		public void run(AccountManagerFuture<Bundle> result) {
			try {
				Bundle bundle = result.getResult();

				Intent launch = (Intent) bundle.get(AccountManager.KEY_INTENT);
				if (launch != null) {
					startActivityForResult(launch, AUTHORIZATION_CODE);
				} else {
					String token = bundle
							.getString(AccountManager.KEY_AUTHTOKEN);

					authPreferences.setToken(token);

					doCoolAuthenticatedStuff();
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private class AuthPreferences {

		private static final String KEY_USER = "user";
		private static final String KEY_TOKEN = "token";

		private SharedPreferences preferences;

		public AuthPreferences(Context context) {
			preferences = context
					.getSharedPreferences("auth", Context.MODE_PRIVATE);
		}

		public void setUser(String user) {
			Editor editor = preferences.edit();
			editor.putString(KEY_USER, user);
			editor.commit();
		}

		public void setToken(String password) {
			Editor editor = preferences.edit();
			editor.putString(KEY_TOKEN, password);
			editor.commit();
		}

		public String getUser() {
			return preferences.getString(KEY_USER, null);
		}

		public String getToken() {
			return preferences.getString(KEY_TOKEN, null);
		}
	}
}