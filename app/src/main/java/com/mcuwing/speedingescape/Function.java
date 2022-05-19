package com.mcuwing.speedingescape;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

class Function {
    static void fullScreen(Window window) {
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }

    static float toPx(Resources res, float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, res.getDisplayMetrics());
    }

    static void setGame(int gameMode, final Window window, Context context, FrameLayout leftRoad, FrameLayout middleRoad, FrameLayout rightLoad) {
        switch(gameMode) {
            default:
                window.findViewById(R.id.MainView).setBackgroundResource(R.drawable.background_level5);
                leftRoad.removeAllViews();
                middleRoad.removeAllViews();
                rightLoad.removeAllViews();
                break;
            case 0:
                window.findViewById(R.id.GameStartButton).setVisibility(View.VISIBLE);
                window.findViewById(R.id.SettingButton).setVisibility(View.VISIBLE);
                window.findViewById(R.id.ExitButton).setVisibility(View.VISIBLE);
                window.findViewById(R.id.Tutorial).setVisibility(View.VISIBLE);
                window.findViewById(R.id.AboutUs).setVisibility(View.VISIBLE);
                window.findViewById(R.id.ScoreTextView).setVisibility(View.VISIBLE);
                window.findViewById(R.id.LifeTextView).setVisibility(View.VISIBLE);
                leftRoad.removeAllViews();
                middleRoad.removeAllViews();
                rightLoad.removeAllViews();
                break;
            case 1:
                window.findViewById(R.id.MainView).setBackgroundResource(R.drawable.background_level1);
                window.findViewById(R.id.GameStartButton).setVisibility(View.INVISIBLE);
                window.findViewById(R.id.SettingButton).setVisibility(View.INVISIBLE);
                window.findViewById(R.id.ExitButton).setVisibility(View.INVISIBLE);
                window.findViewById(R.id.Tutorial).setVisibility(View.INVISIBLE);
                window.findViewById(R.id.AboutUs).setVisibility(View.INVISIBLE);
                leftRoad.removeAllViews();
                middleRoad.removeAllViews();
                rightLoad.removeAllViews();
                break;
            case 2:
                window.findViewById(R.id.MainView).setBackgroundResource(R.drawable.background_level2);
                leftRoad.removeAllViews();
                middleRoad.removeAllViews();
                rightLoad.removeAllViews();
                break;
            case 3:
                window.findViewById(R.id.MainView).setBackgroundResource(R.drawable.background_level3);
                leftRoad.removeAllViews();
                middleRoad.removeAllViews();
                rightLoad.removeAllViews();
                break;
            case 4:
                window.findViewById(R.id.MainView).setBackgroundResource(R.drawable.background_level4);
                leftRoad.removeAllViews();
                middleRoad.removeAllViews();
                rightLoad.removeAllViews();
                break;
            case 5:
                window.findViewById(R.id.MainView).setBackgroundResource(R.drawable.background_level5);
                leftRoad.removeAllViews();
                middleRoad.removeAllViews();
                rightLoad.removeAllViews();
                break;
            case 6:
            case 100:
                window.findViewById(R.id.ScoreTextView).setVisibility(View.INVISIBLE);
                window.findViewById(R.id.LifeTextView).setVisibility(View.INVISIBLE);
                gameMode = 0;
                setGame(gameMode, window, context, leftRoad, middleRoad, rightLoad);
                break;
        }
    }

    static MediaPlayer setBgm(MediaPlayer bgm, int gameMode, Context context) {
        if(bgm != null) {
            bgm.stop();
        }
        switch (gameMode) {
            default:
                bgm = MediaPlayer.create(context, R.raw.bgm_start);
                break;
            case 0:
                bgm = MediaPlayer.create(context, R.raw.bgm_start);
                break;
            case 1:
                bgm = MediaPlayer.create(context, R.raw.bgm_level1);
                break;
            case 2:
                bgm = MediaPlayer.create(context, R.raw.bgm_level2);
                break;
            case 3:
                bgm = MediaPlayer.create(context, R.raw.bgm_level3);
                break;
            case 4:
                bgm = MediaPlayer.create(context, R.raw.bgm_level4);
                break;
            case 5:
                bgm = MediaPlayer.create(context, R.raw.bgm_level5);
                break;
            case 6:
                bgm = MediaPlayer.create(context, R.raw.bgm_win);
                break;
            case 100:
                bgm = MediaPlayer.create(context, R.raw.bgm_gameover);
                break;
        }
        bgm.setLooping(true);
        bgm.setVolume(100, 100);
        return bgm;
    }

    static Car newObstacleCar(Resources res, Context context, float obstacleCarSpeed, Animation.AnimationListener listener, int numObstacleType, String packName) {
        Car car = new Obstacle(new ImageView(context), obstacleCarSpeed, listener);
        car.setType(new Random().nextInt(numObstacleType) + 1);
        car.getImage().setImageResource(res.getIdentifier("obstacle_car"+car.getType(), "drawable", packName));
        return car;
    }

    static Item newItem(Context context, float itemSpeed, Animation.AnimationListener listener, int numItemType, String packName) {
        Item item = null;
        ImageView imageView= new ImageView(context);
        int type = new Random().nextInt(numItemType) + 1;
        switch (type) {
            case 1:
                imageView.setImageResource(R.drawable.item_key);
                item = new Key(imageView, itemSpeed, listener);
                break;
            case 2:
                imageView.setImageResource(R.drawable.item_hourglass);
                item = new Hourglass(imageView, itemSpeed, listener);
                break;
            case 3:
                imageView.setImageResource(R.drawable.item_bomb);
                item = new Bomb(imageView, itemSpeed, listener);
                break;
            case 4:
                imageView.setImageResource(R.drawable.item_shield);
                item = new Shield(imageView, itemSpeed, listener);
                break;
            case 5:
                imageView.setImageResource(R.drawable.item_posion);
                item = new Posion(imageView, itemSpeed, listener);
                break;
        }
        item.setType(type);
        return item;
    }

    static int setDrop(final Drop drop, int nowNum, final FrameLayout leftRoad, final FrameLayout middleRoad, final FrameLayout rightLoad) {
        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 0;
        drop.startAnimation("");
        switch (drop.getLocate()) {
            case 0:
                leftRoad.post(new Runnable() {
                    @Override
                    public void run() {
                        leftRoad.addView(drop.getImage(), params);
                    }
                });
                break;
            case 1:
                middleRoad.post(new Runnable() {
                    @Override
                    public void run() {
                        middleRoad.addView(drop.getImage(), params);
                    }
                });
                break;
            case 2:
                rightLoad.post(new Runnable() {
                    @Override
                    public void run() {
                        rightLoad.addView(drop.getImage(), params);
                    }
                });
                break;
        }
        return nowNum + 1 ==5 ?  0 : nowNum+1;
    }

    static void removeView(Drop drop, FrameLayout leftRoad, FrameLayout middleRoad, FrameLayout rightLoad) {
        switch (drop.getLocate()) {
            case 0:
                leftRoad.removeView(drop.getImage());
                break;
            case 1:
                middleRoad.removeView(drop.getImage());
                break;
            case 2:
                rightLoad.removeView(drop.getImage());
                break;
        }
    }
}
