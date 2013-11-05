package com.gotja;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Outdoorlist extends Activity{
	private ScrollView scroll;
	private ImageView foodpic;
	private TextView Sname;
	private TextView Saddress;
	private TextView dishlist;
	private TextView Opinion;
	private TextView Score;
	private EditText content;
	private Button send;
	private LinearLayout ll;
	private String faccount,uacno,sn,sa,op,sc,pic;
	private Bitmap bitmap = null;
	private ProgressDialog pd;
	private Context context=Outdoorlist.this;
	private ArrayList<String> dish=new ArrayList<String>();
	private ArrayList<String> user=new ArrayList<String>();
	private ArrayList<String> message=new ArrayList<String>();
	String log;
	int p;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.outdoorlist);
		
		foodpic=(ImageView)findViewById(R.id.foodpic);
		Sname=(TextView)findViewById(R.id.shopname);
		Saddress=(TextView)findViewById(R.id.shopaddress);
		dishlist=(TextView)findViewById(R.id.dishes);
		Opinion=(TextView)findViewById(R.id.opinions);
		Score=(TextView)findViewById(R.id.rating);
		content=(EditText)findViewById(R.id.content);
		send=(Button)findViewById(R.id.leave);
		scroll=(ScrollView)findViewById(R.id.scroll);
		ll=(LinearLayout)findViewById(R.id.message);
		Bundle i=getIntent().getExtras();
		uacno=i.getString("uacno");
		p=i.getInt("count");
		faccount=i.getString("faccount");
		
		final ProgressDialog myDialog;		
  		myDialog = ProgressDialog.show
  				(
  						Outdoorlist.this,
  						"Loading...",
  						"Please Wait",
  						true
  						);
		new AsyncTask<Void, Void, String>() {
			String strResult = "";
			@Override
			protected String doInBackground(Void... params) {
				String mesResult="";
				try {
					// Create a new HttpClient and Post Header
					final HttpClient httpclient = new DefaultHttpClient();
					final HttpPost httppost = new HttpPost("http://120.126.16.38/showoutdoor.php");
					// Add your data
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair("uacno",uacno));

					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

					// Execute HTTP Post Request
					HttpResponse response = httpclient.execute(httppost);
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						strResult = EntityUtils.toString(response.getEntity());
						Log.v("log","rrrr "+strResult);

					} else {
						Log.v("response", String.valueOf(response.getStatusLine().getStatusCode()));
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
				} catch (IOException e) {
					// TODO Auto-generated catch block
				} catch (Exception e) {
					Log.e("Exception", e.toString());
				}
				Log.v("strResult", strResult);
				try {
					final HttpClient httpclient = new DefaultHttpClient();
					final HttpPost httppost = new HttpPost("http://120.126.16.38/addfoodmessage.php");
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair("uacno",uacno));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
					HttpResponse response = httpclient.execute(httppost);
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						mesResult = EntityUtils.toString(response.getEntity());
						Log.v("log","mes"+mesResult);
					} else {
						Log.v("response", String.valueOf(response.getStatusLine().getStatusCode()));
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
				} catch (IOException e) {
					// TODO Auto-generated catch block
				} catch (Exception e) {
					Log.e("Exception", e.toString());
				}
				Log.v("mesResult", mesResult);
				return mesResult;
			}

			@Override
			protected void onPostExecute(String result) {
				Log.v("mesresult",result);
				Log.v("inforresult",strResult);
				JSONArray jArray = null;
				JSONArray jArray2= null;
				try {
					  jArray = new JSONArray(result);
					  jArray2 = new JSONArray(strResult);
				} catch (JSONException e2) {
					e2.printStackTrace();
				}

				Log.v("jArray.length", String.valueOf(jArray.length()));
				Log.v("jArray2.length", String.valueOf(jArray2.length()));
				for (int i = 0; i < jArray2.length(); i++) 
				{ 
					JSONObject jsonObject = null;
					try {
						jsonObject = jArray2.getJSONObject(i);
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
					try {							
						    sn=jsonObject.getString("placename");
						    sa=jsonObject.getString("placeaddress");
						    op=jsonObject.getString("opinion");
						    sc=jsonObject.getString("score");
						    pic=jsonObject.getString("photo");
						    dish.add(jsonObject.getString("place"));						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				for (int i = 0; i < jArray.length(); i++) 
					{ 
						JSONObject jsonObject2 = null;
						try {
							jsonObject2 = jArray.getJSONObject(i);
						} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						} 
						try {
						    user.add(jsonObject2.getString("uname"));
						    Log.v(log, user.get(i));
						    message.add(jsonObject2.getString("content"));
						    Log.v(log, message.get(i));						    						
						} catch (JSONException e) {
						// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				send.setOnClickListener(new OnClickListener (){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if((content.getText().toString()!="")&&(content.getText().length()!=0))
						{
							messageThread();
							Log.v("log","銝��摰��");
							finish();
							startActivity(getIntent());
						}
						else
						{
							Toast.makeText(getBaseContext(),R.string.c_blank, Toast.LENGTH_LONG).show();
						}						
					}
				});
				set_View();	
				processThread1();
				set_Board();
				Log.v("log", "銝��摰��");
				myDialog.dismiss();	
			}
		}.execute();
	}
	private final void focusOnView1(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
            	scroll.scrollTo(0, ll.getBottom());            	
            }
        });      
    }
	public void set_View(){		 
		 Sname.setText(sn);
		 Log.v(log,"sn_"+ Sname.getText().toString());
		 //Log.v(log, pic);
		 Saddress.setText(sa);
		 //Log.v(log, Saddress.getText().toString());
		 Opinion.setText(op);
		 //Log.v(log,"op_"+ Opinion.getText().toString());
		 Score.setText(sc);
		 //Log.v(log,"sc_"+ Score.getText().toString());
		 for(int i=0;i<dish.size();i++)
		 {
			 dishlist.append(dish.get(i)+"\n");
		 }
	}
	public void set_Board(){
		if(message.size()!=0)
		{
			int count=message.size();
			TextView[] mes=new TextView[count];
			for(int i=0;i<count;i++)
			{
				final String account=user.get(i);
				final String content=message.get(i);
				mes[i]=new TextView(context);
				mes[i].setTextSize(18);
				mes[i].setText(account+":\r"+content);
				ll.addView(mes[i]);
			}
			
		}
		
	}
	private void processThread1()
	{
		// TODO Auto-generated catch block
				//pd= ProgressDialog.show(context, "Download File", "Downloading");
				Log.i("tag", "processThread()-->"+Thread.currentThread().getName());
				new Thread(){
					@Override
					public void run(){
						downloadFile(pic);
						Log.v("processThread", pic);
						handler1.sendEmptyMessage(0);
					}

				}.start();
	}
	private Handler handler1 =new Handler(){
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			foodpic.setImageBitmap(bitmap);
           Log.v(log,"銝��摰��");
			//pd.dismiss();
			// TODO Auto-generated catch block			
			Thread.currentThread().interrupt();
		}
	};

	protected void downloadFile(String address)
	{
		FTPClient ftp = new FTPClient();
		String server = "120.126.16.38";
		String username = "aloha";
		String password = "123456";
		String remotePath = "outpics";
		Log.v(log,address);
		try
		{			
			ftp.setDefaultTimeout(30000);
			ftp.connect(server, 21);
			int reply = ftp.getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply))  
			{
				ftp.disconnect();
				//showDialog("FTP connect failed");				
			}
			else{
				if(ftp.login(username, password))
				{
			        ftp.changeWorkingDirectory(remotePath);  
			        //ftp.setBufferSize(1024);
					ftp.enterLocalPassiveMode();
					ftp.setFileType(FTP.BINARY_FILE_TYPE);
					InputStream is = ftp.retrieveFileStream(address);
					//InputStream is = ftp.retrieveFileStream("sky@yahoo.com.tw_2.jpg");
					try
			        {
			          if (bitmap != null) 
			            bitmap.recycle();
			            bitmap = BitmapFactory.decodeStream(is);
			            //Log.v(log, "撌脫��唳�獢�");
			            
			        }
			          catch (Exception e)
			          {
			            e.printStackTrace();
			          }
                   is.close();
				}
				ftp.logout();
			}			
		}
		catch(Exception e)
		{
			//showDialog(""+e+"TYPE1");
		}
		finally
		{
			if(ftp.isConnected())
			{
				try{
					ftp.disconnect();
				}
				catch(Exception ioe)
				{
					//showDialog(""+ioe+"TYPE2");
				}
			}
		}
	}
	public void upload(){
		String a=content.getText().toString();
		String uriAPI = "http://120.126.16.38/addfoodmessage.php";
		HttpPost httpRequest = new HttpPost(uriAPI);
		List <NameValuePair> params = new ArrayList <NameValuePair>(); 
	    params.add(new BasicNameValuePair("uacno", uacno));
		params.add(new BasicNameValuePair("id",faccount));
		params.add(new BasicNameValuePair("message", a));
		Log.v("log","dddd"+a );
       try 
		{ 
			httpRequest.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			if(httpResponse.getStatusLine().getStatusCode() == 200)  
			{ 
				String strResult1 = EntityUtils.toString(httpResponse.getEntity());
				Log.v("strResult1", strResult1);
			}
			else 
			{ 
				//mTextView1.setText("Error Response: "+httpResponse.getStatusLine().toString()); 
			} 

			httpclient.getConnectionManager().shutdown();

		} 
		catch (ClientProtocolException e) 
		{  
			//mTextView1.setText(e.getMessage().toString()); 
			e.printStackTrace(); 
		} 
		catch (IOException e) 
		{  
			//mTextView1.setText(e.getMessage().toString()); 
			e.printStackTrace(); 
		} 
		catch (Exception e) 
		{  
			//mTextView1.setText(e.getMessage().toString()); 
			e.printStackTrace();  
		} 		
	}
	private Handler Handler2 =new Handler(){
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			
			content.setText("");
			//pd.dismiss();
			Toast.makeText(getBaseContext(),R.string.c_upsuccess,Toast.LENGTH_LONG).show(); 
			Thread.currentThread().interrupt();
		}
	};
	public void messageThread(){
		//pd= ProgressDialog.show(getBaseContext(), "UploadMessage", "Uploading");
		Log.v("tag", "processThread()-->"+Thread.currentThread().getName());
	
		new Thread(){
		
		@Override
		public void run(){
			upload();
			Handler2.sendEmptyMessage(0);
		}
		
	}.start();		
	}
}

