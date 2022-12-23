package com.anck.dto;

public class ResponseLinkDto {

    private String shortValue;

    public ResponseLinkDto() {
    }

    public ResponseLinkDto(String shortValue) {
        this.shortValue = shortValue;
    }

    public String getShortValue() {
        return shortValue;
    }

    public void setShortValue(String shortValue) {
        this.shortValue = shortValue;
    }
}
