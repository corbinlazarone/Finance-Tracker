package com.fintrackerapi.fintracker.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public class CategoryDto {
    private final UUID id;
    private String name;
    private String description;
    private BigDecimal amountAllocated;

    public CategoryDto(UUID id, String name, String description, BigDecimal amountAllocated) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amountAllocated = amountAllocated;
    }

    // getters and setters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmountAllocated() {
        return amountAllocated;
    }

    public void setAmountAllocated(BigDecimal amountAllocated) {
        this.amountAllocated = amountAllocated;
    }
}
