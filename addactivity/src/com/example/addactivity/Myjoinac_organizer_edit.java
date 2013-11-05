package com.example.addactivity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;


@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class Myjoinac_organizer_edit extends Activity{
	Date dts,dts_st,dts_sd;
	LinearLayout ll;
	Button btneditcomplete;
	Button btntime;
	Button btntime_now;
	Button btndeadlinetime;
	Button btnaddfriend;
	Button btnaddemailfriend;
	EditText editname;
	EditText editmovie;
	EditText editshow;
	EditText editdes;
	EditText editplace;
	TextView tmovie;
	TextView tshow;
	TextView taddtime;
	TextView tdeadline;
	TextView tdeadlinetime;
	String uriAPI = "http://120.126.16.38/myjoinac.php";
	String name,des,place,selecttime,whethervote,time,deadlinetime,moviename,showname,property,mine,whetherdelay;
	String uacno,id,s2,sd,st="0000-00-00 00:00:00",dts_time,dts_deadlinetime; 
	Intent intent; 
	Intent intentaddfriend;
	Intent intentaddemailfriend;
    int p,m,k,vote=1,daytemp=0,daynowtemp=0,deadlinetimetemp=0;
    String  time_process,schedulename,invitedf="",organizer,join="",reject="";
    ArrayList<String> t = new ArrayList<String>();
    ArrayList<String> parray = new ArrayList<String>();
    String[] timearray;
    String[] content;
//===============================================================	 
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myjoinac_organizer_edit);
		
		ll = (LinearLayout) findViewById(R.id.ll);
		btntime = (Button) findViewById(R.id.btntime);
		btntime_now = (Button) findViewById(R.id.btntime_now);
		btndeadlinetime = (Button) findViewById(R.id.btndeadlinetime);
		btneditcomplete = (Button) findViewById(R.id.btneditcomplete);
		btnaddfriend = (Button) findViewById(R.id.btnaddfriend);
		btnaddemailfriend = (Button) findViewById(R.id.btnaddemailfriend);
		
		editname = (EditText) findViewById(R.id.editname);
		editmovie = (EditText) findViewById(R.id.editmovie);
		editshow = (EditText) findViewById(R.id.editshow);
		editdes = (EditText) findViewById(R.id.editdes);
		editplace = (EditText) findViewById(R.id.editplace);
		tmovie = (TextView) findViewById(R.id.tmovie);
		tshow = (TextView) findViewById(R.id.tshow);
		taddtime = (TextView) findViewById(R.id.taddtime);	
		tdeadline = (TextView) findViewById(R.id.tdeadline);
		tdeadlinetime = (TextView) findViewById(R.id.tdeadlinetime);
				
		tmovie.setVisibility(View.GONE);
		editmovie.setVisibility(View.GONE);		
		tshow.setVisibility(View.GONE);
		editshow.setVisibility(View.GONE);
		btntime_now.setVisibility(View.GONE);
		
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
		
        intent = new Intent(this,Myjoinac_organizer.class); 
        intentaddfriend = new Intent(this,Addfriend.class);
        intentaddemailfriend = new Intent(this,Addfriend.class);
		Bundle bundle =getIntent().getExtras();
		uacno=bundle.getString("uacno");
		id=bundle.getString("id");				
