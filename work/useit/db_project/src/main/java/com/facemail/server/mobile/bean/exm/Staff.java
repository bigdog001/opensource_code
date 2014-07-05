package com.facemail.server.mobile.bean.exm;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Staff {
    private int staff_id;
    private String position;
    private Car car;
    private int gender;
    private Date DOB;
    private Branch brach;
    private List<Client> client;
    private List<Lesson> lesson;

    public Staff() {
    }

    public Staff(int staff_id, String position, Car car, int gender, Date DOB, Branch brach, List<Client> client, List<Lesson> lesson) {
        this.staff_id = staff_id;
        this.position = position;
        this.car = car;
        this.gender = gender;
        this.DOB = DOB;
        this.brach = brach;
        this.client = client;
        this.lesson = lesson;
    }

    @OneToMany(mappedBy = "staff")
    public List<Lesson> getLesson() {
        return lesson;
    }

    public void setLesson(List<Lesson> lesson) {
        this.lesson = lesson;
    }

    @OneToMany(mappedBy = "staff")
    public List<Client> getClient() {
        return client;
    }

    public void setClient(List<Client> client) {
        this.client = client;
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "b_id")
    public Branch getBrach() {
        return brach;
    }

    public void setBrach(Branch brach) {
        this.brach = brach;
    }

    @Id
    @Column
    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    @Column
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


    @OneToOne(optional = false, cascade = {CascadeType.ALL})
    @JoinColumn(name = "carNo")
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Column
    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    @Column
    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date dOB) {
        DOB = dOB;
    }

}
