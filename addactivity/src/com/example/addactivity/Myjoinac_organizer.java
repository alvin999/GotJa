package com.example.addactivity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class Myjoinac_organizer extends Activity {
	Date dts,dts_st;
	TextView tjoin;
	TextView ttt;
	TextView tname;
	Button btnedit;
	Button btntime;
	Button btnchatroom;
	ScrollView sv;
	String uriAPI = "http://120.126.16.38/myjoinac.php";
	String name,des,place,selecttime,time,deadlinetime,moviename,showname,property,utemp="1",mine,whetherdelay;
	String uacno,id,str,sd,st="";
    String basetime="";
	Intent intent;
	Intent intentchat;
    int p,m,k,deadlinetimetemp=0;
    String time_process,schedulename,invitedf="",organizer,join="",reject="";
    ArrayList<String> t = new ArrayList<String>();
    String[] timearray;
    String[] content;
    String[] sttr;
    RadioGroup group;

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myjoinac_organizer);
		
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
        btnedit=(Button)findViewById(R.id.btnedit);
        btnchatroom =(Button)findViewById(R.id.btnchatroom);
        intent = new Intent(this,Myjoinac_organizer_edit.class); 
       // intentchat = new Intent(this, ChatRoom.class);
        
		Bundle bundle =getIntent().getExtras();
		uacno=bundle.getString("uacno");
		id=bundle.getString("id");
		TextPaint tp = tjoin.getPaint(); 
		tp.setFakeBoldText(true);
		TextPaint tn= tname.getPaint(); 
		tn.setFakeBoldText(true);
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
                    }else
                    {
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
          		tjoin.setText(e.getMessage().toString());
               e.printStackTrace();  
             } 
