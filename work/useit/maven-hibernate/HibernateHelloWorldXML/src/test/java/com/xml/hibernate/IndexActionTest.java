package com.xml.hibernate;

import com.xml.hibernate.beans.Employee;
import com.xml.hibernate.HibernateUtil;
import java.sql.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.junit.BeforeClass;
import org.junit.Test;

public class IndexActionTest {

    @BeforeClass
    public static void set() throws Exception {
    }

    @Test
    public void trackid() {
        System.out.println("******* READ *******");
        List employees = list();
        System.out.println("Total Employees: " + employees.size());

        // Write
        System.out.println("******* WRITE *******");
        Employee empl = new Employee("Jack", "Bauer", new Date(System.currentTimeMillis()), "911");
        empl = save(empl);
        empl = read(empl.getId());
        System.out.printf("%d %s %s \n", empl.getId(), empl.getFirstname(), empl.getLastname());

        
    }

    private static List list() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();

        List employees = session.createQuery("from Employee").list();
        session.close();
        return employees;
    }

    private static Employee read(Long id) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();

        Employee employee = (Employee) session.get(Employee.class, id);
        session.close();
        return employee;
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

    private static Employee update(Employee employee) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();

        session.beginTransaction();

        session.merge(employee);

        session.getTransaction().commit();

        session.close();
        return employee;

    }

    private static void delete(Employee employee) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();

        session.beginTransaction();

        session.delete(employee);

        session.getTransaction().commit();

        session.close();
    }

}
