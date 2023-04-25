package com.anck.services;

import com.anck.dto.RequestLinkDto;
import com.anck.dto.ResponseLinkDto;
import com.anck.entity.LinkInfo;
import com.anck.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LinkService {

    private final LinkRepository linkRepository;
    private final GeneratorService generatorService;

    @Autowired
    public LinkService(LinkRepository linkRepository, GeneratorService generatorService) {
        this.linkRepository = linkRepository;
        this.generatorService = generatorService;
    }

    @Transactional
    public LinkInfo saveLinkInfo(RequestLinkDto requestLinkDto) {
        LinkInfo link = new LinkInfo();
        link.setOriginalValue(requestLinkDto.getOriginalValue());
        link.setCreationDate(LocalDateTime.now());
        link.setLastUsageDate(LocalDateTime.now());
        link.setShortValue(generatorService.generateShortValue());
        return linkRepository.save(link);
    }

    @Transactional
    public ResponseLinkDto getShortValue(RequestLinkDto requestLinkDto) {
        Optional<LinkInfo> optionalLink =
                linkRepository.findByOriginalValue(requestLinkDto.getOriginalValue());
        if (optionalLink.isPresent()) {
            LinkInfo foundLink = optionalLink.get();
            updateLastUsageDate(foundLink.getId());
            return convertToResponseDto(foundLink);
        } else {
            LinkInfo savedEntity = saveLinkInfo(requestLinkDto);
            return convertToResponseDto(savedEntity);
        }
    }

    public ResponseLinkDto getLongLinkByShortValue(String shortValue) {
        if (shortValue == null || shortValue.isBlank())
            throw new IllegalArgumentException("parameter of short value is not present");

        Optional<LinkInfo> optionalOriginalValue = linkRepository.findByShortValue(shortValue);
        LinkInfo linkInfo = optionalOriginalValue.orElseThrow(() ->
                new EntityNotFoundException("Link with this value doesn't exist"));
        return convertToResponseDto(linkInfo);
    }

    private int updateLastUsageDate(Long id) {
        return linkRepository.updateLastUsage(id);
    }

    public LinkInfo convertToLink(RequestLinkDto requestLinkDto) {
        return new LinkInfo(requestLinkDto.getOriginalValue());
    }

    public ResponseLinkDto convertToResponseDto(LinkInfo linkInfo) {
        return new ResponseLinkDto(linkInfo.getOriginalValue(), linkInfo.getShortValue());
    }

}
