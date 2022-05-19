package com.mcuwing.speedingescape;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {
    String packName;
    Resources res;
    Window window;
    Context context;
    DisplayMetrics metrics;
    int width, height;
    FrameLayout leftRoad, middleRoad, rightLoad;
    Button gameStart, setting, exit;
    Button tutorial, aboutus;
    TextView scoreTextView, lifeTextView, keyTextView;
    MediaPlayer bgm;
    boolean bgmPlay;


    float downX, upX;
    float moveTop, moveBottom, hitRange_begin, hitRange_end;
    int gameMode;
    int gameKey;
    int nextModeKeys;
    float score;
    Timer scoreTimer;
    TimerTask addScore;
    int scoreDelayTime, scoreRepeatTime;
    Timer obstacleTimer;
    TimerTask addObstacle;
    int obstacleDelayTime, obstacleRepeatTime;
    Timer itemTimer;
    TimerTask addItem;
    int itemDelayTime, itemRepeatTime;

    RelativeLayout.LayoutParams params;
    Drop example;

    float playerCarSpeed;
    Drop playerCar;
    String playerMode;
    int hitTimes;

    int numArray;

    int numObstacleType;
    float obstacleCarSpeed;
    Drop[] obstacleCar;
    int nowObstacleCar;

    int numItemType;
    float itemSpeed;
    Drop[] item;
    int nowItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        packName = getPackageName();
        res = getResources();
        window = getWindow();
        context = getApplicationContext();
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        leftRoad = findViewById(R.id.LeftRoad);
        middleRoad = findViewById(R.id.MiddleRoad);
        rightLoad = findViewById(R.id.RightRoad);
        gameStart = findViewById(R.id.GameStartButton);
        setting = findViewById(R.id.SettingButton);
        exit = findViewById(R.id.ExitButton);
        tutorial = findViewById(R.id.Tutorial);
        aboutus = findViewById(R.id.AboutUs);
        scoreTextView = findViewById(R.id.ScoreTextView);
        lifeTextView = findViewById(R.id.LifeTextView);
        keyTextView = findViewById(R.id.KeyTextView);
        bgm = Function.setBgm(bgm, gameMode, context);
        bgmPlay = true;

        moveTop = Function.toPx(res, -100);
        moveBottom = metrics.heightPixels + Function.toPx(res, 100);
        hitRange_begin = height-Function.toPx(res, 100);
        hitRange_end = height;
        gameMode = 0;
        gameKey = 0;
        nextModeKeys = 3;
        score = 0;
        scoreDelayTime = 500;
        scoreRepeatTime = 10;
        obstacleDelayTime = 1000;
        obstacleRepeatTime = 1000;
        itemDelayTime = 3184;
        itemRepeatTime = 3184;

        playerCarSpeed = Function.toPx(res, 97);
        playerCar = new Player(findViewById(R.id.PlayerImage), playerCarSpeed, MainActivity.this);
        playerCar.setLocate(1);
        playerMode = "Normal";
        hitTimes = 0;

        numArray = 50;

        numObstacleType = 26;
        obstacleCarSpeed = Function.toPx(res, 8);
        obstacleCar = new Car [numArray];
        nowObstacleCar = 0;

        numItemType = 3;
        itemSpeed = Function.toPx(res, 8);
        item = new Item[numArray];
        nowItem = 0;

        scoreTextView.setText(String.format(res.getString(R.string.score), 0.));
        lifeTextView.setText(String.format(res.getString(R.string.life), 0));
        keyTextView.setText(String.format(res.getString(R.string.key), gameKey));

        setExample();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(scoreTimer == null) {
            scoreTimer = new Timer();
        }
        if(obstacleTimer == null) {
            obstacleTimer = new Timer();
        }
        if(itemTimer == null) {
            itemTimer = new Timer();
        }

        if(addScore == null) {
            addScore = new TimerTask() {
                @Override
                public void run() {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            score += 0.01;
                            scoreTextView.post(new Runnable() {
                                @Override
                                public void run() {
                                    scoreTextView.setText(String.format(res.getString(R.string.score), score));
                                }
                            });
                        }
                    }).start();
                }
            };
        }
        if(addObstacle == null) {
            addObstacle = new TimerTask() {
                @Override
                public void run() {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            obstacleCar[nowObstacleCar] = Function.newObstacleCar(res, context, obstacleCarSpeed, MainActivity.this, numObstacleType, packName);
                            nowObstacleCar = Function.setDrop(obstacleCar[nowObstacleCar],  nowObstacleCar, leftRoad, middleRoad, rightLoad);
                        }
                    }).start();
                }
            };
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<numArray;i++) {
                        if(obstacleCar[i] != null)
                            obstacleCar[i].startAnimation("");
                    }
                }
            }).start();
        }
        if(addItem == null) {
            addItem = new TimerTask() {
                @Override
                public void run() {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            item[nowItem] = Function.newItem(context, itemSpeed, MainActivity.this, numItemType, packName);
                            nowItem = Function.setDrop(item[nowItem], nowItem, leftRoad, middleRoad, rightLoad);
                        }
                    }).start();
                }
            };
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<numArray;i++) {
                        if(item[i] != null)
                            item[i].startAnimation("");
                    }
                }
            }).start();
        }

        if(scoreTimer != null && addScore != null && gameMode > 0) {
            scoreTimer.schedule(addScore, scoreDelayTime, scoreRepeatTime);
        }
        if(obstacleTimer != null && addObstacle != null && gameMode > 0) {
            obstacleTimer.schedule(addObstacle, obstacleDelayTime, obstacleRepeatTime);
        }
        if(itemTimer !=null && addItem != null && gameMode > 0) {
            itemTimer.schedule(addItem, itemDelayTime, itemRepeatTime);
        }
        if(bgmPlay)
                bgm.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(scoreTimer != null) {
            scoreTimer.cancel();
            scoreTimer = null;
            addScore = null;
        }
        if(obstacleTimer != null) {
            obstacleTimer.cancel();
            obstacleTimer = null;
            addObstacle = null;
        }
        if(itemTimer != null) {
            itemTimer.cancel();
            itemTimer = null;
            addItem = null;
        }
        for(int i=0;i<numArray;i++) {
            if(obstacleCar[i] !=null)
                obstacleCar[i].clearAnimation();
        }
        for(int i=0;i<numArray;i++) {
            if(item[i] !=null)
                item[i].clearAnimation();
        }
        bgm.pause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                String sound = data.getStringExtra("Sound");
                if (sound.equals("Open")) {
                    bgm.start();
                    bgmPlay = true;
                }
                else {
                    bgm.pause();
                    bgmPlay = false;
                    Log.v("Sound", "Mute");
                }
            }
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Function.fullScreen(window);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gameMode > 0 && playerCar.getMovable()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    upX = event.getX();
                    if (upX > downX) {
                        switch (playerCar.getLocate()) {
                        case 0:
                            playerCar.startAnimation("Right");
                            playerCar.setMovable(false);
                            playerCar.setLocate(1);
                            break;
                        case 1:
                            playerCar.startAnimation("Right");
                            playerCar.setMovable(false);
                            playerCar.setLocate(2);
                            break;
                        }
                    }
                    else if (upX < downX) {
                        switch (playerCar.getLocate()) {
                            case 2:
                                playerCar.startAnimation("Left");
                                playerCar.setMovable(false);
                                playerCar.setLocate(1);
                                break;
                            case 1:
                                playerCar.startAnimation("Left");
                                playerCar.setMovable(false);
                                playerCar.setLocate(0);
                                break;
                        }
                    }
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == playerCar.getMovement("Left")) {
            playerCar.MoveX("Left");
            playerCar.setMovable(true);
        }
        else if (animation == playerCar.getMovement("Right")) {
            playerCar.MoveX("Right");
            playerCar.setMovable(true);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        if (example != null && animation == example.getMovement("")) {
            example.MoveY();
            example.startAnimation("");
            if(example.getY() > moveBottom)
                example.setY(moveTop);
        }
        else {
            for(int i=0; i<numArray; i++) {
                if(obstacleCar[i]!= null && animation == obstacleCar[i].getMovement("")) {
                    obstacleCar[i].MoveY();
                    obstacleCar[i].startAnimation("");
                    if(obstacleCar[i].getY() > moveBottom) {
                        obstacleCar[i].clearAnimation();
                        Function.removeView(obstacleCar[i], leftRoad, middleRoad, rightLoad);
                    }
                    else if(obstacleCar[i].getY() > hitRange_begin && obstacleCar[i].getY() < hitRange_end) {
                        if(obstacleCar[i].getLocate() == playerCar.getLocate()) {
                            obstacleCar[i].clearAnimation();
                            obstacleCar[i].setImage(res.getIdentifier("obstacle_car"+obstacleCar[i].getType()+"_broken", "drawable", packName));
                            playerCar.setHitTimes(playerCar.getHitTimes()-1);
                            lifeTextView.setText(String.format(res.getString(R.string.life), playerCar.getHitTimes()));
                            if(playerCar.getHitTimes() == 0) {
                                gameMode = 100;
                                gameKey = 0;
                                obstacleCarSpeed = Function.toPx(res, 8);
                                if(scoreTimer != null) {
                                    scoreTimer.cancel();
                                    scoreTimer = null;
                                    addScore = null;
                                }
                                if(obstacleTimer != null) {
                                    obstacleTimer.cancel();
                                    obstacleTimer = null;
                                    addObstacle = null;
                                }
                                if(itemTimer != null) {
                                    itemTimer.cancel();
                                    itemTimer = null;
                                    addItem = null;
                                }
                                for(int t=0;t<numArray;t++) {
                                    if(obstacleCar[t] !=null)
                                        obstacleCar[t].clearAnimation();
                                }
                                for(int t=0;t<numArray;t++) {
                                    if(item[t] !=null)
                                        item[t].clearAnimation();
                                }
                                Function.setGame(gameMode, window, context, leftRoad, middleRoad, rightLoad);
                                bgm = Function.setBgm(bgm, gameMode, context);
                                if(bgmPlay)
                                        bgm.start();
                                gameOver();
                            }
                            else if(playerCar.getHitTimes() == 1) {
                                playerCar.setImage(R.drawable.player_car);
                            }
                        }
                    }
                }
                else if(item[i] != null && animation == item[i].getMovement("")) {
                    item[i].MoveY();
                    item[i].startAnimation("");
                    if(item[i].getY() > moveBottom) {
                        item[i].clearAnimation();
                        Function.removeView(item[i], leftRoad, middleRoad, rightLoad);
                    }
                    else if(item[i].getY() > hitRange_begin && item[i].getY() < hitRange_end) {
                        if(item[i].getLocate() == playerCar.getLocate()) {
                            item[i].clearAnimation();
                            Function.removeView(item[i], leftRoad, middleRoad, rightLoad);
                            switch(item[i].getType()) {
                                case 1:
                                    gameKey++;
                                    if(gameKey == nextModeKeys) {
                                        gameMode++;
                                        obstacleCarSpeed += Function.toPx(res, 2);
                                        if(gameMode >= 3 && numItemType < 5) numItemType+=1;
                                        nowObstacleCar = 0;
                                        nowItem = 0;
                                        for(Drop obstacle :obstacleCar) {
                                            if(obstacle != null) {
                                                obstacle.clearAnimation();
                                            }
                                        }
                                        for(Drop it :item) {
                                            if(it != null) {
                                                it.clearAnimation();
                                            }
                                        }
                                        Function.setGame(gameMode, window, context, leftRoad, middleRoad, rightLoad);
                                        gameKey = 0;
                                        bgm = Function.setBgm(bgm, gameMode, context);
                                        if(bgmPlay)
                                            bgm.start();
                                        if(gameMode == 6) {
                                            gameWin();
                                        }
                                    }
                                    keyTextView.setText(String.format(res.getString(R.string.key), gameKey));
                                    break;
                                case 2:
                                    obstacleCarSpeed = item[i].Ability_Speed(res, obstacleCar);
                                    break;
                                case 3:
                                    obstacleCarSpeed = item[i].Ability_Speed(res, obstacleCar);
                                    break;
                                case 4://Shield
                                    if(playerCar.getHitTimes() == 1) {
                                        playerCar.setHitTimes(playerCar.getHitTimes() + item[i].Ability_Hited());
                                        playerCar.setImage(R.drawable.player_car_mask);
                                        lifeTextView.setText(String.format(res.getString(R.string.life), playerCar.getHitTimes()));
                                    }
                                    else {
                                        Toast.makeText(this,res.getString(R.string.alreadyItemed), Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case 5://Posion
                                    if(playerCar.getHitTimes() == 1) {
                                        playerCar.setHitTimes(playerCar.getHitTimes() + item[i].Ability_Hited());
                                        lifeTextView.setText(String.format(res.getString(R.string.life), playerCar.getHitTimes()));
                                        playerCar.setImage(R.drawable.player_car_potion);
                                    }
                                    else {
                                        Toast.makeText(this,res.getString(R.string.alreadyItemed), Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }

    public void GameStart(View view) {
        Toast.makeText(this, res.getString(R.string.game_start), Toast.LENGTH_SHORT).show();
        example.clearAnimation();
        example = null;
        lifeTextView.setText(String.format(res.getString(R.string.life), playerCar.getHitTimes()));
        gameMode = 1;
        Function.setGame(gameMode, window, context, leftRoad, middleRoad, rightLoad);
        scoreTimer.schedule(addScore, scoreDelayTime, scoreRepeatTime);
        obstacleTimer.schedule(addObstacle, obstacleDelayTime, obstacleRepeatTime);
        itemTimer.schedule(addItem, itemDelayTime, itemRepeatTime);
        bgm = Function.setBgm(bgm, gameMode, context);
        if(bgmPlay)
            bgm.start();
    }

    public void Setting(View view) {
        Toast.makeText(this, res.getString(R.string.setting), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setAction("option");
        intent.addCategory("android.intent.category.DEFAULT");
        startActivityForResult(intent, 0);
    }

    public void Exit(View view) {
        Toast.makeText(this, res.getString(R.string.exit), Toast.LENGTH_SHORT).show();
        finish();
    }

    public void Tutorial(View view) {
        Toast.makeText(this,res.getString(R.string.tutorial), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setAction("tutorial");
        intent.addCategory("android.intent.category.DEFAULT");
        startActivity(intent);
    }

    public void AboutUs(View view) {
        Toast.makeText(this,res.getString(R.string.aboutus), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setAction("aboutus");
        intent.addCategory("android.intent.category.DEFAULT");
        startActivity(intent);
    }

    private void setExample() {
        example = Function.newObstacleCar(res, context, obstacleCarSpeed, MainActivity.this, numObstacleType, packName);
        params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 0;
        example.setLocate(1);
        middleRoad.addView(example.getImage(), params);
        example.startAnimation("");
    }

    private void gameOver() {
        Intent intent = new Intent();
        intent.setAction("gameeffect");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.putExtra("Effect", "GameOver");
        startActivity(intent);

        if(scoreTimer == null) {
            scoreTimer = new Timer();
        }
        if(obstacleTimer == null) {
            obstacleTimer = new Timer();
        }
        if(itemTimer == null) {
            itemTimer = new Timer();
        }

        if(addScore == null) {
            addScore = new TimerTask() {
                @Override
                public void run() {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            score += 0.01;
                            scoreTextView.post(new Runnable() {
                                @Override
                                public void run() {
                                    scoreTextView.setText(String.format(res.getString(R.string.score), score));
                                }
                            });
                        }
                    }).start();
                }
            };
        }
        if(addObstacle == null) {
            addObstacle = new TimerTask() {
                @Override
                public void run() {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            obstacleCar[nowObstacleCar] = Function.newObstacleCar(res, context, obstacleCarSpeed, MainActivity.this, numObstacleType, packName);
                            nowObstacleCar = Function.setDrop(obstacleCar[nowObstacleCar],  nowObstacleCar, leftRoad, middleRoad, rightLoad);
                        }
                    }).start();
                }
            };
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<numArray;i++) {
                        if(obstacleCar[i] != null)
                            obstacleCar[i].startAnimation("");
                    }
                }
            }).start();
        }
        if(addItem == null) {
            addItem = new TimerTask() {
                @Override
                public void run() {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            item[nowItem] = Function.newItem(context, itemSpeed, MainActivity.this, numItemType, packName);
                            nowItem = Function.setDrop(item[nowItem], nowItem, leftRoad, middleRoad, rightLoad);
                        }
                    }).start();
                }
            };
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<numArray;i++) {
                        if(item[i] != null)
                            item[i].startAnimation("");
                    }
                }
            }).start();
        }
        setExample();
        score = 0;
        scoreTextView.setText(String.format(res.getString(R.string.score), score));
        keyTextView.setText(String.format(res.getString(R.string.key), gameKey));
        playerCar.setHitTimes(1);
        obstacleCar = null;
        obstacleCar = new Drop[numArray];
        item = null;
        item = new Drop[numArray];
        gameMode = 0;
        numItemType = 3;
        bgm = Function.setBgm(bgm, gameMode, context);
        if(bgmPlay)
            bgm.start();
    }

    private void gameWin() {
        Intent intent = new Intent();
        intent.setAction("gameeffect");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.putExtra("Effect", "Win");
        startActivity(intent);

        if(scoreTimer == null) {
            scoreTimer = new Timer();
        }
        if(obstacleTimer == null) {
            obstacleTimer = new Timer();
        }
        if(itemTimer == null) {
            itemTimer = new Timer();
        }

        if(addScore == null) {
            addScore = new TimerTask() {
                @Override
                public void run() {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            score += 0.01;
                            scoreTextView.post(new Runnable() {
                                @Override
                                public void run() {
                                    scoreTextView.setText(String.format(res.getString(R.string.score), score));
                                }
                            });
                        }
                    }).start();
                }
            };
        }
        if(addObstacle == null) {
            addObstacle = new TimerTask() {
                @Override
                public void run() {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            obstacleCar[nowObstacleCar] = Function.newObstacleCar(res, context, obstacleCarSpeed, MainActivity.this, numObstacleType, packName);
                            nowObstacleCar = Function.setDrop(obstacleCar[nowObstacleCar],  nowObstacleCar, leftRoad, middleRoad, rightLoad);
                        }
                    }).start();
                }
            };
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<numArray;i++) {
                        if(obstacleCar[i] != null)
                            obstacleCar[i].startAnimation("");
                    }
                }
            }).start();
        }
        if(addItem == null) {
            addItem = new TimerTask() {
                @Override
                public void run() {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            item[nowItem] = Function.newItem(context, itemSpeed, MainActivity.this, numItemType, packName);
                            nowItem = Function.setDrop(item[nowItem], nowItem, leftRoad, middleRoad, rightLoad);
                        }
                    }).start();
                }
            };
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<numArray;i++) {
                        if(item[i] != null)
                            item[i].startAnimation("");
                    }
                }
            }).start();
        }
        setExample();
        score = 0;
        scoreTextView.setText(String.format(res.getString(R.string.score), score));
        keyTextView.setText(String.format(res.getString(R.string.key), gameKey));
        playerCar.setHitTimes(1);
        obstacleCar = null;
        obstacleCar = new Drop[numArray];
        item = null;
        item = new Drop[numArray];
        gameMode = 0;
        bgm = Function.setBgm(bgm, gameMode, context);
        if(bgmPlay)
            bgm.start();
    }
}