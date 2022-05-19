package com.mcuwing.speedingescape;

import android.content.res.Resources;
import android.view.View;
import android.view.animation.Animation;

class Key extends Item {
    Key(View image, float speed, Animation.AnimationListener listener) {
        super(image, speed, listener);
    }
    //next level
    public float Ability_Speed(Resources res, Drop[] drops) {
        return drops[0].getSpeed();
    }

    public int Ability_Hited() {
        return 0;
    }
}
