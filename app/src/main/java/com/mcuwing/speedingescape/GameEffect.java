package com.mcuwing.speedingescape;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GameEffect extends AppCompatActivity {
    MediaPlayer bgm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_effect);
        String effect = this.getIntent().getStringExtra("Effect");
        switch(effect) {
            case "GameOver":
                bgm = MediaPlayer.create(this, R.raw.bgm_gameover);
                findViewById(R.id.Effect).setBackgroundResource(R.drawable.background_gameover);
                break;
            case "Win":
                bgm = MediaPlayer.create(this, R.raw.bgm_win);
                findViewById(R.id.Effect).setBackgroundResource(R.drawable.background_win);
                break;
        }
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

    public void pre(View view) {
        finish();
    }
}
