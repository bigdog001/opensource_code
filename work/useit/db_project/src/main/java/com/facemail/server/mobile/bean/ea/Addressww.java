package com.facemail.server.mobile.bean.ea;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/4/13
 * Time: 11:00 AM
 */
@Entity
@Table
public class Addressww implements Serializable {
    private int id;
    private String fa1="1";
    private String fa2="2";
    private String fa3="3";
    private String fa4="4";
    private Customer customer;

    @OneToOne
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column
    public String getFa1() {
        return fa1;
    }

    public void setFa1(String fa1) {
        this.fa1 = fa1;
    }

    @Column
    public String getFa2() {
        return fa2;
    }

    public void setFa2(String fa2) {
        this.fa2 = fa2;
    }

    @Column
    public String getFa3() {
        return fa3;
    }

    public void setFa3(String fa3) {
        this.fa3 = fa3;
    }

    @Column
    public String getFa4() {
        return fa4;
    }

    public void setFa4(String fa4) {
        this.fa4 = fa4;
    }
}
