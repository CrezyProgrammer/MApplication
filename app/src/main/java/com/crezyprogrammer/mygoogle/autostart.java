package com.crezyprogrammer.mygoogle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class autostart extends BroadcastReceiver
{
    public void onReceive(Context context, Intent arg1)
    {   if (arg1.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

        Log.i("123321", "auto started");
        Intent intent = new Intent(context, MyService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }

    }}
}