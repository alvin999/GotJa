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
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Show extends ListActivity{

	ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
	private SimpleAdapter adapter;

	private String[] showName = new String[5];
	private String[] showPeriod = new String[5];
	private String[] showStartTime = new String[5];
	private String[] showEndTime = new String[5];
	String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		final Intent intent=new Intent(this,com.example.addactivity.Addshow.class);	
		Bundle b=getIntent().getExtras();
		id=b.getString("id");
		
		ListView lv = getListView();
		ColorDrawable sage = new ColorDrawable(this.getResources().getColor(R.drawable.sage));
		lv.setDivider(sage);
		lv.setDividerHeight(5);
		lv.setBackgroundResource(R.drawable.background);
		//--------http post ���敶望�銵�--------
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String strResult = "";

				try {
					final HttpClient httpclient = new DefaultHttpClient();
					final HttpPost httppost = new HttpPost("http://120.126.16.38/show.php");
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
					nameValuePairs.add(new BasicNameValuePair("id",id));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
					HttpResponse response = httpclient.execute(httppost);
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						strResult = EntityUtils.toString(response.getEntity());
					} else {
						Log.v("response", String.valueOf(response.getStatusLine().getStatusCode()));
					}
				} catch (ClientProtocolException e) {
				} catch (IOException e) {
				} catch (Exception e) {
					Log.e("Exception", e.toString());
				}
				Log.v("strResult", strResult);
				return strResult;
			}

			@Override
			protected void onPostExecute(String result) {
				Log.v("result",result);
				JSONArray jArray = null;
				try {
					jArray = new JSONArray(result);
				} catch (JSONException e2) {
					e2.printStackTrace();
				}
				for (int i = 0; i < jArray.length(); i++) { 
					JSONObject jsonObject = null;
					try {
						jsonObject = jArray.getJSONObject(i);
					} catch (JSONException e1) {
						e1.printStackTrace();
					} 
					try {
						showName[i] = jsonObject.getString("name");
						Log.v("showName", showName[i]);
						showStartTime[i] = jsonObject.getString("starttime");
						showEndTime[i] = jsonObject.getString("endtime");
						showPeriod[i] = showStartTime[i] + "~" +showEndTime[i] ; 
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				for(int i=0; i<showName.length; i++){
					Log.v("showName.length","123");
					HashMap<String,String> item = new HashMap<String,String>();
					item.put( "name", showName[i]);
					item.put( "period",showPeriod[i] );
					list.add( item );				
				}
				adapter = new SimpleAdapter( 
						getBaseContext(), 
						list,
						android.R.layout.simple_list_item_2,
						new String[] { "name","period" },
						new int[] { android.R.id.text1, android.R.id.text2 } );
				setListAdapter( adapter );
				getListView().setTextFilterEnabled(true);
							
				getListView().setOnItemClickListener(new OnItemClickListener() {
			        public void onItemClick(AdapterView<?> parent, View view,
			                int position, long id) {
			        	   final int p=position;
					       Log.v("log",showName[p]);
				       
					       new AlertDialog.Builder(Show.this)
			                .setTitle(showName[p])
			                .setMessage(getString(R.string.choose_function))
			                .setPositiveButton(getString(R.string.get_group), new DialogInterface.OnClickListener() {
			                    @Override
			                    public void onClick(DialogInterface dialog, int which) {
			                    	Bundle bundle1 =getIntent().getExtras();
			    					String id=bundle1.getString("id");
			    					 //String id="888888";
			                    	Bundle bundle = new Bundle();					
			   					 	bundle.putString("name", showName[p]);
			   					 	bundle.putString("id", id);
			   					 	   					 	
			   					 	intent.putExtras(bundle);
			   					 	startActivity(intent);	
			   					 	finish();	                    	
			                    }
			                })
			                .setNegativeButton(getString(R.string.get_search), new DialogInterface.OnClickListener() {
			                    @Override
			                    public void onClick(DialogInterface dialog, int which) {
			                       String searchURL = "http://www.google.com/search?q=" +showName[p];
			     			       Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(searchURL));
			     			       startActivity(browserIntent);			                    	
			                    }
			                })
			                .show();
			        }
			    });		
			
			}
		}.execute();
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
	}
}