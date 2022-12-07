package com.anck.models;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Links")
public class Link {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue()
    private Long id;

    @Column(name = "original_value", nullable = false, unique = true)
    private String originalValue;
    @Column(name = "short_value", unique = true)
    private String shortValue;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "last_usage_date")
    private LocalDateTime lastUsageDate;

    public Link() {
    }
    public Link(String originalValue) {
        this.originalValue = originalValue;
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