//========================================================
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
//========================================================================================		
		 final ProgressDialog myDialog;		
			myDialog = ProgressDialog.show
					(
							Myjoinac_organizer.this,
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
			         	  whetherdelay=jsonObject.getString("whetherdelay");        	  
			         	  property=jsonObject.getString("property");
			         	  mine=jsonObject.getString("mine");
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
			           	if(time.equals("0000-00-00 00:00:00")==true)
			        	  {
			       		  time=getString(R.string.voting);	
			       		  k=0;
			       	    }else{k=1;}           
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
			                 	 if(strResult_process.equals("null")==true)
			                   	{              			
			               				tjoin.append(getString(R.string.undifine)+"\n");
			                   	}else{
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
			         	if(whetherdelay.equals("0")==true)
			         	{
			         		tjoin.append("\n"+getString(R.string.decide_deadlinetime)+"\n"+deadlinetime);
			         	}
			         	
			        	tjoin.append("\n"+getString(R.string.youare_organizer));
			        	 if(whetherdelay.equals("1")==true && k==0){
			        		   	String url_voting ="http://120.126.16.38/voting_organizer.php";
			        				HttpPost httpRequest_voting = new HttpPost(url_voting);
			        				List <NameValuePair> params_voting = new ArrayList <NameValuePair>();
			        				params_voting.add(new BasicNameValuePair("uacno", uacno));
			        				params_voting.add(new BasicNameValuePair("uaccount", id));
			        		       try 
			        		        {  
			        		          httpRequest_voting.setEntity(new UrlEncodedFormEntity(params_voting, HTTP.UTF_8));
			        		          HttpResponse httpResponse_voting = new DefaultHttpClient().execute(httpRequest_voting); 
			        		           
			        		          if(httpResponse_voting.getStatusLine().getStatusCode() == 200) 
			        		          {        	  
			        		        	  String strResult_voting = EntityUtils.toString(httpResponse_voting.getEntity());
			        		        	  Log.v("log", "sstrResult "+strResult_voting);
			        		        	  timearray=strResult_voting.split("~");
			        		        	 for(int i = 0 ; i < timearray.length ; i ++)
			        		        	 {  
			        		        		 st="";
			        		        		 content=timearray[i].split("&");
			        		        		 Log.v("log","content:"+content);
			        		     		    for(int j=1;j<content.length;j++)
			        		        		 {
			        		     		    	if(j==1)
			        		     		    	{
			        		     		    		String[] tt=content[j].split(":");
			        		     		    		st=st+" "+tt[0]+":"+tt[1]; 
			        		     		    	}else if(j==2)
			        		      		    	{
			        		      		    		st=st+" 共"+content[j]+"票";
			        		      		    	}else{
			        		      		    			st=st+" "+content[j];  
			        		      		    		}
			        		        		 }
			        		        		 t.add(st);       		 
			        		        	 } 	 
			        		          }
			        		        }      
			        		         	 catch (Exception e) 
			        		            {  
			        		              tjoin.setText(e.getMessage().toString());
			        		              e.printStackTrace();  
			        		            }  
			        			   //=====================================================================
			        		       LinearLayout ll =(LinearLayout)findViewById(R.id.ll);
			        		         ttt = new TextView(Myjoinac_organizer.this); 
			        		         ttt.setText(getString(R.string.vote_decide_time));
			        		         ttt.setPadding(20, 0, 0, 0);
			        		         ttt.setTextColor(Color.BLACK);
			        		         ttt.setTextSize(18);
			        		         TextPaint tt = ttt.getPaint(); 
			        		 		 tt.setFakeBoldText(true);
			        		         ll.addView(ttt);       
			        		         group=new RadioGroup(Myjoinac_organizer.this);        
			        		         final RadioButton ch[]=new RadioButton[t.size()];     
			        		        for(  int i = 0; i < t.size(); i++) {
			        		     	    ch[i]=new RadioButton(getApplicationContext());
			        		            ch[i].setText(t.get(i));
			        		            Log.v("log", "t "+t.get(i));
			        		            ch[i].setTextColor(Color.BLACK);
			        		            group.addView(ch[i]);          
			        		        } 
			        		        ll.addView(group);
			        		            group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			        						@Override
			        						public void onCheckedChanged(RadioGroup group, int checkedId) {
			        							// TODO Auto-generated method stub			         
			        					      for(int i=0;i<t.size();i++)
			        					      {
			        					    	  	if(checkedId==ch[i].getId())
			        					    	  	{
			        					    	  			str=" "+ch[i].getText().toString();		    	  			    	  			
			        					    	  	}			    	  		
			        					      }	
			        					     sttr=str.split(" ");
			        					     basetime=sttr[2]+" "+sttr[3];
			        					     Log.v("log", basetime);
			        						}						
			        					});							           			
			        			         btntime =new Button(Myjoinac_organizer.this);
			        			         btntime.setText(getString(R.string.ok));
			        			         btntime.setTextColor(Color.WHITE);
			        			         btntime.setTextSize(15);
			        			         btntime.setBackgroundResource(R.layout.button_border2);
			        			         LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);  
			        			         lp.setMargins(0,0, 0, 40);  
			        			         btntime.setLayoutParams(lp);  
			        			         ll.addView(btntime);
			        		                                   
			        		      btntime.setOnClickListener(new Button.OnClickListener(){
			        				@Override
			        				public void onClick(View arg0) {
			        					// TODO Auto-generated method stub							
			        			        Log.v("log","basetime:"+basetime);
			        			        deadlinetimetemp=0;
			        		    		deadlinetimetemp++;
			        		    		if(deadlinetimetemp==1){
			        			        if(basetime.equals("")==true)
			        			        {
			        			        	Toast.makeText(getApplicationContext(),getString(R.string.select_time_notyet),Toast.LENGTH_LONG).show();
			        			        }else{
			        					Calendar c = Calendar.getInstance();// 直接創建一個DatePickerDialog對話框實例，並將它顯示出來
			        			    	Dialog dateDialog = new DatePickerDialog(Myjoinac_organizer.this,
			        			    	// 绑定監聽器
			        			    	new DatePickerDialog.OnDateSetListener() {
			        			    	@Override
			        			    	public void onDateSet(DatePicker dp, int year,
			        			    	int month, int dayOfMonth) {	
			        			    		deadlinetimetemp++;
			        			    		if(deadlinetimetemp==2){	    	
			        			        Calendar time = Calendar.getInstance();
			        			    	String mon=Integer.toString(month + 1);
			        					String da=Integer.toString(dayOfMonth);
			        					if(month + 1<10)
			        					{
			        						mon="0"+mon;
			        					}
			        					if(dayOfMonth<10)
			        					{
			        						da="0"+da;
			        					}	    
			        					sd=year + "-" + mon + "-"+ da + " ";		    	
			        			    	Dialog timeDialog = new TimePickerDialog(
			        			    			Myjoinac_organizer.this,			
			        			    	// 绑定監聽器
			        			    	new TimePickerDialog.OnTimeSetListener() {
			        			    				@Override
			        			    				public void onTimeSet(
			        			    						TimePicker tp,
			        			    						int hourOfDay, int minute) {
			        			    					deadlinetimetemp++;
			        			    					if(deadlinetimetemp==3 || deadlinetimetemp==4){
			        			    					String h=Integer.toString(hourOfDay);
			        			    					String m=Integer.toString(minute);
			        			    					if(minute<10)
			        			    					{
			        			    						m="0"+m;
			        			    					}
			        			    					if(hourOfDay<10)
			        			    					{
			        			    						h="0"+h;
			        			    					}
			        			    					sd=sd+h + ":"+ m+":00";
			        			    					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			        			    			    	Date date=new Date();
			        			    			    	String nowdate=sdf.format(date);							
			        									try {
			        										dts = sdf.parse(sd);
			        									} catch (ParseException e) {
			        										// TODO Auto-generated catch block
			        										e.printStackTrace();
			        									}
			        										Long ut1=dts.getTime();
			        										Long ut2=date.getTime();
			        										Long timeP=ut1-ut2;//毫秒差
			        							//==========================================			
			        											try {
			        												basetime=basetime+":00";
			        												dts_st = sdf.parse(basetime);
			        											} catch (ParseException e) {
			        												// TODO Auto-generated catch block
			        												e.printStackTrace();
			        											}
			        											Long ut3=dts_st.getTime();
			        											Long timed=ut3-ut1;//毫秒差									
			        										 
			        										if(timeP<0)
			        										{
			        											Toast.makeText(getApplicationContext(),getString(R.string.delay_nowtime),Toast.LENGTH_LONG).show();
			        										}
			        										else if(timed<0)
			        										{
			        											Toast.makeText(getApplicationContext(),getString(R.string.delay_deadlinetime),Toast.LENGTH_LONG).show();
			        										}
			        										
			        							else{
			        			    					group.setVisibility(View.GONE);
			        			    			        ttt.setVisibility(View.GONE);
			        			    			        btntime.setVisibility(View.GONE);
			        			    					String url ="http://120.126.16.38/inserttime.php";
			        			    			 		HttpPost httpRequest = new HttpPost(url);
			        			    			 		List <NameValuePair> params = new ArrayList <NameValuePair>();
			        			    					Log.v("log", basetime);
			        			    					params.add(new BasicNameValuePair("uacno", uacno));
			        			    					params.add(new BasicNameValuePair("time",  basetime));
			        			    					params.add(new BasicNameValuePair("deadlinetime", sd));
			        			    					Log.v("log", "bb "+basetime); 
			        			    			        try 
			        			    			         {  
			        			    			           httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			        			    			           HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
			        			    			           
			        			    			           if(httpResponse.getStatusLine().getStatusCode() == 200) 
			        			    			           {        	  
			        			    			         	  String strResult = EntityUtils.toString(httpResponse.getEntity()); 
			        			    			         	  Toast.makeText(Myjoinac_organizer.this, getString(R.string.time_select_organizer), Toast.LENGTH_SHORT).show();
			        			    			         	  finish();
			        			    			           }
			        			    			         }      
			        			    			          	 catch (Exception e) 
			        			    			             {  
			        			    			               tjoin.setText(e.getMessage().toString());
			        			    			               e.printStackTrace();  
			        			    			             }  
			        									}//else	
			        										deadlinetimetemp=0;
			        			    					}//deadlinetimetemp=3
			        			    				}
			        			    	}
			        			    			// 設置初始時間
			        			    			, time.get(Calendar.HOUR_OF_DAY), time
			        			    			.get(Calendar.MINUTE)
			        			    			// true表示采用24小時制
			        			    			, true);
			        			    	timeDialog.setTitle(getString(R.string.please_decide_time));
			        			    	timeDialog.show();
			        			    		}	//deadlinetimetemp=2    			
			        						}
			        			    	}
			        			    	// 設置初始日期
			        			    		, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
			        			    		.get(Calendar.DAY_OF_MONTH));
			        			    		dateDialog.setTitle(getString(R.string.deadlinetime));
			        			    		dateDialog.show();
			        			        	}//deadlinetemp=1
			        			        }
			        				}    	  
			        		      });       
			        		   }//whether
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}             	 
					myDialog.dismiss();		
				}
			}.execute();
		/*HttpPost httpRequest = new HttpPost(uriAPI);
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
         	  whetherdelay=jsonObject.getString("whetherdelay");        	  
         	  property=jsonObject.getString("property");
         	  mine=jsonObject.getString("mine");
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
           	if(time.equals("0000-00-00 00:00:00")==true)
        	  {
       		  time=getString(R.string.voting);	
       		  k=0;
       	    }else{k=1;}           
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
                 	 if(strResult_process.equals("null")==true)
                   	{              			
               				tjoin.append(getString(R.string.undifine)+"\n");
                   	}else{
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
         	if(whetherdelay.equals("0")==true)
         	{
         		tjoin.append("\n"+getString(R.string.decide_deadlinetime)+"\n"+deadlinetime);
         	}
         	
        	tjoin.append("\n"+getString(R.string.youare_organizer));	
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
        }       */   
//================================================================================編輯		
        btnedit.setOnClickListener(new Button.OnClickListener(){     	
            @Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Bundle bundle1 = new Bundle();
	    		bundle1.putString("uacno", uacno);
	    		bundle1.putString("id", id);
	    		
				intent.putExtras(bundle1);
				startActivity(intent);	
				finish();
			}       	
        });       
//=========================================================================================chekbox
  
}
}