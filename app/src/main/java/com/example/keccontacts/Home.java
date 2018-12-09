package com.example.keccontacts;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Home extends Activity {

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
	private static String url = "http://192.168.137.1/kecc/and/chpass.php";
	ImageButton bt1,bt2,bt3,bt4,bt5,bt6,bt7;
	TextView webs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	
		db=new Database(this);
		
		activity=this;
		bt1=(ImageButton)findViewById(R.id.imgbt1);
		bt1.setOnClickListener(new View.OnClickListener() 
        {		
			@Override
			public void onClick(View view) {
					
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
	        	startActivity(i);
				
			}
		});
		
		bt2=(ImageButton)findViewById(R.id.imgbt2);
		bt2.setOnClickListener(new View.OnClickListener() 
        {		
			@Override
			public void onClick(View view) {
					
				Intent i = new Intent(getApplicationContext(), Office.class);
	        	startActivity(i);
				
			}
		});
		
		bt3=(ImageButton)findViewById(R.id.imgbt3);
		bt3.setOnClickListener(new View.OnClickListener() 
        {		
			@Override
			public void onClick(View view) {
					
				 Intent i2 = new Intent(getApplicationContext(), Department.class);
					//Intent i = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(i2);
				
			}
		});
		
		bt4=(ImageButton)findViewById(R.id.imgbt4);
		bt4.setOnClickListener(new View.OnClickListener() 
        {		
			@Override
			public void onClick(View view) {
					

	        	Intent ii = new Intent(getApplicationContext(), UserItemView.class);
				startActivity(ii);


				
			}
		});
		
		bt5=(ImageButton)findViewById(R.id.imgbt5);
		bt5.setOnClickListener(new View.OnClickListener() 
        {		
			@Override
			public void onClick(View view) {
					
				c = db.getUser();
				System.out.println(c.getCount());

				for (int i = 0; i < c.getCount(); i++) {
					if (c.moveToPosition(i)) {
						stid = c.getString(c.getColumnIndex(Database.sid));
						
					}
				}
				
				if(!isOnline(Home.this))
				{	
				
				 Toast.makeText(Home.this,"No Network Connection...", Toast.LENGTH_SHORT).show();
									
					//finish();	
				}
			 else
			 {
				 getpass();
			 }
				
			}
		});
		
		bt6=(ImageButton)findViewById(R.id.imgbt6);
		bt6.setOnClickListener(new View.OnClickListener() 
        {		
			@Override
			public void onClick(View view) {
					
				Intent i3 = new Intent(getApplicationContext(), UpdateData.class);
				startService(i3);
				
			}
		});
		
		bt7=(ImageButton)findViewById(R.id.imgbt7);
		bt7.setOnClickListener(new View.OnClickListener() 
        {		
			@Override
			public void onClick(View view) {
				 Intent i = new Intent(getApplicationContext(), About.class);
					//Intent i = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(i);
				
				  
				
			}
		});
		
		
		webs=(TextView)findViewById(R.id.webss);
		
		String htmlString="Website: <u>www.kongu.ac.in</u>";
		webs.setText(Html.fromHtml(htmlString));
		webs.setOnClickListener(new View.OnClickListener() 
        {		
			@Override
			public void onClick(View view) {
					
				if(!isOnline(Home.this))
				{	
				
				 Toast.makeText(Home.this,"No Network Connection...", Toast.LENGTH_SHORT).show();
									
					//finish();	
				}
			 else
			 {
				 Intent intent = new Intent();
			        intent.setAction(Intent.ACTION_VIEW);
			        intent.addCategory(Intent.CATEGORY_BROWSABLE);
			        intent.setData(Uri.parse("http://www.kongu.ac.in"));
			        startActivity(intent);
			 }
				
			}
		});

		
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
					Toast.makeText(Home.this,"Enter Current Password", Toast.LENGTH_SHORT).show();
					return;
				}
				else if(ntpass1.toString().trim().equals(""))
				{
					Toast.makeText(Home.this,"Enter New Password", Toast.LENGTH_SHORT).show();
					return;
				}
				else if(ntpass2.toString().trim().equals(""))
				{
					Toast.makeText(Home.this,"Enter New Password", Toast.LENGTH_SHORT).show();
					return;
				}
				else if(!ntpass2.toString().trim().equals(ntpass1.toString().trim()))
				{
					Toast.makeText(Home.this,"Password Not Match", Toast.LENGTH_SHORT).show();
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
				//finish();
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
			pDialog = new ProgressDialog(Home.this);
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
        	
        	     	
        	 
        	//Log.d("Create Response", json.toString());
        	Toast.makeText(Home.this,msg, Toast.LENGTH_SHORT).show();
        	
        	 alertMode.dismiss();
        	
			} catch (Exception e) {
	            e.printStackTrace();
	            if(flag==0)
	            {
	            Toast.makeText(Home.this,"Connection Failed...", Toast.LENGTH_SHORT).show();
	            alertMode.dismiss(); 
	            }
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
			{
			  Toast.makeText(Home.this,"Connection Failed...", Toast.LENGTH_SHORT).show();
			alertMode.dismiss();
			}
				
		}
		
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		//MenuInflater menuInflater = getMenuInflater();
		//menuInflater.inflate(R.menu.home, menu);
		return true;
	}


	@Override
	public void onBackPressed() {
		Intent i = new Intent(getApplicationContext(), login.calender.activity.MainActivity4.class);
		startActivity(i);
		finish();
	}

}
