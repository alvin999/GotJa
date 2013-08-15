package com.gotja;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;

public class Movie extends ListActivity{

	ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
	private SimpleAdapter adapter;

	private String[] movieName = new String[5];
	private String[] releaseTime = new String[5];
	String activityid = "100000217523324_1";//-->activityid
	String userid;//-->userid

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		GlobalVariable globalVariable = (GlobalVariable)getApplicationContext();
		userid = globalVariable.userID;
		
		final Intent intent=new Intent(this, com.example.addactivity.Addmovie.class);
		//--------http post --------
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String strResult = "";

				try {
					// Create a new HttpClient and Post Header
					final HttpClient httpclient = new DefaultHttpClient();
					final HttpPost httppost = new HttpPost("http://120.126.16.38/movieRanking.php");
					// Add your data
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
					nameValuePairs.add(new BasicNameValuePair("activityid",activityid));//-->id
					nameValuePairs.add(new BasicNameValuePair("userid",userid));//-->userid
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

					// Execute HTTP Post Request
					HttpResponse response = httpclient.execute(httppost);
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						// 
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
				Log.v("strResult", strResult);
				return strResult;
			}


			@Override
			protected void onPostExecute(String result) {
				Log.v("result",result);

				//
				JSONArray jArray = null;
				try {
					jArray = new JSONArray(result);
				} catch (JSONException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				//Log.v("jArray.length", String.valueOf(jArray.length()));


				for (int i = 0; i < jArray.length(); i++) { 
					JSONObject jsonObject = null;
					try {
						jsonObject = jArray.getJSONObject(i);
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
					try {

						movieName[i] = jsonObject.getString("nmname");
						Log.v("movieName", movieName[i]);
						releaseTime[i] = jsonObject.getString("movieuptime").split(" ")[0];
						Log.v("releaseTime", releaseTime[i]);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//
				for(int i=0; i<movieName.length; i++){
					Log.v("movieName.length","123");
					HashMap<String,String> item = new HashMap<String,String>();
					item.put( "food", movieName[i]);
					item.put( "place",releaseTime[i] );
					list.add( item );

				}
				//
				adapter = new SimpleAdapter( 
						getBaseContext(), 
						list,
						android.R.layout.simple_list_item_2,
						new String[] { "food","place" },
						new int[] { android.R.id.text1, android.R.id.text2 } );

				//ListActivit
				setListAdapter( adapter );

				//
				getListView().setTextFilterEnabled(true);

				getListView().setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						final int p=position;
						Log.v("log",movieName[p]);
						//Toast.makeText(getBaseContext(),str.getTitle(),Toast.LENGTH_SHORT).show();

						new AlertDialog.Builder(Movie.this)
						.setTitle(movieName[p])
						.setMessage(getString(R.string.choose_function))
						.setPositiveButton(getString(R.string.get_group), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// String id="888888";
								Bundle bundle = new Bundle();					
								bundle.putString("moviename", movieName[p]);
								bundle.putString("id", userid);

								intent.putExtras(bundle);
								startActivity(intent);	
								finish();	                    	
							}
						})
						.setNegativeButton(getString(R.string.get_search), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								String searchURL = "http://www.google.com/search?q=" +movieName[p];
								Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(searchURL));
								startActivity(browserIntent);

							}
						})
						.show();
					}
				});

			}


		}.execute();

		//-----------------


	}
}
