package com.anck.services;

import com.anck.models.Link;
import org.springframework.stereotype.Service;

@Service
public class LinkService {

    //private final LinkRepository..

    public Link findOne(int id){ // Просто что бы что то вернуть:)
        return new Link("Link" + id);
    }
}
