package com.example.keccontacts;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.keccontacts.Loadd.loginAccess;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserEdit extends Activity{

	EditText sid,sname,smob,smail,spass;
	private Activity activity;
	Button update1,ok1,ssubmit;
	private Dialog alertMode;
	protected String id1, ids;
	private int screenHeight, width;
	private ProgressDialog pDialog;
	int flag=0;
	Cursor c,c3;
	Database db1,db2,db3;
	private static final String TAG_SUCCESS = "success"; 
	JSONParser jsonParser = new JSONParser();
	JSONArray result = null;
  	JSONArray user = null;
  	JSONObject json,json1,json2;
	private static String url = "http://10.3.6.2/and/edit.php";
	private static final String TAG_RESULT="result";
	private static final String TAG_USER="user";
	 private static final String TAG_ID = "id";
	    private static final String TAG_NAME = "name";
	    private static final String TAG_QUAL ="qual";
	    private static final String TAG_DEPT ="dept";
	    private static final String TAG_TYPE ="type";
	    private static final String TAG_DESI ="desi";
	    private static final String TAG_DOJ ="doj";
	    
	    private static final String TAG_ADDR ="addr";
	    private static final String TAG_MOB ="mob";
	    private static final String TAG_MAIL ="mail";
	    
	String ssid,ssname,ssaddr,ssmob,ssmail;
	String stid,stname,staddr,stmob,stmail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity=this;
		setContentView(R.layout.useredit);
		sid=(EditText)findViewById(R.id.esid);
		sname=(EditText)findViewById(R.id.esname);
		
		smob=(EditText)findViewById(R.id.esmob);
		smail=(EditText)findViewById(R.id.esmail);
update1 = (Button) findViewById(R.id.eupdate);
		
Database db = new Database(this);
db2=new Database(this);
db1=new Database(this);
db3=new Database(this);
c = db.getUser();
System.out.println(c.getCount());

for (int i = 0; i < c.getCount(); i++) {
	if (c.moveToPosition(i)) {
		ssid = c.getString(c.getColumnIndex(Database.sid));
		ssname = c.getString(c.getColumnIndex(Database.name));
		ssmob = c.getString(c.getColumnIndex(Database.mob));
		ssmail = c.getString(c.getColumnIndex(Database.mail));
		
		}
	}
sid.setText(ssid);
sname.setText(ssname);
smob.setText(ssmob);
smail.setText(ssmail);

		update1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
//				Toast.makeText(UserEdit.this,stpass1, Toast.LENGTH_SHORT).show();
				stname=sname.getText().toString();
				stmob=smob.getText().toString();
				stmail=smail.getText().toString();
			//	String stpass=spass.getText().toString();
				if(stname.toString().trim().equals("")||stmail.toString().trim().equals("")||stmail.length()<6||stmob.toString().trim().length()!=10)
					Toast.makeText(UserEdit.this,"Please Enter Fields Correctly", Toast.LENGTH_SHORT).show();
				else{
					if(stmail.contains("@")&&stmail.contains(".")&&(stmob.charAt(0)=='8'||stmob.charAt(0)=='9'||stmob.charAt(0)=='7'))
					{
						if(!isOnline(UserEdit.this))
						{	
						
						 Toast.makeText(UserEdit.this,"No Network Connection...", Toast.LENGTH_SHORT).show();
											
							//this.finish();	
						}
					 else
					 {
						 getpass();
					 }
						
						
					}
					else
						Toast.makeText(UserEdit.this,"Please Enter Fields Correctly", Toast.LENGTH_SHORT).show();
						
				}
					
			}
		});

		
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
	public void getpass()
	{
		if (alertMode != null && alertMode.isShowing())
			return;
		alertMode = new Dialog(activity);
		alertMode.getWindow();
		alertMode.requestWindowFeature(Window.FEATURE_NO_TITLE);

		alertMode.setContentView(R.layout.gpass);
		

		spass=(EditText)alertMode.findViewById(R.id.espass);
		ssubmit = (Button) alertMode.findViewById(R.id.esubmit);
		
		
		ssubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//String stpass1=spass.getText().toString();
				//Toast.makeText(UserEdit.this,stpass1, Toast.LENGTH_SHORT).show();
				String stpass=spass.getText().toString();
				if(stpass.toString().trim().equals(""))
				{
					Toast.makeText(UserEdit.this,"Enter Password", Toast.LENGTH_SHORT).show();
					return;
				}
				else
				new loginAccess().execute();
				//new loginAccess().execute();
				
			alertMode.dismiss();
				
				//db = new Database(activity);
		//		System.out.println(tag + name.getText().toString() + phone.getText().toString());
				//int s = Integer.parseInt(tag) + 1;
				//db.update_user(s, name.getText().toString(), phone.getText().toString());
				//db.close();
				//Intent i = new Intent(activity, MainActivity.class);
				//activity.startActivity(i);

			}
		});
		
		//ids = tag;

		Display display = activity.getWindowManager().getDefaultDisplay();

		width = display.getWidth();
		screenHeight = display.getHeight();

		alertMode.getWindow().setLayout(width, (screenHeight /3));
		// alertMode.getWindow().setBackgroundDrawable(new
		// ColorDrawable(android.graphics.Color.TRANSPARENT));
		alertMode.show();

		//finish();
	

	}
	class loginAccess extends AsyncTask<String, String, String> {

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(UserEdit.this);
			pDialog.setMessage("Changes Update...");
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
        	Toast.makeText(UserEdit.this,msg, Toast.LENGTH_SHORT).show();
        	
        	
        	if(ind==1)
        	{
        	user = json.getJSONArray(TAG_USER);
    		for(int i=0;i<user.length();i++){ 
    			//Toast.makeText(UserEdit.this,msg+"\n"+ind+"", Toast.LENGTH_SHORT).show();
                JSONObject c = user.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String dept = c.getString(TAG_DEPT);
                String type = c.getString(TAG_TYPE);
                String desi = c.getString(TAG_DESI);
                String mob = c.getString(TAG_MOB);
                String mail = c.getString(TAG_MAIL);
                 db2.deleteUser();
                db2.Add_User(id.toString() ,name.toString(),dept.toString(),type.toString(),desi.toString(),mob.toString(),mail.toString());
                //db.Add_Staff("1","2","3","4","5","6","7","8","9","10","11");
        		c3 = db2.getUser();
    		}
    		Intent i = new Intent(getApplicationContext(), Loadd.class);
    		finish();
    		startActivity(i);
        	}
        	else
        	{
        		
        		Intent i = new Intent(getApplicationContext(), UserEdit.class);
	    		finish();
	    		startActivity(i);
	    		        	
        	}
			} catch (Exception e) {
	            e.printStackTrace();
	            if(flag==0)
	            Toast.makeText(UserEdit.this,"Connection Failed...", Toast.LENGTH_SHORT).show();
	            
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
			stid=sid.getText().toString();
			//String stid="13MCR029";
			stmob=smob.getText().toString();
			stmail=smail.getText().toString();
			String stpass=spass.getText().toString();
			
			params.add(new BasicNameValuePair("stid", stid));
			params.add(new BasicNameValuePair("stmob", stmob));
			params.add(new BasicNameValuePair("stmail", stmail));
			params.add(new BasicNameValuePair("password", stpass));
		
			json = jsonParser.makeHttpRequest(url,"POST", params);
			
			Log.d("Create Response", json.toString());
			
			
			} catch (Exception e) {
				e.printStackTrace();
				flag=1;
				//Toast.makeText(UserEdit.this,"Connection failed", Toast.LENGTH_SHORT).show();
			}
			return null;
		}
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			showlist();
			
			if(flag==1)
			  Toast.makeText(UserEdit.this,"Connection Failed...", Toast.LENGTH_SHORT).show();
							
		
		}
		
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.user_edit, menu);
		return true;
	}
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        
        switch (item.getItemId())
        {
        case R.id.home:
        	this.finish();
        	UserItemView.fa.finish();
            return true;
                  
        case R.id.contact:
        	this.finish();
        	UserItemView.fa.finish();
        	Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i);
            return true;
        case R.id.office:
         	finish();
         	UserItemView.fa.finish();
         	Intent i4 = new Intent(getApplicationContext(), Office.class);
         	startActivity(i4);
             return true;
   
        case R.id.department:
        	this.finish();
        	UserItemView.fa.finish();
            Intent i2 = new Intent(getApplicationContext(), Department.class);
			//Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i2);
			return true;
        

        default:
            return super.onOptionsItemSelected(item);
        }
    }


}
