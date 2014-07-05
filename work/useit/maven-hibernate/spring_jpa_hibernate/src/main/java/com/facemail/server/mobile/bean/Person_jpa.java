package com.facemail.server.mobile.bean;

import com.facemail.server.mobile.emu.bigdog;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 5/22/13
 * Time: 5:32 AM
 * To change this template use File | Settings | File Templates.
 * //@Table(name = "person_jpa",uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
 */
@Entity
@Table(name = "person_jpa")
public class Person_jpa implements Serializable {
    private int id;
    private String name;
    private byte[]pic;
    private bigdog dog;
    private Calendar calendar;
    private Date date;
    transient private String abc;
    private Name names;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Embedded
    public Name getNames() {
        return names;
    }

    public void setNames(Name names) {
        this.names = names;
    }

    public String getAbc() {
        return abc;
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Enumerated(EnumType.STRING)
    public bigdog getDog() {
        return dog;
    }

    public void setDog(bigdog dog) {
        this.dog = dog;
    }

    @Lob
    @Column(nullable = true)
    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Column(length = 50 , nullable = false)





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


    public void postit(){

    }
}
