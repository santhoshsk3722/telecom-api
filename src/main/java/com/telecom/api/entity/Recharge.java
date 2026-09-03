package com.telecom.api.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "recharges")
public class Recharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mobileNumber;

    private double amount;

    private String status;

    private LocalDateTime rechargeDate;

    public Recharge() {
    }

    public Recharge(String mobileNumber,
                    double amount,
                    String status,
                    LocalDateTime rechargeDate) {

        this.mobileNumber = mobileNumber;
        this.amount = amount;
        this.status = status;
        this.rechargeDate = rechargeDate;
    }

    public Long getId() {
        return id;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getRechargeDate() {
        return rechargeDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRechargeDate(LocalDateTime rechargeDate) {
        this.rechargeDate = rechargeDate;
    }
}