//============================================================================
			 btnaddfriend.setOnClickListener(new Button.OnClickListener(){
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Bundle bundle2 = new Bundle();
					bundle2.putString("uacno", uacno);
					intentaddfriend.putExtras(bundle2);
			        startActivity(intentaddfriend);					
				}				 
			 });
			 btnaddemailfriend.setOnClickListener(new Button.OnClickListener(){
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Bundle bundle3 = new Bundle();
						bundle3.putString("uacno", uacno);
						intentaddemailfriend.putExtras(bundle3);
				        startActivity(intentaddemailfriend);					
					}				 
				 });
			 btntime_now.setOnClickListener(new Button.OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					daynowtemp=0;
		    		daynowtemp++;
		    		if(daynowtemp==1){
					Calendar c = Calendar.getInstance();// 直接創建一個DatePickerDialog對話框實例，並將它顯示出來
			    	Dialog dateDialog = new DatePickerDialog(Myjoinac_organizer_edit.this,
			    	// 绑定監聽器
			    	new DatePickerDialog.OnDateSetListener() {
			    	@Override
			    	public void onDateSet(DatePicker dp, int year,
			    	int month, int dayOfMonth) {
			    		daynowtemp++;
			    		if(daynowtemp==2){
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
			    	st=year + "-" + mon + "-"+ da+" ";	    	
			    	Log.v("log",st);
			    	Dialog timeDialog = new TimePickerDialog(Myjoinac_organizer_edit.this,			
			    	// 绑定監聽器
			    	new TimePickerDialog.OnTimeSetListener() {
			    				@Override
			    				public void onTimeSet(TimePicker tp,int hourOfDay, int minute) {
			    					daynowtemp++;
			    					if(daynowtemp==3 || daynowtemp==4){
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
			    					st=st+h + ":"+ m+":00";
			    					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    			    	Date date=new Date();
			    			    	String nowdate=sdf.format(date);									
									try {
										dts = sdf.parse(st);
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									Log.v("log","yyyyy "+dts_time);
										Long ut1=dts.getTime();
										Long ut2=date.getTime();
										Long timeP=ut1-ut2;//毫秒差
										if(timeP<0)
										{
											Toast.makeText(getApplicationContext(),getString(R.string.delay_nowtime),Toast.LENGTH_LONG).show();
										}else{	
											Toast.makeText(getApplicationContext(),getString(R.string.change_deadlinetime),Toast.LENGTH_LONG).show();
			    					Log.v("log",st);
			    					vote=0;
			    					taddtime.setText(st);	
			    					}    	
										daynowtemp=0;			
				    				}//daynowtemp3	
			    				}
			    	}
			    			// 設置初始時間
			    			, time.get(Calendar.HOUR_OF_DAY), time
			    			.get(Calendar.MINUTE)
			    			// true表示采用24小時制
			    			, true);
			    	timeDialog.setTitle(getString(R.string.please_decide_time));
			    	timeDialog.show();
			    		}//daynowtemp2
			    		}
			    	}
			    	// 設置初始日期
			    		, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
			    		.get(Calendar.DAY_OF_MONTH));
			    		dateDialog.setTitle(getString(R.string.please_decide_date));
			    		dateDialog.show();	
		    		}//daynowtemp					
				}				 
			 });
