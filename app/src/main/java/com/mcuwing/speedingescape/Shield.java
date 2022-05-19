package com.mcuwing.speedingescape;

import android.content.res.Resources;
import android.view.View;
import android.view.animation.Animation;

class Shield extends Item {
    Shield(View image, float speed, Animation.AnimationListener listener) {
        super(image, speed, listener);
    }
    //Hit 1 times
    public float Ability_Speed(Resources res, Drop[] drops) {
        return drops[0].getSpeed();
    }

    public int Ability_Hited() {
        return 1;
    }
}
