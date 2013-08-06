package com.example.addactivity;

import android.app.Activity;


import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

//繼承android.app.Service
public class Server_MAP extends Service implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener{
 int count=0;
 private LocationClient mLocationClient;
 ArrayList<String> longt=new ArrayList<String>();
 ArrayList<String> latt=new ArrayList<String>();
 String input,search,la,lon;
 PolylineOptions rectOptions;
 LocationManager mgr;
 String GOTJA_BROATCAST_STRING = "YA";
	Intent i = new Intent(GOTJA_BROATCAST_STRING);
 private static final LocationRequest REQUEST = LocationRequest.create()
	      .setInterval(5000)         // 5 seconds
	      .setFastestInterval(16)    // 16ms = 60fps
	      .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
 
    @Override
    public IBinder onBind(Intent intent) {
        return null;
   }
    
    @Override
    public void onCreate() {
     Log.v("log", "onCreate");
     longt=new ArrayList<String>();
     latt=new ArrayList<String>();
    super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
       //handler.postDelayed(showTime, 1000) 	
        super.onStart(intent, startId);
        setUpLocationClientIfNeeded();
		 mLocationClient.connect();     		 
    	  //Send Broadcast    		 
    }
 
   @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("log", "onDestroy");  
        mLocationClient.disconnect(); 
 
    }
   
@Override
public void onLocationChanged(Location location) {
	// TODO Auto-generated method stub
	
	la=Double.toString(location.getLatitude());
	//la=Double.toString(dla);
	latt.add(la);	
	lon=Double.toString(location.getLongitude());
	//lon=Double.toString(dlon);
	longt.add(lon);	

	 if(count!=0){
		  
		  
			  double latt_src=Double.valueOf(latt.get(count-1)).doubleValue();
			  double longt_src=Double.valueOf(longt.get(count-1)).doubleValue();
			  double latt_des=Double.valueOf(latt.get(count)).doubleValue();
			  double longt_des=Double.valueOf(longt.get(count)).doubleValue();		  
		 }

	String c=Integer.toString(count);
	i.putStringArrayListExtra("latt", (ArrayList<String>) latt);
	i.putStringArrayListExtra("longt", (ArrayList<String>) longt);
	i.putExtra("data",Integer.toString(count));
	sendBroadcast(i);
	count++;
	
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
public void onConnected(Bundle connectionHint) {
  mLocationClient.requestLocationUpdates(
      REQUEST,
      this);  // LocationListener
}

@Override
public void onConnectionFailed(ConnectionResult arg0) {
	// TODO Auto-generated method stub	
}

@Override
public void onDisconnected() {
	// TODO Auto-generated method stub	
}
}