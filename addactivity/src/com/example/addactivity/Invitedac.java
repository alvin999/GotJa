package com.example.addactivity;

import java.util.ArrayList;
import java.util.List;

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

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
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
	Button btnvote_again;
	Button btnreject;
	TextView ttt;
	TextView tdeadlinetime;
	Intent intent;
	Intent intent_restart;
	Intent intentvote;
	String uriAPI = "http://120.126.16.38/invitedac.php";
	String uacno,id,zero="0000-00-00 00:00:00",organizer,invitedf="",join="",reject="";
	String time_process,schedulename,it,whetherdelay,vote="1",minevotetime=null,votetemp;
	String name,des,place,selecttime,time,deadlinetime,moviename,showname,property,uutemp,utemp=null,whethervote;
	int p,ctemp;
	boolean tf=true;
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
		btnjoin =(Button)findViewById(R.id.btnjoin);
		btnvote =(Button)findViewById(R.id.btnvote);
		btnvote_again =(Button)findViewById(R.id.btnvote_again);
		btnreject =(Button)findViewById(R.id.btnreject);
		
		TextPaint tp = tinvitedac.getPaint(); 
		tp.setFakeBoldText(true);
		
		intent = new Intent(this,Myactivity.class);
		intent_restart = new Intent(this,Invitedac.class);
		intentvote = new Intent(this,Voting.class);
		Bundle bundle =getIntent().getExtras();
	    uacno=bundle.getString("uacno");	    
		id=bundle.getString("id"); //使用者,不是主揪
		uutemp=bundle.getString("utemp");
		 
       btnvote_again.setVisibility(View.GONE);
       btnvote.setVisibility(View.GONE);
       ttt = new TextView(this);
       if(uutemp.equals("3")==true)
       {
    	   btnreject.setVisibility(View.GONE);
       }
    		
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
				Bundle bundle2 = new Bundle();				
				 bundle2.putString("id", id);
				 bundle2.putString("uacno",uacno);
				 bundle2.putString("utemp",uutemp);
				 
				 intentvote.putExtras(bundle2);
				 startActivity(intentvote);
				 finish();
			}			
		});
		
		btnvote_again.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
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
	        	    invitedf=invitedf+" "+jsonObject_f.getString("fname");
                   if(utemp.equals("1")==true)
                   {
                   	join=join+" "+jsonObject_f.getString("fname");
                   }
                   if(utemp.equals("3")==true)
                   {
                   	reject=reject+" "+jsonObject_f.getString("fname");
                   }
    
	        	  }else{
	        		  organizer=jsonObject_f.getString("fname");
	        	  }    	        	  
        	 }        	 
           }
         }      
          	 catch (Exception e) 
             {  
          		tinvitedac.setText(e.getMessage().toString());
               e.printStackTrace();  
             } 
//========================================================================================		
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
         	  whethervote=jsonObject.getString("whethervote");
         	  whetherdelay=jsonObject.getString("whetherdelay");
         	 votetemp=jsonObject.getString("votetemp");
         	String outtime=jsonObject.getString("outtime");
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
         	 tinvitedac.setText(getString(R.string.organizer)+":"+organizer+"\n"+
         			 		    getString(R.string.activity_name)+":"+name+"\n");
         	 if(p==2)
         	  {
         		 moviename=jsonObject.getString("moviename");
         		tinvitedac.append(getString(R.string.movie_name)+":"+moviename+"\n");
         	  }
         	 if(p==4)
        	  {
         		showname=jsonObject.getString("showname");
         		tinvitedac.append(getString(R.string.show_name)+":"+showname+"\n");
        	  }
         	if(p==5)
         	{   tinvitedac.append(getString(R.string.process_name)+":\n");
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
                 	  JSONArray result_process = new JSONArray(strResult_process);
                  		 
                 	 for (int i = 0; i < result_process.length(); i++) {		        		 
   		        	  JSONObject jsonObject_process = result_process.getJSONObject(i);
   		        	  time_process=jsonObject_process.getString("time");
   		        	  schedulename=jsonObject_process.getString("schedulename");
   		        	  tinvitedac.append(time_process+" "+schedulename+"\n");  		        	  
                 	 }
                   }
                 }
                  	 catch (Exception e) 
                     {  
                     tinvitedac.setText(e.getMessage().toString());
                       e.printStackTrace();  
                     }  
            	
         	} //if(p==5)         	        	
         	 tinvitedac.append(getString(R.string.des)+":"+des+"\n"+
         			 		   getString(R.string.place)+":"+place+"\n"+
         			 		   getString(R.string.friend_invited)+":"+invitedf+"\n "+
         			 		   getString(R.string.join)+":"+join+"\n "+
         			 		   getString(R.string.reject)+":"+reject+"\n"+
         			 		   getString(R.string.time_result)+": "+time+"\n");
         	 if(whethervote.equals("0")==true && outtime.equals("0")==true)
         	 {
         		 tinvitedac.append(getString(R.string.vote_deadlinetime1)+" "+deadlinetime+" "+getString(R.string.vote_deadlinetime3));       				
         	 }
         	 if(whethervote.equals("1")==true )
         	 {
         		if(votetemp.equals("1")==true && whetherdelay.equals("0")==true)
             	{
             		
             		btnvote.setVisibility(View.GONE);
             		btnvote_again.setVisibility(View.VISIBLE);
             	}
      //=============================================================================   		
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
        }   
//==============================================================================如果需要投票+位超過時間        
        if(whethervote.equals("1")==true && whetherdelay.equals("0")==true && votetemp.equals("2")==true)
        { 
        	btnvote.setVisibility(View.VISIBLE);
        }  
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