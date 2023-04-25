package com.anck.entity;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LinkInfo", indexes = {
        @Index(name = "original_value_index", columnList = "original_value"),
        @Index(name = "short_value_index", columnList = "short_value")})

public class LinkInfo {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "original_value", nullable = false, unique = true)
    private String originalValue;
    @Column(name = "short_value", unique = true)
    private String shortValue;
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;
    @Column(name = "last_usage_date", nullable = false)
    private LocalDateTime lastUsageDate;

    public LinkInfo() {
    }
    public LinkInfo(String originalValue) {
        this.originalValue = originalValue;
    }
    public LinkInfo(String originalValue, String shortValue) {
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
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    public LocalDateTime getLastUsageDate() {
        return lastUsageDate;
    }
    public void setLastUsageDate(LocalDateTime lastUsageDate) {
        this.lastUsageDate = lastUsageDate;
    }
}
