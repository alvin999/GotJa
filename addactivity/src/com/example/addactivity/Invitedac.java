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
import android.content.Intent;
import android.text.TextPaint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class Invitedac extends Activity {

	int c=0;
	TextView tinvitedac;
	Button btnjoin;
	Button btnvote;
	Button btnreject;
	Button btnchatroom;
	TextView ttt;
	TextView tname;
	Intent intent;
	Intent intent_restart;
	Intent intentvote;
	Intent intentchat;
	String uriAPI = "http://120.126.16.38/invitedac.php";
	String uacno,id,zero="0000-00-00 00:00:00",organizer,invitedf="",join="",reject="";
	String time_process,schedulename,it,whetherdelay,vote="1",minevotetime=null,votetemp;
	String name,des,place,selecttime,time,deadlinetime,moviename,showname,property,uutemp,utemp=null,whethervote;
	int p,ctemp,countvote;
	
	ArrayList<String> tarray = new ArrayList<String>();
	ArrayList<String> tarray_name = new ArrayList<String>();
	String[] timearray;
	String[] content;	  
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invitedac);
		
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
	     
		tinvitedac =(TextView)findViewById(R.id.tinvitedac);
		tname =(TextView)findViewById(R.id.tname);
		btnjoin =(Button)findViewById(R.id.btnjoin);
		btnvote =(Button)findViewById(R.id.btnvote);
		btnreject =(Button)findViewById(R.id.btnreject);
		btnchatroom =(Button)findViewById(R.id.btnchatroom);
		
		TextPaint tp = tinvitedac.getPaint(); 
		tp.setFakeBoldText(true);
		TextPaint tn = tname.getPaint(); 
		tn.setFakeBoldText(true);
		
		intent = new Intent(this,Myactivity.class);
		intent_restart = new Intent(this,Invitedac.class);
		intentvote = new Intent(this,Voting.class);
		//intentchat = new Intent(this, ChatRoom.class);
		
		Bundle bundle =getIntent().getExtras();
	    uacno=bundle.getString("uacno");	    
		id=bundle.getString("id"); //使用者,不是主揪
		uutemp=bundle.getString("utemp");
		 
       btnvote.setVisibility(View.GONE);
       ttt = new TextView(this);
       if(uutemp.equals("3")==true)
       {
    	   btnreject.setVisibility(View.GONE);
       }
    /*   btnchatroom.setOnClickListener(new Button.OnClickListener(){
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
       });*/
    		
		btnjoin.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				utemp="1";							
				HttpPost httpRequest = new HttpPost(uriAPI);
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
		        	  Toast.makeText(getApplicationContext(),getString(R.string.you_join),Toast.LENGTH_LONG).show();
		          }
		          else 
		          { 
		        	  tinvitedac.setText("Error Response: "+httpResponse.getStatusLine().toString());
		          } 
		        }   
		        catch (Exception e) 
		        {  
		        tinvitedac.setText(e.getMessage().toString());
		          e.printStackTrace();  
		        }				
				finish();
			}     	
        });
		
		btnreject.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				utemp="3";				
				HttpPost httpRequest = new HttpPost(uriAPI);
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
		        	  tinvitedac.setText("Error Response: "+httpResponse.getStatusLine().toString());
		          } 
		        }   
		        catch (Exception e) 
		        {  
		        tinvitedac.setText(e.getMessage().toString());
		          e.printStackTrace();  
		        }				
				finish();
			}     	
        });
		
