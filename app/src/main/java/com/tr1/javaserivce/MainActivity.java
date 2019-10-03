package com.tr1.javaserivce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    TextView broadCastOP;
    TextView ResultReceiverOP;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);
        broadCastOP = findViewById(R.id.textView);
        ResultReceiverOP = findViewById(R.id.textView2);
    }

    public void stopStartedService(View view) {
        Intent intent = new Intent(MainActivity.this,StartService.class);
        stopService(intent);
    }

    public void startStartedService(View view) {
        Intent intent = new Intent(MainActivity.this,StartService.class);
        intent.putExtra("sleepTime",10);
        startService(intent);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        String str = intent.getStringExtra("startServiceExtra");
        broadCastOP.setText(str);
        }
    };

    public void moveToSecondActivity(View view) {

        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.service");
        registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    public void startIntentService(View view) {

        ResultReceiver myResultReceiver = new MyResultReceiver(null);
        Log.i(TAG, "startIntentService: ");

        Intent intent = new Intent(MainActivity.this, MyIntentService.class);
        intent.putExtra("receiver", myResultReceiver);
        intent.putExtra("sleepTime",10);
        startService(intent);
    }

    class MyResultReceiver extends ResultReceiver {

        public MyResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            if(resultCode == 18 && resultData != null){
                final String result = resultData.getString("resultIntentService");

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        
                        ResultReceiverOP.setText(result);
                    }
                });
            }
        }
    }
}


