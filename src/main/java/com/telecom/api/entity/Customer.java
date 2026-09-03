package com.telecom.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String mobileNumber;

    private String plan;

    private double dataBalance;

    private double talktimeBalance;

    public Customer() {
    }

    public Customer(String name,
                    String mobileNumber,
                    String plan,
                    double dataBalance,
                    double talktimeBalance) {

        this.name = name;
        this.mobileNumber = mobileNumber;
        this.plan = plan;
        this.dataBalance = dataBalance;
        this.talktimeBalance = talktimeBalance;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getPlan() {
        return plan;
    }

    public double getDataBalance() {
        return dataBalance;
    }

    public double getTalktimeBalance() {
        return talktimeBalance;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public void setDataBalance(double dataBalance) {
        this.dataBalance = dataBalance;
    }

    public void setTalktimeBalance(double talktimeBalance) {
        this.talktimeBalance = talktimeBalance;
    }
}
