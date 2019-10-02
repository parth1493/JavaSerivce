package com.tr1.javaserivce;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class StartService extends Service {

    private static final String TAG = "StartService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: "+ Thread.currentThread().getName());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: " +Thread.currentThread().getName());

        int sleepTime = intent.getIntExtra("sleepTime",1);

        new MyAsync().execute(sleepTime);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: "+Thread.currentThread().getName());
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: "+Thread.currentThread().getName());
        super.onDestroy();
    }

    class MyAsync extends AsyncTask<Integer,String,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "onPreExecute: " +Thread.currentThread().getName());
        }

        @Override
        protected Void doInBackground(Integer... integers) {

            Log.i(TAG, "doInBackground: "+Thread.currentThread().getName());
            int sleepTime = integers[0];

            int ctr = 1;
            while (ctr <= sleepTime) {
                try {
                    publishProgress(ctr+"");
                    Thread.sleep(sleepTime * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctr++;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Log.i(TAG, "onProgressUpdate: "+Thread.currentThread().getName());
            Toast.makeText(StartService.this, "Value" + values[0], Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i(TAG, "onPostExecute: ");
            Log.i(TAG, "Value: "+aVoid);
            stopSelf();
            super.onPostExecute(aVoid);
        }
    }
}
