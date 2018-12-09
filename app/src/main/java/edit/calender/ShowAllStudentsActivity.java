package edit.calender;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.keccontacts.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ShowAllStudentsActivity extends Activity {
    String answer;
    ListView StudentListView;
    ProgressBar progressBar;
    String HttpUrl = "http://192.168.137.1/Student/AllStudentData.php";
    List<String> IdList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_showallactivity);

        StudentListView = (ListView)findViewById(R.id.listview1);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        //begin
        ConnectivityManager cm = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        // Transfer data from remote MySQL DB to SQLite on Android and perform Sync
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {

            new GetHttpResponse(ShowAllStudentsActivity.this).execute();

        }
        else {
            answer = "No internet Connectivity";
            Toast.makeText(getApplicationContext(), answer,
                    Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), edit.calender.MainActivity3.class);
            startActivity(i);
            finish();
        }

        //end


        //Adding ListView Item click Listener.
        StudentListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO Auto-generated method stub

                Intent intent = new Intent(ShowAllStudentsActivity.this,ShowSingleRecordActivity.class);

                // Sending ListView clicked value using intent.
                intent.putExtra("ListViewValue", IdList.get(position).toString());

                startActivity(intent);

                //Finishing current activity after open next activity.
                finish();

            }
        });
    }

    // JSON parse class started from here.
    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

        String JSonResult;

        List<Student> studentList;

        public GetHttpResponse(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            // Passing HTTP URL to HttpServicesClass Class.
            HttpServicesClass httpServicesClass = new HttpServicesClass(HttpUrl);
            try
            {
                httpServicesClass.ExecutePostRequest();

                if(httpServicesClass.getResponseCode() == 200)
                {
                    JSonResult = httpServicesClass.getResponse();

                    if(JSonResult != null)
                    {
                        JSONArray jsonArray = null;

                        try {
                            jsonArray = new JSONArray(JSonResult);

                            JSONObject jsonObject;

                            Student student;

                            studentList = new ArrayList<Student>();

                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                student = new Student();

                                jsonObject = jsonArray.getJSONObject(i);

                                // Adding Student Id TO IdList Array.
                                IdList.add(jsonObject.getString("sno").toString());

                                //Adding Student Name.
                                student.StudentName = jsonObject.getString("Name").toString();

                                studentList.add(student);

                            }
                        }
                        catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    Toast.makeText(context, httpServicesClass.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)

        {
            progressBar.setVisibility(View.GONE);

            StudentListView.setVisibility(View.VISIBLE);

            ListAdapterClass adapter = new ListAdapterClass(studentList, context);

            StudentListView.setAdapter(adapter);

        }
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), edit.calender.MainActivity3.class);
        startActivity(i);
        finish();
    }
}
