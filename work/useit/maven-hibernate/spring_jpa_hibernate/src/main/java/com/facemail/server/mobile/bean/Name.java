package com.facemail.server.mobile.bean;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 5/24/13
 * Time: 1:14 AM
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
@Access(AccessType.FIELD)
public class Name {
    private String first_name;
    private String middle_name;
    private String last_name;
    private int ssn;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getSsn() {
        return ssn;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }
}
