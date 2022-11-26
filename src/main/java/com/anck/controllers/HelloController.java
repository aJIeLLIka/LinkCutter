package com.anck.controllers;

import org.springframework.stereotype.Controller;

@Controller
public class HelloController {

    public String helloMethod(){
        return "Hello world";
    }
}
