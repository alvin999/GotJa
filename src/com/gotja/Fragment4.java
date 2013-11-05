package com.gotja;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment4 extends Fragment  {
	String id = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f4, container, false);  
        
        id = getArguments().getString("userid");
        
        Button btn_food = (Button) v.findViewById(R.id.btn_food);
        btn_food.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentfood;      		    
	       		intentfood = new Intent(getActivity(),Foods.class);
	       		Bundle bundle = new Bundle();					
			    bundle.putString("id", id);
				 	   					 	
				intentfood.putExtras(bundle);
	       		startActivity(intentfood);				
			}        	
        });
        
        Button btn_movie = (Button) v.findViewById(R.id.btn_movie);
        btn_movie.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentmovie;     		    
	       		intentmovie = new Intent(getActivity(),Movie.class);
	       		Bundle bundle = new Bundle();					
			    bundle.putString("id", id);
				 	   					 	
				intentmovie.putExtras(bundle);
	       		startActivity(intentmovie);				
			}        	
        });
        
        Button btn_show = (Button) v.findViewById(R.id.btn_show);
        btn_show.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentmovie;      		    
	       		intentmovie = new Intent(getActivity(),Show.class);
	       		Bundle bundle = new Bundle();					
			    bundle.putString("id", id);
				 	   					 	
				intentmovie.putExtras(bundle);
	       		startActivity(intentmovie);				
			}        	
        });
        Button btn_comment_outdoor = (Button) v.findViewById(R.id.btn_comment_outdoor);
        btn_comment_outdoor.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentmovie;      		    
	       		intentmovie = new Intent(getActivity(),Outdoorhome.class);
	       		Bundle bundle = new Bundle();					
			    bundle.putString("id", id);
				 	   					 	
				intentmovie.putExtras(bundle);
	       		startActivity(intentmovie);				
			}        	
        });
        Button btn_comment_food = (Button) v.findViewById(R.id.btn_comment_food);
        btn_comment_food.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentmovie;     		    
	       		intentmovie = new Intent(getActivity(),Foodhome.class);
	       		Bundle bundle = new Bundle();					
			    bundle.putString("id", id);
				 	   					 	
				intentmovie.putExtras(bundle);
	       		startActivity(intentmovie);				
			}        	
        });
        return v;
    }
}