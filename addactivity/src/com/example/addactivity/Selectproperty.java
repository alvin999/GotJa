package com.example.addactivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class Selectproperty extends Activity {
	
	ImageButton btneat;
	ImageButton btnmovie;
	ImageButton btnshopping;
	ImageButton btnshow;
	ImageButton btnoutdoor;
	ImageButton btnother;
	
	Intent intenteat; 
	Intent intentmovie; 
	Intent intentshopping; 
	Intent intentshow; 
	Intent intentoutdoor; 
	Intent intentother; 
	String id;
	      
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectproperty);
		
		btneat=(ImageButton)findViewById(R.id.btneat);
		btnmovie=(ImageButton)findViewById(R.id.btnmovie);
		btnshopping=(ImageButton)findViewById(R.id.btnshopping);
		btnshow=(ImageButton)findViewById(R.id.btnshow);
		btnoutdoor=(ImageButton)findViewById(R.id.btnoutdoor);
		btnother=(ImageButton)findViewById(R.id.btnother);
		
		intenteat = new Intent(this,Addeat.class);
		intentmovie = new Intent(this,Addmovie.class);
		intentshopping = new Intent(this,Addshopping.class);
		intentshow = new Intent(this,Addshow.class);
		intentoutdoor = new Intent(this,Addoutdoor.class);
		intentother = new Intent(this,Addother.class);
		
		Bundle bundle1 =getIntent().getExtras();
	       id=bundle1.getString("id");		
    //====================================================================		
		btneat.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stu
				Bundle bundle = new Bundle();
				bundle.putString("id", id);
				 
				intenteat.putExtras(bundle);
				startActivity(intenteat);
				finish();
			}			
		});
		
		btnmovie.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stu
				Bundle bundle = new Bundle();
				bundle.putString("id", id);
				 
				intentmovie.putExtras(bundle);
				startActivity(intentmovie);	
				finish();
			}			
		});
		
		btnshopping.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stu
				Bundle bundle = new Bundle();
				bundle.putString("id", id);
				 
				intentshopping.putExtras(bundle);
				startActivity(intentshopping);
				finish();
			}			
		});
		
		btnshow.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stu
				Bundle bundle = new Bundle();
				bundle.putString("id", id);
				 
				intentshow.putExtras(bundle);
				startActivity(intentshow);	
				finish();
			}			
		});
		
		btnoutdoor.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stu
				Bundle bundle = new Bundle();
				bundle.putString("id", id);
				 
				intentoutdoor.putExtras(bundle);
				startActivity(intentoutdoor);	
				finish();
			}			
		});
		
		btnother.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stu
				Bundle bundle = new Bundle();
				bundle.putString("id", id);
				 
				intentother.putExtras(bundle);
				startActivity(intentother);		
				finish();
			}			
		});
	}
}
