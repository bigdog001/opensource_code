/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycomm.web.bean;

import com.mycomm.dao.mydao.MycommBean;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author jw362j
 */
@Entity
@Table(name = "jiunian")
public class JiuNian extends MycommBean{     
     private String myname;
     private String myaddress;
     private String somethingelse;

   
    public String getMyname() {
        return myname;
    }

    public void setMyname(String myname) {
        this.myname = myname;
    }

    public String getMyaddress() {
        return myaddress;
    }

    public void setMyaddress(String myaddress) {
        this.myaddress = myaddress;
    }

    public String getSomethingelse() {
        return somethingelse;
    }

    public void setSomethingelse(String somethingelse) {
        this.somethingelse = somethingelse;
    }
    
    
     
}
