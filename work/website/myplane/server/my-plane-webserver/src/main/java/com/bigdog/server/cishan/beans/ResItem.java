/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bigdog.server.cishan.beans;

/**
 *
 * @author bigdog
 */
public class ResItem {
    private int id;
    private String resItemName;
    private String brand;
    private double price;
    private int resStatus = 1;// 物品当前状态 1：用户发起捐赠意愿并提交了运单号码,2:仓库已经收到物品并处于完善保存状态
                              //   ,3：系统已经对此物品进行了分配,即将送往受助人手中。
    
    private TransportBean tb;//运输信息
    
}
