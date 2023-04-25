package com.anck.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeneratorService {

    @Value("${app.shortValueLength}")
    public int shortValueLength;


    public String generateShortValue() {
        String shortValue = RandomStringUtils.randomAlphanumeric(shortValueLength);
        System.out.println("shortValue = " + shortValue);
        return shortValue;
    }

}