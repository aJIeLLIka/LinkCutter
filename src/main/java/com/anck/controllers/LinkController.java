package com.anck.controllers;

import com.anck.dto.RequestLinkDto;
import com.anck.dto.ResponseLinkDto;
import com.anck.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;


@RestController
@RequestMapping("/api")
public class LinkController {
    private final LinkService linkService;

    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/getOriginalLink")
    public ResponseLinkDto getOriginalValue(@RequestParam(name = "shortValue") String shortValue) {
        return linkService.getLongLinkByShortValue(shortValue);
    }

    @PostMapping("/createShortLink")
    public ResponseLinkDto createShortValue(@RequestBody RequestLinkDto requestLinkDto) {
        return linkService.getShortValue(requestLinkDto);
    }
}
