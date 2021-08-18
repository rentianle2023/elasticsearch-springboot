package com.es.demo.service;

import com.es.demo.document.Person;
import com.es.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    PersonRepository repository;

    public void savePerson(Person person){
        repository.save(person);
    }

    public Person findById(String id){
        return repository.findById(id).orElse(null);
    }
}
