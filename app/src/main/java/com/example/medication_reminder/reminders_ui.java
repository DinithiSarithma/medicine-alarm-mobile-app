package com.example.medication_reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class reminders_ui extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    TextView mTextView1;
    EditText getMedName;
    EditText getDosage;
    TextView displayMedName;
    public static final String KEY_PREFERENCE_TIME = "preference";
    public static final String KEY_ID_TIME = "id";

    //NotificationHelper notificationHelper = new NotificationHelper(this, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders_ui);

        mTextView1 = findViewById(R.id.setTime);
        getMedName = findViewById(R.id.medName);
        getDosage = findViewById(R.id.dosage);


        Button timePickerBtn = findViewById(R.id.timePickerButton);
        Button alarmSetBtn = findViewById(R.id.okBtn);
        Button myRemindersBtn = findViewById(R.id.myRemindersBtn);

        timePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new dialog_timepicker();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        alarmSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String medNameString = getMedName.getText().toString();
                String dosageString = getDosage.getText().toString();
                if (medNameString.equals("") || dosageString.equals("")){
                    displayErrorToast();
                }else {
                    displayToast(medNameString,dosageString);
                    openMyRemindersWithData(medNameString,dosageString);
                }
            }
        });

        myRemindersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyReminders();
            }

        });



    }


    public void openMyReminders(){
        Intent intent = new Intent(this, my_reminders.class);
        startActivity(intent);
    }

    public void openMyRemindersWithData(String name, String dose){
        Intent intent = new Intent(this, my_reminders.class);
        intent.putExtra("NAME", name);
        intent.putExtra("DOSE",dose);
        startActivity(intent);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);

        updateTimeText(calendar);
        startAlarm(calendar);
        passTimeToReminders(calendar);

        //notificationHelper.initialize();
    }

    public void passTimeToReminders(Calendar calendar){
        String passTime  = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
        SharedPreferences shared = getApplicationContext().getSharedPreferences(KEY_PREFERENCE_TIME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(KEY_ID_TIME, passTime);
        editor.commit();
    }

    private void updateTimeText(Calendar calendar) {
        String timeText = "Alarm set for : ";
        timeText+= DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
        mTextView1.setText(timeText);
    }

    private void startAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void displayToast(String medName, String dosage){
        String displayText = "Your reminder for "+medName+" is set";
        Toast.makeText(this, displayText, Toast.LENGTH_LONG).show();
    }

    public void displayErrorToast(){
        String medNameError = "Cannot keep blank areas !!";
        Toast.makeText(this,medNameError, Toast.LENGTH_LONG).show();
    }

}
