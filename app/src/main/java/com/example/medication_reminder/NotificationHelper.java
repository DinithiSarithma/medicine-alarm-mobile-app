package com.example.medication_reminder;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.EditText;

public class NotificationHelper extends ContextWrapper{

    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";
    private NotificationManager mManager;
//    private Activity activity;
//    private EditText medName = activity.findViewById(R.id.medName);
//    private EditText dosage = activity.findViewById(R.id.dosage);
    private String medNameString;
    private String dosageString;


    public NotificationHelper(Context base, Activity activity) {
        super(base);
//        this.activity = activity;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }



//    public void initialize(){
//        medName = activity.findViewById(R.id.medName);
//        dosage = activity.findViewById(R.id.dosage);
//        medNameString = medName.toString();
//        dosageString = dosage.toString();
//    }

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }

    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        medNameString = "panadol";
        dosageString = "2";
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("Reminder !!")
                .setContentText("It is time to take "+dosageString+ " no of drugs of "+medNameString)
                .setSmallIcon(R.drawable.ic_access_alarms_black_24dp);
    }
}