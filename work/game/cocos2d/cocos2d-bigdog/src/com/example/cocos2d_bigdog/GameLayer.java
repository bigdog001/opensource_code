package com.example.cocos2d_bigdog;

import org.cocos2d.actions.instant.CCFlipX;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.instant.CCShow;
import org.cocos2d.actions.interval.CCJumpTo;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 3/6/14
 * Time: 8:50 AM
 */
public class GameLayer extends CCLayer {
    CCSprite ccSprite ;
    public GameLayer() {
        //初始化精灵对象
        ccSprite = CCSprite.sprite("plaer.png");
        //setup the position for the sprite instance
        CGPoint cgPoint = CGPoint.ccp(100,100);
        ccSprite.setPosition(cgPoint);

        //put the sprite into the gamelayer
        this.addChild(ccSprite);

//        CGPoint ta = CGPoint.ccp(200,300);
//        CCJumpTo jumpTo = CCJumpTo.action(3,ta,200,2);
//        ccSprite.runAction(jumpTo);

        //生成动作对象
//        CCHide hide = CCHide.action();
//        ccSprite.runAction(hide);
//
//        CCShow show = CCShow.action();
//        ccSprite.runAction(show);

//        CGPoint cgPoint1 = CGPoint.ccp(400,300);
//        CCMoveTo moveTo = CCMoveTo.action(3,cgPoint1);
//        ccSprite.runAction(moveTo);

        CCRotateTo ccRotateTo = CCRotateTo.action(3,20);
        ccSprite.runAction(ccRotateTo);




    }
}
