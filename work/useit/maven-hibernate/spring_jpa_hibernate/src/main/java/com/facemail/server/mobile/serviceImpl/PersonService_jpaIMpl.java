package com.facemail.server.mobile.serviceImpl;

import com.facemail.server.mobile.bean.Person_jpa;
import com.facemail.server.mobile.service.PersonService_jpa;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 5/22/13
 * Time: 2:02 PM
 */
@Transactional
public class PersonService_jpaIMpl implements PersonService_jpa {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void save(Person_jpa person) {
       entityManager.persist(person);
    }

    @Override
    public void update(Person_jpa person) {
       entityManager.merge(person);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
    @Override
    public Person_jpa get(int id) {
        return entityManager.find(Person_jpa.class,id);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
    @Override
    public List<Person_jpa> getAll() {
        return entityManager.createQuery("select 0 from Person_jpa  o").getResultList();
    }

    @Override
    public void delete(int person) {
       entityManager.remove(entityManager.getReference(Person_jpa.class, person));
    }


    public void ttt(){}

}
