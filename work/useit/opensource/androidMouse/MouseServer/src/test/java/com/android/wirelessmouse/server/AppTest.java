package com.android.wirelessmouse.server;

import org.junit.Test;
/**
 * Unit test for simple App.
 */
public class AppTest{

     @Test
    public void testApp(){
        MyMouseController mmc = new MyMouseController();
        System.out.println("mouse control start:");
        mmc.Move(20, 20);//坐标为相对坐标
        System.out.println("=======second moving=======");
        mmc.Move(40, 40);

        System.out.println("mouse control stop.");   
    }
}