//============================================================================		
		HttpPost httpRequest = new HttpPost(uriAPI);
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
         	  whethervote=jsonObject.getString("whethervote");
         	  
         	  property=jsonObject.getString("property");
         	  mine=jsonObject.getString("mine");
         	  m=Integer.parseInt(mine);
         	  p=Integer.parseInt(property);
         	  
         	  editname.setText(name);
         	  editdes.setText(des);
         	  editplace.setText(place);
         	  tdeadlinetime.setText(deadlinetime);
         	  
         	  switch(p){
         	 case 1:
         		 //editname.setBackgroundColor(getstyle);
         		 editdes.setBackgroundResource(R.drawable.textarea_eat);
         		 editplace.setBackgroundResource(R.drawable.textarea_eat);
         		 editname.setBackgroundResource(R.drawable.textarea_eat);
         		 btntime.setBackgroundResource(R.drawable.addtime_eat);
         		 btndeadlinetime.setBackgroundResource(R.drawable.addtime_eat);
         		 break;
         	case 2:
        		 editname.setBackgroundResource(R.drawable.textarea_movie);
        		 editdes.setBackgroundResource(R.drawable.textarea_movie);
        		 editplace.setBackgroundResource(R.drawable.textarea_movie);
        		 btntime.setBackgroundResource(R.drawable.addtime_movie);
        		 btndeadlinetime.setBackgroundResource(R.drawable.addtime_movie);
        		 break;
         	case 3:
       		 editname.setBackgroundResource(R.drawable.textarea_shopping);
       		 editdes.setBackgroundResource(R.drawable.textarea_shopping);
       		 editplace.setBackgroundResource(R.drawable.textarea_shopping);
       		 btntime.setBackgroundResource(R.drawable.addtime_shopping);
       		 btndeadlinetime.setBackgroundResource(R.drawable.addtime_shopping);
       		 break;
         	case 4:
       		 editname.setBackgroundResource(R.drawable.textarea_show);
       		 editdes.setBackgroundResource(R.drawable.textarea_show);
       		 editplace.setBackgroundResource(R.drawable.textarea_show);
       		 btntime.setBackgroundResource(R.drawable.addtime_show);
       		 btndeadlinetime.setBackgroundResource(R.drawable.addtime_show);
       		 break;
         	case 5:
       		 editname.setBackgroundResource(R.drawable.textarea_outdoor);
       		 editdes.setBackgroundResource(R.drawable.textarea_outdoor);
       		 editplace.setBackgroundResource(R.drawable.textarea_outdoor);
       		 btntime.setBackgroundResource(R.drawable.addtime_outdoor);
       		 btndeadlinetime.setBackgroundResource(R.drawable.addtime_outdoor);
       		 break;
         	case 6:
       		 editname.setBackgroundResource(R.drawable.textarea_other);
       		 editdes.setBackgroundResource(R.drawable.textarea_other);
       		 editplace.setBackgroundResource(R.drawable.textarea_other);
       		 btntime.setBackgroundResource(R.drawable.addtime);
       		 btndeadlinetime.setBackgroundResource(R.drawable.addtime);
       		 break;
         	  }        	
        	  
         	if(time.equals("0000-00-00 00:00:00")==true)
        	 {
       		  time=getString(R.string.voting);	
       		  btntime.setVisibility(View.GONE);
       		  btntime_now.setVisibility(View.VISIBLE);
       		  k=0;
       	    }else{k=1;}
         	taddtime.setText(time);
         	 if(p==2)  
         	  {
         		moviename=jsonObject.getString("moviename");
         		tmovie.setVisibility(View.VISIBLE);
        		editmovie.setVisibility(View.VISIBLE);
        		editmovie.setText(moviename);
         	  }
         	 if(p==4)
        	  {        		
         		showname=jsonObject.getString("showname");
         		tshow.setVisibility(View.VISIBLE);
        		editshow.setVisibility(View.VISIBLE);
        		editshow.setText(showname);
        	  }  
          }
          else 
          { 
        	  taddtime.setText("Error Response: "+httpResponse.getStatusLine().toString());      	
          } 
        }
        catch (Exception e) 
        {  
        	taddtime.setText(e.getMessage().toString());
          e.printStackTrace();  
        }        
        
        btndeadlinetime.setOnClickListener(new OnClickListener() {
	    	@Override
	    	public void onClick(View source) {
	    		deadlinetimetemp=0;
	    		deadlinetimetemp++;
	    		if(deadlinetimetemp==1){
	    	Calendar c = Calendar.getInstance();// 直接創建一個DatePickerDialog對話框實例，並將它顯示出來
	    	Dialog dateDialog = new DatePickerDialog(Myjoinac_organizer_edit.this,
	    	// 绑定監聽器
	    	new DatePickerDialog.OnDateSetListener() {
	    	@Override
	    	public void onDateSet(DatePicker dp, int year,int month, int dayOfMonth) {
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
	    	Log.v("log",sd);
	    	Dialog timeDialog = new TimePickerDialog(
	    			Myjoinac_organizer_edit.this,			
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
								int count_t=0;								
									try {
										dts_st = sdf.parse(st);
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									Long ut3=dts_st.getTime();
									Long timed=ut3-ut1;//毫秒差
								if(st.equals("0000-00-00 00:00:00")!=true)
								{
									if(timed<0)
									{
										count_t++;
									}	
								}
								if(timeP<0)
								{
									Toast.makeText(getApplicationContext(),getString(R.string.delay_nowtime),Toast.LENGTH_LONG).show();
								}
								else if(count_t!=0)
								{
									Toast.makeText(getApplicationContext(),getString(R.string.delay_deadlinetime),Toast.LENGTH_LONG).show();
								}
								else{	    					
	    					Log.v("log",sd);
	    					tdeadlinetime.setText(sd);	  
	    					} 	    
								deadlinetimetemp=0;
	    					}//deadlinetimetemp=3
	    				}
	    	}
	    			// 設置初始時間
	    			, time.get(Calendar.HOUR_OF_DAY), time
	    			.get(Calendar.MINUTE)
	    			// true表示采用24小時制
	    			, true);
	    	timeDialog.setTitle("請選擇時間");
	    	timeDialog.show();
	    		}//deadlinetimetemp=2
	    		}
	    	}
	    	// 設置初始日期
	    		, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
	    		.get(Calendar.DAY_OF_MONTH));
	    		dateDialog.setTitle("請選擇日期");
	    		dateDialog.show();
	    		}//deadlinetemp=1
	    	}
	    });
        
        if(whethervote.equals("0")==true)
        {
        	btntime.setOnClickListener(new Button.OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub	
					daytemp=0;
		    		daytemp++;
		    		if(daytemp==1){
					Calendar c = Calendar.getInstance();// 直接創建一個DatePickerDialog對話框實例，並將它顯示出來
			    	Dialog dateDialog = new DatePickerDialog(Myjoinac_organizer_edit.this,
			    	// 绑定監聽器
			    	new DatePickerDialog.OnDateSetListener() {
			    	@Override
			    	public void onDateSet(DatePicker dp, int year,
			    	int month, int dayOfMonth) {
			    		daytemp++;
			    		if(daytemp==2){
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
			    	st=year + "-" + mon + "-"+ da+" ";	    	
			    	Log.v("log",st);
			    	Dialog timeDialog = new TimePickerDialog(
			    			Myjoinac_organizer_edit.this,			
			    	// 绑定監聽器
			    	new TimePickerDialog.OnTimeSetListener() {
			    				@Override
			    				public void onTimeSet(
			    						TimePicker tp,
			    						int hourOfDay, int minute) {
			    					daytemp++;
			    					if(daytemp==3 || daytemp==4){
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
			    					st=st+h + ":"+ m+":00";
			    					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    			    	Date date=new Date();
			    			    	String nowdate=sdf.format(date);									
									try {
										dts = sdf.parse(st);
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									Log.v("log","yyyyy "+dts_time);
										Long ut1=dts.getTime();
										Long ut2=date.getTime();
										Long timeP=ut1-ut2;//毫秒差
										int count_t=0;								
										try {
											dts_sd = sdf.parse(tdeadlinetime.getText().toString());
										} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										Long ut3=dts_sd.getTime();
										Long timet=ut1-ut3;//毫秒差
										if(timet<0)
										{
											count_t++;
										}	
										if(timeP<0)
										{
											Toast.makeText(getApplicationContext(),getString(R.string.delay_nowtime),Toast.LENGTH_LONG).show();
										}else if(count_t!=0)
										{
											Toast.makeText(getApplicationContext(),getString(R.string.delay_time),Toast.LENGTH_LONG).show();
										}
										else{		    					
			    					Log.v("log",st);
			    					taddtime.setText(st);	
			    					}    	
										daytemp=0;			
				    				}//daytemp3
			    				}
			    	}
			    			// 設置初始時間
			    			, time.get(Calendar.HOUR_OF_DAY), time
			    			.get(Calendar.MINUTE)
			    			// true表示采用24小時制
			    			, true);
			    	timeDialog.setTitle(getString(R.string.please_decide_time));
			    	timeDialog.show();
			    		}//daytemp2
			    		}
			    	}
			    	// 設置初始日期
			    		, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
			    		.get(Calendar.DAY_OF_MONTH));
			    		dateDialog.setTitle(getString(R.string.please_decide_date));
			    		dateDialog.show();
		    		}//daytemp	  
				}       		
        	});        	      	
        }
        btneditcomplete.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String uri="http://120.126.16.38/edit.php";
				HttpPost httpRequest = new HttpPost(uri);
		    	List <NameValuePair> params = new ArrayList <NameValuePair>();
				params.add(new BasicNameValuePair("uacno", uacno));
				params.add(new BasicNameValuePair("name", editname.getEditableText().toString()));
		        params.add(new BasicNameValuePair("des", editdes.getEditableText().toString())); 
		        params.add(new BasicNameValuePair("place", editplace.getEditableText().toString()));		      
		        params.add(new BasicNameValuePair("time", taddtime.getText().toString()));
		        params.add(new BasicNameValuePair("sd", tdeadlinetime.getText().toString()));
		        params.add(new BasicNameValuePair("property", property));	
		        params.add(new BasicNameValuePair("v", Integer.toString(vote)));
		        Log.v("log","uacno "+uacno);
		        Log.v("log","name "+editname.getEditableText().toString());
		        Log.v("log","des "+editdes.getEditableText().toString());
		        Log.v("log","place "+editplace.getEditableText().toString());
		        Log.v("log","time "+taddtime.getText().toString());
		        Log.v("log","sd "+tdeadlinetime.getText().toString());
		        Log.v("log","property "+property);
		        Log.v("log","v "+Integer.toString(vote));
		          
		        if(property.equals("2")==true) 
		        {
		        	params.add(new BasicNameValuePair("moviename", editmovie.getEditableText().toString()));	
		        }
		        if(property.equals("4")==true)
				 {
		        	params.add(new BasicNameValuePair("showname", editshow.getEditableText().toString()));
				 }
		        try 
		        {  
		          httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		          HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
		          
		          if(httpResponse.getStatusLine().getStatusCode() == 200) 
		          {        	  
		        	  String strResult = EntityUtils.toString(httpResponse.getEntity());
		        	  Log.v("log","seceec");
		          }
		          else 
		          { 
		        	Log.v("log","Error Response: "+httpResponse.getStatusLine().toString());
		          } 
		        }
		        catch (Exception e) 
		        {  
		        	Log.v("log",e.getMessage().toString());
		          e.printStackTrace();  
		        }  								
				Bundle bundle1 = new Bundle();
	    		bundle1.putString("uacno", uacno);
	    		bundle1.putString("id", id);
	    		
				intent.putExtras(bundle1);
				startActivity(intent);	
				finish();
			}        	
        });
	}
}