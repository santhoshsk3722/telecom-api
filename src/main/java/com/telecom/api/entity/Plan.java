package com.telecom.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "plans")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String planName;

    private double price;

    private double dataPerDay;

    private int validityDays;

    private String description;

    public Plan() {
    }

    public Plan(String planName,
                double price,
                double dataPerDay,
                int validityDays,
                String description) {

        this.planName = planName;
        this.price = price;
        this.dataPerDay = dataPerDay;
        this.validityDays = validityDays;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getPlanName() {
        return planName;
    }

    public double getPrice() {
        return price;
    }

    public double getDataPerDay() {
        return dataPerDay;
    }

    public int getValidityDays() {
        return validityDays;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDataPerDay(double dataPerDay) {
        this.dataPerDay = dataPerDay;
    }

    public void setValidityDays(int validityDays) {
        this.validityDays = validityDays;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}