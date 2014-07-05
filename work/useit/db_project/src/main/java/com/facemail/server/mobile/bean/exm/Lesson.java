package com.facemail.server.mobile.bean.exm;

import java.util.Date;

import javax.persistence.*;

import com.facemail.server.mobile.bean.exm.pk.LessonPK;

@Entity
public class Lesson {
    private LessonPK id;
    private int block;
    private String carNo;
    private int mileage;
    private String progress;
    private int fee;
    private Staff staff;
    private Client client;

    public Lesson() {
    }

    public Lesson(LessonPK id, int block, String carNo, int mileage, String progress, int fee, Staff staff, Client client) {
        this.id = id;
        this.block = block;
        this.carNo = carNo;
        this.mileage = mileage;
        this.progress = progress;
        this.fee = fee;
        this.staff = staff;
        this.client = client;
    }

    @EmbeddedId
    public LessonPK getId() {
        return id;
    }

    public void setId(LessonPK id) {
        this.id = id;
    }

    @Column
    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    @Column
    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    @Column
    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    @Column
    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    @Column
    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "staff_id")
    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "clientNo_PK")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
