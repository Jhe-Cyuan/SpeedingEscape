package com.mcuwing.speedingescape;

import android.content.res.Resources;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import java.util.Random;

abstract class Car implements Drop {
    private ImageView image;
    private boolean movable;
    private float speed;
    private int locate;
    private int type;

    Car(View image, float speed) {
        this.image = (ImageView)image;
        this.speed = speed;
        movable = true;
        locate = new Random().nextInt(3);
        type = 0;
    }

    public abstract void startAnimation(String flag);

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
        movable = bool;
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

    public abstract void setHitTimes(int hitTimes);

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

    public abstract Animation getMovement(String flag);

    public abstract int getHitTimes();

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
