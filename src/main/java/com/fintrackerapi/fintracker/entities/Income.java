package com.fintrackerapi.fintracker.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Table(name = "IncomeSources")
@Entity
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(nullable = false)
    private boolean isBiweekly;

    @Column(nullable = false)
    private Integer PaymentDateOne;

    @Column(nullable = false)
    private Integer PaymentDateTwo;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updated_at;

    // getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public boolean getIsBiWeekly() {
        return isBiweekly;
    }

    public void setIsBiweekly(boolean biweekly) {
        isBiweekly = biweekly;
    }

    public Integer getPaymentDateOne() {
        return PaymentDateOne;
    }

    public void setPaymentDateOne(Integer paymentDateOne) {
        PaymentDateOne = paymentDateOne;
    }

    public Integer getPaymentDateTwo() {
        return PaymentDateTwo;
    }

    public void setPaymentDateTwo(Integer paymentDateTwo) {
        PaymentDateTwo = paymentDateTwo;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}