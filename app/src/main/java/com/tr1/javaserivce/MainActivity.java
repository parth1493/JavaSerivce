package com.tr1.javaserivce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void startIntentService(View view) {
        Intent intent = new Intent(MainActivity.this,MyIntentService.class);
        intent.putExtra("sleepTime",10);
        startService(intent);
    }
}


