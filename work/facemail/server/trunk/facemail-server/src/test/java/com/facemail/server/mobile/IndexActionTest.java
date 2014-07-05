/*
 * Copyright 2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facemail.server.mobile;


import com.facemail.server.mobile.bean.Person;
import com.facemail.server.mobile.bean.Person_jpa;
import com.facemail.server.mobile.service.PersonService;
import com.facemail.server.mobile.service.PersonService_jpa;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * 
 */
public class IndexActionTest {
   /* private static PersonService personService;
//    @BeforeClass
    public static void set() throws Exception{
        try {
            ApplicationContext applicationContext= new ClassPathXmlApplicationContext("applicationContext.xml");
            personService = (PersonService) applicationContext.getBean("personService");
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

//    @Test
    public void save() throws Exception {
        Person person = new Person();
        person.setName("我");
        personService.save(person);

    }
//    @Test
    public void delete() throws Exception {
        personService.delete(2);
    }
//    @Test
    public void get() throws Exception {
        System.out.println(personService.get(1).getName());
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(personService.get(1).getName());
    }
//    @Test
    public void getAll() throws Exception {
        List<Person> persons=personService.getAll();
        for (Person person:persons) {
            System.out.println(person.getName());
        }
    }

//    @Test
    public void update() throws Exception {
        Person person = new Person();
        person.setId(1);
        person.setName("陈三");
        personService.update(person);
    }*/

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
