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
import android.graphics.Color;
import android.text.TextPaint;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class Voting extends Activity {
	TextView tname;
	TextView tdeadlinetime;
	Button btnvote;
	String uacno,id,minevotetime=null,vote="1",deadlinetime,uutemp,st;
	String[] timearray,content;
	LinearLayout ll;
	RelativeLayout rr;
	Intent intent;
	
	ArrayList<String> tarray = new ArrayList<String>();
	ArrayList<String> t = new ArrayList<String>();
	ArrayList<String> tarray_name = new ArrayList<String>();
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voting);
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
	        
		ll=(LinearLayout)findViewById(R.id.ll);
		tname =(TextView)findViewById(R.id.tname);
		btnvote =(Button)findViewById(R.id.btnvote);
		intent = new Intent(this,Invitedac.class);
		
		Bundle bundle =getIntent().getExtras();
	    uacno=bundle.getString("uacno");	    
		id=bundle.getString("id");
		uutemp=bundle.getString("utemp");
	
		TextPaint tn = tname.getPaint(); 
		tn.setFakeBoldText(true);
	//=================================================	
	//	 ttt.append(getString(R.string.vote_result));
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
	         	 // tjoin.append(strResult_voting);
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
	     		    	}
	     		    	else if(j==2)
	      		    	{
	      		    		st=st+" 共"+content[j]+"票";
	      		    	}else{
	      		    			st=st+" "+content[j];  
	      		    		}
	        		 }
	     		   Log.v("log","ast:"+st);
	        		 t.add(st);
	        		 
	        	 }	   	 
	           }
	         }      
	          	 catch (Exception e) 
	             {  
	          		tname.setText(e.getMessage().toString());
	          		 
	               e.printStackTrace();  
	             }  	 
//==============================================================================================
	        tarray.clear();
        	tarray_name.clear();
        	String url_voting1 ="http://120.126.16.38/voting.php";
     		HttpPost httpRequest_voting1 = new HttpPost(url_voting1);
     		List <NameValuePair> params_voting1 = new ArrayList <NameValuePair>();
    		params_voting1.add(new BasicNameValuePair("uacno", uacno));
    		params_voting1.add(new BasicNameValuePair("uaccount", id));
            try 
             {  
               httpRequest_voting1.setEntity(new UrlEncodedFormEntity(params_voting1, HTTP.UTF_8));
               HttpResponse httpResponse_voting1 = new DefaultHttpClient().execute(httpRequest_voting1);               
               if(httpResponse_voting1.getStatusLine().getStatusCode() == 200) 
               {        	  
             	  String strResult_voting1 = EntityUtils.toString(httpResponse_voting1.getEntity()); 
             	  JSONArray result_voting1 = new JSONArray(strResult_voting1);             		 
             	 for (int i = 0; i < result_voting1.length(); i++) {		        		 
		        	  JSONObject jsonObject_voting1= result_voting1.getJSONObject(i);
		        	  tarray.add(jsonObject_voting1.getString("time"));
		        	  deadlinetime=jsonObject_voting1.getString("deadlinetime");
		        	  Log.v("log",deadlinetime);
		        	  Log.v("log",tarray.get(i));
             	 }
               }
   //==============================================================================
     		btnvote.setOnClickListener(new Button.OnClickListener(){
       			@Override
       			public void onClick(View v) {
       				// TODO Auto-generated method stub    				
       				for(int i=0;i<tarray_name.size();i++)
       	            {
       	            		minevotetime=minevotetime+"~"+tarray_name.get(i);
       	            }       								
       				String url_voting ="http://120.126.16.38/voting.php";
       	     		HttpPost httpRequest_vote = new HttpPost(url_voting);
       	     		List <NameValuePair> params_vote = new ArrayList <NameValuePair>();
       	    		params_vote.add(new BasicNameValuePair("uacno", uacno));
       	    		params_vote.add(new BasicNameValuePair("uaccount", id));
       	    		params_vote.add(new BasicNameValuePair("vote", vote));
       	    		params_vote.add(new BasicNameValuePair("minevotetime", minevotetime));
       	    		//Log.v("log",minevotetime);       	    		 
       	    		try 
                        {  
                          httpRequest_vote.setEntity(new UrlEncodedFormEntity(params_vote, HTTP.UTF_8));
                          HttpResponse httpResponse_vote = new DefaultHttpClient().execute(httpRequest_vote); 
                          
                          if(httpResponse_vote.getStatusLine().getStatusCode() == 200) 
                          {        	  
                        	  String strResult_vote = EntityUtils.toString(httpResponse_vote.getEntity()); 
                        	 Toast.makeText(getApplicationContext(),"投票成功",Toast.LENGTH_LONG).show();
                        	  
                          }
                        }
                         	 catch (Exception e) 
                            {  
                            tname.setText(e.getMessage().toString());
                              e.printStackTrace();  
                            }  
       				minevotetime=null;			
       				
       			 Bundle bundle1 = new Bundle();				
   				 bundle1.putString("id", id);
   				 bundle1.putString("uacno",uacno);
   				 bundle1.putString("utemp",uutemp);
   				 
   				 intent.putExtras(bundle1);
   				 startActivity(intent); 
   				 finish();
       			}			
       		});
   //===================================================================================================                     
             tdeadlinetime = new TextView(this);
             tdeadlinetime.setText(getString(R.string.vote_deadlinetime1)+" "+deadlinetime+" "+getString(R.string.vote_deadlinetime3));              
             tdeadlinetime.setPadding(20, 0, 0, 0);
             tdeadlinetime.setTextColor(Color.BLACK);
             tdeadlinetime.setTextSize(15);
             TextPaint td = tdeadlinetime.getPaint(); 
     		 td.setFakeBoldText(true);
             ll.addView(tdeadlinetime);
               
             final CheckBox ch[]=new CheckBox[tarray.size()];             
               for( int i = 0; i < tarray.size(); i++) {
            	  // it=Integer.toString(i);
            	   ch[i]=new CheckBox(getApplicationContext());
                   ch[i].setId(i);
                   ch[i].setTextColor(Color.BLACK);
                   ch[i].setText(t.get(i));
                   
                   final int c=i;
                   final String cp=Integer.toString(c);
                   ch[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {				
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						// TODO Auto900000-generated method stub
						if(isChecked)
						{
							tarray_name.add(ch[c].getText().toString());			
							String a=Integer.toString(tarray_name.indexOf(ch[c].getText().toString()));	
							
						}
						else
						{   													
							tarray_name.remove(tarray_name.indexOf(ch[c].getText().toString()));
						}						
					}
				});                   
                    ll.addView(ch[i]);                    
             }
             }
              	 catch (Exception e) 
                 {  
                 tname.setText(e.getMessage().toString());
                   e.printStackTrace();  
                 } 		             	
	}
}
