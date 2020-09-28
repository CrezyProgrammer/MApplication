package com.crezyprogrammer.mygoogle;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new CountDownTimer(100000,4000)
        {
            @Override
            public void onTick(long millisUntilFinished) {
                sendBroadcast(new Intent("fromservice"));

            }

            @Override
            public void onFinish() {

            }
        }.start();
        return START_STICKY;
    }
}