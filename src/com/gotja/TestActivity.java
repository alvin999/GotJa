package com.gotja;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestActivity extends Activity {
	 public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      
	      // 設定 Layout 為 main2.xml
	      setContentView(R.layout.testactivity);
	      
	      // 按鈕物件
	      Button testButton = (Button)findViewById(R.id.finishactivity);
	      
	      // Button 的 OnClick Trigger
	      testButton.setOnClickListener(  new OnClickListener(){
	         public void onClick(View v) {
	            /*
	            // 指定要呼叫的 Activity Class
	            Intent newAct = new Intent();
	            newAct.setClass( helloWorld2.this, helloWorld.class );
	            
	            // 呼叫新的 Activity Class
	            startActivity( newAct );
	            */
	            // 結束原先的 Activity Class
	            TestActivity.this.finish();
	         }
	      });
	   }
}
