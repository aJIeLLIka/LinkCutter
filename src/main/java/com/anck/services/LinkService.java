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
@Transactional
public class LinkService {

    private final LinkRepository linkRepository;
    private final GeneratorService generatorService;

    @Autowired
    public LinkService(LinkRepository linkRepository, GeneratorService generatorService) {
        this.linkRepository = linkRepository;
        this.generatorService = generatorService;
    }

    public ResponseLinkDto getShortValue(RequestLinkDto requestLinkDto) {
        Optional<LinkInfo> optionalLink =
                linkRepository.findByOriginalValue(requestLinkDto.getOriginalValue());
        if (optionalLink.isPresent()) {
            LinkInfo foundLink = optionalLink.get();
            updateLastUsageDate(foundLink.getId());
            return convertToResponseDto(foundLink.getShortValue());
        } else {
            LinkInfo savedEntity = saveLinkInfo(requestLinkDto);
            return convertToResponseDto(savedEntity.getShortValue());
        }
    }

    public ResponseLinkDto getLongLinkByShortValue(String originalValue) {
        if (originalValue == null || originalValue.isBlank())
            throw new IllegalArgumentException("parameter of original link value is not present");

        Optional<LinkInfo> optionalOriginalValue = linkRepository.findByShortValue(originalValue);
        LinkInfo linkInfo = optionalOriginalValue.orElseThrow(EntityNotFoundException::new);
        return convertToResponseDto(linkInfo.getOriginalValue());

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

    private int updateLastUsageDate(Long id) {
        return linkRepository.updateLastUsage(id);
    }

    public ResponseLinkDto convertToResponseDto(String linkValue) {
        return new ResponseLinkDto(linkValue);
    }

}
