package com.example.keccontacts;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;

public class About extends Activity {

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
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
 	finish();
 	Intent i1 = new Intent(getApplicationContext(), Office.class);
 	startActivity(i1);
     return true;

        case R.id.contact:
        	finish();
        	Intent i = new Intent(getApplicationContext(), MainActivity.class);
        	startActivity(i);
            return true;
   
        case R.id.department:
        	finish();
            Intent i2 = new Intent(getApplicationContext(), Department.class);
			//Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i2);
			return true;

        case R.id.user:
        	finish();

        	Intent ii = new Intent(getApplicationContext(), UserItemView.class);
			startActivity(ii);

			return true;

        default:
            return super.onOptionsItemSelected(item);
        }
    }


}
