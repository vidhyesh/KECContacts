package com.example.keccontacts;



import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Office extends Activity {

	Database db1,db2;
	Cursor c1;
	String[] soff;
	String[] sphone;
	ListView list;
	EditText editsearch;
	ListViewAdapterOffice adapter;
	
	public static Activity fa;
	List<StaffClass> arraylist = new ArrayList<StaffClass>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_office);
		
		fa=this;
		
		//				StaffClass wp = new StaffClass(ssid,sname,sdept,stype,smob,smail,7);

	//			arraylist.add(wp);
		list = (ListView) findViewById(R.id.listview);
		soff = new String[] { "Principal(Office)", "Principal(Res.)", "Dean CDSA", "Controller of Examination", "Registrar", "College Fax 1", "College Fax 2", "Dheeran Hostel", "Valluvar Hostel", "Bharathi Hostel 1","Bharathi Hostel 2", "Kamban Hostel", "Ilango Hostel 1", "Ilango Hostel 2", "Kaveri Hostel 1", "Kaveri Hostel 2", "Bhavani Hostel 1", "Bhavani Hostel 2", "Vaigai Hostel 1","Vaigai Hostel 2", "Amaravathy Hostel 1","Amaravathy Hostel 2", "Guest House", "Alumni Guest House", "Dispensary", "IGNOU Study Centre", "Kongu FM Station 1","Kongu FM Station 2", "KVB 1","KVB 2", "Post Office", "Security Office"};
		sphone = new String[] {"220583", "220630", "226513", "226645", "226515", "220087", "226516", "226654", "226661", "226664","226665", "226656", "226658", "226659", "2220764", "2226731", "2226733", "2226734", "2226744","2226750", "2226598","226599", "223666", "226745", "226760", "225252", "226678","226679", "220612","226761","226762", "226759"};
		//Toast.makeText(Office.this,soff.length+" "+sphone.length, Toast.LENGTH_LONG).show();
			for (int i = 0; i < soff.length; i++) {
			StaffClass sc = new StaffClass(soff[i], sphone[i]);
			arraylist.add(sc);
			}
			adapter = new ListViewAdapterOffice(this, arraylist);
			 
			// Binds the Adapter to the ListView
			list.setAdapter(adapter);
			
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.office, menu);
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
