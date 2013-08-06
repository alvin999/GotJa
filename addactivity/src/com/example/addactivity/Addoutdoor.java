package com.example.addactivity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
 
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
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class Addoutdoor extends FragmentActivity
implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
	 private static final LocationRequest REQUEST = LocationRequest.create()
	         .setInterval(5000)         // 5 seconds
	         .setFastestInterval(16)    // 16ms = 60fps
	         .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	 Date dts;
	 Date dts_deadlinetime_array;
	 ScrollView scroll;
	TextView tname;
	TextView tdes;
	TextView mMessageView;
	TextView tplace;
	TextView ttime;
	TextView tprocess;
	TextView tdltime;
	TextView tdeadlinetime;
	
	Button btnaddress;
	Button btnprocess;
	Button btnDate;
	Button btndeadlinetime;
	Button btnp1;
	Button btnp2;
	Button btnp3;
	Button btnp4;
	
	EditText editname;
	EditText editdes;
	EditText editplace;
	EditText edit;
	View v1;
	View v2;
	View v3;
	int totalHeightp = 0; 
    int kp=0;

	AlertDialog.Builder builder ;
	  GoogleMap mMap;  
      LocationClient mLocationClient; 
      Double la,lon;
      int countmaker=0,countmy=0;
      double geoLatitude,geoLongitude;
     String search;
     MarkerOptions markerOpt = new MarkerOptions();
     MarkerOptions markerOptother = new MarkerOptions();
	
	//Fragment map;
	private ListView listInput;	
	private ArrayAdapter<String> adapter;
    private ArrayList<String> items;    
    private ListView listInput2;	
	private ArrayAdapter<String> adapter2;
    private ArrayList<String> items2;
    ArrayList<String> parray = new ArrayList<String>();
    ArrayList<String> tarray = new ArrayList<String>();
    ArrayList<String> dts_array = new ArrayList<String>();
    
    String property="5",s,syear,smonth,sday,myWeek,str,process;
    String sd,s2,dts_time,dts_deadlinetime;
    int counttemp=0;
    Intent intent;    
    private final void focusOnView1(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
            	scroll.scrollTo(0, v1.getBottom());            	
            }
        });      
    }
    private final void focusOnView2(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
            	scroll.scrollTo(0, v2.getBottom());            	
            }
        });      
    }
    private final void focusOnView3(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
            	scroll.scrollTo(0, v3.getBottom());            	
            }
        });      
    }
//===============================================================
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addoutdoor);
		
		scroll = (ScrollView) findViewById(R.id.scroll);
		
		v1=(View) findViewById(R.id.v1);
		v2=(View) findViewById(R.id.v2);
		v3=(View) findViewById(R.id.v3);
		
		editname = (EditText) findViewById(R.id.editname);
		editdes = (EditText) findViewById(R.id.editdes);
		editplace = (EditText) findViewById(R.id.editplace);
		
		tname = (TextView) findViewById(R.id.tname);
		tdes= (TextView) findViewById(R.id.tdes);
		mMessageView= (TextView) findViewById(R.id.message_text);
		tplace= (TextView) findViewById(R.id.tplace);
		tprocess= (TextView) findViewById(R.id.tprocess);
		ttime = (TextView) findViewById(R.id.ttime);
		tdltime = (TextView) findViewById(R.id.tdltime);
		tdeadlinetime = (TextView) findViewById(R.id.tdeadlinetime);
		
		btnaddress = (Button) findViewById(R.id.btnaddress);
		btnprocess = (Button) findViewById(R.id.btnprocess);
		btnDate = (Button) findViewById(R.id.btntime);
		btndeadlinetime = (Button) findViewById(R.id.btndeadlinetime);
		btnp1 = (Button) findViewById(R.id.btnp1);
		btnp2 = (Button) findViewById(R.id.btnp2);
		btnp3 = (Button) findViewById(R.id.btnp3);
		btnp4 = (Button) findViewById(R.id.btnp4);
		
		intent = new Intent(this,Addfinal.class);		
