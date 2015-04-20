/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycomm.dao.mydao;

import java.io.Serializable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author jw362j
 */

@Entity
@DiscriminatorColumn(name="DISC",discriminatorType=DiscriminatorType.CHAR) 
@DiscriminatorValue("I") 
public class MycommBean implements Serializable{
    private Long u_id;
    private String  test;
    
     @Id
    @GeneratedValue
    public Long getU_id() {
        return u_id;
    }

    public void setU_id(Long u_id) {
        this.u_id = u_id;
    }


    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
    
}
