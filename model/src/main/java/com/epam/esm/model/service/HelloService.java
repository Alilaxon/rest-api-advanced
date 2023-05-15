package com.epam.esm.model.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class HelloService {

    public String hello(){
        return "Hello";
    }
}
