package com.anck.services;

import com.anck.DTO.RequestLinkDto;
import com.anck.models.Link;
import com.anck.repositories.LinkRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LinkService {

    private final LinkRepository linkRepository;

    @Autowired
    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Transactional
    public Link save(Link link) {
        enrichLink(link);
        return linkRepository.save(link);
    }

    public Link findOne(Long id) {
        Optional<Link> optionalLinkLink = linkRepository.findById(id);
        if (optionalLinkLink.isPresent()) {
            Link foundLink = optionalLinkLink.get();
            updateLastUsageDate(foundLink);
            return foundLink;
        } else {
            throw new RuntimeException("Link with id=" + id + " doesn't exist");
        }
    }

    public boolean isPresentOriginalValue(Link link) {
        Optional<Link> checkedLink = linkRepository.findByOriginalValue(link.getOriginalValue());
        if (checkedLink.isPresent()) {
            link.setId(checkedLink.get().getId());
            return true;
        } else {
            return false;
        }
    }

    private boolean isPresentShortValue(String shortValue) {
        Optional<Link> link = linkRepository.findByShortValue(shortValue);
        if (link.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    private void enrichLink(Link link) {
        link.setShortValue(generateShortValue());
        link.setCreationDate(LocalDateTime.now());
        link.setLastUsageDate(LocalDateTime.now());
    }

    private void updateLastUsageDate(Link link) {
        link.setLastUsageDate(LocalDateTime.now());
        linkRepository.save(link);
    }

    // TODO: генератор не отвечает за уникальность хранения данных в БД!
    // TODO: вынести в конфиг параметр для определения длины короткой ссылки и сгенерировать ее.
    private String generateShortValue() {
        String shortValue;
        do {
            int shortValueLength = (int) (Math.random() * (10 - 5 + 1) + 5); //рандом от 5 до 10
            shortValue = RandomStringUtils.randomAlphanumeric(shortValueLength);
            System.out.println("shortValue = " + shortValue);
        } while (isPresentShortValue(shortValue));
        return shortValue;
    }

    public String getShortLink(RequestLinkDto requestLinkDto) {
        Optional<Link> optionalOriginalValue = linkRepository.findByOriginalValue(requestLinkDto.getOriginalValue());
        if (optionalOriginalValue.isPresent()) {
            return optionalOriginalValue.get().getShortValue();
        } else {
            return saveOriginalLink(requestLinkDto.getOriginalValue()).getShortValue();
        }
    }

    @Transactional
    public Link saveOriginalLink(String originalValue) {

        Link link = new Link();
        link.setOriginalValue(originalValue);
        link.setCreationDate(LocalDateTime.now());
        link.setLastUsageDate(LocalDateTime.now());
        link.setShortValue(generateShortValue());

        return linkRepository.save(link);
    }
}
