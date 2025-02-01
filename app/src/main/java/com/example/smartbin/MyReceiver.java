package com.example.smartbin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsBroadcastReceiver";
    String msg,phoneNo = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Intant Received: " +intent.getAction() );
        if (intent.getAction() == SMS_RECEIVED) {
            Bundle dataBundle = intent.getExtras();
            if (dataBundle != null){
                Object[] pdus = (Object[]) dataBundle.get("pdus");
                final SmsMessage[] message = new SmsMessage[pdus.length];
                for (int i=0; i<pdus.length; i++) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        String format = dataBundle.getString("format");
                        message[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                    }else{
                        message[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }
                    msg = message[i].getMessageBody();
                    phoneNo = message[i].getOriginatingAddress();
                }
                notifications(context, "SmartBin ID: B1A");
               // Toast.makeText(context, "Message: " +msg, Toast.LENGTH_LONG).show();
            }
        }
    }

    private  void notifications(Context context,String body) {
        Intent i = new Intent(context, Route.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("1", "MY NOTIFICATION", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription(body);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "1");

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_pin)
                .setPriority(Notification.PRIORITY_MAX)
                .setOngoing(true)
                .setContentTitle("NEW COLLECTION REQUIRED")
                .setContentText(body)
                .setColor(context.getResources().getColor(R.color.colorRed))
                .setContentIntent(pendingIntent);
        notificationManager.notify(0, notificationBuilder.build());
    }
}

