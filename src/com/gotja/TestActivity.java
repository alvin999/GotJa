package com.gotja;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestActivity extends Activity {
	 public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      
	      // �]�w Layout �� main2.xml
	      setContentView(R.layout.testactivity);
	      
	      // ���s����
	      Button testButton = (Button)findViewById(R.id.finishactivity);
	      
	      // Button �� OnClick Trigger
	      testButton.setOnClickListener(  new OnClickListener(){
	         public void onClick(View v) {
	            /*
	            // ���w�n�I�s�� Activity Class
	            Intent newAct = new Intent();
	            newAct.setClass( helloWorld2.this, helloWorld.class );
	            
	            // �I�s�s�� Activity Class
	            startActivity( newAct );
	            */
	            // ��������� Activity Class
	            TestActivity.this.finish();
	         }
	      });
	   }
}
