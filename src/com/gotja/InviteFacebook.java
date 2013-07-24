package com.gotja;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

public class InviteFacebook extends Activity {
	
	//宣告facebook會用到的物件
	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};
	private Button sendRequestButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 設定 Layout
		setContentView(R.layout.invitefacebook);
		//宣告facebook物件以及listener
		uiHelper = new UiLifecycleHelper(InviteFacebook.this, callback);
		uiHelper.onCreate(savedInstanceState);
		//設置sendButton
		sendRequestButton = (Button) findViewById(R.id.sendRequestButton);
		sendRequestButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		        sendRequestDialog();        
		    }
		});
	}
	
	private void sendRequestDialog() {
	    Bundle params = new Bundle();
	    params.putString("message", "Learn how to make your Android apps social");
	    params.putString("frictionless", "1"); //Turn on frictionless
	    params.putString("data", "2");
	    WebDialog requestsDialog = (
	        new WebDialog.RequestsDialogBuilder(InviteFacebook.this,
	            Session.getActiveSession(),
	            params))
	            .setOnCompleteListener(new OnCompleteListener() {

	                @Override
	                public void onComplete(Bundle values,
	                    FacebookException error) {
	                    if (error != null) {
	                        if (error instanceof FacebookOperationCanceledException) {
	                            Toast.makeText(InviteFacebook.this.getApplicationContext(), 
	                                "Request cancelled", 
	                                Toast.LENGTH_SHORT).show();
	                        } else {
	                            Toast.makeText(InviteFacebook.this.getApplicationContext(), 
	                                "Network Error", 
	                                Toast.LENGTH_SHORT).show();
	                        }
	                    } else {
	                        final String requestId = values.getString("request");
	                        if (requestId != null) {
	                            Toast.makeText(InviteFacebook.this.getApplicationContext(), 
	                                "Request sent",  
	                                Toast.LENGTH_SHORT).show();
	                          //輸出全部被邀請的id
	    	                	for (int i = 0; values.containsKey("to[" + i + "]"); i++) {
	    	                	    String toElement = values.getString("to[" + i + "]");
	    		                	Log.e("id", toElement);
	    	                	}
	                        } else {
	                            Toast.makeText(InviteFacebook.this.getApplicationContext(), 
	                                "Request cancelled", 
	                                Toast.LENGTH_SHORT).show();
	                        }
	                    }   
	                }

	            })
	            .build();
	    requestsDialog.show();
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	        sendRequestButton.setVisibility(View.VISIBLE);
	    } else if (state.isClosed()) {
	        sendRequestButton.setVisibility(View.INVISIBLE);
	    }
	}

	@Override
	public void onResume() {
		super.onResume();
	    uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
}
