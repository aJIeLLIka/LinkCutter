package com.anck.dto;

public class RequestLinkDto {

    private final String originalValue;

    public RequestLinkDto(String originalValue) {
        this.originalValue = originalValue;
    }

    public String getOriginalValue() {
        return originalValue;
    }

}
