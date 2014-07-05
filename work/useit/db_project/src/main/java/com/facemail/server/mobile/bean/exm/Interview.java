package com.facemail.server.mobile.bean.exm;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Interview {
    private int i_id;
    private Date date;
    private String interverName;
    private Client client;

    public Interview() {
    }

    public Interview(int i_id, Date date, String interverName, Client client) {
        this.i_id = i_id;
        this.date = date;
        this.interverName = interverName;
        this.client = client;
    }

    @OneToOne(mappedBy = "interview", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, optional = true)
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Id
    public int getI_id() {
        return i_id;
    }

    public void setI_id(int i_id) {
        this.i_id = i_id;
    }

    @Column
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column
    public String getInterverName() {
        return interverName;
    }

    public void setInterverName(String interverName) {
        this.interverName = interverName;
    }

}
