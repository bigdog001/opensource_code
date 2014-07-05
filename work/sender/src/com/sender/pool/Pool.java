package com.sender.pool;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/18/12
 * Time: 3:59 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Pool {
    public Pool(){
        initPool();
    }
    public abstract void initPool();
}
