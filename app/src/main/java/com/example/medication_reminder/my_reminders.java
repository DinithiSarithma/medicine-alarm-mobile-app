package com.example.medication_reminder;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class my_reminders extends AppCompatActivity {


    TextView medTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reminders);

        SharedPreferences shared = getApplicationContext().getSharedPreferences("KEY_PREFERENCE_TIME" , MODE_PRIVATE);
        String passedTime = shared.getString("KEY_ID_TIME" , "");

        medTextView = findViewById(R.id.medNameDisplayText);
        medTextView.setText(getIntent().getStringExtra("DOSE") + " drug(s) of "
                +getIntent().getStringExtra("NAME") + " at "+passedTime);



    }


}
