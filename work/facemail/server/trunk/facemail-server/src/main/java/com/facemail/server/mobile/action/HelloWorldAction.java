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
package com.facemail.server.mobile.action;

import com.facemail.server.mobile.bean.Person;
import com.facemail.server.mobile.service.PersonService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.conversion.annotations.Conversion;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validation;

import javax.annotation.Resource;
import java.util.Date;

@Validation()
@Conversion()
public class HelloWorldAction extends ActionSupport {
    @Resource
    private PersonService personService;
    private Date now;
    private String name;
    
    @TypeConversion(converter = "com.facemail.server.mobile.action.DateConverter")
    @RequiredFieldValidator(message = "Please enter the date")
    public void setDateNow(Date now) { this.now = now; }
    public Date getDateNow() { return now; }
   
    @RequiredStringValidator(message = "Please enter a name", trim = true)
    public void setName(String name) { this.name = name; }
    public String getName() { return this.name; }
    
    public String execute() throws Exception {
        Person person = new Person();
        person.setName(name);
        personService.save(person);
        System.out.println(this+"----------");
        return SUCCESS;
    }
}
