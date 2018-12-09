package com.example.keccontacts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SingleItemView extends Activity {
	// Declare Variables
	TextView txtname;
	TextView txtmobile;
	TextView txtmail;
	TextView txtdeparts;
	TextView txtmob;
	TextView txtdesig;
	ImageButton bt1,bt2,bt3,bt4;
	//Button bt1,bt2,bt3;
	String rank;
	String country;
	String population;
	String mail1;
	String sid;
	String mobile1;
	String sdesig,stype;
	Database db3;
	Cursor c;
	int flag=0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.singleitemview);
		// Retrieve data from MainActivity on item click event
		db3 = new Database(this);
		Intent i = getIntent();
		// Get the results of rank
		rank = i.getStringExtra("name");
		// Get the results of country
		country = i.getStringExtra("country");
		// Get the results of population
		mail1 = i.getStringExtra("population");
		sid = i.getStringExtra("id");
		mobile1 = i.getStringExtra("mobile1");
		flag=Integer.parseInt(i.getStringExtra("flag"));
		// Locate the TextViews in singleitemview.xml
		txtname = (TextView) findViewById(R.id.names);
		txtmail = (TextView) findViewById(R.id.mails);
		txtmobile = (TextView) findViewById(R.id.mobiles);
		txtdeparts = (TextView) findViewById(R.id.departs);
		txtdesig = (TextView) findViewById(R.id.designation);
		c = db3.getStaff(sid.trim());
		for (int j = 0; j < c.getCount(); j++) {
			if (c.moveToPosition(j)) {
				sdesig = c.getString(c.getColumnIndex(Database.desig));
				stype=c.getString(c.getColumnIndex(Database.type));
						
			}
		}
		

		// Load the results into the TextViews
		txtname.setText(rank);
		txtdesig.setText(mobile1);
		txtmail.setText(mail1);
		txtdeparts.setText(country);
		txtmobile.setText(sdesig+" [ "+stype+" ]");
		//txtmail.setText(mail1);
		
		bt1=(ImageButton)findViewById(R.id.imgbt1);
		bt1.setOnClickListener(new View.OnClickListener() 
        {		
			@Override
			public void onClick(View view) {
				try {
			
				Toast.makeText(SingleItemView.this,"Call", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:"+mobile1.toString())));
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"Call faild, please try again later!",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				
				
				//.parse("tel:" + "9095477728")));
			}
		});
		bt4=(ImageButton)findViewById(R.id.imgbt4);
		bt4.setOnClickListener(new View.OnClickListener() 
        {		
			@Override
			public void onClick(View view) {
				try {
				Toast.makeText(SingleItemView.this,"Add New Contact", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(Intent.ACTION_INSERT);
				  intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
				  intent.putExtra(ContactsContract.Intents.Insert.NAME,rank );
				  intent.putExtra(ContactsContract.Intents.Insert.PHONE,mobile1.toString());
				  if(!mail1.toString().trim().equals("-"))
				  intent.putExtra(ContactsContract.Intents.Insert.EMAIL, mail1.toString());
				  startActivity(intent);
				
				
				//.parse("tel:" + "9095477728")));
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"Add Contact faild, please try again later!",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}
		});
		bt2=(ImageButton)findViewById(R.id.imgbt2);
		
		bt2.setOnClickListener(new View.OnClickListener() 
        {		
			@Override
			public void onClick(View view) {
				try {
				Toast.makeText(SingleItemView.this,"Message", Toast.LENGTH_SHORT).show();
				
						Intent sendIntent = new Intent(Intent.ACTION_VIEW);
					
						sendIntent.putExtra("address",mobile1.toString() );
						//sendIntent.setData(Uri.parse("sms:8675323660"));
					  //sendIntent.putExtra("sms_body", "default content"); 
					  sendIntent.setType("vnd.android-dir/mms-sms");
					    
					  startActivity(sendIntent);
		
					        
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(),
								"SMS faild, please try again later!",
								Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}

			}
		});
		bt3=(ImageButton)findViewById(R.id.imgbt3);
		bt3.setOnClickListener(new View.OnClickListener() 
        {		
			@Override
			public void onClick(View view) {
				try {
					if(mail1.toString().trim().equals("-"))
						Toast.makeText(SingleItemView.this,"Mail id Not Available", Toast.LENGTH_SHORT).show();
					else
					{
				Toast.makeText(SingleItemView.this,"Mail", Toast.LENGTH_SHORT).show();
				
				

Intent intent2 = new Intent();
intent2.setAction(Intent.ACTION_SEND);
//intent2.setType("message/rfc822");
intent2.setType("vnd.android-dir/mail");
//intent2.putExtra(Intent.EXTRA_EMAIL, new String[]{"sgovindapm@gmail.com","sgovind142@gmail.com"});
intent2.putExtra(Intent.EXTRA_EMAIL, new String[]{mail1.toString()});
//intent2.putExtra(Intent.EXTRA_SUBJECT, "Email Subject");
//intent2.putExtra(Intent.EXTRA_TEXT, "Your text here" );  
startActivity(intent2);

					}
				        
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"Mail faild, please try again later!",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}


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
        	if(flag==1)
        	{
        	MainActivity.fa.finish();
        	}
        	else if(flag==2)
        	{
        	Department.fa.finish();
        	Deptlist.fa.finish();
        	}
            return true;
        case R.id.office:
        	this.finish();
        	if(flag==1)
        	{
        	MainActivity.fa.finish();
        	}
        	else if(flag==2)
        	{
        	Department.fa.finish();
        	Deptlist.fa.finish();
        	}

        //	MainActivity.fa.finish();
            Intent i1 = new Intent(getApplicationContext(), Office.class);
			//Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i1);
			return true;
        
        case R.id.contact:
        	this.finish();
        	if(flag==2)
        	{
        	Department.fa.finish();
        	Deptlist.fa.finish();
        	 Intent i3 = new Intent(getApplicationContext(), MainActivity.class);
 			
 			startActivity(i3);        	
        	}
        	return true;
                
        case R.id.department:
        	this.finish();
        	if(flag==1)
        	{
        	MainActivity.fa.finish();
        	 Intent i3 = new Intent(getApplicationContext(), Department.class);
  			startActivity(i3);        	
         	
        	}
        	else if(flag==2)
        	{
        	Deptlist.fa.finish();
        	}
           	return true;
        case R.id.user:
        	this.finish();
        	if(flag==1)
        	{
        	MainActivity.fa.finish();
        	}
        	else if(flag==2)
        	{
        	Department.fa.finish();
        	Deptlist.fa.finish();
        	}
        	Intent ii = new Intent(getApplicationContext(), UserItemView.class);
			startActivity(ii);

			return true;
			

        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
}