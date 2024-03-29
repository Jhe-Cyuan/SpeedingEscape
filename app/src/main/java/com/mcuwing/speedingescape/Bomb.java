package com.mcuwing.speedingescape;

import android.content.res.Resources;
import android.view.View;
import android.view.animation.Animation;

class Bomb extends Item {
    Bomb(View image, float speed, Animation.AnimationListener listener) {
        super(image, speed, listener);
    }
    //Obstacle Move quicker
    public float Ability_Speed(Resources res, Drop[] drops) {
        float nowSpeed = 0;
        for (Drop drop : drops) {
            if(drop != null && drop.getSpeed() > nowSpeed)
                nowSpeed = drop.getSpeed();
        }
        float newSpeed = nowSpeed;
        if(nowSpeed < Function.toPx(res, 20)) {
            newSpeed += Function.toPx(res, 3);
            for (Drop drop : drops) {
                if(drop != null)
                    drop.setSpeed(newSpeed);
            }
        }
        return newSpeed;
    }

    public int Ability_Hited() {
        return 0;
    }
}
