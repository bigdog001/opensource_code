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
public class ResType {
    private int Id;
    private String typeName;
    private int parentType;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getParentType() {
        return parentType;
    }

    public void setParentType(int parentType) {
        this.parentType = parentType;
    }

        
}
