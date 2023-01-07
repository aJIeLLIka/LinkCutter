package com.anck.dto;

public class RequestLinkDto {

    private String originalValue;

    public RequestLinkDto() {
    }

    public RequestLinkDto(String originalValue) {
        this.originalValue = originalValue;
    }

    public String getOriginalValue() {
        return originalValue;
    }
    public void setOriginalValue(String originalValue) {
        this.originalValue = originalValue;
    }
}
