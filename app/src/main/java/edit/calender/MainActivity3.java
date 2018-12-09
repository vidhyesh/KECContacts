package edit.calender;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.keccontacts.R;

import org.apache.http.util.TextUtils;

import java.util.Calendar;
import java.util.HashMap;
public class MainActivity3 extends Activity {
    EditText StudentName, StudentPhoneNumber;
    Button RegisterStudent, ShowStudents;
    String StudentNameHolder, StudentPhoneHolder;
    Boolean CheckEditText;
    ProgressDialog progressDialog;
    String finalResult;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://192.168.137.1/Student/StudentRegister.php";
    DatePickerDialog picker;
    EditText eText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        StudentName = (EditText) findViewById(R.id.editName);
        StudentPhoneNumber = (EditText) findViewById(R.id.editPhoneNumber);

        //Begin

        eText=(EditText) findViewById(R.id.editPhoneNumber);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar cldr = Calendar.getInstance();

                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity3.this,new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                StudentPhoneNumber.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker.show();

            }
        });

//End
        RegisterStudent = (Button) findViewById(R.id.buttonSubmit);
        ShowStudents = (Button) findViewById(R.id.buttonShow);

        RegisterStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Checking whether EditText is Empty or Not
                CheckEditTextIsEmptyOrNot();

                if (CheckEditText) {
                    // If EditText is not empty and CheckEditText = True then this block will execute.
                    StudentRegistration(StudentNameHolder, StudentPhoneHolder);

                } else {

                    // If EditText is empty then this block will execute .
                    Toast.makeText(MainActivity3.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                }

            }
        });

        ShowStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), edit.calender.ShowAllStudentsActivity.class);
                startActivity(intent);
            }
        });

    }


    public void StudentRegistration(final String S_Name, final String S_Phone) {

        class StudentRegistrationClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(MainActivity3.this, "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(MainActivity3.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("name", params[0]);

                hashMap.put("date", params[1]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }

        }

        StudentRegistrationClass studentRegistrationClass = new StudentRegistrationClass();

        studentRegistrationClass.execute(S_Name, S_Phone);
    }


    public void CheckEditTextIsEmptyOrNot() {

        StudentNameHolder = StudentName.getText().toString();
        StudentPhoneHolder = StudentPhoneNumber.getText().toString();


        if (TextUtils.isEmpty(StudentNameHolder) || TextUtils.isEmpty(StudentPhoneHolder)) {

            CheckEditText = false;

        } else {

            CheckEditText = true;
        }

    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), login.calender.activity.MainActivity4.class);
        startActivity(i);
        finish();
    }
}
