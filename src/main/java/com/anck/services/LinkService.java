package com.anck.services;

import com.anck.models.Link;
import com.anck.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional(readOnly = true)
public class LinkService {

    private final LinkRepository linkRepository;
    @Autowired
    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Transactional
    public Link save(Link link){
        link.setShortValue(generateShortValue(link.getOriginalValue()));
        return linkRepository.save(link);
    }

    public Link findOne(Long id){
        Optional<Link> foundLink = linkRepository.findById(id);
        return foundLink.orElseThrow(() -> new RuntimeException(
                "Link with id=" + id + " doesn't exist"));
    }

    public String generateShortValue(String originalValue){// !!!TEMP IMPLEMENTATION!!!
        Random r = new Random();
        String shortValue = "";
        shortValue += (char)(r.nextInt(26) + 'a');
        shortValue += (char)(r.nextInt(26) + 'a');
        shortValue += (char)(r.nextInt(26) + 'a');
        shortValue += (char)(r.nextInt(26) + 'a');
        shortValue += (char)(r.nextInt(26) + 'a');
        System.out.println("generateShortValue - " + shortValue);
        return shortValue;
    }
}
