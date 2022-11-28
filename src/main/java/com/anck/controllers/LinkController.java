package com.anck.controllers;

import com.anck.models.Link;
import com.anck.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LinkController {
    private final LinkService linkService;
    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }


    @GetMapping("/hello")
    public Link helloMethod(){
        return linkService.findOne(12);
    }

}
