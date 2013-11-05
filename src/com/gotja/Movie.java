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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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

public class Movie extends Activity {
	ListView mListView;
	ImageView img;
	int p;
	private String[] movieName = new String[5];
	private String[] releaseTime = new String[5];
	ArrayList<String> imgurl = new ArrayList<String>();
	ArrayList<Bitmap> imgname = new ArrayList<Bitmap>();
	String id;
	String im;
	DisplayImageOptions options;
	ImageLoader imageLoader;
	ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie);
        options = new DisplayImageOptions.Builder()
     	.showStubImage(R.drawable.load)
     	.showImageForEmptyUri(R.drawable.load)
		.cacheInMemory()
		.cacheOnDisc()
		.resetViewBeforeLoading()
		.build();
     ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
		.threadPoolSize(3) //圖片的pool size
		.threadPriority(Thread.NORM_PRIORITY - 2) //優先權
		.memoryCacheSize(1500000) // 1.5 Mb
		.denyCacheImageMultipleSizesInMemory() //緩存不同大小相同的圖片
		.discCacheFileNameGenerator(new Md5FileNameGenerator()) //緩存文件名字 利用MD5存URL生成唯一名字
		.enableLogging() // Not necessary in common 查看錯誤
		.build();    
     imageLoader = ImageLoader.getInstance();
     imageLoader.init(config);
     Bundle b=getIntent().getExtras();
	 id=b.getString("id");
     mListView = (ListView)findViewById(R.id.listview);

    final ProgressDialog myDialog;		
		myDialog = ProgressDialog.show
				(
						Movie.this,
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
					final HttpPost httppost = new HttpPost("http://120.126.16.38/movieRanking.php");
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);					
					nameValuePairs.add(new BasicNameValuePair("id",id));
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
							movieName[i] = jsonObject.getString("nmname");
							Log.v("log","movieName "+movieName[i]);				
							releaseTime[i] = jsonObject.getString("movieuptime").split(" ")[0];
							Log.v("log","movietime "+releaseTime[i]);
							imgurl.add(jsonObject.getString("poster_url"));
							im=jsonObject.getString("poster_url");
							Log.v("log","movieurl "+imgurl.get(i));
							if(imgurl.get(i).equals("")==true)
		              	   {
								imgurl.set(i, "http://120.126.16.38/movie/movie.png") ; 
		              	   }							
							mListView.setAdapter(new MyAdapter());
							Log.v("log","ok "+String.valueOf(imgurl.size()));
					} catch (JSONException e) {
						e.printStackTrace();
					}									
			
		      }		
				myDialog.dismiss();	
			}				
		}.execute();		
//=======================================================================
        mListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				 final int p=position;
			       Log.v("log",movieName[p]);
			       new AlertDialog.Builder(Movie.this)
	                .setTitle(movieName[p])
	                .setMessage(getString(R.string.choose_function))
	                .setPositiveButton(getString(R.string.get_group), new DialogInterface.OnClickListener() {
	                    @Override 
	                    public void onClick(DialogInterface dialog, int which) {
	                    	Bundle bundle1 =getIntent().getExtras();
	    					String id=bundle1.getString("id");

	                    	Bundle bundle = new Bundle();					
	   					 	bundle.putString("name", movieName[p]);	   					 
	   					 	bundle.putString("id", id);	  
	   					 	Intent intent=new Intent(Movie.this,com.example.addactivity.Addmovie.class);
	   					 	
	   					 	intent.putExtras(bundle);
	   					 	startActivity(intent);	
	   					 	finish();	                    	
	                    } 
	                })
	                .setNegativeButton(getString(R.string.get_search), new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                       String searchURL = "http://www.google.com.tw/movies?near=台北市&q=" +movieName[p];
	     			       Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(searchURL));
	     			       startActivity(browserIntent);	                    	
	                    }
	                })
	                .show();
			}
		});	
    }
    
    //==============================================================
   class MyAdapter extends BaseAdapter{  
        @Override  
        public int getCount() {  
            return imgurl.size();  
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
                    (R.layout.list_temp,null);  
        			TextView mTextView = (TextView)convertView.findViewById(R.id.movieName);
        			mTextView.setText(movieName[position]);
                    TextView Time = (TextView)convertView.findViewById(R.id.releaseTime);
                    Time.setText(releaseTime[position]);
                    img= (ImageView)convertView.findViewById(R.id.img);
     
                   imageLoader.displayImage( imgurl.get(position), img, options, animateFirstListener);
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