package com.anck.services;

import com.anck.dto.RequestLinkDto;
import com.anck.dto.ResponseLinkDto;
import com.anck.entity.LinkInfo;
import com.anck.repositories.LinkRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class LinkServiceTest {

    private final LinkRepository linkRepositoryMock = mock(LinkRepository.class);
    private final GeneratorService generatorService = new GeneratorService();
    private final LinkService linkService = new LinkService(linkRepositoryMock, generatorService);


    @Test
    @DisplayName("тест для корректного ответа, существующей ссылки в бд")
    void getShortValueWithPresentLink() {
        LinkInfo presentLink = new LinkInfo("origValue", "shortValue");
        when(linkRepositoryMock.findByOriginalValue("present"))
                .thenReturn(Optional.of(presentLink));
        when(linkRepositoryMock.updateLastUsage(any()))
                .thenReturn(1);

        ResponseLinkDto actualDto = linkService.getShortValue(new RequestLinkDto("present"));
        assertEquals("origValue", actualDto.getOriginalValue());
        assertEquals("shortValue", actualDto.getShortValue());
    }

    @Test
    @DisplayName("тест для корректного ответа и не существующей ссылки в бд")
    void getShortValueWithOutPresentLink() {
        LinkInfo unPresentLink = new LinkInfo("origValue", "shortValue");
        when(linkRepositoryMock.findByOriginalValue("unPresent"))
                .thenReturn(Optional.empty());
        when(linkRepositoryMock.save(any()))
                .thenReturn(unPresentLink);

        ResponseLinkDto actlDto = linkService.getShortValue(new RequestLinkDto("unPresent"));
        assertEquals("origValue", actlDto.getOriginalValue());
        assertEquals("shortValue", actlDto.getShortValue());
    }

    @Test
    @DisplayName("корректный ответ при получении длинной ссылки")
    void getLongLinkByShortValue() {
        String valid = "valid";
        LinkInfo linkInfo = new LinkInfo();
        linkInfo.setOriginalValue(valid);
        linkInfo.setShortValue(valid);

        when(linkRepositoryMock.findByShortValue(eq(valid)))
                .thenReturn(Optional.of(linkInfo));
        ResponseLinkDto actualDto = linkService.getLongLinkByShortValue(valid);
        assertEquals("valid", actualDto.getOriginalValue());
        assertEquals("valid", actualDto.getShortValue());
    }

    @Test
    @DisplayName("Проброс ошибок при получении длинной ссылки")
    void getLongLinkByShortValueExceptions() {
        assertThrows(IllegalArgumentException.class, () -> linkService.getLongLinkByShortValue(""));
        assertThrows(IllegalArgumentException.class, () -> linkService.getLongLinkByShortValue(null));

        String inValid = "nonExistShortValue";
        when(linkRepositoryMock.findByShortValue(eq(inValid)))
                .thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> linkService.getLongLinkByShortValue(inValid));
    }
}