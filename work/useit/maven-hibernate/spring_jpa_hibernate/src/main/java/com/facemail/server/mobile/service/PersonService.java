package com.facemail.server.mobile.service;

import com.facemail.server.mobile.bean.Person;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 5/22/13
 * Time: 5:44 AM
 * To change this template use File | Settings | File Templates.
 */
public interface PersonService {
    public void save(Person person);
    public void update(Person person);
    public Person get(int id);
    public List<Person> getAll();
    public  void delete(int person);
}
