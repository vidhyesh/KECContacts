package com.example.keccontacts;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

public class Loadd extends Activity {
	
	private Activity activity;
	Button b1,b2,b3,b4;
	Database db,db1,db2,db3,db4,db5;
	Cursor c;
	boolean ckval;
	private Dialog alertMode;
	protected String id1, ids;
	private int screenHeight, width;
	private ProgressDialog pDialog;
	int flag=0;
	JSONParser jsonParser = new JSONParser();
	private static String url = "http://192.168.137.1/kecc/and/login.php";
    private static final String TAG_STAFFS="staffs";
    private static final String TAG_RESULT="result";
    private static final String TAG_USER="user";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_DEPT ="dept";
    private static final String TAG_TYPE ="type";
    private static final String TAG_DESI ="desi";
    private static final String TAG_MOB ="mob";
    private static final String TAG_MAIL ="mail";
  
  	JSONArray staffs = null;
  	JSONArray result = null;
  	JSONArray user = null;
  	JSONArray months = null;
  	Cursor c1,c3,c4,c5;
	JSONObject json,json1,json2;
	EditText id,pass;
	String ssid,sname,sdept,smob,smail;
	static final String month_table = "MONTH_INFO";
	static final String month = "month";
	static final String month1 = "month1";
	static final String dat = "dat";
	static final String day = "day";
	static final String event = "event";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loadd);
		activity=this;
		
		db = new Database(this);
		db1=new Database(this);
		db2=new Database(this);
		db3 = new Database(this);
		db4 = new Database(this);
		db5 = new Database(this);
		ckval = db.ckstaff();
		if(ckval==false)
		{
			 if(!isOnline(Loadd.this))
				{	
		//		 Intent i = new Intent(getApplicationContext(), EmptyActivity.class);
			//		startActivity(i);
				//	this.finish();
				 Toast.makeText(Loadd.this,"No Network Connection...", Toast.LENGTH_SHORT).show();
					Toast.makeText(Loadd.this,"This is First Time After Installation\nPlease Connect Net and Try Again", Toast.LENGTH_SHORT).show();
					
					//this.finish();	
				}
			 else
			 {
			Toast.makeText(Loadd.this,"This is First Time", Toast.LENGTH_SHORT).show();
			firsttime();
			 }
		}
		else
		{
			this.finish();
			Intent i = new Intent(getApplicationContext(), Home.class);
			//Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i);
		}
		/*
		RelativeLayout rlayout=(RelativeLayout)findViewById(R.id.homelayout);
		
		rlayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});*/
			
	}
	private boolean isOnline(Context mContext) {
		ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting())
	   	  {
			return true;
     		}
		    return false;
       	}
	public void firsttime()
	{
		if (alertMode != null && alertMode.isShowing())
			return;
		alertMode = new Dialog(activity);
		alertMode.getWindow();
		alertMode.requestWindowFeature(Window.FEATURE_NO_TITLE);

		alertMode.setContentView(R.layout.edit);


		id=(EditText)alertMode.findViewById(R.id.logid);
				pass=(EditText)alertMode.findViewById(R.id.npass2);
		Button edit = (Button) alertMode.findViewById(R.id.update);
		Button cancel = (Button) alertMode.findViewById(R.id.cancel);
		

		
		edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String sid=id.getText().toString();
				String spass=pass.getText().toString();
				if(sid.toString().trim().equals("")||spass.toString().trim().equals(""))
				{
					Toast.makeText(Loadd.this,"Please Enter id and password", Toast.LENGTH_SHORT).show();
					//finish();
					//Intent i = new Intent(getApplicationContext(), Home.class);
					//startActivity(i);
					firsttime();
					return;
				}
				else
				{
					new loginAccess().execute();
				}
			alertMode.dismiss();
				}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
	
				alertMode.dismiss();
				//firsttime();
			finish();
				//Intent i = new Intent(getApplicationContext(), Home.class);
				//startActivity(i);
			}
		});
		
		
		//ids = tag;

		Display display = activity.getWindowManager().getDefaultDisplay();

		width = display.getWidth();
		screenHeight = display.getHeight();

		alertMode.getWindow().setLayout(width, (screenHeight / 7)*4);
		// alertMode.getWindow().setBackgroundDrawable(new
		// ColorDrawable(android.graphics.Color.TRANSPARENT));
		alertMode.show();

		//finish();
	

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		ckval = db.ckstaff();
		if(ckval==false)
		{
			return true;
		}
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.home, menu);
		return true;
	}

	class loginAccess extends AsyncTask<String, String, String> {

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Loadd.this);
			pDialog.setMessage("Updating...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
		    pDialog.show();
		}
		
		   protected void showList(){
			  
		        try {
		            //JSONObject jsonObj = new JSONObject(myJSON);
		        	
		        	result = json.getJSONArray(TAG_RESULT);
		        	JSONObject c2 = result.getJSONObject(0);
		        	
		        	String msg = c2.getString("status");
		        	
		        	int ind=Integer.parseInt(c2.getString("iid")); 
		        	//Log.d("Create Response", json.toString());
		        	Toast.makeText(Loadd.this,msg, Toast.LENGTH_LONG).show();
		        	if(ind==5)
		        	{
		          staffs = json.getJSONArray(TAG_STAFFS);
		              for(int i=0;i<staffs.length();i++){
		                JSONObject c = staffs.getJSONObject(i);
		                String id = c.getString(TAG_ID);
		                String name = c.getString(TAG_NAME);
		                String dept = c.getString(TAG_DEPT);
		                String type = c.getString(TAG_TYPE);
		                String desi = c.getString(TAG_DESI);
		                String mob = c.getString(TAG_MOB);
		                String mail = c.getString(TAG_MAIL);
		                    
		                db1.Add_Staff(id.toString() ,name.toString(),dept.toString(),type.toString(),desi.toString(),mob.toString(),mail.toString());
		                //db.Add_Staff("1","2","3","4","5","6","7","8","9","10","11");
		        		c1 = db1.getstaffs();
		              }
		        		user = json.getJSONArray(TAG_USER);
		        		for(int i=0;i<user.length();i++){ 
			                JSONObject c = user.getJSONObject(i);
			                String id = c.getString(TAG_ID);
			                String name = c.getString(TAG_NAME);
			                String dept = c.getString(TAG_DEPT);
			                String type = c.getString(TAG_TYPE);
			                String desi = c.getString(TAG_DESI);
			                String mob = c.getString(TAG_MOB);
			                String mail = c.getString(TAG_MAIL);
			                 
			                db2.Add_User(id.toString() ,name.toString(),dept.toString(),type.toString(),desi.toString(),mob.toString(),mail.toString());
			                //db.Add_Staff("1","2","3","4","5","6","7","8","9","10","11");
			        		c3 = db2.getUser();
		        		}
		        		
		        	        
		        		
		            Toast.makeText(Loadd.this,"Record Updated", Toast.LENGTH_SHORT).show();
		            Intent i = new Intent(getApplicationContext(), Loadd.class);
		    		finish();
		    		startActivity(i);
		    		
		        	}
		        	else
		        	{
		        		firsttime();
		        	}
		    		c1.close();
		    		db1.close();
		 
		        } catch (Exception e) {
		            e.printStackTrace();
		            if(flag==0)
		            Toast.makeText(Loadd.this,"Connection Failed...", Toast.LENGTH_SHORT).show();
		            return;
		            //Intent i = new Intent(getApplicationContext(), EmptyActivity.class);
		    		//finish();
		    		//startActivity(i);
		        }
		 
		    }
		   private void executeReq(URL url) throws IOException {
				// TODO Auto-generated method stub
				
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setReadTimeout(6000);
				con.setConnectTimeout(6500);
				con.setRequestMethod("GET");
				con.setDoInput(true);
				
				// Connect
				con.connect();
			}
		@Override
		protected String doInBackground(String... arg0) {
		
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			try {
				URL ip = new URL(url);
				executeReq(ip);
				
			String sid=id.getText().toString();
			String spass=pass.getText().toString();
		params.add(new BasicNameValuePair("sid", sid));
		params.add(new BasicNameValuePair("spass", spass));
		
			
	 json = jsonParser.makeHttpRequest(url,"POST", params);
			  //peoples = json.getJSONArray(TAG_RESULTS);
			
			Log.d("Create Response", json.toString());
			
		
				              
		               
		                
		

			} catch (Exception e) {
				e.printStackTrace();
				flag=1;
				return null;
			}
			
			
			return null;
			
		}
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			
			if(flag==1)
				Toast.makeText(Loadd.this,"Connection failed...", Toast.LENGTH_SHORT).show();
			showList();
				
		}
		
	  }
	
	
    
    
}
