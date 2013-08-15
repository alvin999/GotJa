package com.gotja;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AdviceFragment extends Fragment  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.advice_fragment, container, false);
        
        Button btn_food = (Button) v.findViewById(R.id.btn_food);
        btn_food.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentfood;
       		    
	       		intentfood = new Intent(getActivity(), Foods.class);
	       		startActivity(intentfood);				
			}        	
        });
        
        Button btn_movie = (Button) v.findViewById(R.id.btn_movie);
        btn_movie.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentmovie;
       		    
	       		intentmovie = new Intent(getActivity(), Movie.class);
	       		startActivity(intentmovie);				
			}        	
        });
        return v;
    }
}