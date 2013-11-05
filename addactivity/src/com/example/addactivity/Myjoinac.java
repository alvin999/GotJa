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
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class Myjoinac extends Activity {
	TextView tjoin;
	TextView tname;
	String uriAPI = "http://120.126.16.38/myjoinac.php";
	String name,des,place,selecttime,time,deadlinetime,moviename,showname,property,utemp,mine;
	String uacno,id,whetherdelay; 
    int p,m;
    Button btnreject;
    Button btnchatroom;
    String time_process,schedulename,invitedf="",organizer,join="",reject="";
    Intent intentchat;
    
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myjoinac);
		
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
        
		tjoin=(TextView)findViewById(R.id.tjoin);
		tname=(TextView)findViewById(R.id.tname);
		btnreject = (Button)findViewById(R.id.btnreject);
		btnchatroom =(Button)findViewById(R.id.btnchatroom);
		//intentchat = new Intent(this, ChatRoom.class);
		
		Bundle bundle =getIntent().getExtras();
		uacno=bundle.getString("uacno");
		id=bundle.getString("id");
		TextPaint tp = tjoin.getPaint(); 
		tp.setFakeBoldText(true);
		TextPaint ta = tname.getPaint(); 
		ta.setFakeBoldText(true);
//========================================================================================
		 btnchatroom.setOnClickListener(new Button.OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 Bundle bundle3 = new Bundle();				
					 bundle3.putString("id", id);
					 bundle3.putString("uacno",uacno);
					 intentchat.putExtras(bundle3);
					 startActivity(intentchat);
					// finish();	
				}   	   
		       });
		 
		btnreject.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				utemp="3";
				String uri = "http://120.126.16.38/invitedac.php";
				HttpPost httpRequest = new HttpPost(uri);
		    	List <NameValuePair> params = new ArrayList <NameValuePair>();
				params.add(new BasicNameValuePair("utemp", utemp));
				params.add(new BasicNameValuePair("uacno", uacno));
				params.add(new BasicNameValuePair("uaccount", id));
				try 
		        {
				httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 		          
		          if(httpResponse.getStatusLine().getStatusCode() == 200) 
		          {        	  
		        	  String strResult = EntityUtils.toString(httpResponse.getEntity());
		        	  Toast.makeText(getApplicationContext(),getString(R.string.you_reject),Toast.LENGTH_LONG).show();
		          }
		          else 
		          { 
		        	  tjoin.setText("Error Response: "+httpResponse.getStatusLine().toString());
		          } 
		        }   
		        catch (Exception e) 
		        {  
		        tjoin.setText(e.getMessage().toString());
		          e.printStackTrace();  
		        }				
				finish();
			}     	
        });
		
        String url_f ="http://120.126.16.38/outputparticipater.php";
 		HttpPost httpRequest_f = new HttpPost(url_f);
 		List <NameValuePair> params_f = new ArrayList <NameValuePair>();
		params_f.add(new BasicNameValuePair("uacno", uacno));
		 try 
         {  
           httpRequest_f.setEntity(new UrlEncodedFormEntity(params_f, HTTP.UTF_8));
           HttpResponse httpResponse_f = new DefaultHttpClient().execute(httpRequest_f);           
           if(httpResponse_f.getStatusLine().getStatusCode() == 200) 
           {        	  
         	  String strResult_f = EntityUtils.toString(httpResponse_f.getEntity()); 
         	  JSONArray result_f = new JSONArray(strResult_f);        	 
         	 for (int i = 0; i < result_f.length(); i++) {		        		 
	        	  JSONObject jsonObject_f= result_f.getJSONObject(i);
	        	  String mine=jsonObject_f.getString("mine");	    
	        	  String utemp=jsonObject_f.getString("utemp");
	        	  if(mine.equals("0")==true)
	        	  {	        	    
                    if(utemp.equals("1")==true)
                    {
                    	join=join+" "+jsonObject_f.getString("fname");
                    }
                    else if(utemp.equals("3")==true)
                    {
                    	reject=reject+" "+jsonObject_f.getString("fname");
                    }else{
                    	invitedf=invitedf+" "+jsonObject_f.getString("fname");
                    }  
	        	  }else{
	        		  organizer=jsonObject_f.getString("fname");
	        	  }    	        	  
         	 } 
         	Log.v("log","join:"+join+"gggggg");
         	Log.v("log","reject:"+reject+"gggggg");
         	if(join.equals("")==true)
        	 {
        		join=getString(R.string.noone_join);
        		
        	 }
        	if(reject.equals("")==true)
        	{
        		reject=getString(R.string.noone_reject);
        	}
        	if(invitedf.equals("")==true)
        	{
        		invitedf=getString(R.string.noone_invited);
        	}
           }
         }      
          	 catch (Exception e) 
             {  
          		tjoin.setText(e.getMessage().toString());
               e.printStackTrace();  
             } 
