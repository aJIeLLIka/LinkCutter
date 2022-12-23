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
public class LinkController {
    private final LinkService linkService;

    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/createShortLink")
    public ResponseLinkDto createShortValue(@RequestBody RequestLinkDto requestLinkDto) {
        System.out.println(requestLinkDto.getOriginalValue());
        return linkService.getShortValue(requestLinkDto);
    }

    @GetMapping("/getOriginalLink")
    public ResponseLinkDto getOriginalValue(@RequestParam(name = "shortValue") String shortValue) {
        return linkService.getLongLinkByShortValue(shortValue);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class) // exc при коллизии shortValue (shortValueUniqConstraint)
    private ResponseEntity handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Something went wrong, please try it later:)");
    }

}
