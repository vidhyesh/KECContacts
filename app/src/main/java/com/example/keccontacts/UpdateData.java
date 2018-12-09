package com.example.keccontacts;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.app.Service;
public class UpdateData extends Service {
	Button login,signin;
	private EditText mobile_number,password,email_id,hint;
	private ProgressDialog pDialog;
	int flag=0;
	JSONParser jsonParser = new JSONParser();
	private static String url = "http://192.168.137.1/kecc/and/updat.php";
	 private static final String TAG_STAFFS="staffs";
	private static final String TAG_RESULT="result";
	private static final String TAG_USER="user";
	//private static final String TAG_SUCCESS = "success"; 
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
  	Cursor c;
	Cursor c1,c3,c4;
	JSONObject json,json1,json2;
	
	Database db;
	Database dd;
	Database db1,db2,db3,db4;
	//LazyOrder2 la;
	ArrayList<HashMap<String, String>> personList;
	@Override
	public void onCreate() {
		super.onCreate();
		db = new Database(this);
	 dd=new Database(this);
		db1=new Database(this);
		db2=new Database(this);
		db3 = new Database(this);
		db4 = new Database(this);
		//Toast.makeText(this,"Loading...", Toast.LENGTH_SHORT).show();
		//new loginAccess().execute();
	    }
	@Override
public void onStart(Intent intent,int startid) {
		
		
		//Toast.makeText(this,"BBB", Toast.LENGTH_SHORT).show();
		if(!isOnline(UpdateData.this))
		{	
//		 Intent i = new Intent(getApplicationContext(), EmptyActivity.class);
	//		startActivity(i);
		//	this.finish();
		 Toast.makeText(UpdateData.this,"No Network Connection...", Toast.LENGTH_SHORT).show();
			
			
			//this.finish();	
		}
	 else
	 {
			new loginAccess().execute();
	 }

	
	    }
@Override
public void onDestroy() {
	
	
	
	//new loginAccess().execute();
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
		try{
		pDialog = new ProgressDialog(UpdateData.this);
		pDialog.setMessage("Refresh...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
	    pDialog.show();
		}
		catch(Exception e)
		{
			
		}
	}
	
	   protected void showList(){
		  
	        try {
	            //JSONObject jsonObj = new JSONObject(myJSON);
	        
	        
	        	//Toast.makeText(UpdateData.this,"DB Deleted", Toast.LENGTH_SHORT).show();
	        	result = json.getJSONArray(TAG_RESULT);
	        	JSONObject c2 = result.getJSONObject(0);
	        	
	        	String msg = c2.getString("status");
	        	
	        	int ind=Integer.parseInt(c2.getString("iid")); 
	        	//Log.d("Create Response", json.toString());
	        	//Toast.makeText(Home.this,msg, Toast.LENGTH_SHORT).show();
	        	if(ind==5)
	        	{
	        		dd.deleteStaff();
		        	dd.deleteUser();
		        	
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
	        		
	            //Toast.makeText(Home.this,"Record Updated", Toast.LENGTH_SHORT).show();
	        	Toast.makeText(UpdateData.this,"Refreshed...", Toast.LENGTH_SHORT).show();
	        	Intent i = new Intent(getApplicationContext(), MainActivity.class);
	    		//finish();
	    		startActivity(i);
	    		
	        	}
	        	else
	        	{
	        		//firsttime();
	        	}
	    		c1.close();
	    		db1.close();
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	            if(flag==0)
	            Toast.makeText(UpdateData.this,"Refresh Failed...", Toast.LENGTH_SHORT).show();
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
		
		
		try {
            
		    
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String sid="";
		URL ip = new URL(url);
		executeReq(ip);
		c = db3.getUser();
		System.out.println(c.getCount());
		
		for (int j = 0; j < c.getCount(); j++) {
			if (c.moveToPosition(j)) {
				sid = c.getString(c.getColumnIndex(Database.sid));
				
				
			}
		}
		
	params.add(new BasicNameValuePair("sid", sid));
	
	
		
 json = jsonParser.makeHttpRequest(url,"POST", params);
		  //peoples = json.getJSONArray(TAG_RESULTS);
		
		Log.d("Create Response", json.toString());
		
		           
	                
	

		} catch (Exception e) {
			e.printStackTrace();
			flag=1;
		}
		
		
		return null;
		
		
	}
	protected void onPostExecute(String file_url) {
		pDialog.dismiss();
		if(flag==1)
			Toast.makeText(UpdateData.this,"Refresh Failed...", Toast.LENGTH_SHORT).show();
		showList();
		
		
	}
	
  }




@Override
public IBinder onBind(Intent arg0) {
	// TODO Auto-generated method stub
	return null;
}
	
}
