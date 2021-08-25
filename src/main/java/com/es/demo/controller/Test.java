package com.es.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 这个类+A类，解释了为什么使用构造注入，而不是Autowire属性注入
 */

@Component
public class Test {

    /*@Autowired
    private A a;

    private final String hello = a.hello();*/
}