//======================================================================================	
		btnvote.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				countvote++;
			if(countvote%2==0)
			{
				Bundle bundle2 = new Bundle();				
				 bundle2.putString("id", id);
				 bundle2.putString("uacno",uacno);
				 bundle2.putString("utemp",uutemp);
								 
				 intentvote.putExtras(bundle2);				
				 startActivity(intentvote);
				 finish();				 
			}else
			{
				 votetemp="2";
				 String url_voting_again ="http://120.126.16.38/voting_again.php";
				 HttpPost httpRequest = new HttpPost(url_voting_again);
			    	List <NameValuePair> params = new ArrayList <NameValuePair>();
					params.add(new BasicNameValuePair("votetemp", votetemp));
					params.add(new BasicNameValuePair("uacno", uacno));
					params.add(new BasicNameValuePair("uaccount", id));
					try 
			        {
					httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
			          
			          if(httpResponse.getStatusLine().getStatusCode() == 200) 
			          {        	  
			        	  String strResult = EntityUtils.toString(httpResponse.getEntity());		        	  
			          }
			          else 
			          { 
			        	  tinvitedac.setText("Error Response: "+httpResponse.getStatusLine().toString());
			          } 
			        }   
			        catch (Exception e) 
			        {  
			        tinvitedac.setText(e.getMessage().toString());
			          e.printStackTrace();  
			        }				 
					 Bundle bundle2 = new Bundle();				
					 bundle2.putString("id", id);
					 bundle2.putString("uacno",uacno);
					 bundle2.putString("utemp",uutemp);
										
					intentvote.putExtras(bundle2);
					startActivity(intentvote);
					finish();			
			}
		  }			
		});
//========================================================================================
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
          		tinvitedac.setText(e.getMessage().toString());
               e.printStackTrace();  
             } 