//========================================================================================	
		 final ProgressDialog myDialog;
			
			myDialog = ProgressDialog.show
					(
							Myjoinac.this,
							"Loading...",
							"Please Wait",
							true
							);
			new AsyncTask<Void, Void, String>() {
				@Override
				protected String doInBackground(Void... params) {
					String httpResult = "";
					try {
						// Create a new HttpClient and Post Header
						final HttpClient httpclient = new DefaultHttpClient();
						final HttpPost httppost = new HttpPost(uriAPI);
						// Add your data
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
						nameValuePairs.add(new BasicNameValuePair("uacno", uacno));
						nameValuePairs.add(new BasicNameValuePair("uaccount", id));
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
					JSONArray strResult = null;
					try {
						strResult = new JSONArray(result);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}        		 
					JSONObject jsonObject;
					try {
						jsonObject = strResult.getJSONObject(0);
						 name=jsonObject.getString("name");
				       	  des=jsonObject.getString("destination");
				       	  place=jsonObject.getString("meetplace");
				       	  time=jsonObject.getString("time");
				       	  property=jsonObject.getString("property");
				       	  mine=jsonObject.getString("mine");
				       	 whetherdelay=jsonObject.getString("whetherdelay");
				         m=Integer.parseInt(mine);
			         	  p=Integer.parseInt(property);  
			         	  
			         	 if(name.equals(" ")==true || name.equals("")==true)
			          	{
			          		name=getString(R.string.undifine);
			          	}
			          	if(des.equals(" ")==true || des.equals("")==true)
			          	{
			          		des=getString(R.string.undifine);
			          	}
			          	if(place.equals(" ")==true || place.equals("")==true)
			          	{
			          		place=getString(R.string.undifine);
			          	}
			          	tname.setText(name);
			        	tjoin.setText(getString(R.string.organizer)+"　　　"+organizer+"\n");
			          	if(time.equals("0000-00-00 00:00:00")==true && whetherdelay.equals("0")==true)
			       	  {
			       		  time=getString(R.string.voting);	
			       	  } 
			       	 if(time.equals("0000-00-00 00:00:00")==true && whetherdelay.equals("1")==true)
			      	  {
			      		  time=getString(R.string.organizer_deciding_time);		
			      	  }
			         	 if(p==2)
			         	  {
			         		 moviename=jsonObject.getString("moviename");
			         		if(moviename.equals("")==true || moviename.equals(" ")==true)
			             	{
			         			moviename=getString(R.string.undifine);
			             	}
			         		tjoin.append(getString(R.string.movie_name)+"　"+moviename+"\n");
			         	  }
			         	 if(p==4)
			        	  {
			         		showname=jsonObject.getString("showname");
			         		if(showname.equals(" ")==true || showname.equals("")==true)
			             	{
			         			showname=getString(R.string.undifine);
			             	}
			         		tjoin.append(getString(R.string.show_name)+"　"+showname+"\n");
			        	  }
			         	if(p==5)
			         	{  
			         		tjoin.append(getString(R.string.process)+"\n");
			         		String url ="http://120.126.16.38/process.php";
			         		HttpPost httpRequest_process = new HttpPost(url);
			         		List <NameValuePair> params_process = new ArrayList <NameValuePair>();
			        		params_process.add(new BasicNameValuePair("uacno", uacno));	
			         		 try 
			                 {  
			                   httpRequest_process.setEntity(new UrlEncodedFormEntity(params_process, HTTP.UTF_8));
			                   HttpResponse httpResponse_process = new DefaultHttpClient().execute(httpRequest_process);                   
			                   if(httpResponse_process.getStatusLine().getStatusCode() == 200) 
			                   {        	  
			                 	  String strResult_process = EntityUtils.toString(httpResponse_process.getEntity()); 
			                 	 Log.v("log","strResult_process:"+strResult_process+"ffffff");
			                 	 if(strResult_process.equals("null")==true)
			                  	{              			
			              				tjoin.append(getString(R.string.undifine)+"\n");
			                  	}else{
			                  		Log.v("log","false:"+strResult_process+"ffffff");
			                 	  JSONArray result_process = new JSONArray(strResult_process);                 		 
			                 	  for (int i = 0; i < result_process.length(); i++) {		        		 
			   		        	  JSONObject jsonObject_process = result_process.getJSONObject(i);
			   		        	  time_process=jsonObject_process.getString("time");
			   		        	  schedulename=jsonObject_process.getString("schedulename");
			   		        	  tjoin.append(time_process+" "+schedulename+"\n");
			   		        	
			                 	  } 
			                  	}
			                   }
			                 }
			                  	 catch (Exception e) 
			                     {  
			                     tjoin.setText(e.getMessage().toString());
			                       e.printStackTrace();  
			                     }             	
			         	}
			         	tjoin.append(getString(R.string.des)+"　　　"+des+"\n"+
						 		   getString(R.string.place)+"　"+place+"\n----------------------------------\n"+
						 		   getString(R.string.friend_invited)+"　　"+invitedf+"\n"+
						 		   getString(R.string.join)+"　　　"+join+"\n"+
						 		   getString(R.string.reject)+"　　　"+reject+"\n----------------------------------\n"+
						 		   getString(R.string.time_result)+"　　　"+time); 
			         	if(m==0)
			         	 {
			         		tjoin.append("\n"+getString(R.string.youarenot_organizer));
			         	 }
			         	if(m==1)
			        	 {
			        		tjoin.append("\n"+getString(R.string.youare_organizer));	
			        	 }
			         	 
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}             	 
					myDialog.dismiss();		
				}
			}.execute();
	}
}
