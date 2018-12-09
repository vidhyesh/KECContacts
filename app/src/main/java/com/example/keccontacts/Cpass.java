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

import com.example.keccontacts.UserEdit.loginAccess;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Cpass extends Activity {

	EditText cpass,npass1,npass2;
	String crpass,nrpass,stid;
	private Activity activity;
	Button updat,cancell;
	private Dialog alertMode;
	private ProgressDialog pDialog;
	private int screenHeight, width;
	JSONObject json;
	int flag=0;
	Cursor c,c1;
	Database db,db1;
	private static final String TAG_SUCCESS = "success"; 
	private static final String TAG_RESULT="result";
	JSONParser jsonParser = new JSONParser();
	JSONArray result = null;
	private static String url = "http://192.168.173.1/and/chpass.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity=this;
		setContentView(R.layout.activity_cpass);
		db=new Database(this);
		c = db.getUser();
		System.out.println(c.getCount());

		for (int i = 0; i < c.getCount(); i++) {
			if (c.moveToPosition(i)) {
				stid = c.getString(c.getColumnIndex(Database.sid));
				
			}
		}
		
		if(!isOnline(Cpass.this))
		{	
		
		 Toast.makeText(Cpass.this,"No Network Connection...", Toast.LENGTH_SHORT).show();
							
			finish();	
		}
	 else
	 {
		 getpass();
	 }
		}
	public void getpass()
	{
		
		if (alertMode != null && alertMode.isShowing())
			return;
		
		
		alertMode = new Dialog(activity);
		alertMode.getWindow();
		alertMode.requestWindowFeature(Window.FEATURE_NO_TITLE);

		alertMode.setContentView(R.layout.getpass);
		

		cpass=(EditText)alertMode.findViewById(R.id.cpass);
		npass1=(EditText)alertMode.findViewById(R.id.npass1);
		npass2=(EditText)alertMode.findViewById(R.id.npass2);
		updat = (Button) alertMode.findViewById(R.id.update);
		
		
		updat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//String stpass1=spass.getText().toString();
				//Toast.makeText(UserEdit.this,stpass1, Toast.LENGTH_LONG).show();
				String ctpass=cpass.getText().toString();
				String ntpass1=npass1.getText().toString();
				String ntpass2=npass2.getText().toString();
				if(ctpass.toString().trim().equals(""))
				{
					Toast.makeText(Cpass.this,"Enter Current Password", Toast.LENGTH_SHORT).show();
					return;
				}
				else if(ntpass1.toString().trim().equals(""))
				{
					Toast.makeText(Cpass.this,"Enter New Password", Toast.LENGTH_SHORT).show();
					return;
				}
				else if(ntpass2.toString().trim().equals(""))
				{
					Toast.makeText(Cpass.this,"Enter New Password", Toast.LENGTH_SHORT).show();
					return;
				}
				else if(!ntpass2.toString().trim().equals(ntpass1.toString().trim()))
				{
					Toast.makeText(Cpass.this,"Password Not Match", Toast.LENGTH_SHORT).show();
					return;
				}
				else
				{
				
				//	Toast.makeText(Cpass.this,"Match "+stid, Toast.LENGTH_LONG).show();
				new loginAccess().execute();
				}
			}
		});
		
			cancell= (Button) alertMode.findViewById(R.id.cancel);
		
		
		cancell.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alertMode.dismiss();
				finish();
			}
		});
		//ids = tag;

		
		Display display = activity.getWindowManager().getDefaultDisplay();

		width = display.getWidth();
		screenHeight = display.getHeight();

		alertMode.getWindow().setLayout(width, (screenHeight /2));
		// alertMode.getWindow().setBackgroundDrawable(new
		// ColorDrawable(android.graphics.Color.TRANSPARENT));
		alertMode.show();

		//finish();
	

	}
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event)
	{
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			//moveTaskToBack(true);
			Toast.makeText(Cpass.this,"Back", Toast.LENGTH_SHORT).show();
			alertMode.dismiss();
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
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
	
	class loginAccess extends AsyncTask<String, String, String> {

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Cpass.this);
			pDialog.setMessage("Change Password...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		public void showlist()
		{
			try{
			result = json.getJSONArray(TAG_RESULT);
        	JSONObject c2 = result.getJSONObject(0);
        	String msg = c2.getString("status");
        	
        	int ind=Integer.parseInt(c2.getString("success"));
        	
        	
        	 
        	//Log.d("Create Response", json.toString());
        	Toast.makeText(Cpass.this,msg, Toast.LENGTH_SHORT).show();
        	
        	
        	if(ind==1)
        	{
        	//Intent i = new Intent(getApplicationContext(), Home.class);
    		finish();
    	//	startActivity(i);
        	}
        	else
        	{
        		
        		Intent i = new Intent(getApplicationContext(), Cpass.class);
	    		finish();
	    		startActivity(i);
	    		        	
        	}
			} catch (Exception e) {
	            e.printStackTrace();
	            if(flag==0)
	            Toast.makeText(Cpass.this,"Connection Failed...", Toast.LENGTH_SHORT).show();
	            
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
			crpass=cpass.getText().toString().trim();
			nrpass=npass1.getText().toString().trim();
			params.add(new BasicNameValuePair("stid", stid));
			params.add(new BasicNameValuePair("cpass", crpass));
			params.add(new BasicNameValuePair("npass", nrpass));
			
			json = jsonParser.makeHttpRequest(url,"POST", params);
			
			Log.d("Create Response", json.toString());
			
			
			} catch (Exception e) {
				e.printStackTrace();
				flag=1;
				//Toast.makeText(UserEdit.this,"Connection failed", Toast.LENGTH_LONG).show();
			}
			return null;
		}
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			showlist();
			
			if(flag==1)
			  Toast.makeText(Cpass.this,"Connection Failed...", Toast.LENGTH_SHORT).show();
			 
				
		}
		
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.cpass, menu);
		return true;
	}

}
