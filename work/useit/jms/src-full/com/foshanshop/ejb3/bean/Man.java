package com.foshanshop.ejb3.bean;

import java.io.Serializable;

public class Man implements Serializable{

    private static final long serialVersionUID = -1789733418716736359L;
    private String name;//ĞÕÃû
    private String address;//µØÖ·

    public Man(String name, String address) {
        this.name = name;
        this.address = address;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
