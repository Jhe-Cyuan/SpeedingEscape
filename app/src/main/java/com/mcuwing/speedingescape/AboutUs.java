package com.mcuwing.speedingescape;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AboutUs extends AppCompatActivity {

    MediaPlayer bgm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        bgm = MediaPlayer.create(this, R.raw.bgm_aboutus);
        bgm.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bgm.stop();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Function.fullScreen(this.getWindow());
    }
}
