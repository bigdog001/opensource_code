package com.facemail.server.mobile.bean.exm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Branch {
    private int b_id;
    private String address;
    private String city;
    private List<Staff> staff;

    public Branch() {
    }

    public Branch(int b_id, String address, String city, List<Staff> staff) {
        this.b_id = b_id;
        this.address = address;
        this.city = city;
        this.staff = staff;
    }

    @Id
    @Column
    public int getB_id() {
        return b_id;
    }

    public void setB_id(int b_id) {
        this.b_id = b_id;
    }

    @Column
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @OneToMany(mappedBy = "brach")
    public List<Staff> getStaff() {
        return staff;
    }

    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }

}