//===============================================================================
	    tdes.setVisibility(View.GONE);
		editdes.setVisibility(View.GONE);
		btnaddress.setVisibility(View.GONE);
		mMessageView.setVisibility(View.GONE);		
		//map.setVisibility(View.GONE);
		tplace.setVisibility(View.GONE);
		editplace.setVisibility(View.GONE);	
		btnprocess.setVisibility(View.GONE);
		tprocess.setVisibility(View.GONE);
		ttime.setVisibility(View.GONE);
		btnDate.setVisibility(View.GONE);
		//listInput.setVisibility(View.GONE);
		tdltime.setVisibility(View.GONE);
		btndeadlinetime.setVisibility(View.GONE);
		tdeadlinetime.setVisibility(View.GONE);

	    btnp1.setOnClickListener(new Button.OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					btnp1.setBackgroundResource(R.drawable.process1used);
					btnp2.setBackgroundResource(R.drawable.process2);
					btnp3.setBackgroundResource(R.drawable.process3);
					btnp4.setBackgroundResource(R.drawable.process4);
					focusOnView3();								
				}
	        });
	       btnp2.setOnClickListener(new Button.OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					btnp1.setBackgroundResource(R.drawable.process1);
					btnp2.setBackgroundResource(R.drawable.process2used);
					btnp3.setBackgroundResource(R.drawable.process3);
					btnp4.setBackgroundResource(R.drawable.process4);
					tdes.setVisibility(View.VISIBLE);
					editdes.setVisibility(View.VISIBLE);
					btnaddress.setVisibility(View.VISIBLE);
					mMessageView.setVisibility(View.VISIBLE);
					editplace.setVisibility(View.VISIBLE);
					tplace.setVisibility(View.VISIBLE);
					btnprocess.setVisibility(View.VISIBLE);
					tprocess.setVisibility(View.VISIBLE);
					focusOnView1();									
				}
	       });
	       btnp3.setOnClickListener(new Button.OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					btnp1.setBackgroundResource(R.drawable.process1);
					btnp2.setBackgroundResource(R.drawable.process2);
					btnp3.setBackgroundResource(R.drawable.process3used);
					btnp4.setBackgroundResource(R.drawable.process4);
					tdes.setVisibility(View.VISIBLE);
					editdes.setVisibility(View.VISIBLE);
					btnaddress.setVisibility(View.VISIBLE);
					mMessageView.setVisibility(View.VISIBLE);
					editplace.setVisibility(View.VISIBLE);
					tplace.setVisibility(View.VISIBLE);
					btnprocess.setVisibility(View.VISIBLE);
					tprocess.setVisibility(View.VISIBLE);
					ttime.setVisibility(View.VISIBLE);
					btnDate.setVisibility(View.VISIBLE);
					//listInput.setVisibility(View.VISIBLE);
					tdltime.setVisibility(View.VISIBLE);
					btndeadlinetime.setVisibility(View.VISIBLE);
					tdeadlinetime.setVisibility(View.VISIBLE);
					focusOnView2();									
				}
	       });
	       btnp4.setOnClickListener(new Button.OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(tarray.isEmpty()){
						Toast.makeText(getApplicationContext(),getString(R.string.whether_select_time),Toast.LENGTH_LONG).show();				
					}
					else if(sd==null){
						Toast.makeText(getApplicationContext(),getString(R.string.whether_select_deadline),Toast.LENGTH_LONG).show();				
					}	
					else
					{
					Bundle bundle1 =getIntent().getExtras();
					String id=bundle1.getString("id");
					 
					Bundle bundle = new Bundle();					
					 bundle.putString("id", id);
					 bundle.putString("name", editname.getEditableText().toString());
					 bundle.putString("des", editdes.getEditableText().toString());					 
				     bundle.putString("place", editplace.getEditableText().toString());				    			
				     bundle.putStringArrayList("parray",parray);				     				 
					 bundle.putStringArrayList("tarray", tarray);					 
					 bundle.putString("sd", sd);
					 bundle.putString("property", property);
					 bundle.putString("process", process);				 
					 
				    intent.putExtras(bundle);
				 	startActivity(intent);	
				 	finish();
					}
				}
	      });
  //===========================================================================
      btnaddress.setOnClickListener(new Button.OnClickListener(){
    		@Override
    		public void onClick(View arg0) {
    			// TODO Auto-generated method stub    			
    			String input = editdes.getText().toString().trim();
    			if(input.length()>0)
    			{
    				Geocoder geocoder = new Geocoder(Addoutdoor.this);
    			    List<Address> addresses =null;
    				Address	address=null;
    				try{
    				addresses = geocoder.getFromLocationName(input,1);
    				}
    				catch (IOException e){
    				}
    			        if(addresses == null || addresses.isEmpty())
    				{
    			          Toast.makeText(Addoutdoor.this, "Not Fond", Toast.LENGTH_SHORT).show();}
    			else{
    				address=addresses.get(0);				
    				geoLatitude=address.getLatitude();	
    				geoLongitude=address.getLongitude();
    			        }
    			        if(countmaker==1)
    					 {
    						 	mMap.clear();
    						 	countmaker=0;
    					 }
    					    markerOptother.position(new LatLng(geoLatitude, geoLongitude));
    					    markerOptother.title(input);
    					    markerOptother.draggable(true);
    					    //mMap.Marker.remove();
    					    mMap.addMarker(markerOptother);
    					    countmaker++;
    					    LatLng nkut = new LatLng(geoLatitude, geoLongitude);
    					    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nkut,15.0f));    			
    		}            			
    	}   		
    });
        
