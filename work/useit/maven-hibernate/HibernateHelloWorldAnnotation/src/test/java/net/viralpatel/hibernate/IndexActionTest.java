/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.viralpatel.hibernate;

import java.sql.Date;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author bigdog
 */
public class IndexActionTest {

    @BeforeClass
    public static void set() throws Exception {

    }

    @Test
    public void trackid() {
        // Write
        System.out.println("******* WRITE *******");
        Employee empl = new Employee("Jack1", "Bauer1", new Date(System.currentTimeMillis()), "9112");
        empl = save(empl);
    }

    private static Employee save(Employee employee) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        Long id = (Long) session.save(employee);
        employee.setId(id);
        session.getTransaction().commit();
        session.close();
        return employee;
    }
}
