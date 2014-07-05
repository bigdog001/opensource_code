package com.example.cocos2d_bigdog.schedule;

import com.example.cocos2d_bigdog.util.Util;
import org.cocos2d.layers.CCLayer;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 3/7/14
 * Time: 7:08 AM
 */
public class TimerLayer extends CCLayer{
    public TimerLayer() {
        this.schedule("doit",1);
    }
    private void doit(){
        Util.log("================comes");
    }
}
