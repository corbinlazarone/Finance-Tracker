package com.fintrackerapi.fintracker.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.util.UUID;

public class IncomeDto {
    private UUID userId;
    private String name;
    private BigDecimal amount;
    private boolean isBiweekly;

    @Min(value = 1, message = "Payment date must be at least 1")
    @Max(value = 31, message = "Payment date must not exceed 31")
    private Integer paymentDateOne;

    @Min(value = 1, message = "Payment date must be at least 1")
    @Max(value = 31, message = "Payment date must not exceed 31")
    private Integer paymentDateTwo;

    public IncomeDto(UUID userId, String name, BigDecimal amount, boolean isBiweekly, Integer paymentDateOne, Integer paymentDateTwo) {
        this.userId = userId;
        this.name = name;
        this.amount = amount;
        this.isBiweekly = isBiweekly;
        this.paymentDateOne = paymentDateOne;
        this.paymentDateTwo = paymentDateTwo;
    }

    // getters and setters
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean isBiweekly() {
        return isBiweekly;
    }

    public Integer getPaymentDateOne() {
        return paymentDateOne;
    }

    public Integer getPaymentDateTwo() {
        return paymentDateTwo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setBiweekly(boolean biweekly) {
        isBiweekly = biweekly;
    }

    public void setPaymentDateOne(Integer paymentDateOne) {
        this.paymentDateOne = paymentDateOne;
    }

    public void setPaymentDateTwo(Integer paymentDateTwo) {
        this.paymentDateTwo = paymentDateTwo;
    }
}
