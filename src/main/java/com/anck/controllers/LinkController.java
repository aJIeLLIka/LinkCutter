package com.anck.controllers;

import com.anck.DTO.LinkDTO;
import com.anck.models.Link;
import com.anck.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Long saveLink(@RequestBody LinkDTO linkDTO){
        return linkService.save(convertToLink(linkDTO)).getId();
    }

    private Link convertToLink(LinkDTO linkDTO) {
        Link link = new Link();
        link.setOriginalValue(linkDTO.getOriginValue());
        return link;
    }

}