//===============================================================================listview		
		    listInput = (ListView)findViewById(R.id.listView1);
	        items = new ArrayList<String>();
	        adapter = new MyCustomAdapter(this, android.R.layout.simple_list_item_1, items);
	        listInput.setAdapter(adapter);	
	        listInput.setOnItemLongClickListener(new OnItemLongClickListener(){
				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
					// TODO Auto-generated method stub
	                final int pos = position;
	                new AlertDialog.Builder(Addoutdoor.this)
	                .setTitle(getString(R.string.cancel))
	                .setMessage(getString(R.string.cancel_correct))
	                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                    	tarray.remove(pos);
	                        items.remove(pos);
	                        listInput.setAdapter(adapter);
	                    }
	                })
	                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                    }
	                })
	                .show();
					return false;
				}
	        });	
//====================================================================加入行程        
	        listInput2 = (ListView)findViewById(R.id.listView2);
	        items2 = new ArrayList<String>();
	        adapter2 = new MyCustomAdapter2(this, android.R.layout.simple_list_item_1, items2);
	        listInput2.setAdapter(adapter2);	           
	       listInput2.setOnItemLongClickListener(new OnItemLongClickListener(){
				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
					// TODO Auto-generated method stub
	                final int pos = position;
	                new AlertDialog.Builder(Addoutdoor.this)
	                .setTitle(getString(R.string.cancel))
	                .setMessage(getString(R.string.cancel_correct))
	                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                    	parray.remove(pos);
	                        items2.remove(pos);
	                        listInput2.setAdapter(adapter2);
	                    }
	                })
	                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                    }
	                })
	                .show();
					return false;
				}
	        });
	        final LayoutInflater inflater = LayoutInflater.from(Addoutdoor.this);	
	        builder = new AlertDialog.Builder(this);
			   btnprocess.setOnClickListener(new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						 View login_view = inflater.inflate(R.layout.process_view,null);							
							edit=(EditText)login_view.findViewById(R.id.edit);
					        builder.setTitle(getString(R.string.process_addname));		 
						    builder.setMessage(getString(R.string.process_addcontent));	
					        builder.setView(login_view);					        
		final AlertDialog dialog = builder.setPositiveButton(getString(R.string.ok),
		       new DialogInterface.OnClickListener() {
		        @Override
		        public void onClick(DialogInterface arg0, int arg1) {
		              //如果未輸入提示使用者輸入
		           try{
		        	  		s2=edit.getEditableText().toString();
		        	  		Calendar time = Calendar.getInstance();
		        	  		// 直接創建一個DatePickerDialog對話框實例，並將它顯示出來
		        	  		Dialog timeDialog = new TimePickerDialog(
		        	  				Addoutdoor.this,			
		        	    	// 绑定監聽器
		        	    	new TimePickerDialog.OnTimeSetListener() {
		        	    				@Override
		        	    				public void onTimeSet(
		        	    						TimePicker tp,
		        	    						int hourOfDay, int minute) {
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
		        	    					
		        	    					s2=h + ":"+ m+" "+s2;
		        	    					Calendar c = Calendar.getInstance();
		        	  		Dialog dateDialog = new DatePickerDialog(Addoutdoor.this,
		        	    	// 绑定監聽器
		        	    	new DatePickerDialog.OnDateSetListener() {
		        	    	@Override
		        	    	public void onDateSet(DatePicker dp, int year,
		        	    	int month, int dayOfMonth) {
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
		        	  		s2=year + "-" + mon + "-"+ da+" "+s2 ;
	       	    	                   
		        	    					  items2.add(s2);     
		        	    			          listInput2.setAdapter(adapter2);  
		        	    			          Log.v("log","count "+Integer.toString(adapter2.getCount()));
		        	    				        for (int i = 0; i < adapter2.getCount(); i++) {  
		        	    				            View listItem = adapter2.getView(i, null, listInput2);  
		        	    				            listItem.measure(0, 0);  
		        	    				            if(i==0)
		        	    				            {
		        	    				            	kp=listItem.getMeasuredHeight();	 
		        	    				            }		        	    				            
		        	    				        } 
		        	    				        if(s2.length()>23)
		        	    				        {
		        	    				        	kp=kp+kp;
		        	    				        }    
		        	    				        Log.v("log","s2 "+Integer.toString(s2.length()));
		        	    				        Log.v("log","kp "+Integer.toString(kp));
		        	    				        totalHeightp += kp;
		        	    				        Log.v("log","totalHeightp "+Integer.toString(totalHeightp));
		        	    				        ViewGroup.LayoutParams params = listInput2.getLayoutParams();  
		        	    				        params.height = totalHeightp;
		        	    				        Log.v("log","getDividerHeight() "+Integer.toString(listInput2.getDividerHeight()));
		        	    				        listInput2.setLayoutParams(params);	      	    				        
		        	    				        parray.add(s2);		        	    				     			        	    			          
		        	    	    }
		        	  		}		        	    				
			        	    			       // 設置初始日期
			  			        	    		, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
			  			        	    		.get(Calendar.DAY_OF_MONTH));
			  			        	    		dateDialog.setTitle(getString(R.string.please_decide_time));
			  			        	    		dateDialog.show();		        	    			
		        	    	}
		        	    }
		        	  			
			        	    		// 設置初始時間
		        	    			, time.get(Calendar.HOUR_OF_DAY), time
		        	    			.get(Calendar.MINUTE)
		        	    			// true表示采用24小時制
		        	    			, true);
		        	    	timeDialog.setTitle(getString(R.string.please_decide_date));
		        	    	timeDialog.show();	        	  			
		           }catch(Exception e){		        	          
		           }
		        }
		   }).setNegativeButton("取消",new DialogInterface.OnClickListener() 
		   {   
		        @Override
		           public void onClick(DialogInterface arg0, int arg1) {
		           }
		   }).create();    			      
			      dialog.show();		      		
	             }							
			   });
