package com.anck.controllers;

import com.anck.dto.RequestLinkDto;
import com.anck.entity.LinkInfo;
import com.anck.repositories.LinkRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class LinkControllerWithRepositoryMockTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LinkRepository linkRepositoryMock;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void getOriginalValue() throws Exception {
        LinkInfo presentLink = new LinkInfo("origValue", "shortValue");
        when(linkRepositoryMock.findByShortValue("exist"))
                .thenReturn(Optional.of(presentLink));

        this.mockMvc.perform(get("/getOriginalLink?shortValue=exist"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalValue").value("origValue"))
                .andExpect(jsonPath("$.shortValue").value("shortValue"));
    }

    @Test
    void getOriginalValueShouldThrowIllegalArgumentException() throws Exception {
        this.mockMvc.perform(get("/getOriginalLink?shortValue= ")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("parameter of short value is not present",
                        result.getResolvedException().getMessage()));
    }

    @Test
    void getOriginalValueShouldThrowEntityNotFoundException() throws Exception {
        when(linkRepositoryMock.findByShortValue("nonExist"))
                .thenReturn(Optional.empty());
        this.mockMvc.perform(get("/getOriginalLink?shortValue=nonExist")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("Link with this value doesn't exist",
                        result.getResolvedException().getMessage()));
    }

    @Test
    void createShortValueWithExistOrigValue() throws Exception {
        LinkInfo presentLink = new LinkInfo("existOrigValue", "oldShortValue");
        when(linkRepositoryMock.findByOriginalValue("existOrigValue"))
                .thenReturn(Optional.of(presentLink));

        String existOrigValueDto = objectMapper.writeValueAsString(new RequestLinkDto("existOrigValue"));
        this.mockMvc.perform(post("/createShortLink")
                        .content(existOrigValueDto)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalValue").value("existOrigValue"))
                .andExpect(jsonPath("$.shortValue").value("oldShortValue"));
    }

    @Test
    void createShortValueWithNonExistOrigValue() throws Exception {
        when(linkRepositoryMock.findByOriginalValue("nonExistOrigValue"))
                .thenReturn(Optional.empty());

        LinkInfo newLink = new LinkInfo("nonExistOrigValue", "newShortValue");
        when(linkRepositoryMock.save(any()))
                .thenReturn(newLink);

        String nonExistOrigValueDto = objectMapper.writeValueAsString(new RequestLinkDto("nonExistOrigValue"));
        this.mockMvc.perform(post("/createShortLink")
                        .content(nonExistOrigValueDto)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalValue").value("nonExistOrigValue"))
                .andExpect(jsonPath("$.shortValue").value("newShortValue"));
    }
}