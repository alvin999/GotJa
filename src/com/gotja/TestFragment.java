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
			// ���w�n�I�s�� Activity Class
			newAct = new Intent(getActivity(), InviteFacebook.class);
			startActivity(newAct); 
			// �I�s�s�� Activity Class
			break;
		case R.id.testbutton2:
			// ���w�n�I�s�� Activity Class
			newAct = new Intent(getActivity(), InviteContacts.class);
			startActivity(newAct); 
			// �I�s�s�� Activity Class
			break;
		case R.id.testbutton3:
			// ���w�n�I�s�� Activity Class
			newAct = new Intent(getActivity(), com.example.addactivity.Addfinal.class);

			Bundle bundle = new Bundle();

			ArrayList<String> dateArray= new ArrayList<String>();
			dateArray.add("2013-08-04 �P���� 14:20:13");

			bundle.putString("id", "100001424930972");
			bundle.putString("name", "���\�n�Yԣ");
			bundle.putString("des", "�x�_��sss�\�U");
			bundle.putString("place", "xx���B��");
			bundle.putStringArrayList("tarray", dateArray);
			bundle.putString("sd", "2013-08-02 �P���� 14:20:13");
			bundle.putString("property", "6");

			newAct.putExtras(bundle);

			startActivity(newAct); 
			// �I�s�s�� Activity Class
			break;
		case R.id.testbutton4:
			// ���w�n�I�s�� Activity Class
			newAct = new Intent(getActivity(), ChatRoom.class);
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
		Button testButton2 = (Button) v.findViewById(R.id.testbutton2);
		testButton2.setOnClickListener(this);
		Button testButton3 = (Button) v.findViewById(R.id.testbutton3);
		testButton3.setOnClickListener(this);
		Button testButton4 = (Button) v.findViewById(R.id.testbutton4);
		testButton4.setOnClickListener(this);
		return v;
	}
}
