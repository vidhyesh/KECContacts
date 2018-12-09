package login.calender.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.keccontacts.R;

public class SelectionActivity extends Activity {
    private Button btn1;
    private Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        btn1 = (Button) findViewById(R.id.cal);
        btn2 = (Button) findViewById(R.id.con);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                con();
            }
        });
    }
    private void cal() {
        Intent intent = new Intent(SelectionActivity.this, kec.calender.MainActivity2.class);
        startActivity(intent);
        finish();
    }
    private void con() {
        Intent intent = new Intent(SelectionActivity.this, com.example.keccontacts.Loadd.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),MainActivity4.class);
        startActivity(i);
        finish();
    }
}
