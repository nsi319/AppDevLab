package com.appdevlab.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("NARENSAIRAM", "On create");
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("NARENSAIRAM", "On start");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("NARENSAIRAM", "On stop");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("NARENSAIRAM", "On restart");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("NARENSAIRAM", "On destroy");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("NARENSAIRAM", "On pause");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("NARENSAIRAM", "On resume");

    }
}
