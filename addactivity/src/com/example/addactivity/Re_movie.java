package com.example.addactivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class Re_movie extends Activity {
	
	private ListView listInput;	
	private ArrayAdapter<String> adapter;
    private ArrayList<String> items;
    
    TextView ttt;
    String property="2",id,s;
    String[] dayarray;
    Boolean tf;
    

	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.re_movie);
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectDiskReads()
        .detectDiskWrites()
        .detectNetwork()   // or .detectAll() for all detectable problems
        .penaltyLog()
        .build());
        
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
        .detectLeakedSqlLiteObjects()
        .penaltyLog()
        .penaltyDeath()
        .build());
    
		  Bundle bundle1 =getIntent().getExtras();
	       id=bundle1.getString("id");
	       tf=bundle1.getBoolean("tf");
	       
	       ttt = (TextView)findViewById(R.id.ttt);
	       
		    listInput = (ListView)findViewById(R.id.listView1);
	        items = new ArrayList<String>();
	        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
	        listInput.setAdapter(adapter);
	        
	  if(tf==true){
	        String uriAPI = "http://120.126.16.38/recommend.php";
	        HttpPost httpRequest = new HttpPost(uriAPI);
	    	List <NameValuePair> params = new ArrayList <NameValuePair>();
			params.add(new BasicNameValuePair("property", property));
		
			try 
	        {
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
	          
	          if(httpResponse.getStatusLine().getStatusCode() == 200) 
	          {        	  
	        	  String strResult = EntityUtils.toString(httpResponse.getEntity());
	        	  JSONArray result = new JSONArray(strResult);
	        	  for (int i = 0; i < result.length(); i++) {
		        		 
		        	  JSONObject jsonObject = result.getJSONObject(i);
		        	  String nmname=jsonObject.getString("nmname");
		        	  String movieuptime=jsonObject.getString("movieuptime");
		        	  dayarray=movieuptime.split(" ");
		        	  int dex=i+1;
		        	  s="第"+dex+"名      "+nmname+"\n上映日期: "+dayarray[0];
		        	  items.add(s);
		        	  listInput.setAdapter(adapter);		        	  
	        	  }
	          }
	          else 
	          { 
	        	  ttt.setText("Error Response: "+httpResponse.getStatusLine().toString());
	          } 
	        }   
	        catch (Exception e) 
	        {  
	        ttt.setText(e.getMessage().toString());
	          e.printStackTrace();  
	        }
	  }//tf=true
			 
		    }
	}




