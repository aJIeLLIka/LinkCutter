package com.anck.controllers;

import com.anck.dto.RequestLinkDto;
import com.anck.dto.ResponseLinkDto;
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

    @PostMapping("/createShortLink")
    public ResponseLinkDto createShortValue(@RequestBody RequestLinkDto requestLinkDto) {
        return linkService.getShortValue(requestLinkDto);
    }

    @GetMapping("/getOriginalLink")
    public ResponseLinkDto getOriginalValue(@RequestParam(name = "shortLink") String shortLink) {
        return linkService.getLongLinkByShortValue(shortLink);
    }

}
