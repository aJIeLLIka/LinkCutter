package com.anck.controllers;

import com.anck.dto.RequestLinkDto;
import com.anck.dto.ResponseLinkDto;
import com.anck.entity.LinkInfo;
import com.anck.initializer.Postgres;
import com.anck.repositories.LinkRepository;

import com.anck.services.LinkService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(initializers = Postgres.Initializer.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class LinkControllerWithPostgresContainerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void init(){
        Postgres.container.start();
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void createShortValue() throws Exception {
        String requestDto = objectMapper.writeValueAsString(new RequestLinkDto("newOrigValue"));

        ResultActions perform = this.mockMvc.perform(post("/createShortLink")
                .content(requestDto)
                .contentType(MediaType.APPLICATION_JSON)
        );

        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalValue").value("newOrigValue"))
                .andExpect(jsonPath("$.shortValue").isNotEmpty()
                );
    }

    @Test
    void complexTestSuccessGetOriginalValue() throws Exception {
        String newOrigValueDto = objectMapper.writeValueAsString(new RequestLinkDto("origValue"));
        ResultActions postCreateShortLink = this.mockMvc.perform(post("/createShortLink")
                .content(newOrigValueDto)
                .contentType(MediaType.APPLICATION_JSON)
        );

        ResponseLinkDto responseCreatedLink = objectMapper.readValue(
                postCreateShortLink.andReturn().getResponse().getContentAsString(), ResponseLinkDto.class);

        mockMvc.perform(get("/getOriginalLink?shortValue=" + responseCreatedLink.getShortValue()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalValue").value("origValue"))
                .andExpect(jsonPath("$.shortValue").value(responseCreatedLink.getShortValue()));
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
        this.mockMvc.perform(get("/getOriginalLink?shortValue=nonExist")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("Link with this value doesn't exist",
                        result.getResolvedException().getMessage()));
    }
}
