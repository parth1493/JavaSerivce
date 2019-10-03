package com.tr1.javaserivce;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentService";

    public MyIntentService() {
        super("MyWorkerThread");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: "+Thread.currentThread().getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "onHandleIntent: "+Thread.currentThread().getName());

        int sleepTime = intent.getIntExtra("sleepTime",1);

        ResultReceiver myrResultReceiver = intent.getParcelableExtra("receiver");

        int ctr = 1;
        while (ctr <= sleepTime) {
            try {
                Log.i(TAG, "Value:"+ ctr+"");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctr++;
        }
        Bundle bundle = new Bundle();
        bundle.putString("resultIntentService","Counter stopped"+ctr);
        myrResultReceiver.send(18,bundle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: "+Thread.currentThread().getName());
    }
}
