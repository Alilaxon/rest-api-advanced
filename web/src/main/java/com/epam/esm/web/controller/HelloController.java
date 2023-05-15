package com.epam.esm.web.controller;

import com.epam.esm.model.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final HelloService helloService;


    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping
    public String hello(){

        return helloService.hello();
    }
}
