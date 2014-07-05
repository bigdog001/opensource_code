package com.facemail.server.mobile.bean.ea;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/4/13
 * Time: 10:59 AM
 */
@Entity
@Table(name = "Customerss" )
public class Customer implements Serializable {
    private int id;
    private String f1="1";
    private String f2="2";
    private String f3="3";
    private String f4="4";
    private Addressww address;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column
    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }

    @Column
    public String getF2() {
        return f2;
    }

    public void setF2(String f2) {
        this.f2 = f2;
    }

    @Column
    public String getF3() {
        return f3;
    }

    public void setF3(String f3) {
        this.f3 = f3;
    }

    @Column
    public String getF4() {
        return f4;
    }

    public void setF4(String f4) {
        this.f4 = f4;
    }

    @OneToOne(cascade={CascadeType.ALL})
    @JoinColumn
    public Addressww getAddress() {
        return address;
    }

    public void setAddress(Addressww address) {
        this.address = address;
    }
}
