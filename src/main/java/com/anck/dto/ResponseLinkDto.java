package com.anck.dto;

public class ResponseLinkDto {

    private String originalValue;
    private String shortValue;

    public ResponseLinkDto() {
    }

    public ResponseLinkDto(String originalValue, String shortValue) {
        this.originalValue = originalValue;
        this.shortValue = shortValue;
    }

    public String getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(String originalValue) {
        this.originalValue = originalValue;
    }

    public String getShortValue() {
        return shortValue;
    }

    public void setShortValue(String shortValue) {
        this.shortValue = shortValue;
    }
}
