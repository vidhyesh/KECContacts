package login.calender.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keccontacts.R;

import java.util.HashMap;

import login.calender.helper.SQLiteHandler;
import login.calender.helper.SessionManager;

public class MainActivity4 extends Activity {
    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();
    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btn1 = (Button) findViewById(R.id.btnKec);
        btn2 = (Button) findViewById(R.id.btncal);
        btn3 = (Button) findViewById(R.id.editcal);
        btn4 = (Button) findViewById(R.id.signup);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        txtName.setText(name);
        txtEmail.setText(email);

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
        //btn kec
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        bkec();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        bcal();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        editcal();
            }
        });

       btn4.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), login.calender.activity.RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });




    }
//button keccontact onclick

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };
    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity4.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    //kec btn
    private void bkec() {
        Intent intent = new Intent(MainActivity4.this, com.example.keccontacts.Loadd.class);
        startActivity(intent);
        finish();
    }

    //cal btn

    private void bcal() {
        Intent intent = new Intent(MainActivity4.this, kec.calender.MainActivity2.class);
        startActivity(intent);
        finish();
    }
    //edit cal
    private void editcal() {
        Intent intent = new Intent(MainActivity4.this, edit.calender.MainActivity3.class);
        startActivity(intent);
        finish();
    }
    private void signup() {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
                finish();
            }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (mHandler != null) { mHandler.removeCallbacks(mRunnable); }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(mRunnable, 2000);
    }
}
