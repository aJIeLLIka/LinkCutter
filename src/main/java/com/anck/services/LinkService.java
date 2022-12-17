package com.anck.services;

import com.anck.dto.RequestLinkDto;
import com.anck.dto.ResponseLinkDto;
import com.anck.entity.Link;
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

    //    @Value("${random.generated.string.length}")
//    private int generatedStringLength;
    @Autowired
    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Transactional
    public Link save(RequestLinkDto requestLinkDto) {
        Link link = new Link();
        link.setOriginalValue(requestLinkDto.getOriginalValue());
        link.setCreationDate(LocalDateTime.now());
        link.setLastUsageDate(LocalDateTime.now());
        link.setShortValue(generateShortValue());
        return linkRepository.save(link);
    }

    public ResponseLinkDto getShortValue(RequestLinkDto requestLinkDto) {
        Optional<Link> optionalLink =
                linkRepository.findByOriginalValue(requestLinkDto.getOriginalValue());
        if (optionalLink.isPresent()) {
            Link foundLink = optionalLink.get();
            updateLastUsageDate(foundLink.getId());
            return convertToResponseDto(foundLink);
        } else {
            return convertToResponseDto(save(requestLinkDto));
        }
    }

    private int updateLastUsageDate(Long id) {
        return linkRepository.updateLastUsage(id);
    }

    private String generateShortValue() {
        String shortValue = RandomStringUtils.randomAlphanumeric(10);
        System.out.println("shortValue = " + shortValue);
        return shortValue;
    }

    public Link convertToLink(RequestLinkDto requestLinkDto) {
        return new Link(requestLinkDto.getOriginalValue());
    }

    public ResponseLinkDto convertToResponseDto(Link link) {
        return new ResponseLinkDto(link.getShortValue());
    }

}
