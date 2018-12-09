package com.example.keccontacts;

import java.util.ArrayList;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Deptlist extends Activity {
	
	String dept;
	ListView list;
	ArrayList<StaffClass> arraylist = new ArrayList<StaffClass>();
	ListViewAdapter3 adapter;
	EditText editsearch;
	public static Activity fa;
	Cursor c;
	Database db3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deptlist);
		fa=this;
		db3=new Database(this);
		Intent i = getIntent();
		// Get the results of rank
		dept = i.getStringExtra("dept");
		 //Toast.makeText(Deptlist.this, dept, Toast.LENGTH_SHORT).show();
		 
		 list = (ListView) findViewById(R.id.listview);
			Database db=new Database(this);
			
			c = db.getDeptStaffs(dept);
			
			for (int k = 0; k < c.getCount(); k++) {
				if (c.moveToPosition(k)) {
					//month2[k] = c.getString(c.getColumnIndex(Database.month));
					String ssid = c.getString(c.getColumnIndex(Database.sid));
					String sname = c.getString(c.getColumnIndex(Database.name));
					String sdept = c.getString(c.getColumnIndex(Database.dept));
					String stype = c.getString(c.getColumnIndex(Database.type));
					String smob = c.getString(c.getColumnIndex(Database.mob));
					String smail = c.getString(c.getColumnIndex(Database.mail));
					
					StaffClass wp = new StaffClass(ssid,sname,sdept,stype,smob,smail,dept);
						// Binds all strings into an array
						arraylist.add(wp);
					
				}
			
			}
					  
					
				
							// Pass results to ListViewAdapter Class
				adapter = new ListViewAdapter3(this, arraylist);

				// Binds the Adapter to the ListView
				list.setAdapter(adapter);
				
				editsearch = (EditText) findViewById(R.id.search);
				 
				// Capture Text in EditText
				editsearch.addTextChangedListener(new TextWatcher() {
		 
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
			}
		 


	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.mselect, menu);
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
        	
            return true;
        case R.id.contact:
        	this.finish();
        	Department.fa.finish();
        	Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i);
            return true;
        case R.id.office:
        	this.finish();
        	Department.fa.finish();
        	Intent i2 = new Intent(getApplicationContext(), Office.class);
			startActivity(i2);
            return true;
        
        case R.id.department:
        	this.finish();

			return true;
			
        case R.id.user:
        	this.finish();
        	Department.fa.finish();

        	Intent ii = new Intent(getApplicationContext(), UserItemView.class);
			startActivity(ii);

			return true;
		
        default:
            return super.onOptionsItemSelected(item);
        }
    }

}
