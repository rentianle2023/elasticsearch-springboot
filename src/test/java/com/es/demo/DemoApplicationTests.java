package com.es.demo;


import com.es.demo.document.Person;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class DemoApplicationTests {


    @Test
    public void test(){
        Person person = new Person("1","zhangsan");

        Person person2 = new Person("2","zhangsan");
        List<Person> list = new LinkedList<>();
        List<Person> people = Stream.of(person,person2).collect(Collectors.toList());

        Person person1 = people.stream().filter((person3 -> person3.getId().equals("1"))).findAny().orElse(new Person());
        System.out.println(person1);

       /* Optional<String> personOptional = Optional.ofNullable(person.getName());
        System.out.println(personOptional.map((name) -> name.toUpperCase(Locale.ROOT)).orElseGet(()->"null"));*/
    }
}
