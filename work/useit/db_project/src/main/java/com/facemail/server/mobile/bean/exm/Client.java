package com.facemail.server.mobile.bean.exm;

import javax.persistence.*;
import java.util.List;

@Entity
public class Client {
    private int clientNo_PK;
    private String personalDetails;
    private String validProvisonalLicense;
    private String specialNeeds;
    private String writenTestPassed;
    private Interview interview;
    private Staff staff;
    private List<Lesson> lessons;
    private List<DrivingTest> drivingtests;

    public Client() {
    }

    public Client(int clientNo_PK, String personalDetails, String validProvisonalLicense, String specialNeeds, String writenTestPassed, Interview interview, Staff staff, List<Lesson> lessons, List<DrivingTest> drivingtests) {
        this.clientNo_PK = clientNo_PK;
        this.personalDetails = personalDetails;
        this.validProvisonalLicense = validProvisonalLicense;
        this.specialNeeds = specialNeeds;
        this.writenTestPassed = writenTestPassed;
        this.interview = interview;
        this.staff = staff;
        this.lessons = lessons;
        this.drivingtests = drivingtests;
    }

    @OneToMany(mappedBy = "id.clientNO_PK")
    public List<DrivingTest> getDrivingtests() {
        return drivingtests;
    }

    public void setDrivingtests(List<DrivingTest> drivingtests) {
        this.drivingtests = drivingtests;
    }

    @OneToMany(mappedBy = "client")
    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "staff_id")
    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    @OneToOne(optional = false, cascade = {CascadeType.ALL})
    @JoinColumn(name = "interview_id")
    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }

    @Id
    public int getClientNo_PK() {
        return clientNo_PK;
    }

    public void setClientNo_PK(int clientNo_PK) {
        this.clientNo_PK = clientNo_PK;
    }

    @Column
    public String getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(String personalDetails) {
        this.personalDetails = personalDetails;
    }

    @Column
    public String getValidProvisonalLicense() {
        return validProvisonalLicense;
    }

    public void setValidProvisonalLicense(String validProvisonalLicense) {
        this.validProvisonalLicense = validProvisonalLicense;
    }

    @Column
    public String getSpecialNeeds() {
        return specialNeeds;
    }

    public void setSpecialNeeds(String specialNeeds) {
        this.specialNeeds = specialNeeds;
    }

    @Column
    public String getWritenTestPassed() {
        return writenTestPassed;
    }

    public void setWritenTestPassed(String writenTestPassed) {
        this.writenTestPassed = writenTestPassed;
    }

}
