package kec.calender;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.keccontacts.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends Activity {

    DBController controller = new DBController(this);
    // Progress Dialog Object
    ProgressDialog prgDialog;
    HashMap<String, String> queryValues;
    String searchText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csearch);

        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        EditText edtSearch = (EditText) findViewById(R.id.edtSearch);
        searchText = edtSearch.getText().toString();
        Log.d("Search Text", searchText);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Inside", "Inside Onclick Listner"+searchText);
                ArrayList<HashMap<String, String>> userList = controller.getSearchResults(searchText);
                Log.d("User List" + searchText, userList.toString());
                // If users exists in SQLite DB
                if (userList.size() != 0) {
                    // Set the User Array list in ListView
                    ListAdapter adapter = new SimpleAdapter(SearchActivity.this, userList, R.layout.view_user_entry, new String[]{
                            "userId", "userName"}, new int[]{R.id.userId, R.id.userName});
                    ListView myList = (ListView) findViewById(R.id.listSearch);
                    myList.setAdapter(adapter);
                }
                // Initialize Progress Dialog properties
                prgDialog = new ProgressDialog(getApplicationContext());
                prgDialog.setMessage("Loading Please wait...");
                prgDialog.setCancelable(false);


            }
        });


      /*  btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Inside","Inside Onclick Listner");
                ArrayList<HashMap<String, String>> userList = controller.getSearchResults(searchText);
                Log.d("User List"+searchText,userList.toString());
                // If users exists in SQLite DB
                if (userList.size() != 0) {
                    // Set the User Array list in ListView
                    ListAdapter adapter = new SimpleAdapter(SearchActivity.this, userList, R.layout.view_user_entry, new String[] {
                            "userId", "userName" }, new int[] { R.id.userId, R.id.userName });
                    ListView myList = (ListView) findViewById(R.id.listSearch);
                    myList.setAdapter(adapter);
                }
                // Initialize Progress Dialog properties
                prgDialog = new ProgressDialog(getApplicationContext());
                prgDialog.setMessage("Loading Please wait...");
                prgDialog.setCancelable(false);

            }
        });
        }
*/
   /* public void showResult(View v){
        Log.d("tag","Inside Onclick Listner");
        ArrayList<HashMap<String, String>> userList = controller.getSearchResults(serachText);
        Log.d("tag",userList.toString());
        // If users exists in SQLite DB
        if (userList.size() != 0) {
            // Set the User Array list in ListView
            ListAdapter adapter = new SimpleAdapter(SearchActivity.this, userList, R.layout.view_user_entry, new String[] {
                    "userId", "userName" }, new int[] { R.id.userId, R.id.userName });
            ListView myList = (ListView) findViewById(R.id.listSearch);
            myList.setAdapter(adapter);
        }
        // Initialize Progress Dialog properties
        prgDialog = new ProgressDialog(getApplicationContext());
        prgDialog.setMessage("Loading Please wait...");
        prgDialog.setCancelable(false);
    }*/
    }
}