package com.epam.esm.web.controller.test;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/greetings")
public class GreetingsController {

    @GetMapping
    public List<String> greetings() {

        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("friend");

        return list;
    }
    @GetMapping("/unsecured")
    public String unsecuredData(){
        return "Unsecured Data";
    }

    @GetMapping("/secured")
    public String securedData(){
        return "secured Data";
    }

    @GetMapping("/admin")
    public String adminData(){
        return "admin Data";
    }

    @GetMapping("/info")
    public String userData(Principal principal){
        System.out.println(principal.toString());
       // principal.getName();
        return "info =" + principal.getName();
    }
}
