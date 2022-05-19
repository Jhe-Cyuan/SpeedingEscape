package com.mcuwing.speedingescape;

import android.content.res.Resources;
import android.view.animation.Animation;
import android.widget.ImageView;

interface Drop {

    void startAnimation(String flag);
    void clearAnimation();

    void setX(float newX);
    void setY(float newY);
    void setLocate(int locate);
    void setMovable(boolean bool);
    void setType(int type);
    void setSpeed(float speed);
    void setImage(int image);
    void setHitTimes(int hitTimes);

    float getX();
    float getY();
    int getLocate();
    boolean getMovable();
    int getType();
    ImageView getImage();
    float getSpeed();
    Animation getMovement(String flag);
    int getHitTimes();

    void MoveX(String flag);
    void MoveY();

    float Ability_Speed(Resources res, Drop[] drops);
    int Ability_Hited();
}
