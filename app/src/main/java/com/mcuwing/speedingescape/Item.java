package com.mcuwing.speedingescape;

import android.content.res.Resources;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.Random;

abstract class Item implements Drop {
    private ImageView image;
    private boolean movable;
    private float speed;
    private int locate;
    private int type;
    private Animation animation;

    Item(View image, float speed, Animation.AnimationListener listener) {
        this.image = (ImageView)image;
        this.speed = speed;
        locate = new Random().nextInt(3);
        animation = new TranslateAnimation(0, 0, 0,this.speed);
        animation.setAnimationListener(listener);
        animation.setRepeatCount(-1);
    }

    public void startAnimation(String flag) {
        image.startAnimation(animation);
    }

    public void clearAnimation() {
        image.clearAnimation();
    }

    public void setX(float newX) {
        image.setX(newX);
    }

    public void setY(float newY) {
        image.setY(newY);
    }

    public void setLocate(int locate) {
        this.locate = locate;
    }

    public void setMovable(boolean bool) {
        this.movable = bool;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setImage(int image) {
        this.image.setImageResource(image);
    }

    public void setHitTimes(int hitTimes) {

    }

    public float getX() {
        return image.getX();
    }

    public float getY() {
        return image.getY();
    }

    public int getLocate() {
        return locate;
    }

    public boolean getMovable() {
        return movable;
    }

    public int getType() {
        return this.type;
    }

    public ImageView getImage() {
        return image;
    }

    public float getSpeed() {
        return speed;
    }

    public Animation getMovement(String flag) {
        return animation;
    }

    public int getHitTimes() {
        return 1;
    }

    public void MoveX(String flag) {
        clearAnimation();
        switch(flag) {
            case "Left":
                setX(getX() - speed);
                break;
            case "Right":
                setX(getX() + speed);
                break;
        }
    }

    public void MoveY() {
        clearAnimation();
        setY(getY() + speed);
    }

    public abstract float Ability_Speed(Resources res, Drop[] drops);
    public abstract int Ability_Hited();
}
