package com.example.cocos2d_bigdog;

import android.app.Activity;
import android.os.Bundle;
import com.example.cocos2d_bigdog.touchEvent.touchLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

public class MyActivity extends Activity {
    private CCGLSurfaceView view = null;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpGame();

    }

    private void setUpGame(){
        view = new CCGLSurfaceView(MyActivity.this);
        setContentView(view);
        CCDirector director = CCDirector.sharedDirector();
        director.attachInView(view);
        director.setDisplayFPS(true);
        //设置每渲染一帧数据所需要的时间
        director.setAnimationInterval(1/30.0);
        //生成场景
        CCScene scene = CCScene.node();
//        生成布景层
        CCLayer gameLayer = new touchLayer();
        //将布景层添加到场景
        scene.addChild(gameLayer);
        director.runWithScene(scene);
    }
}
