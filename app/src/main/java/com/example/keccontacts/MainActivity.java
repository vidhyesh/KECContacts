package com.example.keccontacts;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AbsListView.MultiChoiceModeListener;
 
@SuppressLint("NewApi")
public class MainActivity extends Activity {
 
	// Declare Variables
	ListView list;
	ListViewAdapter adapter;
	EditText editsearch;
	String[] rank;
	String[] country;
	String[] population;
	Cursor c;
	Database db3;
	public static Activity fa;
	
	List<StaffClass> arraylist = new ArrayList<StaffClass>();
 
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_main);
		fa=this;
		Intent i2 = getIntent();
		// Get the results of rank
		
		db3 = new Database(this);
 
		// Locate the ListView in listview_main.xml
		list = (ListView) findViewById(R.id.listview);
 
	
		Database db = new Database(this);
		c = db.getstaffs();
		System.out.println(c.getCount());
		
		for (int i = 0; i < c.getCount(); i++) {
			if (c.moveToPosition(i)) {
				String ssid = c.getString(c.getColumnIndex(Database.sid));
				String sname = c.getString(c.getColumnIndex(Database.name));
				String sdept = c.getString(c.getColumnIndex(Database.dept));
				String stype=c.getString(c.getColumnIndex(Database.type));
				String smob = c.getString(c.getColumnIndex(Database.mob));
				String smail = c.getString(c.getColumnIndex(Database.mail));
				StaffClass wp = new StaffClass(ssid,sname,sdept,stype,smob,smail,7);
			//	WorldPopulation wp = new WorldPopulation("111","222","3333");
				// Binds all strings into an array
				arraylist.add(wp);
			
			}
		}
		/*
		for (int i = 0; i < c.getCount(); i++) {
			
				WorldPopulation wp = new WorldPopulation("111","222","3333");
				// Binds all strings into an array
				arraylist.add(wp);
			
			
		}
		*/
 
		// Pass results to ListViewAdapter Class
		adapter = new ListViewAdapter(this, arraylist);
 
		// Binds the Adapter to the ListView
		list.setAdapter(adapter);
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
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
				mode.getMenuInflater().inflate(R.menu.activity_main, menu);
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
 
		
		editsearch.setOnClickListener(new View.OnClickListener()  
        {		
			@Override
			public void onClick(View view) {
				//Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_LONG).show();
				//editsearch.;
								    }
				
		});

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
 
	public void finact()
	{
		finish();
	}
	// Not using options menu in this tutorial

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);
		return true;
	}
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        
        switch (item.getItemId())
        {
        case R.id.home:
        	finish();
        	
            return true;
        case R.id.office:
        	this.finish();
            Intent i1 = new Intent(getApplicationContext(), Office.class);
			//Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i1);
			return true;
            
        case R.id.department:
        	this.finish();
            Intent i2 = new Intent(getApplicationContext(), Department.class);
			//Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i2);
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