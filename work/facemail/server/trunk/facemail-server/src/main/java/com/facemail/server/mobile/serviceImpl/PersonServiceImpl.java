package com.facemail.server.mobile.serviceImpl;

import com.facemail.server.mobile.bean.Person;
import com.facemail.server.mobile.service.PersonService;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 5/22/13
 * Time: 5:47 AM
 */
@Transactional
public class PersonServiceImpl implements PersonService {
    @Resource
    private SessionFactory sessionfactory;

    @Override
    public void save(Person person) {
        sessionfactory.getCurrentSession().persist(person);
    }

    @Override
    public void update(Person person) {
        sessionfactory.getCurrentSession().merge(person);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
    @Override
    public Person get(int id) {
        return (Person) sessionfactory.getCurrentSession().get(Person.class,id);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
    @Override
    public List<Person> getAll() {
        return sessionfactory.getCurrentSession().createQuery("from Person").list();
    }

    @Override
    public void delete(int person) {
        sessionfactory.getCurrentSession().delete(sessionfactory.getCurrentSession().load(Person.class, person));
    }
}
