package com.mcuwing.speedingescape;

import android.content.res.Resources;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

class Player extends Car {
    private Animation left, right;
    private int hitTimes;

    Player(View image, float moveLong, Animation.AnimationListener listener) {
        super(image, moveLong);
        hitTimes = 1;
        left = new TranslateAnimation(0, -getSpeed(), 0, 0);
        left.setAnimationListener(listener);
        left.setDuration(100);
        right = new TranslateAnimation(0, getSpeed(), 0, 0);
        right.setAnimationListener(listener);
        right.setDuration(100);
    }

    public void startAnimation(String flag) {
        switch(flag) {
            case "Left":
                getImage().startAnimation(left);
                break;
            case "Right":
                getImage().startAnimation(right);
                break;
        }
    }

    public void setHitTimes(int hitTimes) {
        this.hitTimes = hitTimes;
    }

    public Animation getMovement(String flag) {
        switch(flag) {
            default:
                return null;
            case "Left":
                return left;
            case "Right":
                return right;
        }
    }

    public int getHitTimes() {
        return hitTimes;
    }

    public float Ability_Speed(Resources res, Drop[] drops) {
        return 0;
    }

    public int Ability_Hited() {
        return 0;
    }
}
