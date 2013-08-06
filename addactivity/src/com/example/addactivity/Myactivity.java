package com.example.addactivity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class Myactivity extends Fragment {
	static private ListView listInput;
	static ArrayAdapter<String> adapter;
	private View v;
	static ArrayList<String> items=new ArrayList();
	static ArrayList<String> uacnoarray=new ArrayList();
	static  int m,nf0=0,nf1=0,nf2=0,n0=0,n1=0,n2=0,n3=0;
	static String s,uacno,utemp,mine;
	static String[] arrayuacno;
	static String[] arrayutemp;
	static String[] arraymine;
	Button btnadd;
	Button btnmap;
	private static String addid;
	static String utemptype=null;

	static String uriAPI = "http://120.126.16.38/myactivity.php";
	String[] selecttypes = null;//声明变量
	//初始化
	void init() {
		selecttypes = new String[]{getString(R.string.all), getString(R.string.unresponse)
				, getString(R.string.joined),getString(R.string.rejected)};
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		

		Log.v("log","oncreate");
	}
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.activity_myactivity, container, false);

		addid = getArguments().getString("userid");
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		.detectDiskReads()
		.detectDiskWrites()
		.detectNetwork()   // or .detectAll() for all detectable problems
		.penaltyLog()
		.build());

		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		.detectLeakedSqlLiteObjects()
		.penaltyLog()
		.penaltyDeath()
		.build()); 

		//Spinner 
		init();     
		Spinner spn = (Spinner) v.findViewById(R.id.spn);
		ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, selecttypes);
		LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spn.setAdapter(LTRadapter);
		spn.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String sel=arg0.getSelectedItem().toString();
				String type=sel;
				Log.v("log",type);	

				if(type.equals(getString(R.string.all))==true)
				{
					spncontent(null);       					
				}

				if(type.equals(getString(R.string.unresponse))==true)
				{
					spncontent("0");      
				}

				if(type.equals(getString(R.string.joined))==true)
				{   
					spncontent("1");
				}
				if(type.equals(getString(R.string.rejected))==true)
				{
					spncontent("3");
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		}); 
		//btn
		btnadd = (Button)v.findViewById(R.id.btnadd);
		btnadd.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intentadd;

				intentadd = new Intent(getActivity(),Selectproperty.class);
				Bundle bundle1 = new Bundle();
				bundle1.putString("id", addid);

				intentadd.putExtras(bundle1);
				startActivity(intentadd);	

			}

		});
		btnmap = (Button)v.findViewById(R.id.btnmap);
		btnmap.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intentmap; 
				intentmap = new Intent(getActivity(),MAP.class);
				startActivity(intentmap);	
			}        	
		});
		//===================================================================			
		//listview	 			 
		listInput = (ListView)v.findViewById(R.id.listView1);
		adapter = new MyCustomAdapter(getActivity(),android.R.layout.simple_list_item_1,items);
		listInput.setAdapter(adapter);

		listInput.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
				// TODO Auto-generated method stub
				final int pos = position;
				new AlertDialog.Builder(getActivity())
				.setTitle(getString(R.string.cancel))
				.setMessage(getString(R.string.cancel_correct))
				.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String[] u;               		                    

						HttpPost httpRequest = new HttpPost("http://120.126.16.38/delete.php");
						List <NameValuePair> params = new ArrayList <NameValuePair>();
						params.add(new BasicNameValuePair("id", addid));
						params.add(new BasicNameValuePair("uacno", uacnoarray.get(pos)));
						try 
						{   
							httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
							HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
							if(httpResponse.getStatusLine().getStatusCode() == 200) 
							{ 

								String strResult = EntityUtils.toString(httpResponse.getEntity());
							}  
							else 
							{ 
								s="Error Response: "+httpResponse.getStatusLine().toString(); 
							} 
						}   
						catch (Exception e) 
						{   
							s=e.getMessage().toString(); 
							e.printStackTrace();  
						}
						//======================================  
						uacnoarray.remove(pos);
						items.remove(pos);
						listInput.setAdapter(adapter);
					}
				})
				.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				})
				.show();
				return false;
			}
		});	 

		listInput.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				String sel=parent.getItemAtPosition(position).toString(); 

				Intent intent;
				Intent intentjoin;
				Intent intentjoin_organizer;

				intent = new Intent(getActivity(),Invitedac.class); 
				intentjoin = new Intent(getActivity(),Myjoinac.class);
				intentjoin_organizer = new Intent(getActivity(),Myjoinac_organizer.class);

				Bundle bundle = new Bundle();
				bundle.putString("uacno", arrayuacno[position]);
				bundle.putString("id", addid);//使用者,不是主揪
				bundle.putString("utemp", arrayutemp[position]);

				int u=Integer.parseInt(arrayutemp[position]);				
				if(u==1 )
				{ 
					m=Integer.parseInt(arraymine[position]);
					if(m==0){
						intentjoin.putExtras(bundle);
						startActivity(intentjoin);	
					}
					else
					{ 
						intentjoin_organizer.putExtras(bundle);
						startActivity(intentjoin_organizer);	
					}
				} 

				else{
					intent.putExtras(bundle);
					startActivity(intent);	
				}

			}
		});
		//====================================================偵測listview是否有滾動     
		listInput.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}
			@Override
			public void onScrollStateChanged(AbsListView view,
					int scrollState) {
				RelativeLayout rr=( RelativeLayout)v.findViewById(R.id.rr);
				// TODO Auto-generated method stub
				if (scrollState == SCROLL_STATE_FLING || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
					rr.setVisibility(View.GONE);
				}
				else{
					rr.setVisibility(View.VISIBLE);
				}

			}

		});
		return v;
	}


	//================================================================

	private static int spncontent(String value1) {

		utemptype=value1;   
		items.clear();
		listInput.setAdapter(adapter);
		n1=0;
		n2=0;
		n3=0;
		n0=0;
		HttpPost httpRequest = new HttpPost(uriAPI);
		List <NameValuePair> params = new ArrayList <NameValuePair>();
		params.add(new BasicNameValuePair("faccount", addid));
		params.add(new BasicNameValuePair("utemp", utemptype));
		try 
		{  
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
			if(httpResponse.getStatusLine().getStatusCode() == 200) 
			{ 

				String strResult = EntityUtils.toString(httpResponse.getEntity()); 
				JSONArray result = new JSONArray(strResult);
				// ttt.setText("Number of Entries"+Integer.toString(result.length()));
				arrayuacno=new String[result.length()];
				arrayutemp=new String[result.length()];
				arraymine=new String[result.length()];
				for (int i = 0; i < result.length(); i++) {

					JSONObject jsonObject = result.getJSONObject(i);
					String uno=jsonObject.getString("uname");
					String name=jsonObject.getString("name");
					utemp=jsonObject.getString("utemp");
					mine=jsonObject.getString("mine");
					uacno=jsonObject.getString("uacno");
					if(utemp.equals("0")==true)
					{
						n0++;     

					}else if(utemp.equals("1")==true)
					{
						n1++; 

					}else if(utemp.equals("2")==true)
					{
						n2++;        	
					}
					else 
					{
						n3++;        	
					}

					arrayuacno[i]=uacno;
					arrayutemp[i]=utemp;
					arraymine[i]=mine;
					s=name;
					uacnoarray.add(uacno);
					items.add(s);        	  
					listInput.setAdapter(adapter);    
				}  
				nf0=n0;
				nf1=n1+n0;
				nf2=n2+n1+n0;

			}  
			else 
			{ 
				s="Error Response: "+httpResponse.getStatusLine().toString(); 
			} 
		}   
		catch (Exception e) 
		{   
			s=e.getMessage().toString(); 
			e.printStackTrace();  
		}  

		return 0;
	}
	//================================================================
	public class MyCustomAdapter extends ArrayAdapter<String> {   	 
		public MyCustomAdapter(Context context, int textViewResourceId,
				ArrayList<String> items) {
			super(context, textViewResourceId, items);  	 
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {  
			//View view = super.getView(position, convertView, parent);  

			TextView mTextView = new TextView(getActivity()); 
			mTextView.setTextColor(Color.BLACK);
			mTextView.setHeight(50);
			mTextView.setGravity(Gravity.CENTER);
			mTextView.setTextSize(50);
			mTextView.setText(items.get(position));
			if (position < nf0) {
				mTextView.setBackgroundResource(R.drawable.list__notyet);

			} else if(nf0 <= position && position < nf1
					){
				// mTextView.setBackgroundResource(R.layout.list_bg1); 
				mTextView.setBackgroundResource(R.drawable.list_attend);

			}else if(nf1 <= position && position < nf2)
			{
				mTextView.setBackgroundResource(R.drawable.list_read);
			}
			else { 
				mTextView.setBackgroundResource(R.drawable.list_reject);
			}
			return mTextView;
		}
	}//MyCustomAdapter
}