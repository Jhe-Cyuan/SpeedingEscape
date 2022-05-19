package com.mcuwing.speedingescape;

import android.content.res.Resources;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

class Obstacle extends Car {
    private Animation animation;

    Obstacle(View image, float movelong, Animation.AnimationListener listener) {
        super(image, movelong);
        animation = new TranslateAnimation(0, 0, 0,getSpeed());
        animation.setAnimationListener(listener);
        animation.setRepeatCount(-1);
    }

    public void startAnimation(String flag) {
        getImage().startAnimation(animation);
    }

    public void setHitTimes(int hitTimes) {

    }

    public Animation getMovement(String flag) {
        return animation;
    }

    public int getHitTimes() {
        return 1;
    }

    public float Ability_Speed(Resources res, Drop[] drops) {
        return 0;
    }

    public int Ability_Hited() {
        return 0;
    }
}
