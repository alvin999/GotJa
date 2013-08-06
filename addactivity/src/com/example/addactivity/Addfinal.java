package com.example.addactivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList; 
import java.util.Date;
import java.util.List; 
  
import org.apache.http.HttpEntity;
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
 
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.StrictMode;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class Addfinal extends Activity {
	 
	Button btninvite;
	Button btnemail;
	Button btnback;
	Button btnp1;
	TextView tresult;

	String id,uname,time;
	String name,des,place,friend,whethervote,uacno="0";
	String syear,smonth,sday,timearray,sd,ccount,property,moviename,showname,processarray,ppcount;
	int pproperty,pcount,tcount;
	
	String[] array;
	ArrayList<String> parray = new ArrayList<String>();
	ArrayList<String> tarray = new ArrayList<String>();
    Intent intent;
    Intent intentemail;
    Intent intentac;
	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addfinal);		
		 
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
 
        btninvite= (Button)findViewById(R.id.btninvite);
        btnemail= (Button)findViewById(R.id.btnemail);
        btnback= (Button)findViewById(R.id.btnback);
        btnp1= (Button)findViewById(R.id.btnp1);
		tresult= (TextView)findViewById(R.id.tresult);
		intent = new Intent(this,Addfriend.class);
		intentemail = new Intent(this,InviteContacts.class);
		
		 Bundle bundle =getIntent().getExtras();
		 id=bundle.getString("id");
		 name=bundle.getString("name");
		 des=bundle.getString("des");
		 place=bundle.getString("place");
		 friend=bundle.getString("friend");	
		 tarray=bundle.getStringArrayList("tarray");
		 sd=bundle.getString("sd");		
		 property=bundle.getString("property");
		 pproperty=Integer.parseInt(property);
		 
		switch(pproperty){
			case 1:					
				intentac = new Intent(this,Addeat.class);
				break;
			case 2:					
				intentac = new Intent(this,Addmovie.class);
				break;
			case 3:					
				intentac = new Intent(this,Addshopping.class);
				break;
			case 4:					
				intentac = new Intent(this,Addshow.class);
				break;
			case 5:					
				intentac = new Intent(this,Addoutdoor.class);
				break;
			case 6:					
				intentac = new Intent(this,Addother.class);
				break;			
		}
		
		 btnp1.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub			
				Bundle bundle = new Bundle();				
				 bundle.putString("id", id);
			 
				 intentac.putExtras(bundle);
				 startActivity(intentac);
				 finish();
			}			 
		 });

		 if(pproperty==1 || pproperty==3 || pproperty==6){		 
		  tresult.setText(getString(R.string.activity_name)+":"+name+"\n");
		 }
		 
		 if(pproperty==2)
		 {
			 moviename=bundle.getString("moviename");
			tresult.setText(getString(R.string.activity_name)+":"+name+"\n"+
							getString(R.string.movie_name)+":"+moviename+"\n");
		 }
		 
		 if(pproperty==4)
		 {
			 showname=bundle.getString("showname");
			 tresult.setText(getString(R.string.activity_name)+":"+name+"\n"+
					 		 getString(R.string.show_name)+":"+moviename+"\n");
		 }
		 
		 if(pproperty==5)
		 {						
			tresult.setText(getString(R.string.activity_name)+":"+name+"\n"+
							getString(R.string.process_name)+":\n");
			 parray=bundle.getStringArrayList("parray");
			 pcount=parray.size();
			 for(int i=0;i<parray.size();i++){
				tresult.append(parray.get(i)+"\n");
				 processarray=processarray+"~"+parray.get(i);
			 }
		 }
		 tresult.append(getString(R.string.des)+":"+des+"\n"+
						getString(R.string.place)+":"+place+"\n"+
						getString(R.string.time_result)+":\n");
		tcount=tarray.size();
		for(int i=0;i<tarray.size();i++)
		{
			tresult.append(" "+tarray.get(i)+"\n");
			timearray=timearray+"~"+tarray.get(i);
		}
		tresult.append(getString(R.string.deadlinetime_result)+":\n"+sd);				
	//=========================================================	        
        btninvite.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub				
				String uriAPI = "http://120.126.16.38/addactivity_other.php";
				HttpPost httpRequest = new HttpPost(uriAPI); 
				
				ccount=Integer.toString(tcount);
				ppcount=Integer.toString(pcount);
				List <NameValuePair> params = new ArrayList <NameValuePair>();
				params.add(new BasicNameValuePair("id", id));
		        params.add(new BasicNameValuePair("name", name));
		        params.add(new BasicNameValuePair("des", des)); 
		        params.add(new BasicNameValuePair("place", place));		      
				params.add(new BasicNameValuePair("timearray", timearray));				
		        params.add(new BasicNameValuePair("count", ccount));
		        params.add(new BasicNameValuePair("sd", sd));
		        params.add(new BasicNameValuePair("property", property));
		        params.add(new BasicNameValuePair("uacno", uacno));
		        
		        if(pproperty==2)
		        {
		        	params.add(new BasicNameValuePair("moviename", moviename));	
		        }
		        if(pproperty==4)
				 {
		        	params.add(new BasicNameValuePair("showname", showname));
				 }
		        if(pproperty==5)
				 {
		        	params.add(new BasicNameValuePair("ppcount", ppcount));
		        	params.add(new BasicNameValuePair("process", processarray));
				 }		        
		        try 
		        {  

		          httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		          HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
		          if(httpResponse.getStatusLine().getStatusCode() == 200) 
		          { 
		        	  
		            String strResult = EntityUtils.toString(httpResponse.getEntity()); 
		            JSONArray result = new JSONArray(strResult);		        	
		         	  JSONObject jsonObject = result.getJSONObject(0);      
		         	 uacno=jsonObject.getString("uacno");
		         	Log.v("log",uacno);		         	 		         	
		            Toast.makeText(getApplicationContext(),getString(R.string.invite_friend),Toast.LENGTH_LONG).show();
		          } 
		          else 
		          { 
		        	  tresult.setText("Error Response: "+httpResponse.getStatusLine().toString()); 
		          } 
		        }   
		        catch (ClientProtocolException e) 
		        {  
		        	tresult.setText(e.getMessage().toString()); 
		          e.printStackTrace(); 
		        } 
		        catch (IOException e) 
		        {  
		        	tresult.setText(e.getMessage().toString()); 
		          e.printStackTrace(); 
		        } 
		        catch (Exception e) 
		        {  
		        	tresult.setText(e.getMessage().toString()); 
		          e.printStackTrace();  
		        } 		
		        
		        Bundle bundle1 = new Bundle();
				bundle1.putString("uacno", uacno);
				intent.putExtras(bundle1);
		       startActivity(intent);
		    }       	
        });
     btnemail.setOnClickListener(new Button.OnClickListener(){
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String uriAPI = "http://120.126.16.38/addactivity_other.php";
			HttpPost httpRequest = new HttpPost(uriAPI); 
			
			ccount=Integer.toString(tcount);
			ppcount=Integer.toString(pcount);
			List <NameValuePair> params = new ArrayList <NameValuePair>();
			params.add(new BasicNameValuePair("id", id));
	        params.add(new BasicNameValuePair("name", name));
	        params.add(new BasicNameValuePair("des", des)); 
	        params.add(new BasicNameValuePair("place", place));		      
			params.add(new BasicNameValuePair("timearray", timearray));				
	        params.add(new BasicNameValuePair("count", ccount));
	        params.add(new BasicNameValuePair("sd", sd));
	        params.add(new BasicNameValuePair("property", property));	      
	        params.add(new BasicNameValuePair("uacno", uacno));
	        
	        if(pproperty==2)
	        {
	        	params.add(new BasicNameValuePair("moviename", moviename));	
	        }
	        if(pproperty==4)
			 {
	        	params.add(new BasicNameValuePair("showname", showname));
			 }
	        if(pproperty==5)
			 {
	        	params.add(new BasicNameValuePair("ppcount", ppcount));
	        	params.add(new BasicNameValuePair("process", processarray));
			 }	        
	        try 
	        {  
	          httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
	          HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
	          if(httpResponse.getStatusLine().getStatusCode() == 200) 
	          { 	        	  
	            String strResult = EntityUtils.toString(httpResponse.getEntity()); 
	            JSONArray result = new JSONArray(strResult);		        	
	         	 JSONObject jsonObject = result.getJSONObject(0);      
	         	 uacno=jsonObject.getString("uacno"); 
	         	 name=jsonObject.getString("name");
	         	 des=jsonObject.getString("destination");
	         	 uname=jsonObject.getString("uname");
	         	 time=jsonObject.getString("time");
	            Toast.makeText(getApplicationContext(),getString(R.string.invite_friend),Toast.LENGTH_LONG).show();
	          } 
	          else 
	          { 
	        	  tresult.setText("Error Response: "+httpResponse.getStatusLine().toString()); 
	          } 
	        }   
	        catch (ClientProtocolException e) 
	        {  
	        	tresult.setText(e.getMessage().toString()); 
	          e.printStackTrace(); 
	        } 
	        catch (IOException e) 
	        {  
	        	tresult.setText(e.getMessage().toString()); 
	          e.printStackTrace(); 
	        } 
	        catch (Exception e) 
	        {  
	        	tresult.setText(e.getMessage().toString()); 
	          e.printStackTrace();  
	        } 
	        if(time.equals("0000-00-00 00:00:00")==true)
	        {
	        	time="未定中";
	        }
	 
	        Bundle bundle1 = new Bundle();
	        bundle1.putString("uacno", uacno);
			bundle1.putString("uname", uname);
			bundle1.putString("name", name);
			bundle1.putString("time", time);
			bundle1.putString("des", des);
			
			intentemail.putExtras(bundle1);
			startActivity(intentemail);	
		}    	 
     });
     btnback.setOnClickListener(new Button.OnClickListener(){
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();			
		}   	 
     });        
	}
}