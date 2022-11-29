package com.anck.controllers;

import com.anck.models.Link;
import com.anck.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class LinkController {
    private final LinkService linkService;
    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/getLink")
    public Link getLink(@RequestParam(name="id") Long id){
        return linkService.findOne(id);
    }

    @GetMapping("/save")
    public Long saveLink(@RequestParam(name="link") String originalValue){
        Link newLink = new Link(); // походу это должно быть не в контроллере...:)
        newLink.setOriginalValue(originalValue);
        return linkService.save(newLink).getId();
    }

}
