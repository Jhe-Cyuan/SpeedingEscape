package com.mcuwing.speedingescape;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Option extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Function.fullScreen(getWindow());
    }

    public void Open(View view) {
        Intent reply = new Intent();
        reply.putExtra("Sound", "Open");
        setResult(RESULT_OK, reply);
        finish();
    }

    public void Mute(View view) {
        Intent reply = new Intent();
        reply.putExtra("Sound", "Mute");
        setResult(RESULT_OK, reply);
        Log.v("Sound", "Mute");
        finish();
    }

    public void pre(View view) {
        finish();
    }
}
