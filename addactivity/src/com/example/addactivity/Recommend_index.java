package com.example.addactivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Recommend_index extends Activity {
	ImageButton btneat;
	ImageButton btnmovie;
	ImageButton btnshopping;
	ImageButton btnshow;
	ImageButton btnoutdoor;
	ImageButton btnother;
	
	Intent intentmovie; 
	Intent intenteat;
	
	Bundle bundle;
	ImageButton btnhot;
	String id;
	boolean tf=true;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recommend_index);
		
		btneat=(ImageButton)findViewById(R.id.btneat);
		btnmovie=(ImageButton)findViewById(R.id.btnmovie);
		btnshopping=(ImageButton)findViewById(R.id.btnshopping);
		btnshow=(ImageButton)findViewById(R.id.btnshow);
		btnoutdoor=(ImageButton)findViewById(R.id.btnoutdoor);
		btnother=(ImageButton)findViewById(R.id.btnother);
	
		btnhot=(ImageButton)findViewById(R.id.btnhot);
		
		intentmovie = new Intent(this,Re_movie.class);
		intenteat = new Intent(this,Re_eat.class);
		
		Bundle bundle1 =getIntent().getExtras();
	       id=bundle1.getString("id");
		
		

		
	/*	btnhot.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tf=false;
			}
		});*/
//=====================================================================		
		btnmovie.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(tf==true)
				{
					intentmovie.putExtras(bundle);
					startActivity(intentmovie);						
				}else{}				
			}
			
		});
		btneat.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(tf==true)
				{
					intenteat.putExtras(bundle);
					startActivity(intenteat);						
				}else{}				
			}
			
		});



	}	
}