//==============================================================================	 
	 btnDate.setOnClickListener(new OnClickListener() {
	    	@Override
	    	public void onClick(View source) {
	    	Calendar c = Calendar.getInstance();// 直接創建一個DatePickerDialog對話框實例，並將它顯示出來
	    	Dialog dateDialog = new DatePickerDialog(Addoutdoor.this,
	    	// 绑定監聽器
	    	new DatePickerDialog.OnDateSetListener() {
	    	@Override
	    	public void onDateSet(DatePicker dp, int year,
	    	int month, int dayOfMonth) {
	    		
	    	Calendar time = Calendar.getInstance();	    	
	    	syear=Integer.toString(year);
			smonth=Integer.toString(month+1);
			sday=Integer.toString(dayOfMonth);
			int m=month+1;
			int c=year/100;
			int d=dayOfMonth;
			  if(month==1)
			  {
			    year--;  
			    month=13;
			  }
			  if(month==2)
			  {
			    year--;
			    month=14;
			  }
			int y=year%100;
			int w=(y+(y/4)+(c/4)-2*c+(26*(m+1)/10)+d-1)%7;
			w=(w+7)%7;
			myWeek = null;
			switch(w)
			{
			case 0:
			myWeek="日";
			break;
			case 1:
			myWeek="一";
			break;
			case 2:
			myWeek="二";
			break;
			case 3:
			myWeek="三";
			break;
			case 4:
			myWeek="四";
			break;
			case 5:
			myWeek="五";
			break;
			case 6:
			myWeek="六";
			break;
			default:
			break;
			}
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
	    	s=year + "-" + mon + "-"+ da + " 星期"+myWeek+" ";
	    	dts_time=year + "-" + mon + "-"+ da+" ";
	      	
	    	Dialog timeDialog = new TimePickerDialog(
	    			Addoutdoor.this,			
	    	// 绑定監聽器
	    	new TimePickerDialog.OnTimeSetListener() {
	    				@Override
	    				public void onTimeSet(
	    						TimePicker tp,
	    						int hourOfDay, int minute) {
	    					
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
	    					
	    					dts_time=dts_time+h + ":"+ m+":00";
	    					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    			    	Date date=new Date();
							try {
								dts = sdf.parse(dts_time);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
								Long ut1=dts.getTime();
								Long ut2=date.getTime();
								Long timeP=ut1-ut2;//毫秒差
								if(timeP<0)
								{
									Toast.makeText(getApplicationContext(),getString(R.string.delay_nowtime),Toast.LENGTH_LONG).show();
								}else{								    					
	    					s=s+h + ":"+ m+":00";
	    					 menuAddItem();
	    					 dts_array.add(dts_time);
	    					 tarray.add(s);      
	    					 }                          	                          
	    				}
	    	}
	    			// 設置初始時間
	    			, time.get(Calendar.HOUR_OF_DAY), time
	    			.get(Calendar.MINUTE)
	    			// true表示采用24小時制
	    			, true);
	    	timeDialog.setTitle(getString(R.string.please_decide_time));
	    	timeDialog.show();
	    		}
	    	}
	    	// 設置初始日期
	    		, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
	    		.get(Calendar.DAY_OF_MONTH));
	    		dateDialog.setTitle(getString(R.string.please_decide_date));
	    		dateDialog.show();
	    	}
	    	});
	     
	 btndeadlinetime.setOnClickListener(new OnClickListener() {
	    	@Override
	    	public void onClick(View source) {
	    	Calendar c = Calendar.getInstance();// 直接創建一個DatePickerDialog對話框實例，並將它顯示出來
	    	Dialog dateDialog = new DatePickerDialog(Addoutdoor.this,
	    	// 绑定監聽器
	    	new DatePickerDialog.OnDateSetListener() {
	    	@Override
	    	public void onDateSet(DatePicker dp, int year,
	    	int month, int dayOfMonth) {
	    		
	    	Calendar time = Calendar.getInstance();	    	
	    	syear=Integer.toString(year);
			smonth=Integer.toString(month+1);
			sday=Integer.toString(dayOfMonth);
			int m=month+1;
			int c=year/100;
			int d=dayOfMonth;
			  if(month==1)
			  {
			    year--;  
			    month=13;
			  }
			  if(month==2)
			  {
			    year--;
			    month=14;
			  }
			int y=year%100;
			int w=(y+(y/4)+(c/4)-2*c+(26*(m+1)/10)+d-1)%7;
			w=(w+7)%7;

			myWeek = null;
			switch(w)
			{
			case 0:
			myWeek="日";
			break;
			case 1:
			myWeek="一";
			break;
			case 2:
			myWeek="二";
			break;
			case 3:
			myWeek="三";
			break;
			case 4:
			myWeek="四";
			break;
			case 5:
			myWeek="五";
			break;
			case 6:
			myWeek="六";
			break;
			default:
			break;
			}
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
	    	sd=year + "-" + mon + "-"+ da + " 星期"+myWeek+" ";	
	    	dts_deadlinetime=year + "-" + mon + "-"+ da+" ";
	    	Log.v("log",dts_deadlinetime);	    	

	    	Dialog timeDialog = new TimePickerDialog(
	    			Addoutdoor.this,			
	    	// 绑定監聽器
	    	new TimePickerDialog.OnTimeSetListener() {
	    				@Override
	    				public void onTimeSet(
	    						TimePicker tp,
	    						int hourOfDay, int minute) {
	    					
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
	    					dts_deadlinetime=dts_deadlinetime+h + ":"+ m+":00";
	    					Log.v("log",dts_deadlinetime);
	    					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    			    	Date date=new Date();
							try {
								dts = sdf.parse(dts_deadlinetime);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
								Long ut1=dts.getTime();
								Long ut2=date.getTime();
								Long timeP=ut1-ut2;//毫秒差
								int count_t=0;
								for(int i=0;i<dts_array.size();i++)
								{
									try {
										dts_deadlinetime_array = sdf.parse(dts_array.get(i));
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									Long ut3=dts_deadlinetime_array.getTime();
									Long timed=ut3-ut1;//毫秒差
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
									sd=sd+h + ":"+ m+":00";
									tdeadlinetime.setText(sd+"\n");	 
								}	                  
	    				}
	    	}
	    			// 設置初始時間
	    			, time.get(Calendar.HOUR_OF_DAY), time
	    			.get(Calendar.MINUTE)
	    			// true表示采用24小時制
	    			, true);
	    	timeDialog.setTitle(getString(R.string.please_decide_time));
	    	timeDialog.show();
	    		}
	    	}
	    	// 設置初始日期
	    		, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
	    		.get(Calendar.DAY_OF_MONTH));
	    		dateDialog.setTitle(getString(R.string.please_decide_date));
	    		dateDialog.show();
	    	}
	    	});
		}
//====================================================================++listview
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch(item.getItemId()){
	            case Menu.FIRST:
	                menuAddItem();
	                break;
	        }
	        return super.onOptionsItemSelected(item);
	    }

	    // 增加 item 到 listview.
	    int totalHeight = 0; 
	    int k=0;
	    private void menuAddItem(){    	    
	            items.add(s);     
	            listInput.setAdapter(adapter);	            		         
		        for (int i = 0; i < adapter.getCount(); i++) {  
		            View listItem = adapter.getView(i, null, listInput);  
		            listItem.measure(0, 0);  
		            if(i==0)
		            {
		            	k=listItem.getMeasuredHeight();	 
		            }
		        }  
		        totalHeight += k;
		        ViewGroup.LayoutParams params = listInput.getLayoutParams();  
		        params.height = totalHeight; 
		        listInput.setLayoutParams(params); 
	    }
//====================================================================== map
@Override
protected void onResume() {
super.onResume();
setUpMapIfNeeded();
setUpLocationClientIfNeeded();
mLocationClient.connect();
}

@Override
public void onPause() {
super.onPause();
if (mLocationClient != null) {
  mLocationClient.disconnect();
}
}

private void setUpMapIfNeeded() {
// Do a null check to confirm that we have not already instantiated the map.
if (mMap == null) {
  // Try to obtain the map from the SupportMapFragment.
  mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
         .getMap(); 
  // Check if we were successful in obtaining the map.
  if (mMap != null) {
    mMap.setMyLocationEnabled(true);
  }
}
}

private void setUpLocationClientIfNeeded() {
if (mLocationClient == null) {
  mLocationClient = new LocationClient(
      getApplicationContext(),
      this,  // ConnectionCallbacks
      this); // OnConnectionFailedListener
}
}
  @Override
  public void onLocationChanged(Location location) {
    mMessageView.setText("Location = " + location.getLatitude()+" "+location.getLongitude());
    if(countmy==0)
    {
    	LatLng nkut = new LatLng(location.getLatitude(), location.getLongitude());
	    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nkut,15.0f)); 
    }
    countmy++;
}

