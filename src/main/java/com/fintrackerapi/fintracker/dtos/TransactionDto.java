package com.fintrackerapi.fintracker.dtos;

import com.fintrackerapi.fintracker.enums.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

public class TransactionDto {
    private String name;
    private BigDecimal amount;
    private String memo;
    private TransactionType type;

    public TransactionDto(String name, BigDecimal amount, String memo, TransactionType type) {
        this.name = name;
        this.amount = amount;
        this.memo = memo;
        this.type = type;
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
