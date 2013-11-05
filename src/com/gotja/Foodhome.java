package com.gotja;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class Foodhome extends Activity {
	ListView mListView;
	ImageView img; 
	int p;
	MyAdapter mMyAdapter;
	ArrayList<String> sname = new ArrayList<String>();
	ArrayList<String> score = new ArrayList<String>();
	ArrayList<String> shopAddress = new ArrayList<String>();
	ArrayList<String> imgurl = new ArrayList<String>();
	ArrayList<Bitmap> imgname = new ArrayList<Bitmap>();
	ArrayList<String> uacno =new ArrayList<String>();
	String userid;
	DisplayImageOptions options;
	ImageLoader imageLoader;
	ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodhome);
        options = new DisplayImageOptions.Builder()
     	.showStubImage(R.drawable.load)
     	.showImageForEmptyUri(R.drawable.load)
		.cacheInMemory()
		.cacheOnDisc()
		.resetViewBeforeLoading()
		.build();
     ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
		.threadPoolSize(3) //�����ool size
		.threadPriority(Thread.NORM_PRIORITY - 2) //�芸�甈�
		.memoryCacheSize(1500000) // 1.5 Mb
		.denyCacheImageMultipleSizesInMemory() //蝺拙�銝��憭批��詨������
		.discCacheFileNameGenerator(new Md5FileNameGenerator()) //蝺拙���辣��� �拍�MD5摮�RL����臭����
		.enableLogging() // Not necessary in common �亦��航炊
		.build();    
     imageLoader = ImageLoader.getInstance();
     imageLoader.init(config);
     
    
        mListView = (ListView)findViewById(R.id.listview);
        
        Bundle b=getIntent().getExtras();
        userid=b.getString("id");
   
   
        final ProgressDialog myDialog;		
      		myDialog = ProgressDialog.show
      				(
      						Foodhome.this,
      						"Loading...",
      						"Please Wait",
      						true
      						);
           new AsyncTask<Void, Void, String>() {
      			@Override
      			protected String doInBackground(Void... params) {
      				String strResult = "";
      				try {
      					final HttpClient httpclient = new DefaultHttpClient();
      					final HttpPost httppost = new HttpPost("http://120.126.16.38/foodadvice.php");
      					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);					
      					nameValuePairs.add(new BasicNameValuePair("uaccount",userid));
      					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
      					HttpResponse response = httpclient.execute(httppost);
      					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
      						strResult = EntityUtils.toString(response.getEntity());
      						Log.v("log",strResult);																		
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
      				return strResult;
      			}

      			@Override
      			protected void onPostExecute(String result) {
      				Log.v("result",result);
      				JSONArray jArray = null;
      				  
      				try {
      					jArray = new JSONArray(result);
      				} catch (JSONException e2) {
      					e2.printStackTrace();
      				}
      				for (int i = 0; i < jArray.length(); i++) { 
      					JSONObject jsonObject = null;
      					try {
      						jsonObject = jArray.getJSONObject(i);
      					} catch (JSONException e1) {
      						e1.printStackTrace();
      					} 
      					try {
      						uacno.add(jsonObject.getString("uacno"));
      		        	    sname.add(jsonObject.getString("shopname"));					
      						score.add(jsonObject.getString("score"));
      						shopAddress.add(jsonObject.getString("shopaddress"));	
      						imgurl.add(jsonObject.getString("photo"));		
      						mListView.setAdapter(new MyAdapter());
      					} catch (JSONException e) {
      						e.printStackTrace();
      					}									
      			
      		      }		
      				myDialog.dismiss();	
      			}				
      		}.execute();
      	     mListView.setOnItemClickListener(new OnItemClickListener(){
     			@Override
     			public void onItemClick(AdapterView<?> arg0, View arg1,
     					int arg2, long arg3) {
     				// TODO Auto-generated method stub
     				Intent i=new Intent(Foodhome.this,Foodlist.class);
     				p=0;
     				i.putExtra("faccount", userid);
     				i.putExtra("uacno",uacno.get(arg2));
     				i.putExtra("count",p);
     				Log.v("log", uacno.get(arg2));
     				Log.v("log", "faccount "+ userid);
     				Log.v("log", "count "+p);
     				startActivity(i);
     			}
     		});
	}
    //==============================================================
   class MyAdapter extends BaseAdapter{  
        @Override  
        public int getCount() {  
            return sname.size();  
        }  
        @Override  
        public Object getItem(int arg0) {  
            return arg0;  
        }  
        @Override  
        public long getItemId(int position) {  
            return position;  
        }  
        @Override  
        public View getView(int position, View convertView, ViewGroup parent) {  
        	convertView = LayoutInflater.from(getApplicationContext()).inflate  
                    (R.layout.outdoor_listitem,null);  
        	          p=position;
                   //   Log.v("log","pos "+Integer.toString(position));
                   //   Log.v("log","pos "+sname.get(position));
                   //   Log.v("log","pos "+score.get(position));
                   //   Log.v("log","pos "+shopAddress.get(position));
                   //   Log.v("log","pos "+imgurl.get(p));
        			TextView mTextView = (TextView)convertView.findViewById(R.id.shopName);
        			mTextView.setText(sname.get(position));
                    TextView Time = (TextView)convertView.findViewById(R.id.rating);
                    Time.setText(score.get(position));
                    TextView Address = (TextView)convertView.findViewById(R.id.shopAddress);
                    Address.setText(shopAddress.get(position));
                    img= (ImageView)convertView.findViewById(R.id.img);
                    if(imgurl.get(p).equals("")==true){
                   img.setBackgroundResource(R.drawable.foodpic);
                    }else{
                    	 imageLoader.displayImage( "http://120.126.16.38/foodpics/"+imgurl.get(p), img, options, animateFirstListener);
                    }
          return convertView;  
        }           
    }  
    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
				} else {
					imageView.setImageBitmap(loadedImage);
				}
				displayedImages.add(imageUri);
			}
		}
	}
}