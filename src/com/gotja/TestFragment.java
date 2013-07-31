package com.gotja;

import java.util.ArrayList;

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
		case R.id.testbutton3:
			// 指定要呼叫的 Activity Class
			newAct = new Intent(getActivity(), com.example.addactivity.Addfinal.class);

			Bundle bundle = new Bundle();

			ArrayList<String> dateArray= new ArrayList<String>();
			dateArray.add("2013-08-04 星期五 14:20:13");

			bundle.putString("id", "100001424930972");
			bundle.putString("name", "晚餐要吃啥");
			bundle.putString("des", "台北市sss餐廳");
			bundle.putString("place", "xx捷運站");
			bundle.putStringArrayList("tarray", dateArray);
			bundle.putString("sd", "2013-08-02 星期五 14:20:13");
			bundle.putString("property", "6");

			newAct.putExtras(bundle);

			startActivity(newAct); 
			// 呼叫新的 Activity Class
			break;
		case R.id.testbutton4:
			// 指定要呼叫的 Activity Class
			newAct = new Intent(getActivity(), ChatRoom.class);
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
		Button testButton3 = (Button) v.findViewById(R.id.testbutton3);
		testButton3.setOnClickListener(this);
		Button testButton4 = (Button) v.findViewById(R.id.testbutton4);
		testButton4.setOnClickListener(this);
		return v;
	}
}
