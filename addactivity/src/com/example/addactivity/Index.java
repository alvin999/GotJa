package com.example.addactivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Index extends Activity {
	Button btnaddactivity;
	Button btnmyactivity;
	Button btnrecommend;
	Button b1;
	Button b2;
	
	Intent intentac;
	Intent intentmy;
	Intent intentre;
	
	String id="sky@yahoo.com.tw";
	String id_sky="sky@yahoo.com.tw";
	
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		
		btnaddactivity =(Button)findViewById(R.id.btnaddactivity);
		btnmyactivity =(Button)findViewById(R.id.btnmyactivity);
		btnrecommend =(Button)findViewById(R.id.btnrecommend);
		b1 =(Button)findViewById(R.id.b1);
		b2 =(Button)findViewById(R.id.b2);
		
		intentac = new Intent(this,Selectproperty.class);
		intentmy = new Intent(this,Myactivity.class);
		intentre = new Intent(this,Recommend_index.class);
		
		b1.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				id="sky@yahoo.com.tw";
				
			}
				
		});
		b2.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				id="user@yahoo.com.tw";
				
			}
				
		});
		
		btnaddactivity.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();				
				 bundle.putString("id", id_sky);
				 
				 intentac.putExtras(bundle);
				startActivity(intentac);
				
			}
				
		});
		
		btnmyactivity.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Bundle bundle = new Bundle();				
				 bundle.putString("id", id);
				
				 
				intentmy.putExtras(bundle);
				startActivity(intentmy);
				
			}
				
		});
		btnrecommend.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();				
				 bundle.putString("id", id_sky);
				 
				 intentre.putExtras(bundle);
				startActivity(intentre);
				
			}
				
		});
		
		
		
	}

}
