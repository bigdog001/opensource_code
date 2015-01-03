package com.bigdog.server.web.crimedoc.bean;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/6/13
 * Time: 8:57 AM
 */

@NamedQueries({
        @NamedQuery(name="getadmin", query= "FROM Admin WHERE username=?1 and passwd=?2"),
        @NamedQuery(name="detectAdmin", query= "FROM Admin WHERE username=?1")
})
@Entity
@Table(name = "admin")
public class Admin {
    private int id;
    private String username;
    private String passwd;
    private String role;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Column
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
