package com.facemail.server.mobile.service;

import com.facemail.server.mobile.bean.Person_jpa;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 5/22/13
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PersonService_jpa {
    public void save(Person_jpa person);
    public void update(Person_jpa person);
    public Person_jpa get(int id);
    public List<Person_jpa> getAll();
    public  void delete(int person);
}
