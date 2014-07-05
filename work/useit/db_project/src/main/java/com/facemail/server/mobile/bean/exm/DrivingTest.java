package com.facemail.server.mobile.bean.exm;

import javax.persistence.*;

import com.facemail.server.mobile.bean.exm.pk.DrivingTestPK;

@Entity
public class DrivingTest {
    private DrivingTestPK id;
    private Client client;
    private String drivingTestPassed;
    private String reasonForFailing;

    public DrivingTest() {
    }

    public DrivingTest(DrivingTestPK id, Client client, String drivingTestPassed, String reasonForFailing) {
        this.id = id;
        this.client = client;
        this.drivingTestPassed = drivingTestPassed;
        this.reasonForFailing = reasonForFailing;
    }

    @EmbeddedId
    public DrivingTestPK getId() {
        return id;
    }

    public void setId(DrivingTestPK id) {
        this.id = id;
    }


    @Column
    public String getDrivingTestPassed() {
        return drivingTestPassed;
    }

    public void setDrivingTestPassed(String drivingTestPassed) {
        this.drivingTestPassed = drivingTestPassed;
    }

    @Column
    public String getReasonForFailing() {
        return reasonForFailing;
    }

    public void setReasonForFailing(String reasonForFailing) {
        this.reasonForFailing = reasonForFailing;
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
