package com.anck.DTO;

public class ResponseLinkDto {

    private Long id;

    private String originalValue;
    private String shortValue;

    public ResponseLinkDto() {
    }

    public ResponseLinkDto(Long id, String originalValue, String shortValue) {
        this.id = id;
        this.originalValue = originalValue;
        this.shortValue = shortValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
