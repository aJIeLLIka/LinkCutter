package com.anck.models;

import javax.persistence.*;

@Entity
@Table(name = "Links")
public class Link {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue()
    private Long id;
    @Column(name = "original_value")
    private  String originalValue;
    @Column(name = "short_value")
    private  String shortValue;

    public Link() {
    }

    public Link(String shortValue) {
        this.shortValue = shortValue;
    }

    public Long getId() {
        return id;
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
