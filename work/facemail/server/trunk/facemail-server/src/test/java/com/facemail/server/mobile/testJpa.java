package com.facemail.server.mobile;

import com.facemail.server.mobile.bean.Person_jpa;
import com.facemail.server.mobile.service.PersonService_jpa;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 5/22/13
 * Time: 2:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class testJpa {
   private static PersonService_jpa personService_jpa;
    @BeforeClass
    public static void set() throws Exception{
        try {
            System.out.println("-------------------------==1");
            ApplicationContext applicationContext= new ClassPathXmlApplicationContext("Jpa_spring.xml");
            personService_jpa = (PersonService_jpa) applicationContext.getBean("personService_jpaIMpl");
            System.out.println("-------------------------==2");
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void save(){
        Person_jpa person_jpa1=new Person_jpa() ;
        person_jpa1.setName("王久念");
        personService_jpa.save(person_jpa1);
    }
}
