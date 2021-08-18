package com.es.demo.controller;

import com.es.demo.document.Person;
import com.es.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @Autowired
    PersonService service;

    @GetMapping("/save")
    public String savePerson(){
        Person person = new Person("1","xiaoming");
        service.savePerson(person);
        return "ok";
    }

}

