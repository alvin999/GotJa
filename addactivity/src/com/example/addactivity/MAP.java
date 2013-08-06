package com.example.addactivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This demo shows how GMS Location can be used to check for changes to the users location.  The
 * "My Location" button uses GMS Location to set the blue dot representing the users location. To
 * track changes to the users location on the map, we request updates from the
 * {@link LocationClient}.
 */
public  class MAP extends FragmentActivity
    implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
	Polyline polyline;
	PolylineOptions rectOptions;
  private GoogleMap mMap;  
  private LocationClient mLocationClient;
  private TextView mMessageView;
  private EditText editaddress;
  private Button btnaddress;
  private Button btnstop;
  private Button btnclean;
  int countmaker=0;
  double geoLatitude,geoLongitude;
  String input,search,la,lon;
  int count=0;
  int stop=1;
  ArrayList<String> longt=new ArrayList<String>();
  ArrayList<String> latt=new ArrayList<String>();
  
  MarkerOptions markerOpt = new MarkerOptions();
  MarkerOptions markerOptother = new MarkerOptions();


  // These settings are the same as the settings for the map. They will in fact give you updates at
  // the maximal rates currently possible.
  private static final LocationRequest REQUEST = LocationRequest.create()
      .setInterval(10000)         // 5 seconds
      .setFastestInterval(16)    // 16ms = 60fps
      .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map);
    
    btnaddress = (Button)findViewById(R.id.btnaddress);
    btnstop = (Button)findViewById(R.id.btnstop);
    btnclean = (Button)findViewById(R.id.btnclean);
   editaddress = (EditText)findViewById(R.id.editaddress);    
    mMessageView = (TextView) findViewById(R.id.message_text);
    btnclean.setVisibility(View.GONE);
    
    btnclean.setOnClickListener(new Button.OnClickListener(){
    	@Override
    	public void onClick(View v) {
    		// TODO Auto-generated method stub
   			
    			Intent intent = new Intent(MAP.this, Server_MAP.class);
    			stopService(intent);  
    			mMap.clear();
    			btnclean.setVisibility(View.GONE);
    	}	  
      });
    
 btnstop.setOnClickListener(new Button.OnClickListener(){
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		stop++;
		if(stop%2==0)
		{
			btnclean.setVisibility(View.GONE);
			btnstop.setText(getString(R.string.stop));
			Intent intent = new Intent(MAP.this, Server_MAP.class);
	        startService(intent);
		}else{	
			btnstop.setText(getString(R.string.start));
			Log.v("log","stop!!!!");
			btnclean.setVisibility(View.VISIBLE);
		}
	}	  
  });
    
        
    btnaddress.setOnClickListener(new Button.OnClickListener(){
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub			
			String input = editaddress.getText().toString().trim();
			if(input.length()>0)
			{
				Geocoder geocoder = new Geocoder(MAP.this);
			    List<Address> addresses =null;
				Address	address=null;
				try{
				addresses = geocoder.getFromLocationName(input,1);
				}
				catch (IOException e){
				}
			        if(addresses == null || addresses.isEmpty())
				{
			          Toast.makeText(MAP.this, "Not Fond", Toast.LENGTH_SHORT).show();}
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
			        mMessageView.append(geoLatitude+" "+geoLongitude); 
					    markerOptother.position(new LatLng(geoLatitude, geoLongitude));
					    markerOptother.title(input);
					    markerOptother.draggable(true);
					    mMap.addMarker(markerOptother);
					    countmaker++;
					    LatLng nkut = new LatLng(geoLatitude, geoLongitude);
					    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nkut,15.0f));    			
		}         
			
	}
		
});

    
  
  }
  private class GotjaBroadcastReceiver extends BroadcastReceiver
	{
		public void onReceive(Context context, Intent intent)
		{
			//loadChatView();
			latt = intent.getStringArrayListExtra("latt");
			 longt = intent.getStringArrayListExtra("longt");
			String t=intent.getExtras().getString("data");
			 
			 count=Integer.parseInt(t);
			Log.v("log","t "+t);
			Log.v("BroadcastReceive", "Receive!");
		}
	}
  GotjaBroadcastReceiver broadcastReceiver = new GotjaBroadcastReceiver();
  @Override
  protected void onResume() {
    super.onResume();
    setUpMapIfNeeded();
    setUpLocationClientIfNeeded();
    mLocationClient.connect();  
    String GOTJA_BROATCAST_STRING ="YA";
	IntentFilter intentFilter = new IntentFilter(GOTJA_BROATCAST_STRING);
	registerReceiver(broadcastReceiver, intentFilter); //註冊監聽
  }

  @Override
  public void onPause() {
    super.onPause();
    if (mLocationClient != null) {
      mLocationClient.disconnect();
      
      unregisterReceiver(broadcastReceiver); //取消監聽
    }
  }
  public void setPolyline(){
	  if(stop%2==0){ 		  
	    	 if(count!=0){
	    			  double latt_src=Double.valueOf(latt.get(count-1)).doubleValue();
	    			  double longt_src=Double.valueOf(longt.get(count-1)).doubleValue();
	    			  double latt_des=Double.valueOf(latt.get(count)).doubleValue();
	    			  double longt_des=Double.valueOf(longt.get(count)).doubleValue();
	    			  Log.v("log",latt.get(count-1)+"~"+longt.get(count-1)+"~"+latt.get(count)+"~"+longt.get(count));
	    			  LatLng src = new LatLng(latt_src,longt_src);
	    	          LatLng des = new LatLng(latt_des,longt_des);
	    			  rectOptions = new PolylineOptions()
	    		      .add(new LatLng(src.latitude, src.longitude),new LatLng(des.latitude, des.longitude))
	    		      .width(3)
	    		      .color(Color.GREEN)
	    		      .geodesic(true);	
	    			  polyline = mMap.addPolyline(rectOptions);	    		  
	    		 }
	    	}
	    	int ss=stop%2;
	    	String s=Integer.toString(ss);
	    	Log.v("log","stop "+s);
  }
  private void setUpMapIfNeeded() {
    if (mMap == null) {
      mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
             .getMap();
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
	    double dla,dlon;
	    dla=(int)(Math.floor(location.getLatitude()*10000))/10000.0;
	    dlon=(int)(Math.floor(location.getLongitude()*10000))/10000.0;	    
	  setPolyline();
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
}

