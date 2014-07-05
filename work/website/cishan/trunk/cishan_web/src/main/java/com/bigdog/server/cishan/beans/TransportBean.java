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
public class TransportBean {
    private int id;
    private int transportType;//1 顺丰，2 申通。。。。
    private String transportVenderName;
    private int transportStatus;//1 捐助者寄送物品进库 0 邮寄物品至受助人
    private String transportNumber;//运单号
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransportType() {
        return transportType;
    }

    public void setTransportType(int transportType) {
        this.transportType = transportType;
    }

    public String getTransportVenderName() {
        return transportVenderName;
    }

    public void setTransportVenderName(String transportVenderName) {
        this.transportVenderName = transportVenderName;
    }

    public int getTransportStatus() {
        return transportStatus;
    }

    public void setTransportStatus(int transportStatus) {
        this.transportStatus = transportStatus;
    }

    public String getTransportNumber() {
        return transportNumber;
    }

    public void setTransportNumber(String transportNumber) {
        this.transportNumber = transportNumber;
    }
    
    
    
}
