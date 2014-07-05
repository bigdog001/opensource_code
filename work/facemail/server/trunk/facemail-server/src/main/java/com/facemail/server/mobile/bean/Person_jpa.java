package com.facemail.server.mobile.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 5/22/13
 * Time: 5:32 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity(name = "person_jpa")
public class Person_jpa implements Serializable {
    private int id;
    private String name;
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Column(length = 50 , nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person_jpa person = (Person_jpa) o;

        if (id != person.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
