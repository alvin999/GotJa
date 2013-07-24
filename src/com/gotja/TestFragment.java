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
    	Intent newAct;
        switch (v.getId()) {
        case R.id.testbutton:
        	// 指定要呼叫的 Activity Class
        	newAct = new Intent(getActivity(), InviteFacebook.class);
        	startActivity(newAct); 
        	// 呼叫新的 Activity Class
            break;
        case R.id.testbutton2:
        	// 指定要呼叫的 Activity Class
        	newAct = new Intent(getActivity(), InviteContacts.class);
        	startActivity(newAct); 
        	// 呼叫新的 Activity Class
            break;
        }
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.testfragment, container, false);
        
        Button testButton = (Button) v.findViewById(R.id.testbutton);
        testButton.setOnClickListener(this);
        Button testButton2 = (Button) v.findViewById(R.id.testbutton2);
        testButton2.setOnClickListener(this);
        return v;
    }
}
