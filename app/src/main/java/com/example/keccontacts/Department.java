package com.example.keccontacts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

public class Department extends Activity {

	String[] dept;
	ListView list;
	EditText editsearch;
	ArrayList<StaffClass> arraylist = new ArrayList<StaffClass>();
	ListViewAdapterDepartment adapter;
	Cursor c;
	Database db3;
	public static Activity fa;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_department);
	
		fa=this;
		
		list = (ListView) findViewById(R.id.listview);
		Database db=new Database(this);
		db3=new Database(this);
		c = db.getDepartments();
		String str="";
		for (int k = 0; k < c.getCount(); k++) {
			if (c.moveToPosition(k)) {
				//month2[k] = c.getString(c.getColumnIndex(Database.month));
				str=c.getString(c.getColumnIndex(Database.dept));
				 StaffClass wp = new StaffClass(str);
					// Binds all strings into an array
					arraylist.add(wp);
				
			}
		
		}
				  
				
			
						// Pass results to ListViewAdapter Class
			adapter = new ListViewAdapterDepartment(this, arraylist);

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
		menuInflater.inflate(R.menu.department, menu);
		return true;
	}
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        
        switch (item.getItemId())
        {
        case R.id.home:
        	this.finish();
        	return true;
           
        case R.id.contact:
        	this.finish();
        	Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i);
            return true;
        case R.id.office:
        	this.finish();
        	Intent i1 = new Intent(getApplicationContext(),Office.class);
			startActivity(i1);
            return true;
        case R.id.user:
        	this.finish();

        	Intent ii = new Intent(getApplicationContext(), UserItemView.class);
			startActivity(ii);

			return true;

        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
