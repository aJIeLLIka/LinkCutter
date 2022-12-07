package com.anck.controllers;

import com.anck.DTO.RequestLinkDto;
import com.anck.DTO.ResponseLinkDto;
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
    public Long saveLink(@RequestBody RequestLinkDto requestLinkDto){
        Link linkForSave = convertToLink(requestLinkDto);
        System.out.println(linkForSave.getOriginalValue());
        if(linkService.isPresentOriginalValue(linkForSave)){
            return linkForSave.getId();
        }else {
            return linkService.save(linkForSave).getId();
        }
    }

    private Link convertToLink(RequestLinkDto requestLinkDto) {
        return new Link(requestLinkDto.getOriginalValue());
    }

    private ResponseLinkDto convertToResponseDto(Link link){
        return new ResponseLinkDto(link.getId(), link.getOriginalValue(), link.getShortValue());
    }

}
