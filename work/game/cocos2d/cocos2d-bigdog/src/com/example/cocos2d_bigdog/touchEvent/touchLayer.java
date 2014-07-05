package com.example.cocos2d_bigdog.touchEvent;

import android.view.MotionEvent;
import com.example.cocos2d_bigdog.util.Util;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 3/7/14
 * Time: 6:58 AM
 */
public class touchLayer extends CCLayer {
    public touchLayer() {
        setIsTouchEnabled(true);
    }

    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
       CGPoint point = CGPoint.ccp(event.getX(), event.getY());
        Util.log(point+"====="+ CCDirector.sharedDirector().convertToGL(point));
        return super.ccTouchesBegan(event);
    }

    @Override
    public boolean ccTouchesMoved(MotionEvent event) {
        return super.ccTouchesMoved(event);
    }

    @Override
    public boolean ccTouchesEnded(MotionEvent event) {
        return super.ccTouchesEnded(event);
    }
}
