package com.fintrackerapi.fintracker.responses;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class IncomeResponse {
    private UUID id;
    private String name;
    private BigDecimal amount;
    private boolean isBiweekly;
    private Integer paymentDateOne;
    private Integer paymentDateTwo;
    private final Date createdAt;
    private final Date updatedAt;

    public IncomeResponse(UUID id, String name, BigDecimal amount, boolean isBiweekly,
                          Integer paymentDateOne, Integer paymentDateTwo,
                          Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.isBiweekly = isBiweekly;
        this.paymentDateOne = paymentDateOne;
        this.paymentDateTwo = paymentDateTwo;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
