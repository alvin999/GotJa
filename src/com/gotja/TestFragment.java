package com.gotja;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class TestFragment extends Fragment implements OnClickListener{	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.testbutton:
        	// ���w�n�I�s�� Activity Class
        	Intent newAct = new Intent(getActivity(), TestActivity.class);
        	startActivity(newAct); 
        	// �I�s�s�� Activity Class
            break;
        }
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.testfragment, container, false);
        
        Button testButton = (Button) v.findViewById(R.id.testbutton);
        testButton.setOnClickListener(this);
        return v;
    }
}