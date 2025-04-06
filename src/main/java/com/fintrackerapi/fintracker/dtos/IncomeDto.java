package com.fintrackerapi.fintracker.dtos;

import java.math.BigDecimal;

public class IncomeDto {
    private String name;
    private BigDecimal amount;
    private boolean isBiweekly;
    private Integer paymentDateOne;
    private Integer paymentDateTwo;

    public IncomeDto(String name, BigDecimal amount, boolean isBiweekly, Integer paymentDateOne, Integer paymentDateTwo) {
        this.name = name;
        this.amount = amount;
        this.isBiweekly = isBiweekly;
        this.paymentDateOne = paymentDateOne;
        this.paymentDateTwo = paymentDateTwo;
    }

    // getters and setters
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
