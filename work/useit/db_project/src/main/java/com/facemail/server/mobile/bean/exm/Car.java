package com.facemail.server.mobile.bean.exm;

import javax.persistence.*;

@Entity
public class Car {
    private int carNo;
    private int numberFaults;
    private int registrationNumber;
    private String faultDescription;
    private Staff staff;

    public Car() {
    }

    public Car(int carNo, int numberFaults, int registrationNumber, String faultDescription, Staff staff) {
        this.carNo = carNo;
        this.numberFaults = numberFaults;
        this.registrationNumber = registrationNumber;
        this.faultDescription = faultDescription;
        this.staff = staff;
    }

    @OneToOne(mappedBy = "car", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, optional = true)
    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    @Id
    @Column
    public int getCarNo() {
        return carNo;
    }

    public void setCarNo(int carNo) {
        this.carNo = carNo;
    }

    @Column
    public int getNumberFaults() {
        return numberFaults;
    }

    public void setNumberFaults(int numberFaults) {
        this.numberFaults = numberFaults;
    }

    @Column
    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(int registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Column
    public String getFaultDescription() {
        return faultDescription;
    }

    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }

}
