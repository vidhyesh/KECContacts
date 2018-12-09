package com.example.keccontacts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserItemView extends Activity {
	// Declare Variables
	TextView txtssid;
	TextView txtsname;
	TextView txtsdept;
	TextView txtmail;
	TextView txtmob;
	Button bt1,bt2,bt3;
	String rank;
	String country;
	String population;
	String mail1;
	String mobile1;
	Button b1;
	Cursor c;
	Database db3;
	public static Activity fa;
	String ssid="",sname="",sdept="",smob="",smail="";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.useritemview);
		fa=this;
		db3=new Database(this);
		c = db3.getUser();
		System.out.println(c.getCount());
		
		for (int j = 0; j < c.getCount(); j++) {
			if (c.moveToPosition(j)) {
				ssid = c.getString(c.getColumnIndex(Database.sid));
				sname = c.getString(c.getColumnIndex(Database.name));
				sdept = c.getString(c.getColumnIndex(Database.dept));
				smob = c.getString(c.getColumnIndex(Database.mob));
				smail = c.getString(c.getColumnIndex(Database.mail));
				
			}
		}
		// Locate the TextViews in singleitemview.xml
		txtssid = (TextView) findViewById(R.id.names);
		txtsname = (TextView) findViewById(R.id.mails);
		txtsdept = (TextView) findViewById(R.id.mobiles);
		txtmob = (TextView) findViewById(R.id.mob2);
		txtmail = (TextView) findViewById(R.id.maill2);
		
		

		// Load the results into the TextViews
		txtssid.setText(ssid);
		txtsname.setText(sname);
		txtsdept.setText(sdept);
		txtmob.setText(smob);
		txtmail.setText(smail);
		
		
		b1=(Button)findViewById(R.id.edit1);
		b1.setOnClickListener(new View.OnClickListener() 
        {		
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), UserEdit.class);
				startActivity(i);
				
			}
		});
		
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
        	
            return true;
        case R.id.contact:
        	this.finish();
        	Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i);
            return true;
        case R.id.office:
         	finish();
         	Intent i4 = new Intent(getApplicationContext(), Office.class);
         	startActivity(i4);
             return true;

        
        case R.id.department:
        	this.finish();
            Intent i2 = new Intent(getApplicationContext(), Department.class);
			//Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i2);
			return true;
       
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}