package com.fintrackerapi.fintracker.responses;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.UUID;

public class BudgetResponse {
    private UUID id;
    private String name;
    private YearMonth budgetMonth;
    private BigDecimal firstBiWeeklyAmount;
    private BigDecimal secondBiWeeklyAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive;
    private Date createdAt;
    private Date updatedAt;

    public BudgetResponse() {}

    public BudgetResponse(UUID id, String name, YearMonth budgetMonth,
                          BigDecimal firstBiWeeklyAmount, BigDecimal secondBiWeeklyAmount,
                          LocalDate startDate, LocalDate endDate, boolean isActive, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.budgetMonth = budgetMonth;
        this.firstBiWeeklyAmount = firstBiWeeklyAmount;
        this.secondBiWeeklyAmount = secondBiWeeklyAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public YearMonth getBudgetMonth() {
        return budgetMonth;
    }

    public void setBudgetMonth(YearMonth budgetMonth) {
        this.budgetMonth = budgetMonth;
    }

    public BigDecimal getFirstBiWeeklyAmount() {
        return firstBiWeeklyAmount;
    }

    public void setFirstBiWeeklyAmount(BigDecimal firstBiWeeklyAmount) {
        this.firstBiWeeklyAmount = firstBiWeeklyAmount;
    }

    public BigDecimal getSecondBiWeeklyAmount() {
        return secondBiWeeklyAmount;
    }

    public void setSecondBiWeeklyAmount(BigDecimal secondBiWeeklyAmount) {
        this.secondBiWeeklyAmount = secondBiWeeklyAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
