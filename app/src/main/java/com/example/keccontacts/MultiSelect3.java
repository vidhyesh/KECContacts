package com.example.keccontacts;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AbsListView.MultiChoiceModeListener;
 
@SuppressLint("NewApi")
public class MultiSelect3 extends Activity {
 
	// Declare Variables
	ListView list;
	MSelectAdapter adapter;
	EditText editsearch;
	String[] rank;
	String[] country;
	String[] population;
	String[] mobile;
	String[] mail;
	String rank1,rank2;
	Cursor c;
	String dept;
	Database db3;
	String str;
	ImageButton bt1,bt2;
	List<StaffClass> arraylist = new ArrayList<StaffClass>();
 
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewmselect);
		db3=new Database(this);
		Intent intent = getIntent();
		// Get the results of rank
		rank1 = intent.getStringExtra("rank").toString().trim();
		dept = intent.getStringExtra("dept").toString().trim();
		str = intent.getStringExtra("str").toString().trim();
		//rank1="13ECR056";
		//Toast.makeText(MultiSelect3.this, dept, Toast.LENGTH_LONG).show();
		Database db = new Database(this);
		c = db.getDeptStaffs(dept);
		//c=db.getstaffs();
		System.out.println(c.getCount());
		
		for (int i = 0; i < c.getCount(); i++) {
			if (c.moveToPosition(i)) {
				String ssid = c.getString(c.getColumnIndex(Database.sid));
				rank2=ssid;
				//rank2="13ECR056";
				String sname = c.getString(c.getColumnIndex(Database.name));
				String sdept = c.getString(c.getColumnIndex(Database.dept));
				String stype = c.getString(c.getColumnIndex(Database.type));
				String smob = c.getString(c.getColumnIndex(Database.mob));
				String smail = c.getString(c.getColumnIndex(Database.mail));
			//	Toast.makeText(MultiSelect.this, ssid, Toast.LENGTH_LONG).show();
			if (rank2.equals(rank1))
			{
			StaffClass wp = new StaffClass(ssid,sname,sdept,stype,smob,smail,true);
			//Toast.makeText(MultiSelect.this, "set true", Toast.LENGTH_LONG).show();
			arraylist.add(wp);
			}
			else
			{
			StaffClass wp = new StaffClass(ssid,sname,sdept,stype,smob,smail,false);
			arraylist.add(wp);
			}
			//	WorldPopulation wp = new WorldPopulation("111","222","3333");
				// Binds all strings into an array
			
			
			}
		}
	
		bt1=(ImageButton)findViewById(R.id.imgb1);
		bt1.setOnClickListener(new View.OnClickListener() 
        {		
			@Override
			public void onClick(View view) {
				int cnt=0;
				  String result="";
				    int totalAmount=0;
				    for (StaffClass p : adapter.getBox()) {
				      if (p.box){
				    	  cnt++;
				        result +=  p.mobile+";";
				        
				      }
				    }
				    if(cnt==0)
				    {
				    	Toast.makeText(MultiSelect3.this, "Please select fields", Toast.LENGTH_SHORT).show();
				    }
				    else
				    {
				    Toast.makeText(MultiSelect3.this, "Message", Toast.LENGTH_SHORT).show();
				
				    try {
						Intent sendIntent = new Intent(Intent.ACTION_VIEW);
					
						sendIntent.putExtra("address",result );
					  sendIntent.setType("vnd.android-dir/mms-sms");
					  startActivity(sendIntent);
				    } catch (Exception e) {
						Toast.makeText(getApplicationContext(),
								"SMS faild, please try again later!",
								Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
				    }
				
				
			}
		});
		
		bt2=(ImageButton)findViewById(R.id.imgb3);
		bt2.setOnClickListener(new View.OnClickListener()  
        {		
			@Override
			public void onClick(View view) {
				int cnt=0,cnt1=0;
				  String result="";
				    int totalAmount=0;
				    for (StaffClass p : adapter.getBox()) {
				      if (p.box){
				    	  cnt++;
				    	  if(!p.mail.toString().trim().equals("-"))
				    	  {
				    		  cnt1++;
				    	  
				        result +=  p.mail+";";
				    	  }
				      }
				    }
				    if(cnt==0)
				    {
				    	Toast.makeText(MultiSelect3.this, "Please select fields", Toast.LENGTH_SHORT).show();
				    }
				    else if(cnt1==0)
				    {
				    	Toast.makeText(MultiSelect3.this, "No Mail id Available", Toast.LENGTH_SHORT).show();
				    }
				    else
				    {
				    Toast.makeText(MultiSelect3.this, "Mail", Toast.LENGTH_SHORT).show();
				
				    try {

Intent intent2 = new Intent();
intent2.setAction(Intent.ACTION_SEND);

intent2.setType("vnd.android-dir/mail");
//intent2.putExtra(Intent.EXTRA_EMAIL, new String[]{"sgovindapm@gmail.com","sgovind142@gmail.com"});
intent2.putExtra(Intent.EXTRA_EMAIL, new String[]{result});
  
startActivity(intent2);

} catch (Exception e) {
						Toast.makeText(getApplicationContext(),
								"Mail faild, please try again later!",
								Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
				    }
				
				
			}
		});
		
		// Locate the ListView in listview_main.xml
		list = (ListView) findViewById(R.id.listview);
 		
 
		// Pass results to ListViewAdapter Class
		adapter = new MSelectAdapter(this, arraylist);
 
		// Binds the Adapter to the ListView
		list.setAdapter(adapter);
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		list.setFocusable(true);
		list.setMultiChoiceModeListener(new MultiChoiceModeListener() {
			 
			@Override
			public void onItemCheckedStateChanged(ActionMode mode,
					int position, long id, boolean checked) {
				// Capture total checked items
				final int checkedCount = list.getCheckedItemCount();
				// Set the CAB title according to total checked items
				mode.setTitle(checkedCount + " item Selected");
				// Calls toggleSelection method from ListViewAdapter Class
				adapter.toggleSelection(position);
			}
 
			@SuppressLint("NewApi")
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			
				return false;
			}
 
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				//mode.getMenuInflater().inflate(R.menu.activity_main2, menu);
				return true;
			}
 
			@Override
			public void onDestroyActionMode(ActionMode mode) {
				// TODO Auto-generated method stub
				adapter.removeSelection();
			}
 
			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				return false;
			}
		});

		// Locate the EditText in listview_main.xml
		editsearch = (EditText) findViewById(R.id.search);
		
		editsearch.setText(str);
		adapter.filter(str);
		editsearch.setOnClickListener(new View.OnClickListener() 
        {		
			@Override
			public void onClick(View view) {
			finish();
			}
		});

		//editsearch.setFocusableInTouchMode(true);
		//editsearch.setFocusable(true);
		// Capture Text in EditText
		/*editsearch.addTextChangedListener(new TextWatcher() {
 
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
				adapter.filter(text);
			}
 
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
			}
 
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
			}
		});
		
		*/
	}
 
	


	// Not using options menu in this tutorial
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.mselect, menu);
		return true;
	}
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	        
	        switch (item.getItemId())
	        {
	        case R.id.home:
	        	this.finish();
	        	Department.fa.finish();
	        	Deptlist.fa.finish();
	            return true;
	        case R.id.contact:
	        	this.finish();
	        	Department.fa.finish();
	        	Deptlist.fa.finish();
	        	Intent i = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(i);
	            return true;
	        case R.id.office:
	        	this.finish();
	        	Department.fa.finish();
	        	Deptlist.fa.finish();
	        	Intent i2 = new Intent(getApplicationContext(), Office.class);
				startActivity(i2);
	            return true;
	       case R.id.department:
	        	this.finish();
	        	Deptlist.fa.finish();
				return true;
				
	        case R.id.user:
	        	this.finish();
	        	Department.fa.finish();
	        	Deptlist.fa.finish();

	        	Intent ii = new Intent(getApplicationContext(), UserItemView.class);
				startActivity(ii);

				return true;
			
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }

}