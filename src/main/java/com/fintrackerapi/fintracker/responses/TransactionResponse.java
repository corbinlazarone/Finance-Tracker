package com.fintrackerapi.fintracker.responses;

import com.fintrackerapi.fintracker.enums.TransactionType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class TransactionResponse {
    private UUID id;
    private String name;
    private BigDecimal amount;
    private String memo;
    private TransactionType type;
    private Date createdAt;
    private Date updatedAt;

    public TransactionResponse() {}

    public TransactionResponse(UUID id, String name, BigDecimal amount, String memo, TransactionType type, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.memo = memo;
        this.type = type;
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
