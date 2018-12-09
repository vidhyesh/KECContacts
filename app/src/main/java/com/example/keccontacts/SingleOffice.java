package com.example.keccontacts;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SingleOffice extends Activity {

	String soff,sphon;
	TextView txtoffice,txtphone;
	ImageButton bt1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_itemoffice);
	
		Intent i = getIntent();
		// Get the results of rank
		soff = i.getStringExtra("offic");
		// Get the results of country
		sphon = i.getStringExtra("phon");
		txtoffice = (TextView) findViewById(R.id.offic);
		txtphone = (TextView) findViewById(R.id.phon);
		txtoffice.setText(soff);
		txtphone.setText("04294-"+sphon);
		
		bt1=(ImageButton)findViewById(R.id.imgbt1);
		bt1.setOnClickListener(new View.OnClickListener() 
        {		
			@Override
			public void onClick(View view) {
				try {
			
				Toast.makeText(SingleOffice.this,"Call", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:"+"04294"+sphon.toString())));
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"Call faild, please try again later!",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				
				
				//.parse("tel:" + "9095477728")));
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_office, menu);
		return true;
	}
	
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch (item.getItemId())
        {
        	case R.id.home:
        	this.finish();
        	Office.fa.finish();
            return true;
 
        	case R.id.office:
            	this.finish();
            	return true;
     
        case R.id.contact:
        	finish();
        	Office.fa.finish();
        	Intent i = new Intent(getApplicationContext(), MainActivity.class);
        	startActivity(i);
            return true;
   
        case R.id.department:
        	finish();
        	Office.fa.finish();
            Intent i2 = new Intent(getApplicationContext(), Department.class);
			//Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i2);
			return true;

        case R.id.user:
        	finish();
        	Office.fa.finish();

        	Intent ii = new Intent(getApplicationContext(), UserItemView.class);
			startActivity(ii);

			return true;

        default:
            return super.onOptionsItemSelected(item);
        }
    }


}
