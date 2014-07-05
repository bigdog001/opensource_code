package com.sender.impl;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/18/12
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract  class Task {
    public Task(){
        initData(this);
    }
    public abstract void initData(Task task);
}
