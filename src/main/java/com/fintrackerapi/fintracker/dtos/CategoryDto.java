package com.fintrackerapi.fintracker.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CategoryDto {
    private String name;
    private String description;
    private BigDecimal amountAllocated;
    private LocalDate dueDate;

    public CategoryDto() {}

    public CategoryDto(String name, String description, BigDecimal amountAllocated, LocalDate dueDate) {
        this.name = name;
        this.description = description;
        this.amountAllocated = amountAllocated;
        this.dueDate = dueDate;
    }

    // getters and setters
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