//========================================================================================	
		 final ProgressDialog myDialog;		
			myDialog = ProgressDialog.show
					(
							Invitedac.this,
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
			         	  deadlinetime=jsonObject.getString("deadlinetime");
			         	  whethervote=jsonObject.getString("whethervote");
			         	  whetherdelay=jsonObject.getString("whetherdelay");
			         	  votetemp=jsonObject.getString("votetemp");
			         	String outtime=jsonObject.getString("outtime");
			         	
			         	if(votetemp.equals("2")==true)
			         	{
			         		btnvote.setText(getString(R.string.vote_i));
			         		countvote=1;
			         	}else{
			         		btnvote.setText(getString(R.string.vote_again));
			         		countvote=0;
			         	}
			         	Log.v("log","votetemp:"+votetemp);
			         	Log.v("log","countvote:"+countvote);
			         	//=======判斷空值
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
			         	//=============
			         	Log.v("log","time "+time);
			         	Log.v("log","whetherdelay "+whetherdelay);
			         	  if(time.equals("0000-00-00 00:00:00")==true && whetherdelay.equals("0")==true)
			         	  {
			         		  time=getString(R.string.voting);	
			         	  } 
			         	 if(time.equals("0000-00-00 00:00:00")==true && whetherdelay.equals("1")==true)
			        	  {
			        		  time=getString(R.string.organizer_deciding_time);		
			        	  }       	  
			         	  property=jsonObject.getString("property");
			         	  p=Integer.parseInt(property); 
			         	  tname.setText(name);
			         	 tinvitedac.setText(getString(R.string.organizer)+"　　　"+organizer+"\n");
			         	 if(p==2)
			         	  {
			         		 moviename=jsonObject.getString("moviename");
			         		if(moviename.equals("")==true || moviename.equals(" ")==true)
			             	{
			         			moviename=getString(R.string.undifine);
			             	}
			         		Log.v("log","movie:"+moviename+"ffffff");
			         		tinvitedac.append(getString(R.string.movie_name)+"　"+moviename+"\n");
			         	  }
			         	 if(p==4)
			        	  {
			         		showname=jsonObject.getString("showname");
			         		if(showname.equals(" ")==true || showname.equals("")==true)
			             	{
			         			showname=getString(R.string.undifine);
			             	}
			         		tinvitedac.append(getString(R.string.show_name)+"　"+showname+"\n");
			        	  }
			         	if(p==5)
			         	{   tinvitedac.append(getString(R.string.process)+"\n");
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
			                 	 if(strResult_process.equals("null")==true)
			                   	{              			
			                 		tinvitedac.append(getString(R.string.undifine)+"\n");
			                   	}else{
			                 	  JSONArray result_process = new JSONArray(strResult_process);                  		 
			                 	 for (int i = 0; i < result_process.length(); i++) {		        		 
			   		        	  JSONObject jsonObject_process = result_process.getJSONObject(i);
			   		        	  time_process=jsonObject_process.getString("time");
			   		        	  schedulename=jsonObject_process.getString("schedulename");
			   		        	  tinvitedac.append(time_process+" "+schedulename+"\n");  		        	  
			                 	 }
			                   	}
			                   }
			                 }
			                  	 catch (Exception e) 
			                     {  
			                     tinvitedac.setText(e.getMessage().toString());
			                       e.printStackTrace();  
			                     }  
			            	
			         	} //if(p==5)         	        	
			         	 tinvitedac.append(getString(R.string.des)+"　　　"+des+"\n"+
			         			 		   getString(R.string.place)+"　"+place+"\n----------------------------------\n"+
			         			 		   getString(R.string.friend_invited)+"　　"+invitedf+"\n"+
			         			 		   getString(R.string.join)+"　　　"+join+"\n"+
			         			 		   getString(R.string.reject)+"　　　"+reject+"\n----------------------------------\n"+
			         			 		   getString(R.string.time_result)+"　　　"+time+"\n");
			         	 if(whethervote.equals("0")==true && outtime.equals("0")==true)
			         	 {
			         		 tinvitedac.append(getString(R.string.vote_deadlinetime1)+" "+deadlinetime+" "+getString(R.string.vote_deadlinetime3));       				
			         	 }   
			         	 if(whethervote.equals("1")==true && whetherdelay.equals("0")==true)
			             { 
			             	btnvote.setVisibility(View.VISIBLE);
			             }  
			             else{btnvote.setVisibility(View.GONE);
			             }
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}             	 
					myDialog.dismiss();		
				}
			}.execute();
   /* 	HttpPost httpRequest = new HttpPost(uriAPI);
    	List <NameValuePair> params = new ArrayList <NameValuePair>();
		params.add(new BasicNameValuePair("uacno", uacno));		
		params.add(new BasicNameValuePair("uaccount", id));
        try 
        {  
          httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
          HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);          
          if(httpResponse.getStatusLine().getStatusCode() == 200) 
          {        	 
        	  String strResult = EntityUtils.toString(httpResponse.getEntity()); 
        	  JSONArray result = new JSONArray(strResult);       	
         	  JSONObject jsonObject = result.getJSONObject(0);      
         	  name=jsonObject.getString("name");
         	  des=jsonObject.getString("destination");
         	  place=jsonObject.getString("meetplace");
         	  time=jsonObject.getString("time");
         	  deadlinetime=jsonObject.getString("deadlinetime");
         	  whethervote=jsonObject.getString("whethervote");
         	  whetherdelay=jsonObject.getString("whetherdelay");
         	  votetemp=jsonObject.getString("votetemp");
         	String outtime=jsonObject.getString("outtime");
         	
         	if(votetemp.equals("2")==true)
         	{
         		btnvote.setText(getString(R.string.vote_i));
         		countvote=1;
         	}else{
         		btnvote.setText(getString(R.string.vote_again));
         		countvote=0;
         	}
         	Log.v("log","votetemp:"+votetemp);
         	Log.v("log","countvote:"+countvote);
         	//=======判斷空值
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
         	//=============
         	Log.v("log","time "+time);
         	Log.v("log","whetherdelay "+whetherdelay);
         	  if(time.equals("0000-00-00 00:00:00")==true && whetherdelay.equals("0")==true)
         	  {
         		  time=getString(R.string.voting);	
         	  } 
         	 if(time.equals("0000-00-00 00:00:00")==true && whetherdelay.equals("1")==true)
        	  {
        		  time=getString(R.string.organizer_deciding_time);		
        	  }       	  
         	  property=jsonObject.getString("property");
         	  p=Integer.parseInt(property); 
         	  tname.setText(name);
         	 tinvitedac.setText(getString(R.string.organizer)+"　　　"+organizer+"\n");
         	 if(p==2)
         	  {
         		 moviename=jsonObject.getString("moviename");
         		if(moviename.equals("")==true || moviename.equals(" ")==true)
             	{
         			moviename=getString(R.string.undifine);
             	}
         		Log.v("log","movie:"+moviename+"ffffff");
         		tinvitedac.append(getString(R.string.movie_name)+"　"+moviename+"\n");
         	  }
         	 if(p==4)
        	  {
         		showname=jsonObject.getString("showname");
         		if(showname.equals(" ")==true || showname.equals("")==true)
             	{
         			showname=getString(R.string.undifine);
             	}
         		tinvitedac.append(getString(R.string.show_name)+"　"+showname+"\n");
        	  }
         	if(p==5)
         	{   tinvitedac.append(getString(R.string.process)+"\n");
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
                 	 if(strResult_process.equals("null")==true)
                   	{              			
                 		tinvitedac.append(getString(R.string.undifine)+"\n");
                   	}else{
                 	  JSONArray result_process = new JSONArray(strResult_process);                  		 
                 	 for (int i = 0; i < result_process.length(); i++) {		        		 
   		        	  JSONObject jsonObject_process = result_process.getJSONObject(i);
   		        	  time_process=jsonObject_process.getString("time");
   		        	  schedulename=jsonObject_process.getString("schedulename");
   		        	  tinvitedac.append(time_process+" "+schedulename+"\n");  		        	  
                 	 }
                   	}
                   }
                 }
                  	 catch (Exception e) 
                     {  
                     tinvitedac.setText(e.getMessage().toString());
                       e.printStackTrace();  
                     }  
            	
         	} //if(p==5)         	        	
         	 tinvitedac.append(getString(R.string.des)+"　　　"+des+"\n"+
         			 		   getString(R.string.place)+"　"+place+"\n----------------------------------\n"+
         			 		   getString(R.string.friend_invited)+"　　"+invitedf+"\n"+
         			 		   getString(R.string.join)+"　　　"+join+"\n"+
         			 		   getString(R.string.reject)+"　　　"+reject+"\n----------------------------------\n"+
         			 		   getString(R.string.time_result)+"　　　"+time+"\n");
         	 if(whethervote.equals("0")==true && outtime.equals("0")==true)
         	 {
         		 tinvitedac.append(getString(R.string.vote_deadlinetime1)+" "+deadlinetime+" "+getString(R.string.vote_deadlinetime3));       				
         	 }
         	 
         	 } 
          else 
          { 
        	  tinvitedac.setText("Error Response: "+httpResponse.getStatusLine().toString());
          } 
        }   
        catch (Exception e) 
        {  
        tinvitedac.setText(e.getMessage().toString());
          e.printStackTrace();  
        }   */
//==============================================================================如果需要投票+位超過時間        
       
	}	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	  if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	    // do something on back.	
		  if(uutemp.equals("0")==true){
		  utemp="2";			
			HttpPost httpRequest = new HttpPost(uriAPI);
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
	        	  Toast.makeText(getApplicationContext(),getString(R.string.choice),Toast.LENGTH_LONG).show();
	          }
	          else 
	          { 
	        	  tinvitedac.setText("Error Response: "+httpResponse.getStatusLine().toString());
	          } 
	        }   
	        catch (Exception e) 
	        {  
	        tinvitedac.setText(e.getMessage().toString());
	          e.printStackTrace();  
	        }
		  }
			finish();
	    //moveTaskToBack(true);
	    return true;
	  }
	  return super.onKeyDown(keyCode, event);
	}	
}