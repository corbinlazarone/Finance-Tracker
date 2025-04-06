package com.fintrackerapi.fintracker.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.UUID;

public class BudgetDto {
    private final UUID id;
    private String name;
    private YearMonth budgetMonth;
    private BigDecimal firstBiWeeklyAmount;
    private BigDecimal secondBiWeeklyAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive;

    public BudgetDto(UUID id, String name, YearMonth budgetMonth,
                     BigDecimal firstBiWeeklyAmount, BigDecimal secondBiWeeklyAmount,
                     LocalDate startDate, LocalDate endDate, boolean isActive) {
        this.id = id;
        this.name = name;
        this.budgetMonth = budgetMonth;
        this.firstBiWeeklyAmount = firstBiWeeklyAmount;
        this.secondBiWeeklyAmount = secondBiWeeklyAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
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
}