@Override
public void onConnected(Bundle connectionHint) {
mLocationClient.requestLocationUpdates(
    REQUEST,
    this);  // LocationListener
}

@Override
public void onDisconnected() {
// Do nothing
}

@Override
public void onConnectionFailed(ConnectionResult result) {
// Do nothing
}
public class MyCustomAdapter extends ArrayAdapter<String> {
	 
	 public MyCustomAdapter(Context context, int textViewResourceId,
	 ArrayList<String> items) {
	 super(context, textViewResourceId, items);	 
	 } 
	      
	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {  		
		 TextView mTextView = new TextView(getApplicationContext()); 
		 mTextView.setTextColor(Color.BLACK);
		 mTextView.setTextSize(20);
		 mTextView.setText(items.get(position));		  
       return mTextView;  
	 }
	}//MyCustomAdapter
public class MyCustomAdapter2 extends ArrayAdapter<String> {	 
	 public MyCustomAdapter2(Context context, int textViewResourceId,
	 ArrayList<String> items) {
	 super(context, textViewResourceId, items);	 
	 } 

	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {  		
		 TextView mTextView = new TextView(getApplicationContext()); 
		 mTextView.setTextColor(Color.BLACK);
		 mTextView.setTextSize(20);
		 mTextView.setText(items2.get(position));		  
      return mTextView;  
	 }
	}//MyCustomAdapter
}

