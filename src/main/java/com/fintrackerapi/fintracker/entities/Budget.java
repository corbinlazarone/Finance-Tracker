package com.fintrackerapi.fintracker.entities;

import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.UUID;

@Table(name = "Budget")
@Entity
public class Budget {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private YearMonth budgetMonth;

    // Bi-weekly amounts (can be different)
    @Column(nullable = false)
    private BigDecimal firstBiWeeklyAmount;

    @Column(nullable = false)
    private BigDecimal secondBiWeeklyAmount;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private boolean isActive;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    // getters and setters
    // Getters and setters
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

    // Utility method to calculate the total monthly amount (not stored in database)
    @Transient
    public BigDecimal getMonthlyAmount() {
        if (firstBiWeeklyAmount == null && secondBiWeeklyAmount == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal first = (firstBiWeeklyAmount != null) ? firstBiWeeklyAmount : BigDecimal.ZERO;
        BigDecimal second = (secondBiWeeklyAmount != null) ? secondBiWeeklyAmount : BigDecimal.ZERO;

        return first.add(second);
    }
}
